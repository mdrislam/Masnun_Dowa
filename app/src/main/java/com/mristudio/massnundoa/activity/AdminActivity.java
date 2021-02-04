package com.mristudio.massnundoa.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DatabaseReference;

import com.mristudio.massnundoa.R;
import com.mristudio.massnundoa.model.DataModel;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ArrayList<DataModel> getAllAdmin = new ArrayList<>();

    //DataBase Wraperance
    private DatabaseReference rootRer;
    private DatabaseReference adminRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        initView();
        initDataBaseraperange();
    }

    /**
     * Initiaize Database Rafferance
     **/
    private void initDataBaseraperange() {
    }

    /**
     * Initiaize All view
     **/
    private void initView() {
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(" Admin Panel ");
    }

    /**
     * Add Home Option Category
     **/
    public void addHomeCatagory(View view) {
    }

    /**
     * Add Home Top Banner
     **/
    public void addHomeBanner(View view) {
        startActivity(new Intent(AdminActivity.this,HomeBannerActivity.class));
    }

    /**
     * Post A Dowa
     **/
    public void postDowa(View view) {
    }

    /**
     * Update Or Delete Post
     **/
    public void updatePost(View view) {
    }

    /**
     * Update Or Delete A home CateGory
     **/
    public void updateCataGory(View view) {
    }

    /**
     * Add Admin Or Manege Admin
     **/
    public void addAdmin(View view) {

    }

    /**
     * Jumps To main apps
     **/
    public void gotoMainApps(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("from", "admin");
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        MenuItem item = menu.findItem(R.id.ic_Logout);
        MenuItem itemExit = menu.findItem(R.id.ic_Exit);
        itemExit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                exitAlertDialog();
                return true;
            }
        });
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                logOutAlertDialog();
//                if (isAdminOrNot(email, pass, getAllAdmin)) {
//                    startActivity(new Intent(AdminActivity.this, EditModaratorProfile.class));
//                } else {
//                    Toasty.warning(AdminActivity.this, "You are not a Sdmin").show();
//                }
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * Exit Alert Methode
     **/
    private void exitAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        setResult(RESULT_OK, new Intent().putExtra("EXIT", true));
                        finish();
                    }

                }).create().show();
    }

    /**
     * Logout Alert Methode Message
     **/
    private void logOutAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Really Logout?")
                .setMessage("Are you sure you want to Logout?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        callLogout();
                        finish();
                    }

                }).create().show();
    }

    /**
     * Make Logout Admin
     **/
    private void callLogout() {

    }


}