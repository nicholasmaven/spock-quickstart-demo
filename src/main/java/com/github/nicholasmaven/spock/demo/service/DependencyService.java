package com.github.nicholasmaven.spock.demo.service;

import com.github.nicholasmaven.spock.demo.repo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mawen
 */
@Service
public class DependencyService {
    @Autowired
    private UserService userService;

    public String getNickname(int id) {
        User user = userService.get(id);
        return user == null ? null : user.getNickname();
    }
}
