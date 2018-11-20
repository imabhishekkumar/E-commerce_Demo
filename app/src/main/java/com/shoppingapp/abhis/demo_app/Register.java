package com.shoppingapp.abhis.demo_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



import java.util.Objects;

public class Register extends AppCompatActivity {
    Button login, registerbtn;
    EditText email, password, name;
    public DatabaseReference mDatabaseRef;
    public FirebaseDatabase mDataBase;


    private FirebaseAuth mAuth;



    ProgressDialog mProgressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mProgressDialog = new ProgressDialog(this);

        login = findViewById(R.id.login);
        registerbtn = findViewById(R.id.registerbtn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);



        mDataBase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDataBase.getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();








        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = name.getText().toString().trim();
                final String em = email.getText().toString().trim();
                final String pwd = password.getText().toString().trim();
                if (!TextUtils.isEmpty(username) &&
                        !TextUtils.isEmpty(em)){
               createNewAccount();
                finish();

            }else
            Toast.makeText(Register.this,"Invalid Details",Toast.LENGTH_SHORT).show();}
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login= new Intent(Register.this,LoginPage.class);
                startActivity(login);
                finish();

            }
        });
    }


    private void createNewAccount() {
        final String username = name.getText().toString().trim();
        final String em = email.getText().toString().trim();
         String pwd = password.getText().toString().trim();
        try{
        if (!TextUtils.isEmpty(username) &&
                !TextUtils.isEmpty(em) &&
                !TextUtils.isEmpty(pwd)) {
            mProgressDialog.setMessage("Creating account..");
            mProgressDialog.show();

            mAuth.createUserWithEmailAndPassword(em, pwd)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            if (authResult != null) {


                                String userid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                                DatabaseReference currentuserdb = mDatabaseRef.child(userid);
                                currentuserdb.child("name").setValue(username);
                                currentuserdb.child("email").setValue(em);
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(username).build();
                                FirebaseUser user= mAuth.getCurrentUser();
                                user.updateProfile(profileUpdates);
                                mProgressDialog.dismiss();



                                Intent intent = new Intent(Register.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);




                            }


                                }






                    });
        }


    }catch(Exception e){}}


    @Override
    protected void onDestroy() {
        mProgressDialog.dismiss();
        super.onDestroy();
    }

    }
