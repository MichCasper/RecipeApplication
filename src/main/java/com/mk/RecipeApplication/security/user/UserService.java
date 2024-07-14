package com.mk.RecipeApplication.security.user;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<List<UserEntity>> getAllUsers() {
        return Optional.of(userRepository.findAll());
    }

    public Optional<UserEntity> getByName(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<UserEntity> getById(Integer id) {
        return userRepository.findById(id);
    }

    public UserEntity createUserEntity(UserEntity user) {
        return userRepository.save(user);
    }


    public UserEntity updateUserEntity(UserEntity user) throws Exception {
        UserEntity userToUpdate = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception("User not found"));

        userToUpdate.setId(user.getId());
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(user.getPassword());
        try {
            return userRepository.save(userToUpdate);
        } catch (Exception e) {
            throw new Exception("Error updating user!");
        }
    }

    public void deleteUser(UserEntity user) throws Exception {
        userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception("Recipe not found"));

        userRepository.deleteById(user.getId());
    }
}

