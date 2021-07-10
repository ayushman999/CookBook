package com.ayushman999.cookbook;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ayushman999.cookbook.Model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeUtil {
    boolean onerror=false;
    Recipe recipe=null;
    public Recipe getRandomRecipe(Context context)
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
                        ingList+="\n"+currentIng;
                        amtList+="\n"+currentAmt;
                    }
                    String description=mealObject.getString("strInstructions");
                    String srcLink=mealObject.getString("strSource");
                    recipe=new Recipe(mealId,title,category,recipeImg,ytLink,ingList,amtList,description,srcLink);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onerror=true;
                Toast.makeText(context, "Could not load your recipe!", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(context).addToRequestQue(jsonObjectRequest);
        while (recipe==null);
        return recipe;
    }
}
