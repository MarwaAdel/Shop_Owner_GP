package com.example.marwaadel.shopownertablet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marwaadel.shopownertablet.mPicasso.PicassoClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {
    TextView tvTitle, tvDescription, tvBefore, tvAfter, tvAmount, tvStartOffer, tvEndOffer;
    offerAdapter OfferAdapter;
    ListView listview;
    String offer;
    public static final String TAG = DetailActivityFragment.class.getSimpleName();
    static final String DETAIL_OFFER = "DETAIL_OFFER";

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
       // final ListView listview = (ListView) rootView.findViewById(R.id.mListView);
      //  setHasOptionsMenu(true);
        Bundle arguments = getArguments();
        if (arguments != null) {
            offer = (String) arguments.getSerializable(DetailActivityFragment.DETAIL_OFFER);


            // 3. get reference to offer textView
            final ImageView Img = (ImageView) rootView.findViewById(R.id.btndetail);
            tvTitle = (TextView) rootView.findViewById(R.id.titlte_detail);
            tvDescription = (TextView) rootView.findViewById(R.id.description_detail);
            tvBefore = (TextView) rootView.findViewById(R.id.before_detail);
            tvAfter = (TextView) rootView.findViewById(R.id.after_detail);
            tvAmount = (TextView) rootView.findViewById(R.id.amount_detail);
            tvStartOffer = (TextView) rootView.findViewById(R.id.fromdate_detail);
            tvEndOffer = (TextView) rootView.findViewById(R.id.todate_detail);
            // 4. display details on textView
            FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference offerRef = FirebaseDatabase.getInstance().getReference().child("OfferList").child(user.getUid()).child(offer);
            offerRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    PicassoClient.downloadImg(getActivity(), (String) dataSnapshot.child("offerImage").getValue(), Img);
                    tvTitle.setText((String) dataSnapshot.child("Title").getValue());
                    tvDescription.setText((String) dataSnapshot.child("Description").getValue());
                    tvBefore.setText((String) dataSnapshot.child("DiscountBefore").getValue());
                    tvAfter.setText((String) dataSnapshot.child("DiscountAfter").getValue());
                    tvAmount.setText((String) dataSnapshot.child("Amount").getValue());
                    tvStartOffer.setText((String) dataSnapshot.child("DayStartOffer").getValue());
                    tvEndOffer.setText((String) dataSnapshot.child("DayEndOffer").getValue());
                }


                @Override
                public void onCancelled(DatabaseError DatabaseError) {

                }
            });


        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
       // inflater.inflate(R.menu.menu_detail, menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent in = new Intent(getContext(), addoffers.class);
                in.putExtra("object", offer);
                startActivity(in);

                return true;

            case R.id.action_delete:
                if (isConnected(getActivity().getApplicationContext())) {
                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference itemRef = FirebaseDatabase.getInstance().getReference().child("OfferList").child(user.getUid()).child(offer);
                    itemRef.removeValue();
                    Toast.makeText(getContext(), "deleted", Toast.LENGTH_SHORT).show();


                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
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
            default:
                return super.onOptionsItemSelected(item);
        }
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


}
