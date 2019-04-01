package app.greeshma.recipe;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private ArrayList<RecipeItem> recipeItems;
    private Context mContext;
    private LayoutInflater inflater;

    public RecipeListAdapter(ArrayList<RecipeItem> recipeItems, Context mContext) {
        this.recipeItems = recipeItems;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.recipe_list_item, viewGroup, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeViewHolder holder, final int i) {
        final RecipeItem recipeItem = recipeItems.get(i);
        holder.name.setText(recipeItem.getName());
        holder.ings.setText(recipeItem.getIngredients());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSteps(recipeItem.getSteps());
            }
        });
    }

    private void showSteps(String steps) {
        // custom dialog
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.recipe_steps);

        TextView recipeSteps = dialog.findViewById(R.id.recipe_steps);
        recipeSteps.setText(steps);

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return recipeItems.size();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView ings;

        RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.recipe_name);
            ings = (TextView) itemView.findViewById(R.id.recipe_ingredients);
        }
    }
}
