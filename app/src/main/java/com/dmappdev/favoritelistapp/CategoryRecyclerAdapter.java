package com.dmappdev.favoritelistapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryViewHolder>{

//    String[] categories = {"Hobbies", "Games", "Food", "Gadgets", "Family", "Kitchen"};

    interface CategoryClickedInterface{
        void categoryClick(Category category);
    }

    private ArrayList<Category> categories;
    private CategoryClickedInterface categoryClickedInterface;

    public CategoryRecyclerAdapter(ArrayList<Category> categories, CategoryClickedInterface categoryClickedInterface) {
        this.categories = categories;
        this.categoryClickedInterface = categoryClickedInterface;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.category_viewholder, parent, false);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, final int position) {
        holder.getTxtCategoryNumber().setText(Integer.toString(position + 1));
        holder.getTxtCategoryName().setText(categories.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryClickedInterface.categoryClick(categories.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void addCategory(Category category){
        categories.add(category);
        notifyItemInserted(categories.size() - 1);
    }
}
