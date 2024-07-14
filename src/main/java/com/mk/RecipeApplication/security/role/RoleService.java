package com.mk.RecipeApplication.security.role;

import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(Role role) throws Exception {
        return roleRepository.save(role);
    }

    public void deleteRecipe(int id) throws Exception{
        roleRepository.findById(id)
                        .orElseThrow(() -> new Exception("Role not found"));
        roleRepository.deleteById(id);
    }
}
