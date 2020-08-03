package com.backend.controller;

import com.backend.dto.user.User;
import com.backend.service.JwtService;
import com.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static java.awt.SystemColor.info;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserLoginController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("/user/signin")
    public ResponseEntity<Map<String, Object>> signin(@RequestBody User user, HttpServletResponse res){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try{
            User loginUser = userService.signin(user.getEmail(), user.getPassword());
            String token = jwtService.create(loginUser);
            res.setHeader("jwt-auth-token", token);

            resultMap.put("status", true);
            resultMap.put("info", info);
            resultMap.put("request_body", user);
            status = HttpStatus.ACCEPTED;
        } catch(RuntimeException e){
            log.error("정보조회 실패", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);

    }



}
