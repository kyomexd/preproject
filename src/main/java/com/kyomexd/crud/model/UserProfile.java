package com.kyomexd.crud.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@RequiredArgsConstructor
@Getter
@Setter
public class UserProfile {
    private int id;
    private String name;
    private String email;
    private String password;
    private String age;
    private boolean hasAdmin;
    private boolean hasUser;
    private String city;
}
