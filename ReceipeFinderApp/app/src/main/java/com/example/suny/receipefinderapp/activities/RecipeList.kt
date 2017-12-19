package com.example.suny.receipefinderapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.suny.receipefinderapp.R
import com.example.suny.receipefinderapp.data.LEFT_LINK
import com.example.suny.receipefinderapp.data.QUERY
import com.example.suny.receipefinderapp.data.RecipeListAdapter
import com.example.suny.receipefinderapp.model.Recipe
import kotlinx.android.synthetic.main.activity_recipe_list.*
import org.json.JSONException
import org.json.JSONObject

class RecipeList : AppCompatActivity() {

    var volleyRequest: RequestQueue? = null
    var recipeList: ArrayList<Recipe>? = null
    var recipeAdapter: RecipeListAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        var url: String?

        var extras = intent.extras
        var ingredients = extras.get("ingredients")
        var searchTerm = extras.get("search")

        if (extras != null && !ingredients.equals("")
                && !searchTerm.equals("")) {
            //construct our url
            var tempUrl = LEFT_LINK + ingredients + QUERY + searchTerm


            url = tempUrl

        }else {

            url = "http://www.recipepuppy.com/api/?i=onions,garlic&q=omelet&p=3"

        }




        recipeList = ArrayList<Recipe>()
        volleyRequest = Volley.newRequestQueue(this)

        getRecipte(url)
    }

    fun getRecipte(url: String) {
        val recipteRequest = JsonObjectRequest(Request.Method.GET,
                url, Response.Listener {
                    response: JSONObject ->
                    try {
                        val resultArray = response.getJSONArray("results")

                        for(i in 0..resultArray.length() -1) {
                            var recipeObj = resultArray.getJSONObject(i)
                            var title = recipeObj.getString("title")
                            var link = recipeObj.getString("href")
                            var thumbnail = recipeObj.getString("thumbnail")
                            var ingredients = recipeObj.getString("ingredients")

                            var recipe = Recipe()
                            recipe.title = title
                            recipe.thumbnail = thumbnail
                            recipe.link = link
                            recipe.ingredients = ingredients

                            recipeList!!.add(recipe)

                            recipeAdapter = RecipeListAdapter(recipeList!!, this)
                            layoutManager = LinearLayoutManager(this)

                            //setup list/recyclerview
                            recyclerViewID.layoutManager = layoutManager
                            recyclerViewID.adapter = recipeAdapter


                        }
                        recipeAdapter!!.notifyDataSetChanged()

                    }catch (e: JSONException) { e.printStackTrace() }


        },
                Response.ErrorListener {
                    error: VolleyError? ->
                     try {
                         Log.d("Error",error.toString())
                     }catch (e: JSONException) {
                         e.printStackTrace()
                     }
                })

        volleyRequest!!.add(recipteRequest)
    }
}
