package com.example.marwaadel.shopownertablet;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.marwaadel.shopownertablet.model.OfferDataModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A placeholder fragment containing a simple view.
 */
public class ShowOfferFragment extends Fragment {

    ListView listview;

    offerAdapter OfferAdapter;

    ImageView FAB;
    TextView title;
    String mListId;
    LinearLayout linlaHeaderProgress;
    DatabaseReference refListName;

    Toolbar toolbar;
    private SearchView searchView;
    private MenuItem searchMenuItem;
    private ImageView mTitleView;
    private String mInput;
    private Toolbar mToolbar;
    OfferDataModel offer;
    // offerAdapter OfferAdapter;
//    FragmentManager fragmentManager;


    public ShowOfferFragment() {
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayUseLogoEnabled(false);
//        Intent myIntent = getIntent();
//        handleIntent(myIntent);
//        lv = (ListView) findViewById(R.id.mListView);
//        // DatabaseReference refListName = new DatabaseReference(Constants.FIREBASE_URL_ACTIVE_LIST);
//
//
//        DatabaseReference refListName = FirebaseDatabase.getInstance().getReference();.child("OfferList").child(Constants.My_UID);
//        Query query2 = refListName.orderByChild("Title");
//        OfferAdapter = new offerAdapter(this, OfferDataModel.class, R.layout.offeritem, query2);
//        lv.setAdapter(OfferAdapter);
        //  Toast.makeText(SearchActivity.this, query, Toast.LENGTH_SHORT).show();//here imp afetr click search
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_main_actions, menu);
        //     Toast.makeText(Sh.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();

        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchManager searchManger = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManger.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                doMySearch(newText);
                return false;
            }
        });

    }

    void doMySearch(String query) {

        Query query2 = refListName.orderByChild("Title").startAt(query).endAt(query + "\uf8ff");
        OfferAdapter = new offerAdapter(getActivity(), OfferDataModel.class, R.layout.offeritem, query2);
        listview.setAdapter(OfferAdapter);
       // Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();//here imp afetr click search


    }
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_show_offer, container, false);
        setHasOptionsMenu(true);
        //*** search code
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayUseLogoEnabled(false);
//        Intent myIntent = getActivity().getIntent();
//        handleIntent(myIntent);
        handleIntent(getActivity().getIntent());
        listview = (ListView) rootView.findViewById(R.id.mListView);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        refListName = FirebaseDatabase.getInstance().getReference().child("OfferList").child(user.getUid());
        Query query2 = refListName.orderByChild("status").equalTo("false");

        OfferAdapter = new offerAdapter(getActivity(), OfferDataModel.class, R.layout.offeritem, query2);
        listview.setAdapter(OfferAdapter);
        if (isConnected(getActivity().getApplicationContext())) {
            FAB = (ImageView) rootView.findViewById(R.id.fab);
            //FAB.setBackgroundColor(Color.parseColor("#7d3971"));
//            FAB.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7d3971")));
            FAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), addoffers.class);

                    startActivity(intent);
                    // getActivity().finish();


                }
            });


        }            else {

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

    return rootView;
}




        //Toast.makeText(SearchActivity.this, query, Toast.LENGTH_SHORT).show();//here imp afetr click search


//        //****
//        if (isConnected(getActivity().getApplicationContext())) {
//            linlaHeaderProgress = (LinearLayout) rootView.findViewById(R.id.linlaHeaderProgress);
//            //listview = (ListView) rootView.findViewById(R.id.mListView);
//
//           // DatabaseReference refListName = new DatabaseReference(Constants.FIREBASE_URL_ACTIVE_LIST);
//            DatabaseReference listsRef = FirebaseDatabase.getInstance().getReference();;
//            AuthData authData = listsRef.getAuth();
//            Log.i("uidbgrb: ", authData.getUid());
//
//            mListId = authData.getUid();
//            Log.i("uidbgrb2: ", mListId);
//            listsRef = FirebaseDatabase.getInstance().getReference();.child("OfferList").child(Constants.My_UID);
//            Query query = listsRef.orderByChild("status").equalTo("false");
//            listsRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
////                    Log.d("fief", "onDataChange: " + dataSnapshot.getValue().toString());
//                }
//
//                @Override
//                public void onCancelled(DatabaseError DatabaseError) {
//
//                }
//            });
//            Log.d("kmjk", "onCreateView: " + listsRef.getKey());
//            mOfferAdapter = new FirebaseListAdapter<OfferDataModel>(getActivity(), OfferDataModel.class, R.layout.offeritem, query) {
//                @Override
//                protected void populateView(View v, final OfferDataModel model, final int position) {
//
//                    ImageView imgOffer = (ImageView) v.findViewById(R.id.offerImg);
//                    title = (TextView) v.findViewById(R.id.titlteoffer);
//                    TextView description = (TextView) v.findViewById(R.id.descriptionoffer);
//                    TextView price = (TextView) v.findViewById(R.id.priceoffer);
//                    final ImageView detailImg = (ImageView) v.findViewById(R.id.detailBtn);
//                    final ImageView analysisImg = (ImageView) v.findViewById(R.id.analysisBtn);
//
//                    Log.d("data", "populateView " + model.getDescription());
//
//
//                    detailImg.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            //detailImg.setImageResource(R.drawable.info_icon_active);
//                            //analysisImg.setImageResource(R.drawable.analysis_icon_unactive);
//                            // Log.d("clicled", String.valueOf(position));
//                            Bundle arguments = new Bundle();
//                            arguments.putSerializable("DETAIL_OFFER", getRef(position).getKey());
//
//                            DetailActivityFragment fragment = new DetailActivityFragment();
//                            fragment.setArguments(arguments);
//
//
//                            ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.offer_detail_container, fragment, DetailActivityFragment.TAG)
//                                    .commit();
//
//
//                        }
//                    });
//
//                    analysisImg.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            //detailImg.setImageResource(R.drawable.info_icon_unactive);
//                            //analysisImg.setImageResource(R.drawable.analysis_icon_active);
//
//                            //  Log.d("clicled", String.valueOf(position));
//                            Bundle arguments = new Bundle();
//                            arguments.putSerializable("DETAIL_REPORT", model);
//
//                            ReportActivityFragment fragment = new ReportActivityFragment();
//                            fragment.setArguments(arguments);
//
////
//                            ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.offer_detail_container, fragment, ReportActivityFragment.TAG)
//                                    .commit();
//
//
//                        }
//                    });
//
//                    PicassoClient.downloadImg(getActivity(), model.getOfferImage(), imgOffer);
//                    title.setText(model.getTitle());
//                    description.setText(model.getDescription());
//
//
//                    price.setText(model.getDiscountAfter() + " L.E");
//                }
//            };
//            Log.e("ref", String.valueOf(listsRef));
//            linlaHeaderProgress.setVisibility(View.VISIBLE);
//            listview.setAdapter(OfferAdapter);
//            OfferAdapter.registerDataSetObserver(new DataSetObserver() {
//                @Override
//                public void onChanged() {
//                    super.onChanged();
//                    linlaHeaderProgress.setVisibility(View.GONE);
//                }
//            });
//
//
//            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View v,
//                                        int position, long id) {
//
//
//                    OfferDataModel offer = OfferAdapter.getItem(position);
//                    Log.d("clicked", offer.getTitle());
//
//
//                }
//            });
//

//            FAB = (ImageView) rootView.findViewById(R.id.fab);
//            //FAB.setBackgroundColor(Color.parseColor("#7d3971"));
////            FAB.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7d3971")));
//            FAB.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Intent intent = new Intent(getActivity(), addoffers.class);
//
//                    startActivity(intent);
//                    // getActivity().finish();
//
//
//                }
//            });
//
//
//        } else {
//
//            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
//            alertDialog.setTitle("No Internet connection.");
//            alertDialog.setMessage("You have no internet connection");
//            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//            alertDialog.show();
//        }
//
//  return rootView;
//    }


    /*search*/
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (OfferAdapter != null) {
//            OfferAdapter.cleanup();
////           finish();
//        }
//    }


}
