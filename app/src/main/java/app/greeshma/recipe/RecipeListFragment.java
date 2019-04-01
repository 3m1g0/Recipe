package app.greeshma.recipe;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class RecipeListFragment extends Fragment {

    private List<String> ingredients;

    public RecipeListFragment() {
        // Required empty public constructor
    }

    public static RecipeListFragment newInstance(ArrayList<String> ingredients) {
        RecipeListFragment fragment = new RecipeListFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("ingredients", ingredients);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ingredients = getArguments().getStringArrayList("ingredients");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final DBHelper dbHelper = new DBHelper(getActivity());
        String ings = "";
        ArrayList<RecipeItem> recipeList = dbHelper.getRecipesByIngredients(ingredients);
        for(RecipeItem item : recipeList) {
            Log.d("MSPSP", item.getName());
            ings += item.getName() + ", ";
        }

        ings = ings.substring(0, ings.length() - 2);

        TextView header = view.findViewById(R.id.home_recipes_header);
        header.setText("Recipes related to " + ings);
        RecyclerView recipesRecycler = view.findViewById(R.id.recipe_recycler);
        recipesRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recipesRecycler.addItemDecoration(new GridSpacingItemDecoration(getActivity(), R.dimen.item_offset));
        final RecipeListAdapter recipesListAdapter = new RecipeListAdapter(recipeList, getActivity());
        recipesRecycler.setAdapter(recipesListAdapter);
    }
}
