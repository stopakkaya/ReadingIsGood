package com.topakkaya.reading.controller;

import com.topakkaya.reading.model.AuthenticationRequest;
import com.topakkaya.reading.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class AuthenticationController {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @GetMapping()
    public String welcome() {
        return "Welcome to Getir Reading is Good App";
    }

    @PostMapping("/generate-token")
    public String generateToken(@RequestBody AuthenticationRequest request) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (Exception ex) {
            throw new Exception("Invalid username or Password");
        }

        return jwtUtil.generateToken(request.getEmail());
    }
}
