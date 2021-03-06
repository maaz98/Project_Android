package com.example.shado.buylist;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


//Class is extending AsyncTask because this class is going to perform a networking operation
@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class Mail extends AsyncTask<Void,Void,Void> {

    //Declaring Variables
    private Context context;
    private Session session;

    //Information to send email
    private String email;
    private String subject;
    private String message;

    private String EMAIL;
    private String PASSWORD;


    //Progress dialog to show while sending email
    private ProgressDialog progressDialog;

    //Class Constructor
    public Mail(Context context, String email, String subject, String message, String EMAIL, String PASSWORD){
        //Initializing variables
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.EMAIL = EMAIL;
        this.PASSWORD = PASSWORD;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... params) {
        //Creating properties

                final Properties props = new Properties();
               Thread t = new Thread(new Runnable() {
                   @Override
                   public void run() {
                       try {
                           //Configuring properties for gmail
                           //If you are not using gmail you may need to change the values
                           props.put("mail.smtp.host", "smtp.gmail.com");
                           props.put("mail.smtp.socketFactory.port", "465");
                           props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                           props.put("mail.smtp.auth", "true");
                           props.put("mail.smtp.port", "465");

                           //Creating a new session
                           session = Session.getDefaultInstance(props,
                                   new javax.mail.Authenticator() {
                                       //Authenticating the password
                                       protected PasswordAuthentication getPasswordAuthentication() {
                                           return new PasswordAuthentication(EMAIL, PASSWORD);
                                       }
                                   });


                           //Creating MimeMessage object
                           MimeMessage mm = new MimeMessage(session);

                           //Setting sender address
                           mm.setFrom(new InternetAddress(EMAIL));
                           //Adding receiver
                           mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                           //Adding subject
                           mm.setSubject(subject);
                           //Adding message
                           mm.setText(message);

                           //Sending email
                           Transport.send(mm);

                       } catch (MessagingException e) {
                           e.printStackTrace();
                       }
                   }
               });
               t.start();

        return null;
    }
}