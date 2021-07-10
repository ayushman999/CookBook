package com.ayushman999.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ayushman999.cookbook.Model.Recipe;
import com.ayushman999.cookbook.RoomDB.Recipes;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchedRecipeActivity extends AppCompatActivity {
    Recipes recipe;
    boolean isSaved=false;
    private TextView title,category,yt_link,ing_list,amt_list,desc,src_link;
    private ImageView saveBtn,recipeImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_recipe);
        Intent data=getIntent();
        String id=data.getStringExtra("id");
        setUpView();
        searchRecipe(id);
    }

    private void searchRecipe(String id) {
        String randomRecipeUrl="https://www.themealdb.com/api/json/v1/1/lookup.php?i="+id;
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
                    recipe=new Recipes(mealId,title,category,recipeImg,ytLink,ingList,amtList,description,srcLink);
                    setuprecipe(recipe);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchedRecipeActivity.this, "Could not load your recipe!", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(SearchedRecipeActivity.this).addToRequestQue(jsonObjectRequest);

    }

    private void setuprecipe(Recipes recipe) {
        title.setText(recipe.getTitle());
        category.setText(recipe.getCategory());
        Glide.with(SearchedRecipeActivity.this).load(recipe.getRecipeImg()).into(recipeImg);
        yt_link.setText(recipe.getYtLink());
        ing_list.setText(recipe.getIngList());
        amt_list.setText(recipe.getAmtList());
        desc.setText(recipe.getDescription());
        src_link.setText(recipe.getSrcLink());
    }

    private void setUpView() {
        title=findViewById(R.id.searched_recipe_title);
        category=findViewById(R.id.searched_recipe_category);
        recipeImg=findViewById(R.id.searched_recipe_img);
        yt_link=findViewById(R.id.searched_recipe_ytLink);
        saveBtn=findViewById(R.id.searched_recipe_btn);
        ing_list=findViewById(R.id.searched_recipe_ing);
        amt_list=findViewById(R.id.searched_recipe_amt);
        desc=findViewById(R.id.searched_recipe_desc);
        src_link=findViewById(R.id.searched_recipe_srcLink);
        saveBtn.setOnClickListener(v->{
            saveBtn();
        });
    }

    private void saveBtn() {
        if(!isSaved)
        {
            saveBtn.setImageResource(R.drawable.ic_save_full);
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
            isSaved=true;
        }
        else
        {
            saveBtn.setImageResource(R.drawable.ic_save_border);
            Toast.makeText(this, "Removed!", Toast.LENGTH_SHORT).show();
            isSaved=false;
        }
    }
}