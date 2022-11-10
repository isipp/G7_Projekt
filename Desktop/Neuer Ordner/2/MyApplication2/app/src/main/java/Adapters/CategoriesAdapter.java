package Adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

import DataClasses.Account;
import DataClasses.Categorie;
import database.AccountHelper;
import database.CategoriesHelper;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private ArrayList<Categorie> categoriesList;
    private RecyclerViewInterface recyclerViewInterface;

    public CategoriesAdapter(Context context, ArrayList<Categorie> list,RecyclerViewInterface recyclerViewInterface) {
        categoriesList = list;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView id;
        ImageView DeleteImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_cat);
            id = itemView.findViewById(R.id.id_cat);

            //Image to delete categories
            DeleteImage = itemView.findViewById(R.id.image_delete);
            DeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Position, that we want to delete
                    int position_delete = getAdapterPosition();
                    //Delete account called
                    delete_categories(view,position_delete);
                }
            });
            edit_account(this);

        }


    }

    @NonNull
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from (viewGroup.getContext()).inflate(R.layout.categorie_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.itemView.setTag(categoriesList.get(position));
        viewHolder.id.setText(String.format("%d", categoriesList.get(position).getId()));
        viewHolder.name.setText(categoriesList.get(position).getName());


    }
    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    //Delete account
    public void delete_categories(View itemView, int position_delete){
        CategoriesHelper categoriesHelper = new CategoriesHelper(itemView.getContext());
        //check if objects exist
        if (categoriesList.get(position_delete) != null) {
            categoriesHelper.deleteCat(categoriesList.get(position_delete).getName(), categoriesList.get(position_delete).getId());
            //check if objects exist
            if (categoriesList.size() > 0) {
                categoriesList.remove(categoriesList.get(position_delete));
                //Notify to live update
                notifyItemRemoved(position_delete);
            }
        }
    }
    public void edit_account(CategoriesAdapter.ViewHolder viewHolder){
        // Edit functionality
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.update_layout_categories);
                EditText editName= dialog.findViewById(R.id.editName);
                EditText editId= dialog.findViewById(R.id.editId);
                Button button = dialog.findViewById(R.id.edit_button);

                //current info before editing
                editName.setText(categoriesList.get(position).getName());
                editId.setText(String.valueOf(categoriesList.get(position).getId()));

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = "";
                        String id = "";
                        if(!editName.getText().toString().equals("")) {
                            name =  editName.getText().toString();
                        } else{
                            Toast.makeText(view.getContext(),"Please enter Name!", Toast.LENGTH_SHORT).show();
                        }
                        if(!editId.getText().toString().equals("")) {
                            id =  editId.getText().toString();
                        } else{
                            Toast.makeText(view.getContext(),"Please enter id!", Toast.LENGTH_SHORT).show();
                        }
                        //changing Categories info
                        CategoriesHelper categoriesHelper = new CategoriesHelper(view.getContext());
                        categoriesHelper.changeCat(categoriesList.get(position).getName(),categoriesList.get(position).getId(), name, Integer.parseInt(id));
                        categoriesList.set(position, new Categorie(Integer.parseInt(id), name));


                        dialog.dismiss();
                    }
                });
                dialog.show();
                return false;
            }
        });
    }

}
