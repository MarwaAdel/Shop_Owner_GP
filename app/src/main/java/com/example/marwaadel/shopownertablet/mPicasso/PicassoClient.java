package com.example.marwaadel.shopownertablet.mPicasso;

import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;

import com.example.marwaadel.shopownertablet.R;
import com.squareup.picasso.Picasso;


public class PicassoClient {

    public  static  void  downloadImg(Activity c, String url, ImageView img){
        if(url != null && url.length()>0){

            Picasso.with(c).load(url).placeholder(R.drawable.add_pic).into(img);
            Log.d("p","p");
        }else {
            Picasso.with(c).load(R.drawable.add_pic).into(img);
            Log.d("p","p2");
        }
    }
}
