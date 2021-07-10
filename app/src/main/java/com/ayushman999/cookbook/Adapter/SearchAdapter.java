package com.ayushman999.cookbook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayushman999.cookbook.Model.Recipe;
import com.ayushman999.cookbook.R;
import com.ayushman999.cookbook.RoomDB.Recipes;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.RecipeHolder> {
    List<Recipe> recipes;
    Context context;
    OnRecipeClicked listener;
    public SearchAdapter(List<Recipe> recipes, Context context,OnRecipeClicked listener) {
        this.recipes = recipes;
        this.context = context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_element,parent,false);
        RecipeHolder holder=new RecipeHolder(view);
        holder.image.setOnClickListener(v->{
            listener.RecipeClicked(recipes.get(holder.getAdapterPosition()).getMealId());
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.RecipeHolder holder, int position) {
        Recipe recipe=recipes.get(position);
        holder.title.setText(recipe.getTitle());
        holder.category.setText(recipe.getCategory());
        Glide.with(context).load(recipe.getImgLink()).into(holder.image);
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
