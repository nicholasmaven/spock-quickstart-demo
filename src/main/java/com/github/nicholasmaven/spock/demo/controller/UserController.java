package com.github.nicholasmaven.spock.demo.controller;

import com.github.nicholasmaven.spock.demo.service.UserService;
import com.github.nicholasmaven.spock.demo.repo.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author mawen
 */
@RestController
@RequestMapping("v1/user")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value="/list/{id}", produces = APPLICATION_JSON_VALUE)
    public User listOne(@PathVariable("id") int id) {
        return userService.get(id);
    }
}
