package com.ayushman999.cookbook.RoomDB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface RecipeDao {
    @Insert
    void insert(Recipes recipe);

    @Update
    void update(Recipes recipe);

    @Delete
    void delete(Recipes recipe);

    @Query("DELETE FROM recipe_table")
    void deleteAllNodes();

    @Query("SELECT * FROM recipe_table ORDER BY id ASC")
    LiveData<List<Recipes>> getAllRecipes();
}
