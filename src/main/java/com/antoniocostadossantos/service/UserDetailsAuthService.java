package com.antoniocostadossantos.service;

import com.antoniocostadossantos.model.UserEntity;
import com.antoniocostadossantos.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsAuthService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .roles("USER")
                .build();
    }
}
