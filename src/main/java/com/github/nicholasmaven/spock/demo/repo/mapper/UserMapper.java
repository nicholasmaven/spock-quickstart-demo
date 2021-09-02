package com.github.nicholasmaven.spock.demo.repo.mapper;

import com.github.nicholasmaven.spock.demo.repo.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    User selectByPrimaryKey(int id);

    int insert(User user);

    int updateByPrimaryKey(User user);

    int deleteByPrimaryKey(int id);

    List<User> selectByPrimaryKeys(List<Integer> ids);
}
