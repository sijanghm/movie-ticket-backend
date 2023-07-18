package com.sijan.ticketbooking.controller;


import com.sijan.ticketbooking.dto.JwtAuthDto;
import com.sijan.ticketbooking.dto.LoginDto;
import com.sijan.ticketbooking.dto.RegisterDto;
import com.sijan.ticketbooking.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("login")
    public ResponseEntity<JwtAuthDto> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        JwtAuthDto jwtAuthDto =  new JwtAuthDto();
        jwtAuthDto.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthDto);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
