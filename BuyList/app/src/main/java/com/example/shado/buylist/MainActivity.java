package com.example.shado.buylist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.wunderlist.slidinglayer.SlidingLayer;

import java.util.ArrayList;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static SlidingLayer slidingLayer;
    private EditText list_name;
    private Button create_list;
    public static ArrayList<List> mainList;
    public static Adapter adapter;
    RecyclerView list_view;
    ImageView close;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        slidingLayer = (SlidingLayer) findViewById(R.id.slidingLayer);
        slidingLayer.setChangeStateOnTap(false);
        slidingLayer.setSlidingEnabled(false);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.INVISIBLE);
               slidingLayer.openLayer(true);
            }
        });
        context = getApplicationContext();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      /*  ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();*/
       // drawer.setVisibility(View.GONE);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setVisibility(View.INVISIBLE);
        navigationView.setNavigationItemSelectedListener(this);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mainList = loadData();
        adapter = new Adapter(this,mainList);

        list_view = (RecyclerView) findViewById(R.id.recycler_list);
        list_view.setAdapter(adapter);
        list_view.setLayoutManager(new LinearLayoutManager(this));


        list_name = findViewById(R.id.name_et);
        create_list = findViewById(R.id.create_list_btn);

        create_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = list_name.getText().toString();
                if(!name.trim().equals("")){
                    long time = System.currentTimeMillis();
                    List list = new List(name,time);
                    mainList.add(list);
                    saveList(mainList);
                    adapter.notifyDataSetChanged();
                    list_name.setText("");
                    slidingLayer.closeLayer(true);
                    fab.setVisibility(View.VISIBLE);
                }
            }
        });

        close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingLayer.closeLayer(true);
                fab.setVisibility(View.VISIBLE);
            }
        });
/*
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(getString(R.string.key));
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String a = (String)dataSnapshot.getValue();
                if(a.equals(getString(R.string.buylist))){
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        */
    }

    public static void saveList(ArrayList<List> mainList) {
        Paper.book().write(CONSTANTS.EMAIL+"main_list", mainList);
        adapter.notifyDataSetChanged();
        Log.e("List",mainList.toString());
    }

    private ArrayList<List> loadData() {
      ArrayList<List> list =  Paper.book().read(CONSTANTS.EMAIL+"main_list", new ArrayList<List>());
      return list;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_account) {
            Intent intent = new Intent(this,UserProfile.class);
            startActivity(intent);
        }
        if (id == R.id.action_logout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void logout() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
            mAuth.signOut();

        }
        SharedPreferences.Editor editor = getSharedPreferences("BUYLIST", MODE_PRIVATE).edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();
        //finish();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public static void deleteItem(int position) {
        mainList.remove(position);
        saveList(mainList);
    }

    public void shareItem(int position) {

    }

    public static void changeName(final int position, final Context ctx) {
        final EditText taskEditText = new EditText(ctx);
        /*new MaterialDialog.Builder(ctx)
                .title("")
                //.content(R.string.content)
                .positiveText("afgree")
                .negativeText("diaagree")
                .show();*/
        AlertDialog dialog = new AlertDialog.Builder(ctx)
                .setTitle("Rename list")
                .setMessage("Enter new name")
                .setView(taskEditText)
                .setPositiveButton("Rename", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newName = String.valueOf(taskEditText.getText());
                    if(!newName.trim().equals("")) {
                        mainList.get(position).setName(newName);
                        saveList(mainList);
                    }
                    else{
                        Toast.makeText(ctx, "Enter a valid name", Toast.LENGTH_SHORT).show();
                    }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();

     }


}
