package org.example.service;

import lombok.AllArgsConstructor;
import org.example.repository.UserRepository;
import org.example.rpovzi.tables.daos.UserDao;
import org.example.rpovzi.tables.pojos.User;
import org.jooq.Condition;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.jooq.impl.DSL.trueCondition;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    public List<User> getList(Integer page, Integer pageSize) {

        Condition condition = trueCondition();

        return userRepository.fetch(condition, page, pageSize);
    }

    public User create(User user){
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        userDao.insert(user);
        return user;
    }

    public User update(User user){
        userDao.update(user);
        return user;
    }

    public void delete(Long id){
        userDao.deleteById(id);
    }

    public User get(Long id) {
        return userDao.findById(id);
    }

}
