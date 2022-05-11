package com.mikle.mikle.models;

import com.mikle.mikle.models.enums.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // аннотация генерации уникального значения
    @Column(name = "id")
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private boolean active; // для бана/подтверждения юзера

    // для удаления товара, при удалении самого пользователя
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private Image avatar;

    @Column(name = "password", length = 1000)
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    // первичный ключ указываем как user_id для таблицы юзера (в ней айди юзера и роль)
    @Enumerated(EnumType.STRING) // для преобразования в строкойвый вид
    private Set<Role> roles = new HashSet<>(); // создали сет ролей

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private List<Products> products = new ArrayList<>();

    @Column(name = "date_of_created")
    private LocalDateTime dateOfCreated; // дата создания юзера

    @PrePersist
    private void init() { // инициализация переменной даты создания пользователя
        dateOfCreated = LocalDateTime.now();
    }

    // security
    public boolean isAdmin() {
        return roles.contains(Role.ROLE_ADMIN);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active; // чтоб можно было организовать бан юзеров
    }
}
