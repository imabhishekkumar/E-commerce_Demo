package com.shoppingapp.abhis.demo_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseReference,mUserDatabaseReference;
    RecyclerView recyclerView;
    ItemAdapter itemAdapter;
    List<Model> itemList;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    String product_name;
    TextView navEmail, navName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mDatabase.getReference().child("Products");
        mAuth= FirebaseAuth.getInstance();
        mUserDatabaseReference=mDatabase.getReference().child("Users").child(mAuth.getUid());
        mDatabaseReference.keepSynced(true);
        mUserDatabaseReference.keepSynced(true);
        itemList=new ArrayList<>();



        recyclerView=findViewById(R.id.recyclerViewID);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        navName = headerView.findViewById(R.id.nav_username);
        navEmail= headerView.findViewById(R.id.nav_userEmail);
        navEmail.setText(mAuth.getCurrentUser().getEmail());
        navName.setText(mAuth.getCurrentUser().getDisplayName());
        mUserDatabaseReference.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //navName.setText(dataSnapshot.child("name").getValue().toString());
           }

           @Override
           public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

           }

           @Override
           public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

           }

           @Override
           public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
        Toast.makeText(MainActivity.this,"Welcome, " + mAuth.getCurrentUser().getDisplayName(),Toast.LENGTH_SHORT).show();
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
       // Model model=dataSnapshot.getValue(Model.class);
        //itemList.add(model);
        Model model=new Model();
for(long i=1;i<=dataSnapshot.getChildrenCount();i++)
{
    model.setItemName(dataSnapshot.child(String.valueOf(i)).child("name").getValue().toString());
    itemList.add(model);
}

        itemAdapter=new ItemAdapter(MainActivity.this,itemList);
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.notifyDataSetChanged();
       // model.setItemName(dataSnapshot.getValue().toString());
    /*    for(int i=0;i<3;i++) {
            product_name = dataSnapshot.child(String.valueOf(i)).child("name").getValue().toString();
            model.setItemName(product_name);
        }*/
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id== R.id.action_logout){
            mAuth.signOut();
            Intent main = new Intent(MainActivity.this,LoginPage.class);
            startActivity(main);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
        } else if (id == R.id.nav_wishlist) {

        } else if (id == R.id.nav_cart) {

        } else if (id == R.id.nav_logout) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
