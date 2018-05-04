package com.example.shado.buylist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;

import java.util.ArrayList;

import static com.example.shado.buylist.MainActivity.changeName;
import static com.example.shado.buylist.MainActivity.deleteItem;
import static com.example.shado.buylist.MainActivity.mainList;


class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private int position;
        Context ctx;
        public MyMenuItemClickListener(int positon, Context ctx) {
            this.position=positon;
            this.ctx = ctx;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {

                case R.id.action_rename:
                    changeName(position,ctx);
                    return true;
                case R.id.action_delete:
                    deleteItem(position);
                    return true;
                case R.id.action_share:
                    String shareBody = getStringFromList(mainList.get(position));
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mainList.get(position).getName()+" Shopping List");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    ctx.startActivity(Intent.createChooser(sharingIntent,"Share via"));

                 default:
            }
            return false;
        }

    private String getStringFromList(List list) {
        String text ="";
        ArrayList<Item> itemArrayList= new ArrayList<>(list.itemsList.values());
        for (int i=0;i<itemArrayList.size();i++){
            text = text+"-"+itemArrayList.get(i).getName();
            if(itemArrayList.get(i).getQuantity()>1){
                text = text+" ["+itemArrayList.get(i).getQuantity()+"]";
            }
            text = text + "\n";
        }
        return text;
    }


}

