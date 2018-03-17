package com.example.shado.buylist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.shado.buylist.ListDetail.toggleChecked;


public class DetailedListAdapter extends RecyclerView.Adapter<DetailedListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Item> mItems;


    public DetailedListAdapter(Context context, ArrayList<Item> data) {
        this.context = context;
        mItems = new ArrayList();
        this.mItems = data;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView name;
        TextView num;
        CheckBox checkBox;

        private MyViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;
            this.name = itemView.findViewById(R.id.item_name);
            this.num = itemView .findViewById(R.id.item_num);
            this.checkBox = itemView.findViewById(R.id.checkbox);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_detail_list_item, parent, false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.name.setText(mItems.get(position).getName());
         holder.checkBox.setChecked(mItems.get(position).isChecked);
    if (mItems.get(position).getQuantity() == 1){
           holder.num.setVisibility(View.INVISIBLE);
    }
    else{
        holder.num.setText(String.valueOf(mItems.get(position).getQuantity()));
        holder.num.setVisibility(View.VISIBLE);
    }
    holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            toggleChecked(position,b);
        }
    });
    }

    @Override
    public int getItemCount() {
        if(mItems== null) return 0;
       else  return mItems.size();
    }
}
