package com.github.nicholasmaven.spock.demo.repo.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author mawen
 */
@Data
public class User {
    private Integer id;
    private String username;
    private String nickname;
}
