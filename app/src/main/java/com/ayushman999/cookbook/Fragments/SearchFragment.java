package com.ayushman999.cookbook.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ayushman999.cookbook.Adapter.OnRecipeClicked;
import com.ayushman999.cookbook.Adapter.SearchAdapter;
import com.ayushman999.cookbook.Model.Recipe;
import com.ayushman999.cookbook.MySingleton;
import com.ayushman999.cookbook.R;
import com.ayushman999.cookbook.RoomDB.Recipes;
import com.ayushman999.cookbook.SearchedRecipeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements OnRecipeClicked {
    EditText searchEdit;
    Button searchBtn;
    RecyclerView searchRecycler;
    RecyclerView.LayoutManager manager;
    SearchAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        setUpViews(view);
        return view;
    }

    private void setUpViews(View view) {
        searchBtn=view.findViewById(R.id.recipe_search_btn);
        searchEdit=view.findViewById(R.id.recipe_search);
        searchRecycler=view.findViewById(R.id.search_recylerView);
        manager=new LinearLayoutManager(getContext());
        searchRecycler.setLayoutManager(manager);
        searchBtn.setOnClickListener(v -> {
            String input=searchEdit.getText().toString().trim();
            if(input.equals(""))
            {
                Toast.makeText(getContext(), "Enter something to search!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                searchQuery(input.split(" ")[0]);
            }
        });
    }

    private void searchQuery(String input) {
        List<Recipe> recipes=new ArrayList<>();
        String searchRecipeUrl="https://themealdb.com/api/json/v1/1/search.php?s="+input;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, searchRecipeUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("meals");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject mealObject= jsonArray.getJSONObject(i);
                        String mealId=mealObject.getString("idMeal");
                        String title= mealObject.getString("strMeal");
                        String category= mealObject.getString("strCategory");
                        String recipeImg= mealObject.getString("strMealThumb");
                        Recipe recipe=new Recipe(mealId,title,category,recipeImg);
                        recipes.add(recipe);
                    }
                    adapter=new SearchAdapter(recipes,getContext(),SearchFragment.this::RecipeClicked);
                    searchRecycler.setAdapter(adapter);

                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Zero search result", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Could not load your recipe!", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(getContext()).addToRequestQue(jsonObjectRequest);


    }

    @Override
    public void RecipeClicked(String id) {
        Intent transfer=new Intent(getContext(), SearchedRecipeActivity.class);
        transfer.putExtra("id",id);
        startActivity(transfer);
    }
}