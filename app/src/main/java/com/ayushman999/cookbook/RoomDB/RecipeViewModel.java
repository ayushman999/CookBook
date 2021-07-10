package com.ayushman999.cookbook.RoomDB;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {
    private RecipeRepository recipeRepository;
    private LiveData<List<Recipes>> allRecipes;
    public RecipeViewModel(@NonNull Application application) {
        super(application);
        recipeRepository=new RecipeRepository(application);
        allRecipes=recipeRepository.getAllrecipes();
    }
    public void insert(Recipes recipes)
    {
        recipeRepository.insert(recipes);
    }

    public void update(Recipes recipes)
    {
        recipeRepository.update(recipes);
    }

    public void delete(Recipes recipes)
    {
        recipeRepository.delete(recipes);
    }

    public void deleteAll()
    {
        recipeRepository.deleteAll();
    }

    public LiveData<List<Recipes>> getAllRecipes() {
        return allRecipes;
    }
}
