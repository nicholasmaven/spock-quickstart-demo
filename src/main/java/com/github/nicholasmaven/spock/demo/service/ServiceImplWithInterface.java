package com.github.nicholasmaven.spock.demo.service;

import com.github.nicholasmaven.spock.demo.repo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mawen
 */
@Service
public class ServiceImplWithInterface implements ServiceInterface {
    @Autowired
    private DependencyService depService;

    @Override
    public String getName() {
        return "mawen";
    }

    @Override
    public String getNickName(int id) {
        return depService.getNickname(id);
    }
}
