package com.mk.RecipeApplication.instruction;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing instructions.
 * Provides endpoints for CRUD operations on instructions, both individually and by recipe.
 */
@RestController
@RequestMapping("/instructions")
public class InstructionController {

    private final InstructionService instructionService;

    /**
     * Constructor for initializing the controller with the required service.
     *
     * @param instructionService the service handling instruction-related logic.
     */
    public InstructionController(InstructionService instructionService) {
        this.instructionService = instructionService;
    }

    /**
     * Retrieves all instructions in the database.
     *
     * @return ResponseEntity containing a list of all instructions or a 404 response if no instructions are found
     */
    @GetMapping
    public ResponseEntity<List<Instruction>> getAllInstructions() {
        return instructionService.getAllInstructions()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all instructions related to a specific recipe
     *
     * @param recipeId unique recipe id for the instructions to retrieve
     * @return ResponseEntity containing a list of instructions or a 404 response if none are found
     */
    @GetMapping("/for-recipe/{recipeId}")
    public ResponseEntity<List<Instruction>> getAllInstructionsByRecipeId(@PathVariable int recipeId) {
        return instructionService.getInstructionsByRecipeId(recipeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a single instruction by its unique identifier
     *
     * @param id Unique identifier of the instruction
     * @return ResponseEntity with the requested instruction or a 404 if it is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Instruction> getInstructionById(@PathVariable int id) {
        return instructionService.getInstructionById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates multiple instructions for a specific recipe.
     *
     * @param recipeId id of the recipe
     * @param instructions a list of instructions to be created
     * @return ResponseEntity with a list of the saved instructions or a 400 if creation fails
     */
    @PostMapping("/for-recipe/{recipeId}/list")
    public ResponseEntity<List<Instruction>> createInstructions(@PathVariable int recipeId, @RequestBody List<Instruction> instructions) {
        try {
            List<Instruction> savedInstructions = instructionService.createInstructions(recipeId, instructions);
            return ResponseEntity.ok(savedInstructions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Creates a single instruction for a specific recipe.
     *
     * @param recipeId id of the recipe
     * @param instruction instruction to be created
     * @return ResponseEntity containing the created Instruction object or a 400 if creation failed
     */
    @PostMapping("/for-recipe/{recipeId}")
    public ResponseEntity<Instruction> createInstruction(@PathVariable int recipeId, @RequestBody Instruction instruction) {
        try {
            Instruction savedInstruction = instructionService.createInstruction(recipeId, instruction);
            return ResponseEntity.ok(savedInstruction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Updates all instructions for a specified recipe. Deletes the instructions of the recipe, if they are not contained in the List of Instructions in the RequestBody.
     *
     * @param recipeId id of the recipe
     * @param instructions list of instructions
     * @return ResponseEntity containing the updated list of Instruction objects or a 400 if updating failed
     */
    @PutMapping("/for-recipe/{recipeId}")
    public ResponseEntity<List<Instruction>> updateInstructions(@PathVariable int recipeId, @RequestBody List<Instruction> instructions) {
        try {
            List<Instruction> updatedInstructions = instructionService.updateInstructionsByRecipeId(recipeId, instructions);
            return ResponseEntity.ok(updatedInstructions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Updates a single instruction, identified by its unique id.
     *
     * @param id id of the instruction
     * @param instruction updated Instruction object
     * @return ResponseEntity containing the updated Instruction Object or a 400 if updating failed
     */
    @PutMapping("/{id}")
    public ResponseEntity<Instruction> updateInstruction(@PathVariable int id, @RequestBody Instruction instruction) {
        try {
            Instruction updatedInstruction = instructionService.updateInstruction(id, instruction);
            return ResponseEntity.ok(updatedInstruction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Deleting all instructions for a specified recipe.
     *
     * @param recipeId id of the recipe
     * @return ResponseEntity with a 204 if deletion was successful or a 400 if deletion failed
     */
    @DeleteMapping("/for-recipe/{recipeId}")
    public ResponseEntity<List<Instruction>> deleteInstructions(@PathVariable int recipeId) {
        try {
            instructionService.deleteInstructionsByRecipeId(recipeId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Deleting an instruction identified by its unique id.
     *
     * @param id id of the instruction
     * @return ResponseEntity with a 204 if deletion was successful or a 400 if deletion failed
     */
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