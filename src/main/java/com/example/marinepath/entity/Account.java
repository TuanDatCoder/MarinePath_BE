package com.example.marinepath.entity;

import com.example.marinepath.entity.Enum.Account.AccountGenderEnum;
import com.example.marinepath.entity.Enum.Account.AccountProviderEnum;
import com.example.marinepath.entity.Enum.Account.AccountRoleEnum;
import com.example.marinepath.entity.Enum.Account.AccountStatusEnum;
import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Email
    @Column(name = "email",unique = true, nullable = false)
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender",nullable = true)
    private AccountGenderEnum gender;

    @Column(name = "picture")
    private String picture;

    @Column(name = "google_id", unique = true)
    private String googleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role",nullable = false)
    private AccountRoleEnum role;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider",nullable = false)
    private AccountProviderEnum provider;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private AccountStatusEnum status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_deleted",nullable = false)
    private Boolean isDeleted;

@Transient
@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
}
    @Transient
    @Override
    public String getUsername() {
        return this.email;
    }

    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return this.status != AccountStatusEnum.BLOCKED;
    }

    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isEnabled() {
        return this.status == AccountStatusEnum.VERIFIED;
    }
}
