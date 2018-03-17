package com.example.shado.buylist;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;

import static com.example.shado.buylist.MainActivity.changeName;
import static com.example.shado.buylist.MainActivity.deleteItem;


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


                 default:
            }
            return false;
        }


}

