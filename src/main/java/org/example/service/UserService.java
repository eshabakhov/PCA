package org.example.service;

import lombok.AllArgsConstructor;
import org.example.repository.UserRepository;
import org.example.rpovzi.tables.daos.UserDao;
import org.example.rpovzi.tables.pojos.User;
import org.jooq.Condition;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;

import static org.jooq.impl.DSL.trueCondition;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository UserRepository;

    private final UserDao UserDao;

    private final PasswordEncoder passwordEncoder;

    public List<User> getList(Integer page, Integer pageSize) {

        Condition condition = trueCondition();

        return UserRepository.fetch(condition, page, pageSize);
    }

    public User create(User user) throws ValidationException {
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        if (!user.getPasswordHash().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$")){
            throw new ValidationException("Пароль слишком простой.");
        }
        UserDao.insert(user);
        return user;
    }

    public User update(User user){
        UserDao.update(user);
        return user;
    }

    public void delete(Long id){
        UserDao.deleteById(id);
    }

    public User get(Long id) {
        return UserDao.findById(id);
    }

}
