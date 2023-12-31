package com.sijan.ticketbooking.service;

import com.sijan.ticketbooking.dto.LoginDto;
import com.sijan.ticketbooking.dto.RegisterDto;
import com.sijan.ticketbooking.entity.Role;
import com.sijan.ticketbooking.entity.User;
import com.sijan.ticketbooking.repository.RoleRepository;
import com.sijan.ticketbooking.repository.UserRepository;
import com.sijan.ticketbooking.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

       return jwtTokenProvider.generateToken(authentication);
    }

    public String register(RegisterDto registerDto) {

//        checks if username and email is already registered or not

        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new RuntimeException("Username already existed. Use unique username");
        }

        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new RuntimeException("Email already existed. Use unique email");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();

//      sign up garda default role USER hunxa. admin chai database dekhi nai add garney ho
        Role userRole = roleRepository.findByRole("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);
        return "User Registerd Successfully";
    }
}
