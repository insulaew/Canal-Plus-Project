package com.meetingplanner.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetingplanner.model.User;
import com.meetingplanner.repository.UserRepository;

/*Classe du service de l'entité User*/
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) { return userRepository.findUserByEmail(email).orElseThrow(); }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

}
