package com.mk.RecipeApplication.security;

import com.mk.RecipeApplication.security.role.Role;
import com.mk.RecipeApplication.security.role.RoleRepository;
import com.mk.RecipeApplication.security.user.UserEntity;
import com.mk.RecipeApplication.security.user.UserRepository;
import com.mk.RecipeApplication.security.userRoles.UserRole;
import com.mk.RecipeApplication.security.userRoles.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, UserRoleRepository userRoleRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new User(userEntity.getUsername(), userEntity.getPassword(), mapRolesToAuthorities(userEntity));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(UserEntity userEntity) {
        int userId = userEntity.getId();
        List<UserRole> userRoles = userRoleRepository.findAllByUserId(userId);
        List<Role> roles = roleRepository.findAll();
        HashMap<Integer, String> hashMap = new HashMap<>();
        for (Role role : roles) {
            hashMap.put(role.getId(), "ROLE_" + role.getName());
        }
        return userRoles.stream().map(userRole -> new SimpleGrantedAuthority(hashMap.get(userRole.getRoleId()))).collect(Collectors.toList());
    }
}


