package com.antoniocostadossantos.service;

import com.antoniocostadossantos.exceptions.InvalidUserCredentialsException;
import com.antoniocostadossantos.model.UserEntity;
import com.antoniocostadossantos.model.UserLoginDTO;
import com.antoniocostadossantos.repository.UserRepository;
import com.antoniocostadossantos.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repository) {
        this.repository = repository;
        this.encoder = new BCryptPasswordEncoder();
    }

    public UserEntity register(String username, String password) {
        // TODO TRANSFORMAR A SENHA EM HASH
        String encryptedPassword = encoder.encode(password);
        UserEntity userEntity = new UserEntity(username, encryptedPassword);
        return repository.save(userEntity);
    }

    private Optional<UserEntity> findByUserName(String username) {
        return repository.findByUsername(username);
    }

    public String loginUser(UserLoginDTO userLogin) {
        Optional<UserEntity> user = findByUserName(userLogin.username());

        if (user.isPresent()) {
            boolean passwordMatch = checkEncodedPassword(userLogin.password(), user.get().getPassword());
            if (passwordMatch) {
                return JwtUtil.generateToken(userLogin.username());
            }
        }
        throw new InvalidUserCredentialsException("Credenciais inválidas.");
    }

    private boolean checkEncodedPassword(String rawPassword, String userPassword) {
        return encoder.matches(rawPassword, userPassword);
    }
}
