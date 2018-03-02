package com.example.marwaadel.shopownertablet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FireBaseLoginActivity extends AppCompatActivity {


    DatabaseReference mFirebaseRef;
    ProgressDialog mAuthProgressDialog;
    Button LoginBtn;
    EditText mEditTextEmailInput, mEditTextPasswordInput;
    String type;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    boolean mFlag = false;

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_login_screen);

        mEditTextEmailInput = (EditText) findViewById(R.id.edit_text_email);
        mEditTextPasswordInput = (EditText) findViewById(R.id.edit_text_password);
        LoginBtn = (Button) findViewById(R.id.login_with_password);
        mAuthProgressDialog = new ProgressDialog(FireBaseLoginActivity.this);
        mAuthProgressDialog.setTitle(getString(R.string.progress_dialog_loading));
        mAuthProgressDialog.setMessage(getString(R.string.progress_dialog_authenticating_with_firebase));
        mAuthProgressDialog.setCancelable(false);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {

            Intent i = new Intent(FireBaseLoginActivity.this, MainActivity.class);
            //  i.putExtra("UID", user.getUid());

            //Constants.My_UID = user.getUid();
            startActivity(i);
            finish();
            Log.d("Login    ", "onAuthStathanged:signed_in:" + user.getUid());
        } else {
            // User is signed out
            Log.d("Login", "onAuthStateChanged:signed_out");
            LoginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signInPassword();
                }
            });
            mEditTextPasswordInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                    if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        signInPassword();
                    }
                    return true;
                }
            });

        }
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            }
        };
        /**
         * Call signInPassword() when user taps "Done" keyboard action
         */


    }

    public void signInPassword() {
        String email = mEditTextEmailInput.getText().toString();
        String password = mEditTextPasswordInput.getText().toString();
        /**
         *
         * If email and password are not empty show progress dialog and try to authenticate
         */
        if (email.equals("")) {
            mEditTextEmailInput.setError(getString(R.string.error_cannot_be_empty));
            return;
        }

        if (password.equals("")) {
            mEditTextPasswordInput.setError(getString(R.string.error_cannot_be_empty));
            return;
        }
        mAuthProgressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mAuthProgressDialog.dismiss();
                        final FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            //Log.d("login", "signInWithEmail:onComplete:" + task.isSuccessful());
                            // mAuthProgressDialog.dismiss();
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (task.isSuccessful()) {
                                mAuthProgressDialog.dismiss();
                                //  Log.d("see",mAuth.getCurrentUser().getUid());

                                DatabaseReference userRrf = FirebaseDatabase.getInstance().getReference().child("users");

                                DatabaseReference userExist = FirebaseDatabase.getInstance().getReference().child("users");
                                userExist.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        for (DataSnapshot child : snapshot.getChildren()) {
                                            Log.e("key", child.getKey());
                                            if (child.getKey().equals(user.getUid())) {
                                                mFlag = true;
                                                type = (String) child.child("type").getValue();
                                            }
                                        }
                                        if (mFlag == true) {
                                            if (type.equals("Shop")) {
                                                Intent intent = new Intent(FireBaseLoginActivity.this, MainActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
//                                            } else {
//                                                showErrorToast("you are not shop owner !");
//                                            }
                                            } else {
                                                showErrorToast("you are not shop owner !");
                                            }
                                        } else {
                                            showErrorToast("you are not shop owner !");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
//                                userRrf.addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(DataSnapshot dataSnapshot) {
//                                        type = (String) dataSnapshot.child("type").getValue();
//                                        if (type.equals("Shop")) {
//                                            Intent intent = new Intent(FireBaseLoginActivity.this, MainActivity.class);
//                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                            startActivity(intent);
//                                            finish();
//                                        } else {
//                                            showErrorToast("you are not shop owner !");
//                                        }
//
//                                    }
//
//                                    @Override
//                                    public void onCancelled(DatabaseError DatabaseError) {
//
//                                        mAuthProgressDialog.dismiss();
////                                        switch (databaseError.getCode()) {
////                                            case DatabaseError.UNAVAILABLE:
////                                                mEditTextEmailInput.setError(getString(R.string.error_message_email_issue));
////                                                break;
////                                            case DatabaseError.NETWORK_ERROR:
////                                                showErrorToast(getString(R.string.error_message_failed_sign_in_no_network));
////                                                break;
////                                            default:
////                                                showErrorToast(databaseError.toString());
////                                        }
//                                    }
//                                });


                            }
                        }

                        if (!task.isSuccessful()) {
                            Log.w("signin", "signInWithEmail", task.getException());
                            Toast.makeText(FireBaseLoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                });
    }

    private void showErrorToast(String message) {
        Toast.makeText(FireBaseLoginActivity.this, message, Toast.LENGTH_LONG).show();
    }


}