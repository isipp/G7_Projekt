package Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.UI.TransactionDetail;

import java.util.ArrayList;
import java.util.List;

import DataClasses.Transaction;
import DataClasses.TransactionType;


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private List<Transaction> taList;

    public TransactionAdapter(Context context, List<Transaction> list) {
        taList = list;
    }

    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from (viewGroup.getContext()).inflate(R.layout.transaction_list_item, viewGroup, false);
        return new TransactionAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder viewHolder, int position) {
        Transaction ta = taList.get(position);
        viewHolder.itemView.setTag(taList.get(position));
        viewHolder.title.setText(ta.getTitle());
        String amountText = String.format("%.2f", ta.getAmount());
        viewHolder.amount.setText((ta.getType() == TransactionType.EXPENSE ? "-" : "+") + amountText);
        viewHolder.categories.setText(ta.getCategories());


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TransactionDetail.class);
                //intent.putExtra("ID", accountArrayList.get(position).getId());
                //intent.putExtra("NAME", accountArrayList.get(position).getName());
                v.getContext().startActivity(intent);
                System.out.println("[APP] ta adapter: clicked on ta");
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, amount, categories;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            amount = itemView.findViewById(R.id.amount);
            categories = itemView.findViewById(R.id.categories_list);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // still to be implemented
                    //System.out.println("[APP] ta adapter: clicked on " + view.findViewById(R.id.id));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return taList.size();
    }
}


///////////////



/*








public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

    private ArrayList<Account> accountArrayList;

    public AccountAdapter(Context context, ArrayList<Account> list) {
        accountArrayList = list;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView accountName, id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            accountName = itemView.findViewById(R.id.name);
            id = itemView.findViewById(R.id.id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // still to be implemented
                }
            });
        }
    }

    @NonNull
    @Override
    public AccountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from (viewGroup.getContext()).inflate(R.layout.user_list_items, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountAdapter.ViewHolder viewHolder, int position) {
        viewHolder.itemView.setTag(accountArrayList.get(position));
        viewHolder.id.setText(String.format("%d", accountArrayList.get(position).getId()));
        viewHolder.accountName.setText(accountArrayList.get(position).getName());


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Account.class);
                intent.putExtra("ID", accountArrayList.get(position).getId());
                intent.putExtra("NAME", accountArrayList.get(position).getName());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountArrayList.size();
    }
}
*/