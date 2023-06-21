package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dto.UserContextDto;
import org.example.dto.ResponseList;
import org.example.dto.UserDto;
import org.example.exception.ValidationException;
import org.example.repository.UserRepository;
import org.example.rpovzi.tables.daos.UserDao;
import org.example.rpovzi.tables.pojos.User;
import org.jooq.Condition;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static org.jooq.impl.DSL.trueCondition;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    public ResponseList<UserDto> getList(Integer page, Integer pageSize) {
        ResponseList<UserDto> responseList = new ResponseList<>();
        Condition condition = trueCondition();

        List<UserDto> list = userRepository.fetchDtos(condition, page, pageSize);
        responseList.setList(list);
        responseList.setTotal(userRepository.count(condition));
        responseList.setCurrentPage(page);
        responseList.setPageSize(pageSize);
        return responseList;
    }

    public User create(User user) throws ValidationException {
        if (!user.getPasswordHash().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$")) {
            throw new ValidationException("Пароль слишком простой.");
        }
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        userDao.insert(user);
        return user;
    }

    public User update(User user) {
        // todo nullpointer

        if (Objects.isNull(user.getPasswordHash())) {
            User old = userDao.findById(user.getId());
            user.setPasswordHash(old.getPasswordHash());
        }
        else {
            user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        }

        userDao.update(user);
        return user;
    }

    public void delete(Long id) {
        userDao.deleteById(id);
    }

    public User get(Long id) {
        // todo nullpointer
        User user = userDao.findById(id);
        user.setPasswordHash(null);
        return user;

    }

    public User getByUsername(String username) {
        return userDao.fetchByLogin(username).stream().findFirst().orElse(null);
    }

    public UserContextDto getContext(String name) {
        User user = userDao.fetchByLogin(name).get(0);
        UserContextDto userContextDto = new UserContextDto();
        userContextDto.setLogin(name);
        userContextDto.setIsAdmin(user.getIsAdmin());
        return userContextDto;
    }
}
