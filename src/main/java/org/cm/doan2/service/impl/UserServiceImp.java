package org.cm.doan2.service.impl;

import org.cm.doan2.dto.UserDTO;
import org.cm.doan2.model.Role;
import org.cm.doan2.model.User;
import org.cm.doan2.repository.UserRepository;
import org.cm.doan2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User checkLogin(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            if (user.get().getPassword().equals(password)) {
                return user.get();
            }
        }
        return null;
    }

    @Override
    public boolean registerUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            return false;
        }
       try{
           User user = new User();
           Role role = new Role();
           role.setId(2L);
           user.setUsername(userDTO.getFullName());
           user.setEmail(userDTO.getEmail());
           user.setPassword(userDTO.getPassword());
           user.setRole(role);
           userRepository.save(user);
       }catch(Exception e){
           return false;
       }
        return true;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
