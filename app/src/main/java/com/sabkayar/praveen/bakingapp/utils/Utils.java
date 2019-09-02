package com.sabkayar.praveen.bakingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.sabkayar.praveen.bakingapp.model.Ingredient;
import com.sabkayar.praveen.bakingapp.model.Recipe;
import com.sabkayar.praveen.bakingapp.model.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static class JSONUtils {
        static String loadJSONFromAsset(Context context) {
            String json;
            try {
                InputStream inputStream = context.getAssets().open("baking.json");
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                inputStream.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;
        }

        private static final String ID = "id";
        private static final String NAME = "name";
        private static final String INGREDIENTS = "ingredients";
        private static final String STEPS = "steps";
        private static final String SERVINGS = "servings";
        private static final String IMAGE = "image";

        public static List<Recipe> getRecipes(Context context) {
            List<Recipe> recipeList = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(loadJSONFromAsset(context));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    Recipe recipe = new Recipe();
                    recipe.setId(json.optInt(ID));
                    recipe.setName(json.optString(NAME));
                    recipe.setIngredients(Utils.JSONUtils.getIngredients(json.getString(INGREDIENTS)));
                    recipe.setSteps(Utils.JSONUtils.getSteps(json.getString(STEPS)));
                    recipe.setServings(json.optInt(SERVINGS));
                    recipe.setImage(json.optString(IMAGE));
                    recipeList.add(recipe);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return recipeList;
        }


        private static final String QUANTITY = "quantity";
        private static final String MEASURE = "measure";
        private static final String INGREDIENT = "ingredient";

        static List<Ingredient> getIngredients(String ingredients) {
            List<Ingredient> ingredientList = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(ingredients);
                Utils utils = new Utils();
                Recipe recipe = new Recipe();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    Ingredient ingredient = new Ingredient();
                    ingredient.setQuantity(json.optInt(QUANTITY));
                    ingredient.setMeasure(json.optString(MEASURE));
                    ingredient.setIngredient(json.optString(INGREDIENT));
                    ingredientList.add(ingredient);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return ingredientList;
        }

        private static final String STEP_ID = "id";
        private static final String SHORT_DESCRIPTION = "shortDescription";
        private static final String DESCRIPTION = "description";
        private static final String VIDEO_URL = "videoURL";
        private static final String THUMBNAIL_URL = "thumbnailURL";

        static List<Step> getSteps(String steps) {
            List<Step> stepList = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(steps);
                Recipe recipe = new Recipe();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    Step step = new Step();
                    step.setId(json.optInt(STEP_ID));
                    step.setShortDescription(json.optString(SHORT_DESCRIPTION));
                    step.setDescription(json.optString(DESCRIPTION));
                    step.setVideoURL(json.optString(VIDEO_URL));
                    step.setThumbnailURL(json.optString(THUMBNAIL_URL));
                    stepList.add(step);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return stepList;
        }


        public static Recipe getRecipeBasedOnRecipeId(int recipeId, Context context) {
            Recipe recipe = null;
            try {
                JSONArray jsonArray = new JSONArray(loadJSONFromAsset(context));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    if (json.optInt(ID) == recipeId) {
                        recipe = new Recipe();
                        recipe.setId(json.optInt(ID));
                        recipe.setName(json.optString(NAME));
                        recipe.setIngredients(Utils.JSONUtils.getIngredients(json.getString(INGREDIENTS)));
                        recipe.setSteps(Utils.JSONUtils.getSteps(json.getString(STEPS)));
                        recipe.setServings(json.optInt(SERVINGS));
                        recipe.setImage(json.optString(IMAGE));
                        break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return recipe;
        }

    }


    private static final String SHARED_PREF_RECIPE = "shared_pref_recipe";
    private static final String KEY_RECIPE_ID = "key_recipe_id";

    public static void saveLastSeenRecipeId(Context context, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_RECIPE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_RECIPE_ID, value);
        editor.apply();
    }

    public static int getLastSeenRecipeId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_RECIPE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_RECIPE_ID, 1);
    }


}
