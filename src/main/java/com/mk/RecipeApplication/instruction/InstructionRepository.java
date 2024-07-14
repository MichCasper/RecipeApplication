package com.mk.RecipeApplication.instruction;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InstructionRepository extends JpaRepository<Instruction, Integer> {
    List<Instruction> findAllByRecipeId(Integer recipeId);
}
