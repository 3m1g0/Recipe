package app.greeshma.recipe;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "recipe.db";
    private static final String RECIPES_TABLE_NAME = "recipes";
    private static final String RECIPES_COLUMN_NAME = "name";
    private static final String RECIPES_COLUMN_INGREDIENTS = "ingredients";
    private static final String RECIPES_COLUMN_STEPS = "steps";

    DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECIPES_TABLE = "CREATE TABLE " + RECIPES_TABLE_NAME + "("
                + RECIPES_COLUMN_NAME + " TEXT PRIMARY KEY," + RECIPES_COLUMN_INGREDIENTS + " TEXT,"
                + RECIPES_COLUMN_STEPS + " TEXT" + ")";
        db.execSQL(CREATE_RECIPES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RECIPES_TABLE_NAME);
        onCreate(db);
    }

    private List<RecipeItem> getAllRecipes() {
        List<RecipeItem> items = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + RECIPES_TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                RecipeItem recipeItem = new RecipeItem();
                recipeItem.setName(cursor.getString(0));
                recipeItem.setIngredients(cursor.getString(1));
                recipeItem.setSteps(cursor.getString(2));
                items.add(recipeItem);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // return contact list
        return items;
    }

    ArrayList<RecipeItem> getRecipesByIngredients(List<String> ingredients) {
        ArrayList<RecipeItem> items = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "";
        for(String ingredient : ingredients) {
            selectQuery += RECIPES_COLUMN_INGREDIENTS + " LIKE '%" + ingredient + "%' OR ";
        }
        selectQuery = selectQuery.substring(0, selectQuery.length() - 4);

        Log.d("MSPSP", selectQuery);

        Cursor cursor = db.query(RECIPES_TABLE_NAME,
                new String[] { RECIPES_COLUMN_NAME, RECIPES_COLUMN_INGREDIENTS, RECIPES_COLUMN_STEPS },
                selectQuery,
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                RecipeItem recipeItem = new RecipeItem();
                recipeItem.setName(cursor.getString(0));
                recipeItem.setIngredients(cursor.getString(1));
                recipeItem.setSteps(cursor.getString(2));
                items.add(recipeItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        // return contact list
        return items;
    }

    private void addRecipe(RecipeItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RECIPES_COLUMN_NAME, item.getName());
        values.put(RECIPES_COLUMN_INGREDIENTS, item.getIngredients());
        values.put(RECIPES_COLUMN_STEPS, item.getSteps());

        db.insert(RECIPES_TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    List<String> getAllIngredients() {
        List<RecipeItem> recipes = getAllRecipes();
        List<String> ingredients = new ArrayList<>();
        if(recipes != null && !recipes.isEmpty()) {
            for(RecipeItem recipe : recipes) {
                String[] ings = recipe.getIngredients().split(",");
                for(String ing : ings) {
                    ing = ing.trim();
                    ing = ing.toUpperCase();
                    if(!ingredients.contains(ing)) {
                        ingredients.add(ing);
                    }
                }
            }
        }
        Collections.sort(ingredients);
        return ingredients;
    }

    void generateDatabase(Context mContext) {
        List<RecipeItem> recipes = getAllRecipes();
        if(recipes == null || recipes.isEmpty()) {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Creating Database...");
            progressDialog.show();
            initDatabase();
            progressDialog.dismiss();
        }
    }

    private void initDatabase() {
        addRecipe(new RecipeItem("Egg, Salt, Chilli Powder, Oil", "Omellette", "1. Break eggs as required and take it in a bowl\n2. Beat them and add salt, chilli powder to the mixture\n3. Heat oil on the pan and put the egg mixture on it\n4. Fry on both sides."));
        addRecipe(new RecipeItem("Curd,salt, water", "Butter Milk", "1. Take curd in a glass and whisk it\n2. Add water, salt and whisk it."));
        addRecipe(new RecipeItem("Apple, grapes, banana, melon, pepper", "Fruit Salad", "1. Cut available fruits into smaller pieces and add pepper"));
        addRecipe(new RecipeItem("White/brown rice, water", "Rice", "1. Take rice in a bowl\n2. Fill it with 1:2 ratios of water and put it to boil."));
        addRecipe(new RecipeItem("Egg,Water, Pepper, salt", "Boiled Egg", "1. Take eggs, put it to boil\n2. When it is done peel the outer layer\n3. Cut into halves add salt and pepper. "));
        addRecipe(new RecipeItem("Pasta, veggies, water, oil ,salt and pepper", "Pasta", "1. Boil pasta in water till it turns soft\n2. Drain the water\n3. Take a pan add oil shallow fry all veggies\n4. Add salt and pepper\n5. And add pasta."));
        addRecipe(new RecipeItem("Ginger-garlic paste, salt, lime, pepper, oil, chicken", "Grilled chicken", "1. Take chicken\n2. Add ginger-garlic paste\n3. Add salt, pepper and lime\n4. Put oil and marinate well\n5. Bake it at 400F for 20 minutes."));
        addRecipe(new RecipeItem("Flour, sugar, water, oil", "Pan cakes", "1. Mix flour with water till it becomes a thick batter\n2. Add sugar\n3. Take a pan\n4. Add oil and put the batter on pan\n5. Heat it on both sides"));
        addRecipe(new RecipeItem("Chocolate ice-cream, milk and ice", "Chocolate milkshake", "1. Take chocolate ice-cream in a mixer\n2. Add milk and ice\n3. Blend it."));
        addRecipe(new RecipeItem("Bread, veggies, salt and pepper", "Sandwich", "1. Toast bread\n2. Shallow fry veggies\n3. Add salt and pepper\n4. Put these veggies on toast."));
        addRecipe(new RecipeItem("Lemon, salt, sugar, water", "Lemonade", "1. Add lemon, salt, sugar to water\n2. Mix it well"));
        addRecipe(new RecipeItem("Shrimp, oil, salt, pepper, ginger-garlic paste", "Shrimp fry", "1. Mix shrimp, oil, salt,pepper and ginger garlic paste\n2. Bake it at 400F for 20 minutes"));
        addRecipe(new RecipeItem("Noodles, seasoning, water", "Maggi", "1. Boil water\n2. Add seasoning and noodles\n3. Boil it "));
        addRecipe(new RecipeItem("Tomatoes, water, salt, pepper", "Tomato soup", "1. Boil tomatoes\n2. Peel the outer layer, blend it\n3. Boil the blended paste\n4. Add water, salt and pepper."));
        addRecipe(new RecipeItem("Fish, salt, pepper, oil, lime, ginger, garlic paste", "Fish fry", "1. Make ginger- garlic paste\n2. Add lime, oil, salt, pepper, marinate fish with this mixture and bake it at 350F for 30 mins."));
        addRecipe(new RecipeItem("Tortillas, salt, pepper, lettuce, veggies", "Lettuce wrap", "1. Shallow fry veggies in oil\n2. Add salt, pepper\n3. Take tortillas, place a lettuce wrap and fill in with veggies\n4. Roll it as a wrap."));
        addRecipe(new RecipeItem("Onions, oil, tomatoes, veggies, rice, water, salt, pepper,ginger-garlic paste, spices", "Mixed veg rice", "1. Put oil in a cooker\n2. Add onions,tomatoes,ginger garlic paste, spices, salt and pepper\n3. When it is cooked, add rice, add water 1:2 ratio and pressure cook it"));
        addRecipe(new RecipeItem("Flour, sugar, water, butter, choco-chips, egg, baking powder", "Cookies", "1. Make a soft dough with flour,sugar,water,butter,choco-chips,eggs and baking powder\n2. Flatten the dough and cut into squared pieces\n3. Bake these pieces at 400F for 25 mins"));
        addRecipe(new RecipeItem("Melon, grapes, apples, cucumber, carrot, sugar, water", "Smoothie", "1. Blend melon,grapes,apples,cucumbers and carrot\n2. add sugar and water\n3. Blend again, serve it in a glass"));
        addRecipe(new RecipeItem("Hersheys chocolate syrup, milk, water, ice cubes", "Hershey's milk shake", "1. Blend hersheys chocolate syrup with milk and water\n2. Add ice cubes"));
    }
}
