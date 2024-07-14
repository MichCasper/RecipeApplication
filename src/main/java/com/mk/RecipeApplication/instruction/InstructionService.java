package com.mk.RecipeApplication.instruction;

import com.mk.RecipeApplication.ingredient.Ingredient;
import com.mk.RecipeApplication.recipe.RecipeRepository;
import com.mk.RecipeApplication.recipe.RecipeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InstructionService {

    private final RecipeRepository recipeRepository;
    private final InstructionRepository instructionRepository;

    public InstructionService(InstructionRepository instructionRepository, RecipeService recipeService, RecipeRepository recipeRepository) {
        this.instructionRepository = instructionRepository;
        this.recipeRepository = recipeRepository;
    }

    public Optional<Instruction> getInstructionById(int id) {
        return instructionRepository.findById(id);
    }

    public Optional<List<Instruction>> getAllInstructions() {
        List<Instruction> instructions = instructionRepository.findAll();
        return Optional.of(instructions);
    }

    public Optional<List<Instruction>> getInstructionsByRecipeId(int recipeId) {
        List<Instruction> instructions = instructionRepository.findAllByRecipeId(recipeId);
        return Optional.of(instructions);
    }

    public Instruction createInstruction(int recipeId, Instruction instruction) throws Exception {
        recipeRepository.findById(recipeId)
                .orElseThrow(() -> new Exception("Recipe for instruction cannot be found"));

        return instructionRepository.save(instruction);
    }

    public List<Instruction> createInstructions(int recipeId, List<Instruction> instructions) throws Exception {
        recipeRepository.findById(recipeId)
                .orElseThrow(() -> new Exception("Recipe for instruction cannot be found"));

        return instructionRepository.saveAll(instructions);
    }

    public Instruction updateInstruction(int id, Instruction instruction) throws Exception {
        Instruction instructionToUpdate = instructionRepository.findById(id)
                .orElseThrow(() -> new Exception("Instruction not found"));

        instructionToUpdate.setRecipeId(instruction.getRecipeId());
        instructionToUpdate.setStep(instruction.getStep());
        instructionToUpdate.setDescription(instruction.getDescription());

        try {
            return instructionRepository.save(instructionToUpdate);
        } catch (Exception e) {
            throw new Exception("Error updating instruction");
        }
    }

    public List<Instruction> updateInstructionsByRecipeId(int recipeId, List<Instruction> instructions) throws Exception {
        List<Instruction> oldInstructions = instructionRepository.findAllByRecipeId(recipeId);
        Optional<List<Instruction>> optionalInstructions = Optional.of(oldInstructions);

        if (optionalInstructions.get().isEmpty()) {
            return instructionRepository.saveAll(instructions);
        } else {
            List<Instruction> instructionsToBeSaved = new ArrayList<>();
            for (Instruction instruction : instructions) {
                instructionsToBeSaved.add(
                        new Instruction(
                                instruction.getRecipeId(),
                                instruction.getStep(),
                                instruction.getDescription()));
            }
            List<Instruction> savedInstructions = instructionRepository.saveAll(instructionsToBeSaved);
            instructionRepository.deleteAll(oldInstructions);
            return savedInstructions;
        }
    }

    public void deleteInstruction(int id) throws Exception {
        instructionRepository.findById(id)
                .orElseThrow(() -> new Exception("Instruction not found"));

        instructionRepository.deleteById(id);
    }

    public void deleteInstructionsByRecipeId(int recipeId) throws Exception {
        List<Instruction> instructions = instructionRepository.findAllByRecipeId(recipeId);
        if(instructions.isEmpty()) {
            throw new Exception("Ingredients not found");
        }

        instructionRepository.deleteAll(instructions);
    }
}
