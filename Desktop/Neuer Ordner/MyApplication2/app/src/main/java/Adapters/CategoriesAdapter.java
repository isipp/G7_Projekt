package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

import DataClasses.Categorie;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private ArrayList<Categorie> categoriesList;

    public CategoriesAdapter(Context context, ArrayList<Categorie> list) {
        categoriesList = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_cat);
            id = itemView.findViewById(R.id.id_cat);
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

}
