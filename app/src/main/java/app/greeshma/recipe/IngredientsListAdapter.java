package app.greeshma.recipe;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.IngredientViewHolder> {

    private List<String> ingredients;
    private ArrayList<String> selectedIngredients;
    private Context mContext;
    private LayoutInflater inflater;

    public IngredientsListAdapter(List<String> ingredients, Context mContext) {
        this.ingredients = ingredients;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
        this.selectedIngredients = new ArrayList<>();
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.ingredient_list_item, viewGroup, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final IngredientViewHolder holder, final int i) {
        final String ingred = ingredients.get(i);
        holder.checkBox.setText(ingred);
        holder.checkBox.setChecked(this.selectedIngredients.contains(ingred));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!selectedIngredients.contains(ingred))
                        selectedIngredients.add(ingred);
                } else {
                    selectedIngredients.remove(ingred);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public ArrayList<String> getSelectedIngredients() {
        return this.selectedIngredients;
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;

        IngredientViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = (CheckBox) itemView.findViewById(R.id.ingredient_list_checkbox);
            this.setIsRecyclable(false);
        }
    }
}
