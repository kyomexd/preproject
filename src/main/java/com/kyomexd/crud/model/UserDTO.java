package com.kyomexd.crud.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    private int id;
    private String name;
    private String email;
    private String password;
    private String age;
    private boolean hasAdmin;
    private boolean hasUser;
    private String city;
}
