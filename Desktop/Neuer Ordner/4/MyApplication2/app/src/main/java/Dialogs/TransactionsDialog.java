package Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myapplication.R;

public class TransactionsDialog extends AppCompatDialogFragment {
    private EditText transaction_from;
    private EditText amount_transactions;
    private EditText type_trans;
    private EditText categories_transaction;

    private TransactionsDialogListner listner;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Inflaters
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.transaction_dialog, null);
        //Positive or Negative answer
        builder.setView(view )
                .setTitle("Adding Transactions")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//TODO
                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String from = transaction_from.getText().toString();
                        int amount = Integer.parseInt(amount_transactions.getText().toString());
                        String type = type_trans.getText().toString();
                        String categories = categories_transaction.getText().toString();
                        listner.applyTexts_trans(from,amount, type, categories);
                    }
                });
        editName = view.findViewById(R.id.enter_name_cat);
        editId= view.findViewById(R.id.enter_id_cat);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listner = (TransactionsDialogListner) context;
        } catch (ClassCastException o) {
            throw new ClassCastException(context.toString() + "CategoriesDialog");
        }

    }
    //What to do with text, that you entered in Dialog Window. applyTexts fuction is in "Accounts"
    public interface TransactionsDialogListner{
        void applyTexts_trans(String from, int amount, String type, String categories);
    }
}