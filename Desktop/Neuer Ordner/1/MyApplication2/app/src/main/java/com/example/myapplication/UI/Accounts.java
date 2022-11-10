package com.example.myapplication.UI;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

import Adapters.AccountAdapter;
import Adapters.RecyclerViewInterface;
import DataClasses.Account;
import Dialogs.AccountDialog;
import database.AccountHelper;

public class Accounts extends AppCompatActivity  implements AccountDialog.AccountDialogListner, RecyclerViewInterface {

    RecyclerView rView;
    RecyclerView.Adapter Adapter;
    RecyclerView.LayoutManager lManager;
    ArrayList<Account> accountArray;
    Button but;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        //List of Accounts
        accountArray = new ArrayList<Account>();

        displayDatabaseInfo();
        //recycler View
        rView = findViewById(R.id.all_users_list);
        rView.setHasFixedSize(true);
        //layout manager
        lManager = new LinearLayoutManager(this);
        rView.setLayoutManager(lManager);
        //Adapter
        Adapter = new AccountAdapter(this, accountArray,this);
        rView.setAdapter(Adapter);

        but = findViewById(R.id.updateAll);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Opening dialog to create new Account
                openDialog();
            }
        });
    }

    //Displaying all the data
    private void displayDatabaseInfo() {
        accountArray.clear();

        Cursor cursor = new AccountHelper(this).readAllData();
        int amountIndex = cursor.getColumnIndex("amount");
        int nameIndex = cursor.getColumnIndex("name");

        while (cursor.moveToNext()){
            String nameAccount = cursor.getString(nameIndex);
            int amountAccount = cursor.getInt(amountIndex);

            //Adding everything from database in array to show it
            accountArray.add(new Account(amountAccount, nameAccount));
        }
    }
    //Opening Dialog window
    public void openDialog(){
         AccountDialog accountDialog = new AccountDialog();
         accountDialog.show(getSupportFragmentManager(), "Account dialog");
    }


    //Adding entered things in DB
    @Override
    public void applyTexts(String name, int amount) {
        //Adding new account to Database
        Context con = this;
         new AccountHelper(con).addAcc(name, amount);
    }

    @Override
    public void onLongItemClick(int position) {
        Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
    }

}
