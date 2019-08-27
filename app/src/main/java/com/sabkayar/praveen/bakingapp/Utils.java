package com.sabkayar.praveen.bakingapp;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Utils {
    static class JSONUtils {

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
            List<Utils.Recipe> recipeList = new ArrayList<>();
            try {
                Utils utils = new Utils();
                JSONArray jsonArray = new JSONArray(loadJSONFromAsset(context));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    Utils.Recipe recipe = utils.new Recipe();
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

        static List<Recipe.Ingredient> getIngredients(String ingredients) {
            List<Recipe.Ingredient> ingredientList = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(ingredients);
                Utils utils = new Utils();
                Recipe recipe = utils.new Recipe();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    Recipe.Ingredient ingredient = recipe.new Ingredient();
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

        static List<Recipe.Step> getSteps(String steps) {
            List<Recipe.Step> stepList = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(steps);
                Utils utils = new Utils();
                Recipe recipe = utils.new Recipe();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    Recipe.Step step = recipe.new Step();
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
    }
    public class Recipe {
        private Integer id;
        private String name;
        private java.util.List<Ingredient> ingredients = null;
        private List<Step> steps = null;
        private Integer servings;
        private String image;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();


        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Ingredient> getIngredients() {
            return ingredients;
        }

        public void setIngredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
        }

        public List<Step> getSteps() {
            return steps;
        }

        public void setSteps(List<Step> steps) {
            this.steps = steps;
        }

        public Integer getServings() {
            return servings;
        }

        public void setServings(Integer servings) {
            this.servings = servings;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        public class Ingredient {
            private Integer quantity;
            private String measure;
            private String ingredient;
            private Map<String, Object> additionalProperties = new HashMap<String, Object>();

            public Integer getQuantity() {
                return quantity;
            }

            public void setQuantity(Integer quantity) {
                this.quantity = quantity;
            }

            public String getMeasure() {
                return measure;
            }

            public void setMeasure(String measure) {
                this.measure = measure;
            }


            public String getIngredient() {
                return ingredient;
            }

            public void setIngredient(String ingredient) {
                this.ingredient = ingredient;
            }

            public Map<String, Object> getAdditionalProperties() {
                return this.additionalProperties;
            }

            public void setAdditionalProperty(String name, Object value) {
                this.additionalProperties.put(name, value);
            }

        }

        public class Step {

            private Integer id;
            private String shortDescription;
            private String description;
            private String videoURL;
            private String thumbnailURL;
            private Map<String, Object> additionalProperties = new HashMap<String, Object>();


            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }


            public String getShortDescription() {
                return shortDescription;
            }


            public void setShortDescription(String shortDescription) {
                this.shortDescription = shortDescription;
            }


            public String getDescription() {
                return description;
            }


            public void setDescription(String description) {
                this.description = description;
            }


            public String getVideoURL() {
                return videoURL;
            }


            public void setVideoURL(String videoURL) {
                this.videoURL = videoURL;
            }


            public String getThumbnailURL() {
                return thumbnailURL;
            }


            public void setThumbnailURL(String thumbnailURL) {
                this.thumbnailURL = thumbnailURL;
            }


            public Map<String, Object> getAdditionalProperties() {
                return this.additionalProperties;
            }


            public void setAdditionalProperty(String name, Object value) {
                this.additionalProperties.put(name, value);
            }

        }

    }
}
