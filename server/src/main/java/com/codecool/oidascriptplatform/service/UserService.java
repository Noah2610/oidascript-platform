package com.codecool.oidascriptplatform.service;

import com.codecool.oidascriptplatform.controller.data.CreateUserRequestBody;
import com.codecool.oidascriptplatform.exception.RegisterUserException;
import com.codecool.oidascriptplatform.exception.SaveUserException;
import com.codecool.oidascriptplatform.model.User;
import com.codecool.oidascriptplatform.repository.UserRepository;
import com.codecool.oidascriptplatform.service.data.UserDetailsImpl;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findUserByUsername(String username) {
        userRepository.findByUsername(username).orElseThrow();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User registerUser(CreateUserRequestBody userData) throws RegisterUserException {
        User user = new User();
        user.setUsername(userData.getUsername());

        user.setPasswordHash(passwordEncoder.encode(userData.getPassword()));

        try {
            User savedUser = userRepository.save(user);
            return savedUser;
        } catch (DataIntegrityViolationException ex) {
            throw new RegisterUserException("Please use a different username");
        } catch (RuntimeException ex) {
            throw new SaveUserException();
        }
    }
}
