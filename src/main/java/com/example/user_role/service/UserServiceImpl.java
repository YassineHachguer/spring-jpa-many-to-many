package com.example.user_role.service;

import com.example.user_role.entities.Role;
import com.example.user_role.entities.User;
import com.example.user_role.repository.RoleRepository;
import com.example.user_role.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor // injection via constructeur
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Override
    public User addNewUser(User user) {
        user.setUserId(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    @Override
    public Role addNewRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public User findUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Role findRoleByRoleName(String roleName) {
        return roleRepository.findRoleByRoleName(roleName);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user =  findUserByUserName(username);
        Role role = findRoleByRoleName(roleName);
        if(user.getRoles() != null){
            user.getRoles().add(role);
            role.getUsers().add(user);
        }
    }

    @Override
    public User authenticate(String username, String password) {
        User user = findUserByUserName(username);
        if (user==null) throw new RuntimeException("Bad credentials!");
        if(user.getPassword().equals(password)){
            return user;
        }
        throw new RuntimeException("Bad Credentials!");
    }
}
