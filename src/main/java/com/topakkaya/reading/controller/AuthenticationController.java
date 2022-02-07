package com.topakkaya.reading.controller;

import com.topakkaya.reading.model.AuthenticationRequest;
import com.topakkaya.reading.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class AuthenticationController {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    /**
     * @author samet topakkaya
     * @apiNote welcome endpoint for check system authentication/welcome page
     */

    @GetMapping()
    public String welcome() {
        return "Welcome to Getir Reading is Good App";
    }

    /**
     * @author samet topakkaya
     * @apiNote endpoint generates bearer token for system authorization.
     * @param request consist user email and password
     */

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
