package org.cm.doan2.service;

import org.cm.doan2.dto.UserDTO;
import org.cm.doan2.model.User;

import java.util.List;

public interface UserService {
    User checkLogin(String email, String password);
    boolean registerUser(UserDTO userDTO);
    List<User> getAllUsers();
}
