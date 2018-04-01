package com.example.shado.buylist;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.wunderlist.slidinglayer.SlidingLayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static com.example.shado.buylist.MainActivity.mainList;
import static com.example.shado.buylist.MainActivity.saveList;

public class ListDetail extends AppCompatActivity {

    /*
    *  This  Activity displays the list items as well as enables users to add more items to their list.
    *  this activity also handles the functionality of sorting and deleting.
     */

    //defining the widgets used.
    List myList;
    public static RecyclerView dummylist;
    ImageView add;
    ImageView close;
    EditText item_name;
    public static SlidingLayer slidingLayer;
    public static DummyListAdapter dummy_adapter;
    public static HashMap<String,Item> dummyItemHashMap;
    public static ArrayList<Item> dummyItemArrayList;

    public static int mode = 0;

    public static RecyclerView mainlist;
    public static DetailedListAdapter main_adapter;
    public static HashMap<String,Item> mainItemHashMap;
    public static ArrayList<Item>  mainItemArrayList;

    public static int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);
        if(getSupportActionBar()!=null) { getSupportActionBar().setDisplayHomeAsUpEnabled(true);}
         position = getIntent().getIntExtra("pos",-1);
        if(position!=-1){
            myList = mainList.get(position);
        }
        //this code sinpset adds back button in action bar
        if(getSupportActionBar()!=null) { getSupportActionBar().setTitle(myList.getName());}
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //initialising drawer through which users can add more products to their list.
        slidingLayer = (SlidingLayer) findViewById(R.id.slidingLayer);
        slidingLayer.setChangeStateOnTap(false);
        slidingLayer.setSlidingEnabled(false);

        Log.e("data",myList.toString());

       mainItemHashMap = mainList.get(position).getItemsList();
       mainItemArrayList = new ArrayList<>(mainItemHashMap.values());

      // populateDummyList(mainItemArrayList);
        //Recycler view which displays items that are currently in the list.

       mainlist = (RecyclerView) findViewById(R.id.main_list);
       main_adapter = new DetailedListAdapter(this,mainItemArrayList);
       mainlist.setAdapter(main_adapter);
       mainlist.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration mDividerItemDecoration2 = new DividerItemDecoration(
                mainlist.getContext(),
                DividerItemDecoration.VERTICAL
        );
        mainlist.addItemDecoration(mDividerItemDecoration2);

        //when this button is clicked, drawer pops out which would contain some suggested items.

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.INVISIBLE);
                slidingLayer.openLayer(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                m.getItem(2).setVisible(false);
                m.getItem(3).setVisible(false);
                m.getItem(4).setVisible(false);
                m.getItem(0).setVisible(true);
                m.getItem(1).setVisible(false);


            }
        });
        // button to close the drawer
        close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareMainList();
                slidingLayer.closeLayer(true);
                m.getItem(2).setVisible(true);
                m.getItem(3).setVisible(true);
                m.getItem(4).setVisible(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                m.getItem(0).setVisible(false);
                m.getItem(1).setVisible(false);
                fab.setVisibility(View.VISIBLE);
            }
        });
        item_name = findViewById(R.id.item_name_et);
        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String name = item_name.getText().toString().trim();
                 if(!name.equals("")){
                     addItem(name);
                     item_name.setText("");
                }
            }
        });

        loadData();
        dummyItemArrayList = new ArrayList<>(dummyItemHashMap.values());

        dummylist = (RecyclerView) findViewById(R.id.dummy_list);
        dummy_adapter = new DummyListAdapter(this,dummyItemArrayList);

        dummylist.setAdapter(dummy_adapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        dummylist.setLayoutManager(linearLayoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                dummylist.getContext(),
                DividerItemDecoration.VERTICAL
        );
        dummylist.addItemDecoration(mDividerItemDecoration);
      //  prepareMainList();
    }
//This method populate the list of suggested items
    private void populateDummyList(ArrayList<Item> mainItemArrayList) {
        for(int i=0;i<mainItemArrayList.size();i++){
            if(dummyItemHashMap.containsKey(mainItemArrayList.get(i).getName())){
                dummyItemHashMap.get(mainItemArrayList.get(i).getName()).setQuantity(mainItemArrayList.get(i).getQuantity());
            }
            else{
                dummyItemHashMap.put(mainItemArrayList.get(i).getName(),mainItemArrayList.get(i));
            }
        }
    }


    public void loadData() {
        HashMap<String,Item> dummylist = new HashMap<String,Item>();
        int id =0;
        dummylist.put("milk",new Item("milk",++id));
        dummylist.put("bread",new Item("bread",++id));
        dummylist.put("eggs",new Item("eggs",++id));
        dummylist.put("butter",new Item("butter",++id));
        dummylist.put("cheese",new Item("cheese",++id));
        dummylist.put("toilet paper",new Item("toilet paper",++id));
        dummylist.put("chicken",new Item("chicken",++id));
        dummylist.put("potatoes",new Item("potatoes",++id));
        dummylist.put("coffee",new Item("coffee",++id));
        dummylist.put("lettuce",new Item("lettuce",++id));
        dummylist.put("toothpaste",new Item("toothpaste",++id));
        dummylist.put("juice",new Item("juice",++id));
        dummylist.put("shampoo",new Item("shampoo",++id));
        dummylist.put("water",new Item("water",++id));
        dummylist.put("ketchup",new Item("ketchup",++id));
        dummylist.put("rice",new Item("rice",++id));
        dummylist.put("ham",new Item("ham",++id));
        dummylist.put("cucumber",new Item("cucumber",++id));
        dummylist.put("pasta",new Item("pasta",++id));
        dummylist.put("ice cream",new Item("ice cream",++id));
        dummylist.put("tuna",new Item("tuna",++id));
        dummylist.put("olive oil",new Item("olive oil",++id));
        dummylist.put("tea",new Item("tea",++id));
        dummylist.put("beer",new Item("beer",++id));
        dummylist.put("conditioner",new Item("conditioner",++id));
        dummylist.put("sausage",new Item("sausage",++id));
        dummylist.put("cottage cheese",new Item("cottage cheese",++id));
        dummylist.put("cookies",new Item("cookies",++id));
        dummylist.put("laundry detergent",new Item("laundry detergent",++id));
        dummylist.put("mustard",new Item("mustard",++id));
        dummylist.put("napkins",new Item("napkins",++id));
        dummylist.put("mayonnaise",new Item("mayonnaise",++id));
        dummylist.put("body wash",new Item("body wash",++id));
        dummylist.put("chocolate",new Item("chocolate",++id));
        dummylist.put("turkey",new Item("turkey",++id));
        dummylist.put("paper towel",new Item("paper towel",++id));
        dummylist.put("shaving cream",new Item("shaving cream",++id));
        dummylist.put("hand soap",new Item("hand soap",++id));
        dummylist.put("paprika",new Item("paprika",++id));
        dummylist.put("tampons",new Item("tampons",++id));
        dummylist.put("shower gel",new Item("shower gel",++id));
        dummylist.put("deodorant",new Item("deodorant",++id));
        dummylist.put("salad dressing",new Item("salad dressing",++id));
        dummylist.put("chicken nuggets",new Item("chicken nuggets",++id));
        dummylist.put("candy",new Item("candy",++id));
        dummylist.put("corn flakes",new Item("corn flakes",++id));
        dummylist.put("baby food",new Item("baby food",++id));
        dummylist.put("pizza",new Item("pizza",++id));
        dummyItemHashMap = dummylist;
        dummyItemArrayList = new ArrayList<>(dummyItemHashMap.values());

        populateDummyList(mainItemArrayList);
    }

    @Override
    public boolean onSupportNavigateUp(){
        prepareMainList();
        finish();
        return true;
    }
    // when the user clicks on increase quantity button for any item
    public static void increaseQuantity(int pos, Context ctx){
        dummyItemArrayList.get(pos).setQuantity(dummyItemArrayList.get(pos).getQuantity()+1);

    if(mainItemHashMap.containsKey(dummyItemArrayList.get(pos).getName())) {
        mainItemHashMap.get(dummyItemArrayList.get(pos).getName()).setQuantity(dummyItemArrayList.get(pos).getQuantity());
    }
     else{
        mainItemHashMap.put(dummyItemArrayList.get(pos).getName(),dummyItemArrayList.get(pos));
    }
        mainList.get(position).setItemsList(mainItemHashMap);
        saveList(mainList);

        mainItemArrayList = new ArrayList<>(mainItemHashMap.values());
        main_adapter = new DetailedListAdapter(ctx,mainItemArrayList);

        mainlist.setAdapter(main_adapter);
        mainlist.setLayoutManager(new LinearLayoutManager(ctx));
        main_adapter.notifyDataSetChanged();
        dummy_adapter.notifyDataSetChanged();
    }

    //
    public static void decreaseQuantity(int pos, Context ctx){
        if(dummyItemArrayList.get(pos).getQuantity()>1) {
            dummyItemArrayList.get(pos).setQuantity(dummyItemArrayList.get(pos).getQuantity() - 1);
            mainItemHashMap.get(dummyItemArrayList.get(pos).getName()).setQuantity(dummyItemArrayList.get(pos).getQuantity());
        }
        else {
           dummyItemArrayList.get(pos).setQuantity(dummyItemArrayList.get(pos).getQuantity() - 1);
            if(mainItemHashMap.containsKey(dummyItemArrayList.get(pos).getName()))
            mainItemHashMap.remove(dummyItemArrayList.get(pos).getName());
        }
        mainList.get(position).setItemsList(mainItemHashMap);
        saveList(mainList);

        mainItemArrayList = new ArrayList<>(mainItemHashMap.values());
        main_adapter = new DetailedListAdapter(ctx,mainItemArrayList);

        mainlist.setAdapter(main_adapter);
        mainlist.setLayoutManager(new LinearLayoutManager(ctx));
        main_adapter.notifyDataSetChanged();
        dummy_adapter.notifyDataSetChanged();
    }



    public void addItem(String name){
        if(!dummyItemHashMap.containsKey(name)){
            Item item = new Item(name,dummyItemArrayList.size());
        dummyItemHashMap.put(name,item);
        dummyItemArrayList.add(item);
        dummy_adapter.notifyDataSetChanged();
        dummylist.scrollToPosition(dummyItemArrayList.size()-1);
    }
    else{
            Toast.makeText(this, "Item already present in list", Toast.LENGTH_SHORT).show();
        }
    }

    public static void prepareMainList(){

    }

    public static void toggleChecked(String name, boolean b, int id){
       if(mainList.get(position).getItemsList().get(name).getId() == id){
           mainList.get(position).getItemsList().get(name).setChecked(b);
       }
        Log.e("setting checked",mainList.get(position).getItemsList().get(name).getName()+" "+String.valueOf(b));
        saveList(mainList);
        mainItemHashMap = mainList.get(position).getItemsList();
        mainItemArrayList = new ArrayList<>(mainItemHashMap.values());
     //   main_adapter.notifyDataSetChanged();

    }
Menu m;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list, menu);
        m = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort) {
               if(mode ==0 ){
                Collections.sort(dummyItemArrayList);
                dummy_adapter.notifyDataSetChanged();
                item.setTitle("Sort by : Popularity");
                mode = 1;
               }
               else{
                   Collections.sort(dummyItemArrayList);
                   dummy_adapter.notifyDataSetChanged();
                   item.setTitle("Sort Alphabetically");
                   mode = 0;
               }
        }

       /* if (id == R.id.action_sort_mainlist) {
            if(mode ==0 ){
                Collections.sort(mainItemArrayList);
                main_adapter.notifyDataSetChanged();
                item.setTitle("Sort by : Popularity");
                mode = 1;
            }
            else{
                Collections.sort(mainItemArrayList);
                main_adapter.notifyDataSetChanged();
                item.setTitle("Sort Alphabetically");
                mode = 0;
            }
        }*/

       else if (id == R.id.action_check) {
            for (int i = 0; i < mainItemArrayList.size(); i++) {
                toggleChecked(mainItemArrayList.get(i).getName(), true, mainItemArrayList.get(i).getId());
            }
            main_adapter.notifyDataSetChanged();
        }

        else if (id == R.id.action_uncheck) {
            for (int i = 0; i < mainItemArrayList.size(); i++) {
                toggleChecked(mainItemArrayList.get(i).getName(), false, mainItemArrayList.get(i).getId());
            }
            main_adapter.notifyDataSetChanged();
        }

       else if (id == R.id.action_delete) {
            mainList.get(position).getItemsList().clear();
            saveList(mainList);
            Toast.makeText(this, "All Items deleted", Toast.LENGTH_SHORT).show();
            mainItemArrayList.clear();
            mainItemHashMap.clear();
            main_adapter = new DetailedListAdapter(this,mainItemArrayList);
            loadData();
            mainlist.setAdapter(main_adapter);
            mainlist.setLayoutManager(new LinearLayoutManager(this));
            main_adapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }

}
