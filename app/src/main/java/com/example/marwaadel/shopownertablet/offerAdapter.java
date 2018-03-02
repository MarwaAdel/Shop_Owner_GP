package com.example.marwaadel.shopownertablet;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marwaadel.shopownertablet.mPicasso.PicassoClient;
import com.example.marwaadel.shopownertablet.model.OfferDataModel;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * Created by Marwa Adel on 6/18/2016.
 */
public class offerAdapter extends FirebaseListAdapter<OfferDataModel> {
    Activity activity;
    int lastPosition = -1;
    View LastViewd = null;
    View LastViewo = null;


    public offerAdapter(Activity activity, Class<OfferDataModel> modelClass, int modelLayout, Query ref) {
        super(activity, modelClass, modelLayout, ref);
        this.activity = activity;
        Log.d("ddd", ref.toString());
    }

    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else return false;
    }


    @Override
    protected void populateView(View v, final OfferDataModel model, final int position) {
        //   lv=(ListView) v.findViewById(R.id.mListView);
        final ImageView detailImg = (ImageView) v.findViewById(R.id.detailBtn);
        final ImageView analysisImg = (ImageView) v.findViewById(R.id.analysisBtn);
        ImageView imgOffer = (ImageView) v.findViewById(R.id.offerImg);
        TextView title = (TextView) v.findViewById(R.id.titlteoffer);
        TextView description = (TextView) v.findViewById(R.id.descriptionoffer);
        TextView price = (TextView) v.findViewById(R.id.priceoffer);
        final ImageView moreIcon = (ImageView) v.findViewById(R.id.sidemenu);
        Log.d("data", "populateView " + model.getDescription());

        PicassoClient.downloadImg(activity, model.getOfferImage(), imgOffer);
        title.setText(model.getTitle());
        description.setText(model.getDescription());

        price.setText(model.getDiscountAfter() + " L.E");


        detailImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  detailImg.setBackgroundResource(R.drawable.info_icon_active);
                //analysisImg.setBackgroundResource(R.drawable.analysis_icon_unactive);

                if (LastViewo != null && LastViewd != null) {
                    Log.e("in", String.valueOf(LastViewo));
                    ((ImageView) LastViewo).setBackgroundResource(R.drawable.analysis_icon_unactive);
                    ((ImageView) LastViewd).setBackgroundResource(R.drawable.info_icon_unactive);
                } else if (LastViewd != null) {
                    ((ImageView) LastViewd).setBackgroundResource(R.drawable.info_icon_unactive);
                }

                detailImg.setBackgroundResource(R.drawable.info_icon_active);
                analysisImg.setBackgroundResource(R.drawable.analysis_icon_unactive);
                LastViewd = v;

                Bundle arguments = new Bundle();
                arguments.putSerializable("DETAIL_OFFER", getRef(position).getKey());

                DetailActivityFragment fragment = new DetailActivityFragment();
                fragment.setArguments(arguments);

                ((MainActivity) activity).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.offer_detail_container, fragment)
                        .commit();


            }
        });
        analysisImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //detailImg.setBackgroundResource(R.drawable.info_icon_unactive);
                //analysisImg.setBackgroundResource(R.drawable.analysis_icon_active);
                if (LastViewo != null && LastViewd != null) {
                    Log.e("in", String.valueOf(LastViewo));
                    ((ImageView) LastViewo).setBackgroundResource(R.drawable.analysis_icon_unactive);
                    ((ImageView) LastViewd).setBackgroundResource(R.drawable.info_icon_unactive);
                } else if (LastViewo != null) {
                    ((ImageView) LastViewo).setBackgroundResource(R.drawable.analysis_icon_unactive);
                }

                detailImg.setBackgroundResource(R.drawable.info_icon_unactive);
                analysisImg.setBackgroundResource(R.drawable.analysis_icon_active);
                LastViewo = v;
                Bundle arguments = new Bundle();
                arguments.putSerializable("DETAIL_REPORT", model);

                ReportActivityFragment fragment = new ReportActivityFragment();
                fragment.setArguments(arguments);


                ((MainActivity) activity).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.offer_detail_container, fragment, ReportActivityFragment.TAG)
                        .commit();


            }
        });


        moreIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(activity, moreIcon, Gravity.RIGHT);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int i = item.getItemId();
                        if (i == R.id.editItem) {
                            //do something
                            Log.e("item", String.valueOf(i));
                            Intent in = new Intent(activity, addoffers.class);
                            in.putExtra("object", getRef(position).getKey());
                            activity.startActivity(in);
                            return true;
                        } else if (i == R.id.deleteItem) {
                            //do something
                            if (isConnected(activity)) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                builder.setMessage("Are you sure you want to delete this offer ?")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                DatabaseReference itemRef = FirebaseDatabase.getInstance().getReference().child("OfferList").child(user.getUid()).child(getRef(position).getKey());
                                                itemRef.removeValue();

                                            }
                                        })
                                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            } else {
                                AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                                alertDialog.setTitle("No Internet connection.");
                                alertDialog.setMessage("You have no internet connection");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            }
                            return true;
                        } else {
                            return onMenuItemClick(item);
                        }
                    }
                });

                popup.show();

            }
        });

    }

    public boolean isConnected(Activity act) {
        ConnectivityManager cm = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else return false;
    }

}
