package com.ayushman999.cookbook.RoomDB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe_table")
public class Recipes {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String mealId;
    private String title;
    private String category;
    private String recipeImg;
    private String ytLink;
    private String ingList;
    private String amtList;
    private String description;
    private String srcLink;

    public Recipes(String mealId, String title, String category, String recipeImg, String ytLink, String ingList, String amtList, String description, String srcLink) {
        this.mealId = mealId;
        this.title = title;
        this.category = category;
        this.recipeImg = recipeImg;
        this.ytLink = ytLink;
        this.ingList = ingList;
        this.amtList = amtList;
        this.description = description;
        this.srcLink = srcLink;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getMealId() {
        return mealId;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getRecipeImg() {
        return recipeImg;
    }

    public String getYtLink() {
        return ytLink;
    }

    public String getIngList() {
        return ingList;
    }

    public String getAmtList() {
        return amtList;
    }

    public String getDescription() {
        return description;
    }

    public String getSrcLink() {
        return srcLink;
    }
}
