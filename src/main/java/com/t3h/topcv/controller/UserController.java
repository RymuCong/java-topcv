package com.t3h.topcv.controller;

import com.t3h.topcv.entity.Account;
import com.t3h.topcv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Account> userProfile() {
        return ResponseEntity.ok(userService.getUser());
    }
}