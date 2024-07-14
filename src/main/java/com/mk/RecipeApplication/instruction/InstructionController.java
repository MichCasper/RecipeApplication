package com.mk.RecipeApplication.instruction;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructions")
public class InstructionController {

    private final InstructionService instructionService;

    public InstructionController(InstructionService instructionService) {
        this.instructionService = instructionService;
    }

    @GetMapping
    public ResponseEntity<List<Instruction>> getAllInstructions() {
        return instructionService.getAllInstructions()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/for-recipe/{recipeId}")
    public ResponseEntity<List<Instruction>> getAllInstructionsByRecipeId(@PathVariable int recipeId) {
        return instructionService.getInstructionsByRecipeId(recipeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instruction> getInstructionById(@PathVariable int id) {
        return instructionService.getInstructionById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/for-recipe/{recipeId}/list")
    public ResponseEntity<List<Instruction>> createInstructions(@PathVariable int recipeId, @RequestBody List<Instruction> instructions) {
        try {
            List<Instruction> savedInstructions = instructionService.createInstructions(recipeId, instructions);
            return ResponseEntity.ok(savedInstructions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/for-recipe/{recipeId}")
    public ResponseEntity<Instruction> createInstruction(@PathVariable int recipeId, @RequestBody Instruction instruction) {
        try {
            Instruction savedInstruction = instructionService.createInstruction(recipeId, instruction);
            return ResponseEntity.ok(savedInstruction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/for-recipe/{recipeId}")
    public ResponseEntity<List<Instruction>> updateInstructions(@PathVariable int recipeId, @RequestBody List<Instruction> instructions) {
        try {
            List<Instruction> updatedInstructions = instructionService.updateInstructionsByRecipeId(recipeId, instructions);
            return ResponseEntity.ok(updatedInstructions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instruction> updateInstruction(@PathVariable int id, @RequestBody Instruction instruction) {
        try {
            Instruction updatedInstruction = instructionService.updateInstruction(id, instruction);
            return ResponseEntity.ok(updatedInstruction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/for-recipe/{recipeId}")
    public ResponseEntity<List<Instruction>> deleteInstructions(@PathVariable int recipeId) {
        try {
            instructionService.deleteInstructionsByRecipeId(recipeId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Instruction> deleteInstruction(@PathVariable int id) {
        try {
            instructionService.deleteInstruction(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
