package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.rpovzi.tables.pojos.User;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping(value = "/list")
    public List<User> getList(
            @RequestParam(value = "/page", defaultValue = "1") Integer page,
            @RequestParam(value = "/pageSize", defaultValue = "10") Integer pageSize) {
        return userService.getList(page, pageSize);
    }

    @GetMapping(value = "/{id}")
    public User get(
            @PathVariable Long id) {
        return userService.get(id);
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping
    public User update(@RequestBody User user) {
        return userService.update(user);
    }

    @DeleteMapping(value = "/{id}")
    public void create(@PathVariable Long id) {
        userService.delete(id);
    }
}
