package com.example.shado.buylist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.shado.buylist.ListDetail.decreaseQuantity;
import static com.example.shado.buylist.ListDetail.increaseQuantity;


public class DummyListAdapter extends RecyclerView.Adapter<DummyListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Item> mItems;


    public DummyListAdapter(Context context, ArrayList<Item> data) {
        this.context = context;
        mItems = new ArrayList();
        this.mItems = data;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView name;
        TextView num;
        ImageView remove;
        ImageView add;

        private MyViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;
            this.name = itemView.findViewById(R.id.item_name);
            this.num = itemView .findViewById(R.id.item_num);
            this.remove = itemView.findViewById(R.id.remove);
            this.add = itemView.findViewById(R.id.add);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_dummy_list_item, parent, false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.name.setText(mItems.get(position).getName());
       if(mItems.get(position).getQuantity() == 0){
           holder.remove.setVisibility(View.INVISIBLE);
           holder.num.setVisibility(View.INVISIBLE);
         //  holder.remove.setImageResource(R.drawable.ic_remove_circle_black_24dp);
        //  holder.add.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.MULTIPLY);
       }
       else if (mItems.get(position).getQuantity() == 1){
           holder.num.setText(String.valueOf(mItems.get(position).getQuantity()));
           holder.remove.setVisibility(View.VISIBLE);
           holder.num.setVisibility(View.VISIBLE);
           holder.remove.setImageResource(R.drawable.ic_cancel_black_24dp);
       }

       else{
           holder.num.setText(String.valueOf(mItems.get(position).getQuantity()));
           holder.remove.setVisibility(View.VISIBLE);
           holder.num.setVisibility(View.VISIBLE);
           holder.remove.setImageResource(R.drawable.ic_remove_circle_black_24dp);
       }
       holder.add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               increaseQuantity(position,context);
           }
       });

       holder.remove.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               decreaseQuantity(position,context);
           }
       });

    }

    @Override
    public int getItemCount() {
        if(mItems== null) return 0;
       else  return mItems.size();
    }
}
