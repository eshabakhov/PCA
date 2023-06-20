package org.example.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String login;
    private String name;
    private String surname;
    private String patronymic;
    private Boolean isAdmin;
}
