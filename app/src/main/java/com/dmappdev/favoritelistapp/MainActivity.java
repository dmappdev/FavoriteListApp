package com.dmappdev.favoritelistapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CategoryRecyclerAdapter.CategoryClickedInterface {

    private RecyclerView categoryRecyclerView;
    private FloatingActionButton fab;
    private CategoryManager mCategoryManager = new CategoryManager(this);

    public static final String CATEGORY_OBJECT_KEY = "CATEGORY_KEY";
    public static final int MAIN_ACTIVITY_REQUEST_CODE = 1;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayCreateCategoryDialog();
//                Toast.makeText(MainActivity.this,"Floating button", Toast.LENGTH_SHORT)
//                        .show();
            }
        });

        setSupportActionBar(toolbar);
        ArrayList<Category> categories = mCategoryManager.getCategories();

        categoryRecyclerView = findViewById(R.id.category_recycler_view);
        categoryRecyclerView.setAdapter(new CategoryRecyclerAdapter(categories, this));
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void displayCreateCategoryDialog() {
        String alertTitle = getString(R.string.create_category);
        String positiveButtonTitle = getString(R.string.positive_button_title);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText categoryEditText = new EditText(this);
        categoryEditText.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setTitle(alertTitle);
        builder.setView(categoryEditText);

        builder.setPositiveButton(positiveButtonTitle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Category category = new Category(categoryEditText.getText().toString(),
                        new ArrayList<String>());
                mCategoryManager.saveCategory(category);

                CategoryRecyclerAdapter categoryRecyclerAdapter = (CategoryRecyclerAdapter) categoryRecyclerView.getAdapter();
                categoryRecyclerAdapter.addCategory(category);
                dialogInterface.dismiss();
                displayCategoryItems(category);
            }
        });
        builder.create().show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayCategoryItems(Category category) {
        Intent categoryIntent = new Intent(MainActivity.this, CategoryItemsActivity.class);

        categoryIntent.putExtra(CATEGORY_OBJECT_KEY, category);
        startActivityForResult(categoryIntent, MAIN_ACTIVITY_REQUEST_CODE);
    }

//    CategoryRecyclerAdapter.CategoryClickedInterface method

    @Override
    public void categoryClick(Category category) {
        displayCategoryItems(category);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MAIN_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            if (data != null){
                mCategoryManager.saveCategory((Category) data.getSerializableExtra(CATEGORY_OBJECT_KEY));
                updateCategories();
            }
        }
    }

    private void updateCategories() {

        ArrayList<Category> categories = mCategoryManager.getCategories();
        categoryRecyclerView.setAdapter(new CategoryRecyclerAdapter(categories, this));

    }
}