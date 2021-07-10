package com.ayushman999.cookbook.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ayushman999.cookbook.Adapter.OnRecipeClicked;
import com.ayushman999.cookbook.R;
import com.ayushman999.cookbook.RoomDB.RecipeViewModel;
import com.ayushman999.cookbook.RoomDB.Recipes;
import com.ayushman999.cookbook.RoomDB.RecipesAdapter;
import com.ayushman999.cookbook.SearchedRecipeActivity;

import java.util.List;


public class SavedFragment extends Fragment implements OnRecipeClicked {
    private RecipeViewModel recipeViewModel;
    RecyclerView.LayoutManager manager;
    RecyclerView recyclerView;
    RecipesAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_saved, container, false);
        recyclerView=view.findViewById(R.id.saved_recipe_recycler);
        manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        adapter=new RecipesAdapter(getContext(),SavedFragment.this::RecipeClicked);
        recyclerView.setAdapter(adapter);

        recipeViewModel=new ViewModelProvider(requireActivity()).get(RecipeViewModel.class);
        recipeViewModel.getAllRecipes().observe(getViewLifecycleOwner(), new Observer<List<Recipes>>() {
            @Override
            public void onChanged(List<Recipes> recipes) {
                adapter.setRecipes(recipes);
            }
        });
        return view;
    }

    @Override
    public void RecipeClicked(String id) {
        Intent transfer=new Intent(getContext(), SearchedRecipeActivity.class);
        transfer.putExtra("id",id);
        startActivity(transfer);
    }
}