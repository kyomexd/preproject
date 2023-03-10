package com.kyomexd.crud.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
@RequiredArgsConstructor
@Getter
@Setter
public class Role implements GrantedAuthority {
    @Id
    private Long id;
    @Column
    private String role;

    public Role(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public String toString() {
        String roleName = role.replace("ROLE_", "");
        return roleName;
    }
}
