package com.ayushman999.cookbook.RoomDB;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ayushman999.cookbook.Model.Recipe;

import java.util.List;

public class RecipeRepository {
    private RecipeDao recipeDao;
    private LiveData<List<Recipes>> allrecipes;
    public RecipeRepository(Application application)
    {
        RecipeDatabase database=RecipeDatabase.getInstance(application);
        recipeDao=database.recipeDao();
        allrecipes=recipeDao.getAllRecipes();
    }
    public void insert(Recipes recipes)
    {
        new InsertRecipesAsyncTask(recipeDao).execute(recipes);
    }
    public void update(Recipes recipes)
    {
        new UpdateRecycpeAsync(recipeDao).execute(recipes);
    }

    public void delete(Recipes recipes)
    {
        new DeleteRecipeAsync(recipeDao).execute(recipes);
    }

    public void deleteAll()
    {
        new DeleteAllAsync(recipeDao).execute();
    }

    public LiveData<List<Recipes>> getAllrecipes() {
        return allrecipes;
    }
    private static class InsertRecipesAsyncTask extends AsyncTask<Recipes,Void,Void>{
        private RecipeDao recipeDao;
        private InsertRecipesAsyncTask(RecipeDao recipeDao)
        {
            this.recipeDao=recipeDao;
        }
        @Override
        protected Void doInBackground(Recipes... recipes) {
            recipeDao.insert(recipes[0]);
            return null;
        }
    }

    private static class UpdateRecycpeAsync extends AsyncTask<Recipes,Void,Void>{
        private RecipeDao recipeDao;
        private UpdateRecycpeAsync(RecipeDao recipeDao)
        {
            this.recipeDao=recipeDao;
        }
        @Override
        protected Void doInBackground(Recipes... recipes) {
            recipeDao.update(recipes[0]);
            return null;
        }
    }

    private static class DeleteRecipeAsync extends AsyncTask<Recipes,Void,Void>{
        private RecipeDao recipeDao;
        private DeleteRecipeAsync(RecipeDao recipeDao)
        {
            this.recipeDao=recipeDao;
        }
        @Override
        protected Void doInBackground(Recipes... recipes) {
            recipeDao.delete(recipes[0]);
            return null;
        }
    }

    private static class DeleteAllAsync extends AsyncTask<Recipes,Void,Void>{
        private RecipeDao recipeDao;
        private DeleteAllAsync(RecipeDao recipeDao)
        {
            this.recipeDao=recipeDao;
        }
        @Override
        protected Void doInBackground(Recipes... recipes) {
            recipeDao.deleteAllNodes();
            return null;
        }
    }
}
