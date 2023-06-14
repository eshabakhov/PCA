package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.rpovzi.tables.pojos.User;
import org.example.service.AuditService;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.security.Principal;
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
        auditService.audit(principal, "/users/list", "GET");
        return userService.getList(page, pageSize);
    }

    @GetMapping(value = "/{id}")
    public User get(
            Principal principal,
            @PathVariable Long id) {
        auditService.audit(principal, "/users/{id}", "GET");
        return userService.get(id);
    }

    @PostMapping
    public User create(Principal principal, @RequestBody User user) throws ValidationException {
        auditService.audit(principal, "/users/", "POST");
        return userService.create(user);
    }

    @PutMapping
    public User update(Principal principal, @RequestBody User user) {
        auditService.audit(principal, "/users/", "PUT");
        return userService.update(user);
    }

    @DeleteMapping(value = "/{id}")
    public void create(Principal principal, @PathVariable Long id) {
        auditService.audit(principal, "/users/{id}", "DELETE");
        userService.delete(id);
    }
}
