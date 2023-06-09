package org.example.config;

import org.example.repository.UserRepository;
import org.example.rpovzi.tables.pojos.Audit;
import org.example.rpovzi.tables.pojos.User;
import org.example.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.time.LocalDateTime;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;

    private final DataSource dataSource;

    private final UserAttempts userAttempts;

    private final AuditService auditService;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public SecurityConfig(UserRepository userRepository,
                          DataSource dataSource,
                          UserAttempts userAttempts,
                          AuditService auditService,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.dataSource = dataSource;
        this.userAttempts = userAttempts;
        this.auditService = auditService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void configure(WebSecurity web) {
        // Разрешаем читать доки без авторизации
        web.ignoring().antMatchers("/swagger-ui/**", "/api-docs/**", "/actuator/**");
    }

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

                .antMatchers(HttpMethod.GET, "/audit/*").hasRole("ADMIN")

                .antMatchers("/user").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()

                .formLogin()
                .loginProcessingUrl("/login")

                .failureHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    String username = request.getParameter("username");

                    String message = "Неверные логин или пароль.";

                    if (authentication.getClass().isAssignableFrom(DisabledException.class)) {
                        message = String.format("Учётная запись заблокирована после %d неудачных попыток.", attemptsNumber);
                    }

                    String json = String.format("{\"message\": \"%s\"}", message);

                    response.getWriter().println(json);
                    registerAttempt(username);
                })

                .successHandler((request, response, authentication) -> {
                    String login = authentication.getName();
                    auditService.create(new Audit(null, login, "/login", "POST", LocalDateTime.now()));
                    User user = userRepository.fetchActual(login);

                    userAttempts.getUserAttemptsMap().remove(login);

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
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery("select login, password_hash, is_locked is not TRUE from phone_calls.user " +
                        "where login=?")
                .authoritiesByUsernameQuery("select login, case when is_admin is true then 'ROLE_ADMIN' else 'ROLE_USER' end from phone_calls.user " +
                        "where login=?");
    }

    private void registerAttempt(String username) {
        auditService.create(new Audit(null, username, "/login", "POST", LocalDateTime.now()));
        if (userAttempts.getUserAttemptsMap().containsKey(username)) {
            int attempts = userAttempts.getUserAttemptsMap().get(username);
            if (attempts > attemptsNumber - 1) {
                userRepository.setLock(username, LocalDateTime.now());
            }
            userAttempts.getUserAttemptsMap().put(username, attempts + 1);
        } else {
            userAttempts.getUserAttemptsMap().put(username, 1);
        }
    }

}
