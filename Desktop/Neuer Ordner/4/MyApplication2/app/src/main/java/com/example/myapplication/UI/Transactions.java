package com.example.myapplication.UI;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import java.util.ArrayList;
import java.util.List;

import Adapters.AccountAdapter;
import Adapters.TransactionAdapter;
import DataClasses.Transaction;
import DataClasses.TransactionType;
import Dialogs.CategoriesDialog;
import Dialogs.TransactionsDialog;
import database.TransactionHelper;

public class Transactions extends AppCompatActivity {

    private List<Transaction> taList = new ArrayList<>();

    RecyclerView rView;
    RecyclerView.Adapter Adapter;
    RecyclerView.LayoutManager lManager;
    Button addButton, changeButton;
    TransactionType typeSetting = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        rView = findViewById(R.id.transaction_list);
        rView.setHasFixedSize(true);
        //layout manager
        lManager = new LinearLayoutManager(this);
        rView.setLayoutManager(lManager);

        this.setSelectionTypeTo(this.typeSetting);


        addButton = findViewById(R.id.ta_add_btn);
        changeButton = findViewById(R.id.ta_change_type_btn);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                System.out.println("[APP] ta ui: add btn clicked");
                openDialog();

            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                System.out.println("[APP] ta ui: chnage btn clicked");
                changeTypeSetting();
            }
        });



    }

    private void changeTypeSetting() {
        this.typeSetting = nextType(this.typeSetting);
        setSelectionTypeTo(typeSetting);
        changeButton.setText("SHOW " + this.nameOf(nextType(this.typeSetting)));
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

    private void setSelectionTypeTo(TransactionType type) {
        if(type == null) {
            this.taList = new TransactionHelper(this).getAll();
        } else {
            this.taList = new TransactionHelper(this).getAllOfType(type);
        }
        Adapter = new TransactionAdapter(this, taList);
        rView.setAdapter(Adapter);
    }


    public void onClickTransaction(View view) {
        Intent intent = new Intent(this, Accounts.class);
        startActivity(intent);
    }
    public void openDialog(){
        TransactionsDialog transactionsDialog = new TransactionsDialog();
        transactionsDialog.show(getSupportFragmentManager(), "Trans dialog");
    }
}
