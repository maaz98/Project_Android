package com.example.shado.buylist;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.wunderlist.slidinglayer.SlidingLayer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import cafe.adriel.androidaudiorecorder.model.AudioChannel;
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate;
import cafe.adriel.androidaudiorecorder.model.AudioSource;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static android.os.Environment.getExternalStoragePublicDirectory;
import static com.example.shado.buylist.MainActivity.context;
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
    public static SlidingLayer slidingLayer2;
    public static Date date;
    public static int mode = 0;

    public static RecyclerView mainlist;
    public static DetailedListAdapter main_adapter;
    public static HashMap<String,Item> mainItemHashMap;
    public static ArrayList<Item>  mainItemArrayList;

    public static int position;

    static EditText quantity_et;
    static EditText price_et;
    static ImageView close_btn;
    static TextView item_name_tv;
    static EditText notes_et;

    static RecyclerView recyclerView;

    public static ArrayList<Attachment> list;
    public static AttachmentAdapter attachmentAdapter;
    static int pos;

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

        list = new ArrayList<>();
        //initialising drawer through which users can add more products to their list.
        slidingLayer = (SlidingLayer) findViewById(R.id.slidingLayer);
        slidingLayer.setChangeStateOnTap(false);
        slidingLayer.setSlidingEnabled(false);

        slidingLayer2 = (SlidingLayer) findViewById(R.id.slidingLayer2);
        slidingLayer2.setChangeStateOnTap(false);
        slidingLayer2.setSlidingEnabled(false);
        quantity_et = (EditText)findViewById(R.id.qty);
        price_et = (EditText)findViewById(R.id.price);
        close_btn = (ImageView) findViewById(R.id.cls);
        item_name_tv = (TextView) findViewById(R.id.name);
        notes_et = (EditText)findViewById(R.id.notes_et);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_list);

       camera_ll = (LinearLayout)findViewById(R.id.event_1_1);
       gallery_ll = (LinearLayout)findViewById(R.id.event_1_2);
       video_ll= (LinearLayout)findViewById(R.id.event_1_3);
       file_ll= (LinearLayout)findViewById(R.id.event_1_4);
       audio_ll= (LinearLayout)findViewById(R.id.event_1_5);
       record_ll= (LinearLayout)findViewById(R.id.event_1_6);

        Log.e("data",myList.toString());

       mainItemHashMap = mainList.get(position).getItemsList();
       mainItemArrayList = new ArrayList<>(mainItemHashMap.values());

       // populateDummyList(mainItemArrayList);
       // Recycler view which displays items that are currently in the list.
       mainlist = (RecyclerView) findViewById(R.id.main_list);
       main_adapter = new DetailedListAdapter(this,mainItemArrayList);
       mainlist.setAdapter(main_adapter);
       mainlist.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration mDividerItemDecoration2 = new DividerItemDecoration(
                mainlist.getContext(),
                DividerItemDecoration.VERTICAL
        );
        mainlist.addItemDecoration(mDividerItemDecoration2);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
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

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mainItemArrayList.get(pos).setQuantity(Integer.parseInt(quantity_et.getText().toString()));
                    mainItemArrayList.get(pos).setPrice(Double.parseDouble(price_et.getText().toString()));
                    mainItemArrayList.get(pos).setNotes(notes_et.getText().toString());

                    mainItemArrayList.get(pos).setAttachments(list);
                    mainItemHashMap.get(mainItemArrayList.get(pos).getName()).setAttachments(list);
                    mainItemHashMap.get(mainItemArrayList.get(pos).getName()).setQuantity(Integer.parseInt(quantity_et.getText().toString()));
                    mainItemHashMap.get(mainItemArrayList.get(pos).getName()).setNotes(notes_et.getText().toString());
                    mainItemHashMap.get(mainItemArrayList.get(pos).getName()).setPrice(Double.parseDouble(price_et.getText().toString()));
                    attachmentAdapter.notifyDataSetChanged();
                    mainList.get(position).setItemsList(mainItemHashMap);
                    saveList(mainList);
                    main_adapter.notifyDataSetChanged();
                    slidingLayer2.closeLayer(true);
                }catch(Exception e){
                    Toast.makeText(ListDetail.this,"Error: " +e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        camera_ll.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if((ActivityCompat.checkSelfPermission(ListDetail.this,Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED)&&(ActivityCompat.checkSelfPermission(ListDetail.this,Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)&&(ActivityCompat.checkSelfPermission(ListDetail.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)){
                    openCameraRequest();
                }
                else{
                    if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                        Toast.makeText(ListDetail.this, "Camera permission is required to capture the image.", Toast.LENGTH_SHORT).show();
                    }
                    requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},CAMERA_TYPE);

                }
            }
        });

       final ArrayList<String> filePaths = new ArrayList<>();

        file_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AddText();
                FilePickerBuilder.getInstance().setMaxCount(1)
                        .setSelectedFiles(filePaths)
                        .setActivityTheme(R.style.LibAppTheme)
                        .pickFile(ListDetail.this, 123);
            }
        });

        record_ll.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                if((ActivityCompat.checkSelfPermission(ListDetail.this,Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_GRANTED)&&(ActivityCompat.checkSelfPermission(ListDetail.this,Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)&&(ActivityCompat.checkSelfPermission(ListDetail.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)){
                    RecordAudio();
                }
                else{
                    if(shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)){
                        Toast.makeText(ListDetail.this, "Microphone permission is required to record audio.", Toast.LENGTH_SHORT).show();
                    }
                    requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},RECORD_TYPE);

                }
            }
        });

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
    public File photoFile;
    public String destination;
    public File audioFile;
    public String AudioDestination;
    private String mCurrentPhotoPath;
    public static File CameraFile;
    public static int CAMERA_TYPE = 1;
    public static int GALLERY_TYPE = 2;
    public static int VIDEO_TYPE = 3;
    public static int FILE_TYPE = 4;
    public static int AUDIO_TYPE = 5;
    public static int RECORD_TYPE = 6;

    private void openCameraRequest() {
        EasyImage.openCamera(this,CAMERA_TYPE);
     /*
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(this.getPackageManager()) != null) {

            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.i("error", "IOException");
            }
            if (photoFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(cameraIntent, CAMERA_TYPE);
                CameraFile = photoFile;
            }
        }*/
    }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPG_" + timeStamp + "_";
        File storageDir = getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpeg ",
                storageDir
        );
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        destination = mCurrentPhotoPath;

        return image;
    }

    private void UploadFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_TYPE);
    }

    static String AUDIO_FILE_NAME = "";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {

                list.add(new Attachment(CAMERA_TYPE,imageFile.getAbsolutePath()));
                mainItemArrayList.get(pos).setAttachments((ArrayList<Attachment>) list);
                mainItemHashMap.get(mainItemArrayList.get(pos).getName()).setAttachments((ArrayList<Attachment>) list);
                attachmentAdapter.notifyDataSetChanged();
                mainList.get(position).setItemsList(mainItemHashMap);
                saveList(mainList);
                main_adapter.notifyDataSetChanged();

            }

        });

        if (resultCode == RESULT_OK) {
            if(requestCode==CAMERA_TYPE)   {
               /* if (data != null) {
                    post(data.getData(), requestCode);
                } else {
                    Toast.makeText(this, "Something's wrong! Please try again. ", Toast.LENGTH_SHORT).show();
                }*/
            }
            else if(requestCode == RECORD_TYPE){
                Attachment attachment  = new Attachment(RECORD_TYPE,AUDIO_FILE_NAME);
                list.add(attachment);
                attachmentAdapter.notifyDataSetChanged();
            }

            if(resultCode== Activity.RESULT_OK && data!=null && requestCode ==123) {
                ArrayList<String> docPaths = new ArrayList<>();
                docPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
                save(docPaths);
            }


        }


    }

    private void save(ArrayList<String> docPaths) {
        list.add(new Attachment(FILE_TYPE,docPaths.get(0)));
        mainItemArrayList.get(pos).setAttachments((ArrayList<Attachment>) list);
        mainItemHashMap.get(mainItemArrayList.get(pos).getName()).setAttachments((ArrayList<Attachment>) list);
        attachmentAdapter.notifyDataSetChanged();
        mainList.get(position).setItemsList(mainItemHashMap);
        saveList(mainList);
        main_adapter.notifyDataSetChanged();
    }

    public void RecordAudio(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        AUDIO_FILE_NAME = getFilesDir()+"/"+ timeStamp+"recorded_audio.wav";


         String imageFileName = "WAV_" + timeStamp + "_";
        File storageDir = getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,
                    ".wav ",
                    storageDir
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
       // mCurrentPhotoPath = "file:" + image.getAbsolutePath();
       // destination = mCurrentPhotoPath;
        AUDIO_FILE_NAME = image.getAbsolutePath();
        int color = getResources().getColor(R.color.colorPrimaryDark);
        AndroidAudioRecorder.with(this)
                // Required
                .setFilePath(image.getAbsolutePath())
                .setColor(color)
                .setRequestCode(RECORD_TYPE)

                // Optional
                .setSource(AudioSource.MIC)
                .setChannel(AudioChannel.STEREO)
                .setSampleRate(AudioSampleRate.HZ_48000)
                .setAutoStart(true)
                .setKeepDisplayOn(true)

                // Start recording
                .record();
    }

    private void post(Uri uri, int type) {

        Attachment singleAttachment = null;

         if(type==CAMERA_TYPE) {
            Uri uri2 = Uri.fromFile(photoFile);
            singleAttachment  = new Attachment(type, uri2.getPath());
        }

        else if(type==RECORD_TYPE){
            singleAttachment  = new Attachment(type,uri.getPath());
            Log.e("uri", uri.getPath());
        }

        list.add(singleAttachment);
        attachmentAdapter.notifyDataSetChanged();
    }

   /* public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU W*//**//*ILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }
*/
    /*public static String getStrPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
*/
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

    @RequiresApi(api = Build.VERSION_CODES.N)
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

        else if (id == R.id.action_bell){
          //  if(mainList.get(position).getNotificationId()!=0){
                item.setIcon(R.drawable.bell);

                DatePickerDialog dialog = new DatePickerDialog(this);

                dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar calendar= Calendar.getInstance();
                        calendar.set(year,month,dayOfMonth,0,0,0);
                        date = calendar.getTime();
                        Date d = Calendar.getInstance().getTime();
                        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                date.setHours(hourOfDay);
                                date.setMinutes(minute);
                                PingMe pm = new PingMe();
                                int id =  pm.setNotification("Buy List App",mainList.get(position).getName()+" is due.",date,getApplicationContext());
                                mainList.get(position).setNotificationId(id);
                                Toast.makeText(ListDetail.this, "Reminder set for " +date.toLocaleString(), Toast.LENGTH_SHORT).show();
                                saveList(mainList);
                            }
                        };
                        TimePickerDialog dialog1 = new TimePickerDialog(ListDetail.this,listener,d.getHours(),d.getMinutes(),false);

                        dialog1.show();

                    }
                });
                dialog.show();



        }
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

   public static void openDrawer(final int p){
        pos = p;
       quantity_et.setText(String.valueOf(mainItemArrayList.get(pos).getQuantity()));
       price_et.setText(String.valueOf(mainItemArrayList.get(pos).getPrice()));
       item_name_tv.setText(mainItemArrayList.get(pos).getName());
       if(mainItemArrayList.get(pos).getNotes()!=null)
       notes_et.setText(String.valueOf(mainItemArrayList.get(pos).getNotes()));
       list = mainItemArrayList.get(pos).getAttachments();
       Log.e("List",String.valueOf(list));
       attachmentAdapter = new AttachmentAdapter(list,context);
       attachmentAdapter.notifyDataSetChanged();
       recyclerView.setLayoutManager(new GridLayoutManager(context,4));
       recyclerView.setAdapter(attachmentAdapter);
       slidingLayer2.openLayer(true);
   }
    static LinearLayout camera_ll;
    static LinearLayout gallery_ll;
    static LinearLayout video_ll;
    static LinearLayout file_ll;
    static LinearLayout audio_ll;
    static LinearLayout record_ll;

    public static void remove(int position){
        list.remove(position);
        attachmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CAMERA_TYPE){openCameraRequest();}
        else if (requestCode == GALLERY_TYPE){UploadFromGallery();}
    }
}
