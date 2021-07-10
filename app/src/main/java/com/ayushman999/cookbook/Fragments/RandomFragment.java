package com.ayushman999.cookbook.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ayushman999.cookbook.Model.Recipe;
import com.ayushman999.cookbook.MySingleton;
import com.ayushman999.cookbook.R;
import com.ayushman999.cookbook.RecipeUtil;
import com.ayushman999.cookbook.RoomDB.RecipeViewModel;
import com.ayushman999.cookbook.RoomDB.Recipes;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RandomFragment extends Fragment {
    private TextView title,category,yt_link,ing_list,amt_list,desc,src_link;
    private ImageView saveBtn,recipeImg;
    Recipe recipe;
    Recipes recipes;
    RecipeViewModel recipeViewModel;
    boolean isSaved=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_random, container, false);
        setUpView(view);
        getRandomRecipe(getContext());
        return view;
    }

    private void setUpView(View view) {
        title=view.findViewById(R.id.rdm_recipe_title);
        category=view.findViewById(R.id.rdm_recipe_category);
        recipeImg=view.findViewById(R.id.rdm_recipe_image);
        yt_link=view.findViewById(R.id.rdm_recipe_ytLink);
        saveBtn=view.findViewById(R.id.save_btn);
        ing_list=view.findViewById(R.id.rdm_recipe_ing);
        amt_list=view.findViewById(R.id.rdm_recipe_amt);
        desc=view.findViewById(R.id.rdm_recipe_description);
        src_link=view.findViewById(R.id.rdm_recipe_src_link);
        saveBtn.setOnClickListener(v->{
            saveBtn();
        });
    }

    private void saveBtn() {
        recipeViewModel=new ViewModelProvider(requireActivity()).get(RecipeViewModel.class);
        if(!isSaved)
        {
            recipeViewModel.insert(recipes);
            saveBtn.setImageResource(R.drawable.ic_save_full);
            Toast.makeText(getContext(), "Saved!", Toast.LENGTH_SHORT).show();
            isSaved=true;
        }
        else
        {
            recipeViewModel.delete(recipes);
            saveBtn.setImageResource(R.drawable.ic_save_border);
            Toast.makeText(getContext(), "Removed!", Toast.LENGTH_SHORT).show();
            isSaved=false;
        }

    }

    public void getRandomRecipe(Context context)
    {

        String randomRecipeUrl="https://www.themealdb.com/api/json/v1/1/random.php";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, randomRecipeUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("meals");
                    JSONObject mealObject= jsonArray.getJSONObject(0);
                    String mealId=mealObject.getString("idMeal");
                    String title= mealObject.getString("strMeal");
                    String category= mealObject.getString("strCategory");
                    String recipeImg= mealObject.getString("strMealThumb");
                    String ytLink= mealObject.getString("strYoutube");
                    String ingList="";
                    String amtList="";
                    int i=1;
                    String currentIng=mealObject.getString("strIngredient"+i);
                    String currentAmt=mealObject.getString("strMeasure"+i);
                    while (!currentIng.equals(""))
                    {
                        currentAmt=mealObject.getString("strMeasure"+i);
                        currentIng=mealObject.getString("strIngredient"+i);
                        ingList+=currentIng+"\n";
                        amtList+=currentAmt+"\n";
                        i++;

                    }
                    String description=mealObject.getString("strInstructions");
                    String srcLink=mealObject.getString("strSource");
                    recipe=new Recipe(mealId,title,category,recipeImg,ytLink,ingList,amtList,description,srcLink);
                    recipes=new Recipes(mealId,title,category,recipeImg,ytLink,ingList,amtList,description,srcLink);
                    setuprecipe(recipe);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Could not load your recipe!", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(getContext()).addToRequestQue(jsonObjectRequest);

    }

    private void setuprecipe(Recipe recipe) {
        title.setText(recipe.getTitle());
        category.setText(recipe.getCategory());
        Glide.with(getContext()).load(recipe.getImgLink()).into(recipeImg);
        yt_link.setText(recipe.getYtLink());
        ing_list.setText(recipe.getIngList());
        amt_list.setText(recipe.getAmtList());
        desc.setText(recipe.getDescription());
        src_link.setText(recipe.getSrcLink());

    }
}