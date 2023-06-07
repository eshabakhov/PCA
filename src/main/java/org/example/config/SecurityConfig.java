package org.example.config;

import org.apache.commons.lang3.tuple.Pair;
import org.example.repository.UserRepository;
import org.example.rpovzi.tables.pojos.Audit;
import org.example.rpovzi.tables.pojos.User;
import org.example.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditService auditService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        // Разрешаем читать доки без авторизации
        web.ignoring().antMatchers("/swagger-ui/**", "/api-docs/**", "/actuator/**");
    }

    private Map<String, Pair<Integer, LocalDateTime>> userAttemptsMap = new HashMap<>();

    @Value("${auth.attempts.reset.hours}")
    private Integer attemptFailuresResetHours;

    @Value("${auth.attempts.number}")
    private Integer attemptsNumber;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()

                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()

                .authorizeRequests()
                // Разрешаем изменение данных только админам
                .antMatchers(HttpMethod.POST, "/abonents").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/abonents").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/abonents").hasRole("ADMIN")

                .antMatchers(HttpMethod.POST, "/calls").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/calls").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/calls").hasRole("ADMIN")

                .antMatchers(HttpMethod.POST, "/cities").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/cities").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/cities").hasRole("ADMIN")

                .antMatchers("/user").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()

                .formLogin()
                .loginProcessingUrl("/login")

                .failureHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    String username = request.getParameter("username");
                    registerAttempt(username);
                })

                .successHandler((request, response, authentication) -> {
                    String login = authentication.getName();
                    auditService.create(new Audit(null, login, "/login", "POST", LocalDateTime.now()));
                    User user = userRepository.fetchActual(login);
                    response.setContentType("application/json");
                    // Отдаём, чтобы менять интерфейс на фронте
                    response.getWriter().write(String.format("{\"isAdmin\": \"%s\"}", user.getIsAdmin()));
                    response.setStatus(HttpServletResponse.SC_OK);
                })

                .permitAll()
                .and()

                .logout()
                .logoutSuccessHandler((request, response, authentication) -> {
                    auditService.create(new Audit(null, authentication.getName(), "/logout", "POST", LocalDateTime.now()));
                    response.setStatus(HttpServletResponse.SC_OK);
                });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select login, password_hash, TRUE from phone_calls.user " +
                        "where login=?")
                .authoritiesByUsernameQuery("select login, case when is_admin is true then 'ROLE_ADMIN' else 'ROLE_USER' end from phone_calls.user " +
                        "where login=?");
    }

    private void registerAttempt(String username) {
        auditService.create(new Audit(null, username, "/login", "POST", LocalDateTime.now()));
        if (userAttemptsMap.containsKey(username)) {
            int attempts = userAttemptsMap.get(username).getLeft();
            userAttemptsMap.put(username, Pair.of(attempts + 1, LocalDateTime.now()));
        } else {
            userAttemptsMap.put(username, Pair.of(1, LocalDateTime.now()));
        }
    }

}
