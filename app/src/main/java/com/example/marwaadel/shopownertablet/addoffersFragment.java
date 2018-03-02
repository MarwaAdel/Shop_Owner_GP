package com.example.marwaadel.shopownertablet;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.cloudinary.Cloudinary;
import com.example.marwaadel.shopownertablet.mPicasso.PicassoClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class addoffersFragment extends Fragment implements View.OnClickListener {


    //UI References
    private EditText fromDateEtxt;
    private EditText toDateEtxt;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;


    DatabaseReference listsRef;
    EditText mTitle, mDescription, mBefore, mAfter,mAmount;
    ImageButton btn1;
    String toEdit = null;
    String uuidRef;
    String mListId;
    Cloudinary cloudinary;
    Uri uri;
    private int PICK_IMAGE_REQUEST = 1;
    String Generated_Id;


   // private AutoCompleteTextView actv;
    public addoffersFragment() {
    }
    public String generatePIN() {
        int randomPIN = (int) (Math.random() * 9000) + 1000;
        return String.valueOf(randomPIN);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miCompose:
                // Toast.makeText(getActivity(), "msg msg", Toast.LENGTH_SHORT).show();
              // listsRef = FirebaseDatabase.getInstance().getReference();.child("OfferList");
//                listsRef = FirebaseDatabase.getInstance().getReference();;
//                AuthData authData = listsRef.getAuth();
//                Log.i("uidbgrb: " , authData.getUid());
//
//                mListId = authData.getUid();
//                Log.i("uidbgrb2: " , mListId);
//                Log.d("constant",Constants.My_UID);
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
               DatabaseReference Ref = FirebaseDatabase.getInstance().getReference().child("OfferList").child(user.getUid());


                String title = mTitle.getText().toString();
                String description = mDescription.getText().toString();
                String before = mBefore.getText().toString();
                String after = mAfter.getText().toString();
                String day1 = fromDateEtxt.getText().toString();
                String day2 = toDateEtxt.getText().toString();
                String amount = mAmount.getText().toString();




                if( mTitle.getText().toString().length() == 0 )
                    mTitle.setError( "Title is required!" );
                if( mDescription.getText().toString().length() == 0 )
                    mDescription.setError( "Description is required!" );
                if( mAfter.getText().toString().length() == 0 )
                    mAfter.setError( "this is required!" );

                if( mBefore.getText().toString().length() == 0 )
                    mBefore.setError( "this is required!" );

                if( mAmount.getText().toString().length() == 0 )
                    mAmount.setError( "amount is required!" );
                if( fromDateEtxt.getText().toString().length() == 0 )
                    fromDateEtxt.setError( "Start Offer is required!" );
                if( toDateEtxt.getText().toString().length() == 0 )
                    toDateEtxt.setError( "End Offer is required!" );
                int b = Integer.parseInt(before.trim());
                int a = Integer.parseInt(after.trim());
                if(b >= a){
                    mAfter.setError( "check your number !!" );
                    mBefore.setError( "check your number !!" );
                }
                else {
                    if (isConnected(getContext())) {

                        if (uri != null) {
                            try {

                                Generated_Id = generatePIN();
                                final InputStream in = ((addoffers) getActivity()).getContentResolver().openInputStream(uri);
                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            cloudinary.uploader().upload(in, Cloudinary.asMap("public_id", Generated_Id));
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };

                                new Thread(runnable).start();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        if (toEdit != null) { //update
                            FirebaseUser user2= FirebaseAuth.getInstance().getCurrentUser();
                            DatabaseReference eRef = FirebaseDatabase.getInstance().getReference().child("OfferList").child(user2.getUid()).child(toEdit);
                            Map<String, Object> updates = new HashMap<>();
                            updates.put("Title", title);
                            updates.put("Description", description);
                            updates.put("DiscountBefore", before);
                            updates.put("DiscountAfter", after);
                            updates.put("DayStartOffer", day1);
                            updates.put("DayEndOffer", day2);
                            updates.put("Amount", amount);
                            if (Generated_Id != null) {
                                updates.put("offerImage", cloudinary.url().generate(Generated_Id + ".jpg"));
                            }
                            eRef.updateChildren(updates);
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            Map<String, Object> values = new HashMap<>();
                            values.put("Title", title);
                            values.put("Description", description);
                            values.put("DiscountBefore", before);
                            values.put("DiscountAfter", after);
                            values.put("DayStartOffer", day1);
                            values.put("DayEndOffer", day2);
                            values.put("status", "false");
                            values.put("Amount", amount);
                            values.put("offerImage", cloudinary.url().generate(Generated_Id + ".jpg"));
                            Ref.push().setValue(values);
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    } else {

                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
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
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_addoffers, container, false);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        //----------
        Map config = new HashMap();
        config.put("cloud_name", "gp");
        config.put("api_key", "667862958976234");
        config.put("api_secret", "zAQ9orjld73mDil8fFsdDNXUQrg");
        cloudinary = new Cloudinary(config);
        //----------

        Intent intent = getActivity().getIntent();
        // String shopUUID = intent.getStringExtra("e
        // ditobj");
        toEdit = (String) intent.getSerializableExtra("object");
        uuidRef = intent.getStringExtra("uuid");

        if (toEdit != null) {
            FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference offerRef = FirebaseDatabase.getInstance().getReference().child("OfferList").child(user.getUid()).child(toEdit);
             offerRef.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        PicassoClient.downloadImg(getActivity(), (String) dataSnapshot.child("offerImage").getValue(), btn1);
        mTitle.setText((String) dataSnapshot.child("Title").getValue());
        mDescription.setText((String) dataSnapshot.child("Description").getValue());
        mBefore.setText((String) dataSnapshot.child("DiscountBefore").getValue());
        mAfter.setText((String) dataSnapshot.child("DiscountAfter").getValue());
        mAmount.setText((String) dataSnapshot.child("Amount").getValue());
        fromDateEtxt.setText((String) dataSnapshot.child("DayStartOffer").getValue());
        toDateEtxt.setText((String) dataSnapshot.child("DayEndOffer").getValue());
    }

    @Override
    public void onCancelled(DatabaseError DatabaseError) {

    }
});


//            mMonth1.setText(toEdit.getMonthStartOffer());
//            mYear1.setText(toEdit.getYearStartOffer());
//            mDay2.setText(toEdit.getDayEndOffer());
//            mMonth2.setText(toEdit.getMonthEndOffer());
//            mYear2.setText(toEdit.getYearEndOffer());


        }


        toolbar.setNavigationIcon(R.drawable.close_icon);
        ((addoffers) getActivity()).setSupportActionBar(toolbar);
        ((addoffers) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), MainActivity.class);
                startActivity(in);
                //getActivity().finish();
            }
        });
        mTitle = (EditText) rootView.findViewById(R.id.editTilte);
        mDescription = (EditText) rootView.findViewById(R.id.editDescription);
        mBefore = (EditText) rootView.findViewById(R.id.editTilte2);
        mAfter = (EditText) rootView.findViewById(R.id.editTilte4);
        mAfter = (EditText) rootView.findViewById(R.id.editTilte4);
        fromDateEtxt = (EditText) rootView.findViewById(R.id.etxt_fromdate);
        toDateEtxt = (EditText) rootView.findViewById(R.id.etxt_todate);
        mAmount=(EditText) rootView.findViewById(R.id.editAmount);

       // actv = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView1);
        findViewsById();
        setDateTimeField();
//        mDay1 = (EditText) rootView.findViewById(R.id.editDay);
//        mMonth1 = (EditText) rootView.findViewById(R.id.editMonth);
//        mYear1 = (EditText) rootView.findViewById(R.id.editYear);
//        mDay2 = (EditText) rootView.findViewById(R.id.editDay2);
//        mMonth2 = (EditText) rootView.findViewById(R.id.editMonth2);
//        mYear2 = (EditText) rootView.findViewById(R.id.editYear2);
        btn1 = (ImageButton) rootView.findViewById(R.id.btn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });

        return  rootView;
    }

    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    private void findViewsById() {
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();
        toDateEtxt.setInputType(InputType.TYPE_NULL);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, bYtE);
                btn1.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v == fromDateEtxt) {
            fromDatePickerDialog.show();
        } else if (v == toDateEtxt) {
            toDatePickerDialog.show();
        }
    }
}
