package com.example.bakingappnano;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.bakingappnano.dummy.DummyContent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ItemListActivity extends AppCompatActivity {
    TextView textViewingredient;

    private boolean TwoPane;
    List<RecipeDetails.StepDetails> stepList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        textViewingredient=findViewById(R.id.id_tv_ingre);


        RecipeDetails recipeDetails= (RecipeDetails) getIntent().getSerializableExtra("resultData");
        setTitle(recipeDetails.getName());
        stepList=recipeDetails.getStepsList();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(recipeDetails.getName());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (findViewById(R.id.item_detail_container) != null) {
            TwoPane = true;
        }

        List<RecipeDetails.IngredientsDetails> resultIngredient=recipeDetails.getIngredientsList();
        String ingredient="";
        for(int i=0;i<resultIngredient.size();i++){
            ingredient+=(i+1)+". "+resultIngredient.get(i).getQuantity()+" "+resultIngredient.get(i).getMeasure()+" of "+resultIngredient.get(i).getIngredient()+"\n";
        }
        BakingWidgetServices.seeviceCall(this,ingredient);

        textViewingredient.setText(ingredient);
        Log.i("result",resultIngredient.get(1).getIngredient());
        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        recyclerView.setFocusable(false);// Not to set recycle position  directly when activity opened
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, stepList, TwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ItemListActivity ParentActivity;
        private final List<RecipeDetails.StepDetails> Values;
        private final boolean TwoPane;

        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      List<RecipeDetails.StepDetails> items,
                                      boolean twoPane) {
            Values = items;
            ParentActivity = parent;
            TwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.IdView.setText(Values.get(position).getShortDescription());
            holder.IdView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RecipeDetails.StepDetails resultStep=  Values.get(position);

                    if (TwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putSerializable("resultStep",  resultStep);
                        ItemDetailFragment fragment = new ItemDetailFragment();
                        fragment.setArguments(arguments);
                        ParentActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {
                        Intent intent=new Intent(v.getContext(),ItemDetailActivity.class);
                        intent.putExtra("resultStep",  resultStep);
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return Values.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder  {
            final Button IdView;

            ViewHolder(View view) {
                super(view);
                IdView =  view.findViewById(R.id.id_text);
            }

        }
    }
}
