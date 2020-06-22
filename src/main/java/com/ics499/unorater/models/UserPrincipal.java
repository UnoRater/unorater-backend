package com.ics499.unorater.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A custom UserDetails class that is
 * used by spring security to perform
 * authentication and authorization.
 */
public class UserPrincipal implements UserDetails {

    private Integer id;

    private String username;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private Integer departmentNum;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Integer id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public UserPrincipal(Integer id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities, int departmentNum) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.departmentNum = departmentNum;
    }

    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());

        return new UserPrincipal(
                user.getUserID(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public static UserPrincipal createDepartmentAdmin(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());

        return new UserPrincipal(
                user.getUserID(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                user.getDepartmentNum()
        );
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Integer getDepartmentNum() {
        return departmentNum;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public String toString() {
        return "UserPrincipal {" +
                "id= " + id +
                " userName= " + username +
                " password=*********" +
                " email= " + email +
                " departmentNumber= " + departmentNum;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
