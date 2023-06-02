package com.example.instaserverv3.auth;


import com.example.instaserverv3.security.JwtService;
import com.example.instaserverv3.user.User;
import com.example.instaserverv3.user.UserRepository;
import com.example.instaserverv3.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@CrossOrigin(exposedHeaders = {
        "Set-Cookie"
})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User user) {
        var userToBeSaved = User.builder()
                .id(UUID.randomUUID())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(UserRole.USER)
                .accountCreatedAt(new Timestamp(new Date().getTime()))
                .accountActive(true)
                .build();
        User result = userRepository.save(userToBeSaved);
        AuthenticationResponse response = new AuthenticationResponse();
        if (result.getId().length() > 0) {
            String token = jwtService.generateToken(result);
            response.setToken(token);
        }
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity authenticate(@RequestBody User user) throws ResponseStatusException {
        User foundUser = userRepository.findUserByUsername(user.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "username or password is incorrect"));
        String plainPass = user.getPassword();
        boolean passCheck = passwordEncoder.matches(plainPass, foundUser.getPassword());
        if (passCheck) {
            String token = jwtService.generateToken(foundUser);

            HttpHeaders header = new HttpHeaders();
            String headerToken = "test=" + token +  "; " + "Secure;" + " HttpOnly";
            header.add(HttpHeaders.SET_COOKIE, headerToken);


            return ResponseEntity.ok().headers(header).body(new AuthenticationResponse(token, foundUser.getId()));
        } else {
            return ResponseEntity.status(401).body("Incorrect username or password");
        }
    }
}
