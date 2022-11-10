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

import DataClasses.Account;

public class AccountDialog extends AppCompatDialogFragment {
    private EditText editName;
    private EditText editAmount;
    private AccountDialogListner listner;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Inflaters
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.account_dialog, null);
        //Positive or Negative answer
        builder.setView(view)
                .setTitle("Adding Account")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//TODO
                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = editName.getText().toString();
                        int amount = Integer.parseInt(editAmount.getText().toString());
                        listner.applyTexts(name, amount);
                    }
                });
        editName = view.findViewById(R.id.enter_name);
        editAmount = view.findViewById(R.id.enter_amount);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listner = (AccountDialogListner) context;
        } catch (ClassCastException o) {
            throw new ClassCastException(context.toString() + "AccountDialog");
        }

    }
//What to do with text, that you entered in Dialog Window. applyTexts fuction is in "Accounts"
    public interface AccountDialogListner{
        void applyTexts(String name, int amount);
    }
}
