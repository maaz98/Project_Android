package com.example.shado.buylist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.hdodenhof.circleimageview.CircleImageView;

//This activity displays user's Profile info as well as has option to update password.

public class UserProfile extends AppCompatActivity {

    //Declaring widgets
    TextView NameText;
    TextView emailText;
    EditText password;
    Button changePassWord;
    CircleImageView imageView;
    private static HashMap<String, User> userMap;
    private String email = CONSTANTS.EMAIL;
    private String name = CONSTANTS.NAME;

    private String authType;
    String ImageUrl;
    FirebaseAuth mAuth;
    CardView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        if(getSupportActionBar()!=null)  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    //Initialsing widgets.
        userMap=null;
        password=(EditText)findViewById(R.id.password_et);
        NameText=(TextView)findViewById(R.id.name_tv);
        emailText=(TextView)findViewById(R.id.email_tv);
        changePassWord=(Button)findViewById(R.id.save_btn);
         imageView=(CircleImageView) findViewById(R.id.profile_image);
        view = (CardView) findViewById(R.id.card);
        NameText.setText(name);
        emailText.setText(email);
        try {
            addImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
       mAuth = FirebaseAuth.getInstance();
        if(CONSTANTS.isGmail){
            view.setVisibility(View.GONE);
        }

        if (userMap == null) {
            userMap = readUsersMap(this);
            Log.e("usermsp",String.valueOf(userMap));
        }
        //When change password button is clicked.
        changePassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String Newpassword= password.getText().toString();
                    Log.e("password",Newpassword);
                    password.setVisibility(View.INVISIBLE);
                    password.setText("");
                    userMap.get(email).setPassword(Newpassword);        //saving updated password
                    Toast.makeText(UserProfile.this, "Password Updated successfully", Toast.LENGTH_SHORT).show();
                password.setVisibility(View.GONE);
                changePassWord.setVisibility(View.GONE);
                storeUserMap(userMap, getApplicationContext());
            }
        });

     view.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             if(password.getVisibility()==View.GONE){
                 password.setVisibility(View.VISIBLE);
                 changePassWord.setVisibility(View.VISIBLE);
             }
             else{
                 password.setVisibility(View.GONE);
                 changePassWord.setVisibility(View.GONE);
             }
         }
     });

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
//This method displays the image of person logged in through Gmail.
    private void addImage() throws IOException {
        ImageUrl="";
        if(email.contains("@gmail.com")){
        String url = "http://picasaweb.google.com/data/entry/api/user/"+email.replace("@gmail.com","")+"?alt=json";
         String data = getJSON(url,100000);
        try {if(data==null){}
            else{
            JSONObject jsonObject = new JSONObject(data);
           JSONObject object = jsonObject.getJSONObject("entry").getJSONObject("gphoto$thumbnail");
            ImageUrl = object.getString("$t");

        }} catch (JSONException e) {
            e.printStackTrace();
        }

         if(ImageUrl!="") {
            Picasso.with(this).load(ImageUrl).into(imageView);
        }
    }}

    private void logout() {
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
    //method to read user information from phone
    public static HashMap<String, User> readUsersMap(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("BUYLIST", MODE_PRIVATE);

        try {
            String userMapRawString = preferences.getString("usersMap", "");
            if (userMapRawString.equals("")) {
                return new HashMap<>();
            }

            byte[] rawUserMap = Base64.decode(userMapRawString, Base64.DEFAULT);

            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(rawUserMap);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream);
            return (HashMap<String, User>)objectInputStream.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            Log.e("BUYLIST", e.getMessage());
            return null;
        }
}
     //method to write/update user information to phone

    public static void storeUserMap(HashMap<String, User> userHashMap, Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("BUYLIST", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(userHashMap);
            String userMapRaw = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            editor.putString("usersMap", userMapRaw);
            editor.apply();
            Log.i("dee", "User map raw: " + userMapRaw);
        }
        catch (IOException e) {
            Log.e("BUYLIST", e.getMessage());
        }
    }
    public String getJSON(String url, int timeout) {
        HttpURLConnection c = null;
        try {
            URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(timeout);
            c.setReadTimeout(timeout);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    return sb.toString();
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }
}
