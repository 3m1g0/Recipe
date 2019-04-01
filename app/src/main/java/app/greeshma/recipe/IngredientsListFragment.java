package app.greeshma.recipe;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class IngredientsListFragment extends Fragment {

    public IngredientsListFragment() {
    }

    public static IngredientsListFragment newInstance() {
        IngredientsListFragment fragment = new IngredientsListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingredients_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final DBHelper dbHelper = new DBHelper(getActivity());
        dbHelper.generateDatabase(getActivity());

        List<String> ingredients = dbHelper.getAllIngredients();

        RecyclerView ingredientsRecycler = view.findViewById(R.id.home_ingredients_list);
        ingredientsRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ingredientsRecycler.addItemDecoration(new GridSpacingItemDecoration(getActivity(), R.dimen.item_offset));
        final IngredientsListAdapter ingredientsListAdapter = new IngredientsListAdapter(ingredients, getActivity());
        ingredientsRecycler.setAdapter(ingredientsListAdapter);

        Button searchBtn = view.findViewById(R.id.home_search_recipes);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> selectedIngredients = ingredientsListAdapter.getSelectedIngredients();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, RecipeListFragment.newInstance(selectedIngredients));
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
            }
        });
    }
}
