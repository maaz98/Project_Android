package com.example.shado.buylist;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {


    private Context context;
    private ArrayList<List> mItems;


    public Adapter(Context context, ArrayList<List> data) {
        this.context = context;
        mItems = new ArrayList();
        this.mItems = data;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView name;
        TextView num;
        ImageView more;
        ProgressBar bar;

        private MyViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;
            this.name = itemView.findViewById(R.id.title);
            this.num = itemView .findViewById(R.id.num);
            this.more = itemView.findViewById(R.id.more);
            this.bar = itemView.findViewById(R.id.bar);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list, parent, false);

        return new MyViewHolder(view);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.name.setText(mItems.get(position).getName());
        holder.num.setText(mItems.get(position).getCheckedItems()+"/"+mItems.get(position).getTotalItems());
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.more,position);
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ListDetail.class);
                intent.putExtra("pos",position);
                context.startActivity(intent);
            }
        });
        if(mItems.get(position).getTotalItems()==0){
            holder.bar.setProgress(0);
        }
        else {
            int p = ((int) mItems.get(position).getCheckedItems()*100)/mItems.get(position).getTotalItems();
            Log.e("progress",String.valueOf(p));
            holder.bar.setProgress(p, true);
        }
    }

    private void showPopupMenu(View view,int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(view.getContext(),view );
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.more_options_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position,context));
        popup.show();
    }
    @Override
    public int getItemCount() {
        if(mItems== null) return 0;
       else  return mItems.size();
    }
}
