package com.example.demo.core.token.controller;

import com.example.demo.core.token.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/")
public class TokenController {

    @Autowired
    TokenService tokenService;

    @PostMapping("/genToken")
    public ResponseEntity<String> login(@RequestParam("username") String username){
        String data =  tokenService.genToken(username);
        return ResponseEntity.ok(data);
    }

}
