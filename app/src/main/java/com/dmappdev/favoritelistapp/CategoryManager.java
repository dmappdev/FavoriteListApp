package com.dmappdev.favoritelistapp;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

public class CategoryManager {

    private Context mContext;

    public CategoryManager(Context context) {
        mContext = context;
    }

    public void saveCategory(Category category) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        HashSet itemsHashSet = new HashSet(Arrays.asList(category.getItems()));

        editor.putStringSet(category.getName(), itemsHashSet)
                .apply();
    }

    public ArrayList<Category> getCategories() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        Map<String, ?> data = sharedPreferences.getAll();
        ArrayList<Category> categories = new ArrayList<>();
        for (Map.Entry<String, ?> pair: data.entrySet()) {
            Category category = new Category(pair.getKey(),
                    new ArrayList<String>((HashSet) pair.getValue()));

            categories.add(category);
        }
        return categories;
    }

}
