package com.ayushman999.cookbook.RoomDB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Recipes.class}, version = 1)
public abstract class RecipeDatabase extends RoomDatabase {
    private static RecipeDatabase instance;
    public static final int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseWriteExecutor=
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public abstract RecipeDao recipeDao();

    public static synchronized RecipeDatabase getInstance(Context context)
    {
        if(instance==null)
        {
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    RecipeDatabase.class,"recipe_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                RecipeDao dao = instance.recipeDao();
                dao.deleteAllNodes();

            });
        }
    };
}
