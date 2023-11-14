
package com.example.project.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.project.repository.UserRepository;

@Service
public class MyUserDetail implements UserDetails, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    private String email;
    private String password;
    private String name;
    private String role;
    private Integer id;
    private GrantedAuthority grantedAuthority;

    public MyUserDetail() {
        super();
    }

    public MyUserDetail(String email, String password, String name, Integer id, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.id = id;
        this.grantedAuthority = new SimpleGrantedAuthority(role);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.login(username);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(grantedAuthority);
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
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
        return true;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public Integer getId() {
        return id;
    }

}