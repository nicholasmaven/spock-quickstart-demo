package com.github.nicholasmaven.spock.demo.service;

import com.github.nicholasmaven.spock.demo.exception.UserException;
import com.github.nicholasmaven.spock.demo.repo.entity.User;
import com.github.nicholasmaven.spock.demo.repo.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author mawen
 */
@Service
public class UserService {
    UserMapper mapper;

    public UserService(UserMapper mapper) {
        this.mapper = mapper;
    }

    public User get(int id) {
        User user = mapper.selectByPrimaryKey(id);
        if (user == null) {
            throw new UserException("no user");
        }
        return user;
    }

    public List<User> getBunch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) return Collections.emptyList();
        return mapper.selectByPrimaryKeys(ids);
    }

    public void update(User user) {
        if (user != null && valid(user.getId(), user.getUsername(), user.getNickname())) {
            mapper.updateByPrimaryKey(user);
        }
    }

    // only for demo
    public List<String> doSth(List<Integer> user) {
        //
        return Arrays.asList("username-1", "username-2", "username-3", "username4");
    }

    // only for demo
    public Integer getStudentScore(String className, String userName) {
        //
        return 1;
    }

    public boolean valid(Integer id, String name, String nickname) {
        if (id == null) return false;
        if (name == null || name.length() > 10 || name.length() < 5) {
            return false;
        }
        return nickname != null && nickname.length() >= 5 && nickname.length() <= 20;
    }

}
