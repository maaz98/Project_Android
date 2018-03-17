package com.example.shado.buylist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.wunderlist.slidinglayer.SlidingLayer;

import java.util.ArrayList;

import static com.example.shado.buylist.MainActivity.mainList;
import static com.example.shado.buylist.MainActivity.saveList;

public class ListDetail extends AppCompatActivity {

    List myList;
    public static RecyclerView dummylist;
    ImageView add;
    ImageView close;
    EditText item_name;
    public static SlidingLayer slidingLayer;
    public static DummyListAdapter dummy_adapter;
    public static ArrayList<Item> dummyItemArrayList;

    public static RecyclerView mainlist;
    public static DetailedListAdapter main_adapter;
    public static ArrayList<Item> mainItemArrayList;
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
        if(getSupportActionBar()!=null) { getSupportActionBar().setTitle(myList.getName());}
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        slidingLayer = (SlidingLayer) findViewById(R.id.slidingLayer);
        slidingLayer.setChangeStateOnTap(false);
        slidingLayer.setSlidingEnabled(false);

        dummyItemArrayList = loadData();
        //dummylist = findViewById(R.id.list);
        dummylist = (RecyclerView) findViewById(R.id.dummy_list);
        dummy_adapter = new DummyListAdapter(this,dummyItemArrayList);

        dummylist.setAdapter(dummy_adapter);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        dummylist.setLayoutManager(linearLayoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                dummylist.getContext(),
                DividerItemDecoration.VERTICAL
        );
        dummylist.addItemDecoration(mDividerItemDecoration);

        mainItemArrayList = mainList.get(position).getItemsList();
        mainlist = (RecyclerView) findViewById(R.id.main_list);
       main_adapter = new DetailedListAdapter(this,mainItemArrayList);

       mainlist.setAdapter(main_adapter);
       mainlist.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration mDividerItemDecoration2 = new DividerItemDecoration(
                mainlist.getContext(),
                DividerItemDecoration.VERTICAL
        );
        mainlist.addItemDecoration(mDividerItemDecoration2);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.INVISIBLE);
                slidingLayer.openLayer(true);
            }
        });

        close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareMainList();
                slidingLayer.closeLayer(true);
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
    }

    private ArrayList<Item> loadData() {
        ArrayList<Item> dummylist = new ArrayList<>();
        int id =0;
        dummylist.add(new Item("milk",++id));
        dummylist.add(new Item("bread",++id));
        dummylist.add(new Item("eggs",++id));
        dummylist.add(new Item("butter",++id));
        dummylist.add(new Item("cheese",++id));
        dummylist.add(new Item("toilet paper",++id));
        dummylist.add(new Item("chicken",++id));
        dummylist.add(new Item("potatoes",++id));
        dummylist.add(new Item("coffee",++id));
        dummylist.add(new Item("lettuce",++id));
        dummylist.add(new Item("toothpaste",++id));
        dummylist.add(new Item("juice",++id));
        dummylist.add(new Item("shampoo",++id));
        dummylist.add(new Item("water",++id));
        dummylist.add(new Item("ketchup",++id));
        dummylist.add(new Item("rice",++id));
        dummylist.add(new Item("ham",++id));
        dummylist.add(new Item("cucumber",++id));
        dummylist.add(new Item("pasta",++id));
        dummylist.add(new Item("ice cream",++id));
        dummylist.add(new Item("tuna",++id));
        dummylist.add(new Item("olive oil",++id));
        dummylist.add(new Item("tea",++id));
        dummylist.add(new Item("beer",++id));
        dummylist.add(new Item("conditioner",++id));
        dummylist.add(new Item("sausage",++id));
        dummylist.add(new Item("cottage cheese",++id));
        dummylist.add(new Item("cookies",++id));
        dummylist.add(new Item("laundry detergent",++id));
        dummylist.add(new Item("mustard",++id));
        dummylist.add(new Item("napkins",++id));
        dummylist.add(new Item("mayonnaise",++id));
        dummylist.add(new Item("body wash",++id));
        dummylist.add(new Item("chocolate",++id));
        dummylist.add(new Item("turkey",++id));
        dummylist.add(new Item("paper towel",++id));
        dummylist.add(new Item("shaving cream",++id));
        dummylist.add(new Item("hand soap",++id));
        dummylist.add(new Item("paprika",++id));
        dummylist.add(new Item("tampons",++id));
        dummylist.add(new Item("shower gel",++id));
        dummylist.add(new Item("deodorant",++id));
        dummylist.add(new Item("salad dressing",++id));
        dummylist.add(new Item("chicken nuggets",++id));
        dummylist.add(new Item("candy",++id));
        dummylist.add(new Item("corn flakes",++id));
        dummylist.add(new Item("baby food",++id));
        dummylist.add(new Item("pizza",++id));

        return dummylist;
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public static void increaseQuantity(int position){
        dummyItemArrayList.get(position).setQuantity(dummyItemArrayList.get(position).getQuantity()+1);
        dummy_adapter.notifyDataSetChanged();
    }
    public static void decreaseQuantity(int position){
        if(dummyItemArrayList.get(position).getQuantity()>0) {
            dummyItemArrayList.get(position).setQuantity(dummyItemArrayList.get(position).getQuantity() - 1);
            dummy_adapter.notifyDataSetChanged();

        }
    }

    public static void addItem(String name){
       dummyItemArrayList.add(new Item(name,0));
        dummy_adapter.notifyDataSetChanged();
        dummylist.scrollToPosition(dummyItemArrayList.size()-1);
    }

    public static void prepareMainList(){
        if(mainItemArrayList==null) mainItemArrayList= new ArrayList<>();
        mainItemArrayList.clear();
        for(int i=0;i<dummyItemArrayList.size();i++){
            if(dummyItemArrayList.get(i).getQuantity()>0){
                mainItemArrayList.add(dummyItemArrayList.get(i));
            }
        }
    mainList.get(position).setItemsList(mainItemArrayList);
    mainList.get(position).setTotalItems(mainItemArrayList.size());
    saveList(mainList);
    main_adapter.notifyDataSetChanged();
    }

    public static void toggleChecked(int pos, boolean b){
        mainList.get(position).getItemsList().get(pos).setChecked(b);
        saveList(mainList);
    }

}
