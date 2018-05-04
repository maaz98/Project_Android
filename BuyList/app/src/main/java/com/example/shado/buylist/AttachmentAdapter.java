package com.example.shado.buylist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;


public class AttachmentAdapter extends
        RecyclerView.Adapter<AttachmentAdapter.MyViewHolder> {

    Context ctx;
    ArrayList<Attachment> list = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder  {

        ImageView imageView;
        ImageView bin;
        View mView;
        Context ctx;

        public MyViewHolder(final View itemView) {
            super(itemView);
            mView = itemView;
            this.imageView=(ImageView)mView.findViewById(R.id.img);
            this.bin=(ImageView)mView.findViewById(R.id.bin);
        }

    }

    public AttachmentAdapter(ArrayList<Attachment> mData,Context ctx) {

        this.list=new ArrayList<>();
        this.list.clear();
        this.list = mData;
        this.ctx=ctx;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int n) {
        try{
        switch(list.get(n).getType()) {
            case 1:
            case 2:
               // Picasso.with(ctx).load(new File(list.get(n).getAddress())).into(holder.imageView);
                Picasso.with(ctx).load(R.drawable.ic_action_gallery).into(holder.imageView);

                break;
            case 3: Picasso.with(ctx).load(R.drawable.ic_action_video).into(holder.imageView);
                break;
            case 4: Picasso.with(ctx).load(R.drawable.ic_action_file).into(holder.imageView);
                break;
            case 5:
            case 6: Picasso.with(ctx).load(R.drawable.ic_action_audio).into(holder.imageView);
                break;
        }}catch(Exception e){
            e.printStackTrace();
        }
        if(list.get(n)==null){

            holder.mView.setVisibility(View.INVISIBLE);
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(list.get(n).getType()==1){
                    Intent myIntent = new Intent(android.content.Intent.ACTION_VIEW);
                    String source = list.get(n).getAddress();
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    File file = new File(source);
                    Log.e("Uri",source+"123456");
                    //  String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
                    myIntent.setDataAndType(Uri.fromFile(file),"image/jpeg");
                    ctx.startActivity(myIntent);
                }
                else if(list.get(n).getType()==6){
                    Log.e("here",list.get(n).getAddress()+"1111");
                    Intent myIntent = new Intent(android.content.Intent.ACTION_VIEW);
                    String source = list.get(n).getAddress();
                    File file = new File(source);
                    Log.e("Uri",source+"");
                    //  String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
                    myIntent.setDataAndType(Uri.fromFile(file),"audio/wav");
                    ctx.startActivity(myIntent);
                }
                else {
                    Intent myIntent = new Intent(android.content.Intent.ACTION_VIEW);
                    String source = list.get(n).getAddress();
                    File file;
                    if(source!=null) {
                        file = new File(source);
                        Log.e("Uri", source + "");
                        String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
                        String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                        myIntent.setDataAndType(Uri.fromFile(file), mimetype);
                        ctx.startActivity(myIntent);

                    Log.e("mimeType", mimetype + " ");
                    Log.e("extension", extension + " ");
                }else{
                        Log.e("ERROR","source is null");
                    }
                }
            }
        });
        holder.bin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListDetail.remove(n);
            }
        });
    }

    @Override
    public int getItemCount() {
       if(list!=null) return list.size();
       else return 0;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attachment_item_layout,parent, false);
        return new MyViewHolder(v);
    }

    private String fileExt(String url) {
        if (url.indexOf("?") > -1) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.lastIndexOf(".") == -1) {
            return null;
        } else {
            String ext = url.substring(url.lastIndexOf(".") + 1);
            if (ext.indexOf("%") > -1) {
                ext = ext.substring(0, ext.indexOf("%"));
            }
            if (ext.indexOf("/") > -1) {
                ext = ext.substring(0, ext.indexOf("/"));
            }
            return ext.toLowerCase();

        }
    }
}