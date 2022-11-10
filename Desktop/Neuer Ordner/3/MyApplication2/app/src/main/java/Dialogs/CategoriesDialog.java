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

public class CategoriesDialog extends AppCompatDialogFragment {
    private EditText editName;
    private EditText editId;
    private CategoriesDialogListner listner;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Inflaters
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.categories_dialog, null);
        //Positive or Negative answer
        builder.setView(view )
                .setTitle("Adding Categories")
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
                        int id = Integer.parseInt(editId.getText().toString());
                        listner.applyTexts_cat(name,id);
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
            listner = (CategoriesDialogListner) context;
        } catch (ClassCastException o) {
            throw new ClassCastException(context.toString() + "CategoriesDialog");
        }

    }
    //What to do with text, that you entered in Dialog Window. applyTexts fuction is in "Accounts"
    public interface CategoriesDialogListner{
        void applyTexts_cat(String name, int id);
    }
}