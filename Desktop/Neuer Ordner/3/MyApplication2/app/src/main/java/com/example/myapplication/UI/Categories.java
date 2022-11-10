package com.example.myapplication.UI;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

import Adapters.AccountAdapter;
import Adapters.CategoriesAdapter;
import Adapters.RecyclerViewInterface;
import DataClasses.Account;
import DataClasses.Categorie;
import Dialogs.AccountDialog;
import Dialogs.CategoriesDialog;
import database.AccountHelper;
import database.CategoriesHelper;

public class Categories extends AppCompatActivity implements CategoriesDialog.CategoriesDialogListner, RecyclerViewInterface {

    RecyclerView recyclerView;
    CategoriesAdapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Categorie> categoriesArrayList;
    Button button;


    TextView emptyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);


        emptyList = findViewById(R.id.empty_text);


        categoriesArrayList = new ArrayList<Categorie>();


        displayDatabaseInfo();

        recyclerView = findViewById(R.id.categories_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        myAdapter = new CategoriesAdapter(this, categoriesArrayList,this);
        recyclerView.setAdapter(myAdapter);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }


    private void displayDatabaseInfo() {
        categoriesArrayList.clear();

        Cursor cursor = new CategoriesHelper(this).readAllData();
        int nameIndex = cursor.getColumnIndex("name");
        int idIndex = cursor.getColumnIndex("id");

        while (cursor.moveToNext()){
            String nameCategories = cursor.getString(nameIndex);
            int idCategories = cursor.getInt(idIndex);


            categoriesArrayList.add(new Categorie(idCategories, nameCategories));
        }

    }
    //Opening Dialog window
    public void openDialog(){
        CategoriesDialog categoriesDialog = new CategoriesDialog();
        categoriesDialog.show(getSupportFragmentManager(), "Cat dialog");
    }
    //Adding entered things in DB
    @Override
    public void applyTexts_cat(String name, int id) {
        Context con = this;
        new CategoriesHelper(con).addCat(name, id);
    }

    @Override
    public void onLongItemClick(int position) {
        Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
    }


}
