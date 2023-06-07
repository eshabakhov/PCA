package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.rpovzi.tables.pojos.Audit;
import org.example.rpovzi.tables.pojos.User;
import org.example.service.AuditService;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    private final AuditService auditService;


    @GetMapping(value = "/list")
    public List<User> getList(
            Principal principal,
            @RequestParam(value = "/page", defaultValue = "1") Integer page,
            @RequestParam(value = "/pageSize", defaultValue = "10") Integer pageSize) {
        auditService.create(new Audit(null, principal.getName(), "/users/list", "GET", LocalDateTime.now()));
        return userService.getList(page, pageSize);
    }

    @GetMapping(value = "/{id}")
    public User get(
            Principal principal,
            @PathVariable Long id) {
        auditService.create(new Audit(null, principal.getName(), "/users/{id}", "GET", LocalDateTime.now()));
        return userService.get(id);
    }

    @PostMapping
    public User create(Principal principal, @RequestBody User user) {
        auditService.create(new Audit(null, principal.getName(), "/users/", "POST", LocalDateTime.now()));
        return userService.create(user);
    }

    @PutMapping
    public User update(Principal principal, @RequestBody User user) {
        auditService.create(new Audit(null, principal.getName(), "/users/", "PUT", LocalDateTime.now()));
        return userService.update(user);
    }

    @DeleteMapping(value = "/{id}")
    public void create(Principal principal, @PathVariable Long id) {
        auditService.create(new Audit(null, principal.getName(), "/users/{id}", "DELETE", LocalDateTime.now()));
        userService.delete(id);
    }
}
