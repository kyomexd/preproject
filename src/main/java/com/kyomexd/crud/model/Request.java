package com.kyomexd.crud.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="requests")
@RequiredArgsConstructor
@Getter
@Setter
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column
    @NotNull
    String username;
    @Column
    String message;
    @Column
    boolean resolved;
    @Transient
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

}
