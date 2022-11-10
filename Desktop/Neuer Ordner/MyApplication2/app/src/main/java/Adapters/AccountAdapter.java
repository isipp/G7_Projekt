package Adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import Exception.DeleteException;
import com.example.myapplication.R;
import DataClasses.Account;
import database.AccountHelper;

import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

    private ArrayList<Account> accountArrayList;
    private RecyclerViewInterface recyclerViewInterface;

    public AccountAdapter(Context context, ArrayList<Account> list,RecyclerViewInterface recyclerViewInterface) {
        accountArrayList = list;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView accountName, amount;
        ImageView DeleteImage;

        public ViewHolder(@NonNull View itemView, final AdapterView.OnItemClickListener listener) {
            super(itemView);
            //Info, that is shown on Recycle view
            accountName = itemView.findViewById(R.id.name);
            amount = itemView.findViewById(R.id.amount);

            //Image to delete account
            DeleteImage = itemView.findViewById(R.id.image_delete);
            DeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Position, that we want to delete
                    int position_delete = getAdapterPosition();
                    //Delete account called
                    delete_account(view,position_delete);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {


                @Override
                public boolean onLongClick(View view) {
                    recyclerViewInterface.onLongItemClick(getAdapterPosition());
                    return false;
                }
            });
        }


    }


    @NonNull
    @Override
    public AccountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from (viewGroup.getContext()).inflate(R.layout.user_list_items, viewGroup, false);
        return new ViewHolder(view,null);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountAdapter.ViewHolder viewHolder, int position) {
        viewHolder.itemView.setTag(accountArrayList.get(position));
        viewHolder.amount.setText(String.format("%d", accountArrayList.get(position).getAmount()));
        viewHolder.accountName.setText(accountArrayList.get(position).getName());
        //EDIT
        edit_account(viewHolder,position);



    }

//Delete account
    public void delete_account(View itemView, int position_delete){
        AccountHelper accountHelper = new AccountHelper(itemView.getContext());
        //check if objects exist
        if (accountArrayList.get(position_delete) != null) {
            accountHelper.deleteAcc(accountArrayList.get(position_delete).getName(), accountArrayList.get(position_delete).getAmount());
            //check if objects exist
            if (accountArrayList.size() > 0) {
                accountArrayList.remove(accountArrayList.get(position_delete));
                //Notify to live update
                notifyItemRemoved(position_delete);
            }
        }else{
            //Exception
            throw new DeleteException("Nothing to delete");
        }
    }

    // Edit functionality
    public void edit_account(AccountAdapter.ViewHolder viewHolder, int position){
        // Edit functionality
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.update_layout);
                EditText editName= dialog.findViewById(R.id.editName);
                EditText editAmount= dialog.findViewById(R.id.editAmount);
                Button button = dialog.findViewById(R.id.edit_button);

                //current info before editing
                editName.setText(accountArrayList.get(position).getName());
                editAmount.setText(String.valueOf(accountArrayList.get(position).getAmount()));

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = "";
                        String amount = "";
                        if(!editName.getText().toString().equals("")) {
                            name =  editName.getText().toString();
                        } else{
                            Toast.makeText(view.getContext(),"Please enter Name!", Toast.LENGTH_SHORT).show();
                        }
                        if(!editAmount.getText().toString().equals("")) {
                            amount =  editAmount.getText().toString();
                        } else{
                            Toast.makeText(view.getContext(),"Please enter Amount!", Toast.LENGTH_SHORT).show();
                        }
                        //changing Account info
                        AccountHelper accountHelper = new AccountHelper(view.getContext());
                        accountHelper.changeAcc(accountArrayList.get(position).getName(),accountArrayList.get(position).getAmount(), name, Integer.parseInt(amount));
                        accountArrayList.set(position, new Account(Integer.parseInt(amount), name));


                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountArrayList.size();
    }
}
