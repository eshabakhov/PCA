package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.UserContextDto;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "/context")
@AllArgsConstructor
public class ContextController {

    private final UserService userService;

    @GetMapping
    public UserContextDto getContext(Principal principal){
        return userService.getContext(principal.getName());
    }

}
