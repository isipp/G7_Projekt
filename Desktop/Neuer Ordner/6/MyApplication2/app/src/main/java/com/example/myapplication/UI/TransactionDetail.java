package com.example.myapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.TransactionAdapter;
import DataClasses.Transaction;
import DataClasses.TransactionType;
import database.TransactionHelper;

public class TransactionDetail extends AppCompatActivity {

    private List<Transaction> taList = new ArrayList<>();

    //RecyclerView rView;
    //RecyclerView.Adapter Adapter;
    //RecyclerView.LayoutManager lManager;

    Button addButton, changeButton;

    TransactionType typeSetting = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_detail);


        addButton = findViewById(R.id.ta_add_btn);
        //changeButton = findViewById(R.id.ta_change_type_btn);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                System.out.println("[APP] ta ui: add btn clicked");


            }
        });
    /*
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                System.out.println("[APP] ta ui: chnage btn clicked");
                changeTypeSetting();
            }
        });*/



    }



    private TransactionType nextType(TransactionType before) {
        if(before == null) return TransactionType.EXPENSE;
        else switch(before) {
            case EXPENSE:
                return TransactionType.INCOME;
            case INCOME:
                return null;
        }
        return null;
    }

    private String nameOf(TransactionType type) {
        if(type == null) return "ALL";
        else return type.name() + "S";
    }



    public void onClickTransaction(View view) {
        Intent intent = new Intent(this, Accounts.class);
        startActivity(intent);
    }



}
