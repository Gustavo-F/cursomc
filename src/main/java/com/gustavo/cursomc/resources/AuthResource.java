package com.gustavo.cursomc.resources;

import javax.servlet.http.HttpServletResponse;

import com.gustavo.cursomc.security.JWTUtil;
import com.gustavo.cursomc.security.UserSS;
import com.gustavo.cursomc.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {   
  
    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping(value = "refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSS user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
    
        response.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }
}
