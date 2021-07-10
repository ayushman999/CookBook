package com.ayushman999.cookbook.RoomDB;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayushman999.cookbook.Adapter.OnRecipeClicked;
import com.ayushman999.cookbook.Adapter.SearchAdapter;
import com.ayushman999.cookbook.Model.Recipe;
import com.ayushman999.cookbook.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeHolder>{
    OnRecipeClicked listener;
    Context context;
    List<Recipes> recipes=new ArrayList<>();
    public RecipesAdapter(Context context, OnRecipeClicked listener)
    {
        this.context=context;
        this.listener=listener;
    }
    @NonNull
    @Override
    public RecipesAdapter.RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_element,parent,false);
        RecipesAdapter.RecipeHolder holder=new RecipesAdapter.RecipeHolder(view);
        holder.image.setOnClickListener(v->{
            listener.RecipeClicked(recipes.get(holder.getAdapterPosition()).getMealId());
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapter.RecipeHolder holder, int position) {
        Recipes recipe=recipes.get(position);
        holder.title.setText(recipe.getTitle());
        holder.category.setText(recipe.getCategory());
        Glide.with(context).load(recipe.getRecipeImg()).into(holder.image);
    }

    public void setRecipes(List<Recipes> recipes)
    {
        this.recipes=recipes;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipeHolder extends RecyclerView.ViewHolder {
        TextView title,category;
        ImageView image;
        public RecipeHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.search_title);
            category=itemView.findViewById(R.id.search_category);
            image=itemView.findViewById(R.id.search_image);
        }
    }
}
