package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myapplication.R;

public class Home extends AppCompatActivity {


    ImageView one;
    Button Accounts;
    Button Categories;
    Button Transactions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        one = findViewById(R.id.one);

        Accounts = findViewById(R.id.Accounts);
        Categories = findViewById(R.id.Categories);
        Transactions = findViewById(R.id.Transactions);

    }

    public void onClickAccounts(View view) {
        Intent intent = new Intent(this, Accounts.class);
        startActivity(intent);
    }
    public void onClickCategories(View view) {
        Intent intent = new Intent(this, Categories.class);
        startActivity(intent);
    }
    public void onClickTransactions(View view) {
        Intent intent = new Intent(this, Transactions.class);
        startActivity(intent);
    }
}