package org.example.dto;

import lombok.Data;

@Data
public class UserContextDto {

    private String login;
    private Boolean isAdmin;
}