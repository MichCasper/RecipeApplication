package com.mk.RecipeApplication.instruction;


import javax.persistence.*;

@Entity
@Table(name="instructions")
public class Instruction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "instructions_id_seq")
    @SequenceGenerator(name = "instructions_id_seq", sequenceName = "instructions_id_seq", allocationSize = 1)
    private int id;
    private int recipeId;
    private int step;
    private String description;

    public Instruction(int id, int recipeId, int step, String description) {
        this.id = id;
        this.recipeId = recipeId;
        this.step = step;
        this.description = description;
    }

    public Instruction(int recipeId, int step, String description) {
        this.recipeId = recipeId;
        this.step = step;
        this.description = description;
    }

    public Instruction() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
