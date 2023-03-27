package com.kyomexd.crud.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class Captcha {

    private String id;
    private String value;
    private String comment;
}
