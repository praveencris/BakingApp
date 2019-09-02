package com.sabkayar.praveen.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.sabkayar.praveen.bakingapp.model.Ingredient;
import com.sabkayar.praveen.bakingapp.model.Recipe;
import com.sabkayar.praveen.bakingapp.ui.activities.RecipeDetailActivity;
import com.sabkayar.praveen.bakingapp.ui.activities.RecipeListActivity;
import com.sabkayar.praveen.bakingapp.utils.Utils;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        Recipe recipe = Utils.JSONUtils.getRecipeBasedOnRecipeId(Utils.getLastSeenRecipeId(context), context);
        views.setTextViewText(R.id.tv_recipe_name, recipe.getName());
        List<Ingredient> ingredientsList = recipe.getIngredients();
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (Ingredient ingredient : ingredientsList) {
            stringBuilder.append(i++).append(" : ").append(ingredient.getQuantity()).append(" ").append(ingredient.getMeasure()).append(" ").append(ingredient.getIngredient()).append("\n");
        }
        views.setTextViewText(R.id.tv_ingredients_details, stringBuilder.toString());

        Intent intent = new Intent(context,RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.RECIPE_EXTRA,recipe);
        PendingIntent pendingIntent = PendingIntent.getActivities(context, 0, new Intent[]{intent}, 0);
        views.setOnClickPendingIntent(R.id.rv_recipe_widget, pendingIntent);




        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

