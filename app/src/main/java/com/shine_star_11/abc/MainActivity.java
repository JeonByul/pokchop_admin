package com.shine_star_11.abc;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, OnMapLongClickListener, GoogleMap.OnMapClickListener {

    SupportMapFragment sMapFragment;
    private GoogleMap mMap;
    LinearLayout llBottomSheet;
    List<ClipData.Item> items;

    FloatingActionButton fabButton;

    AdRequest adRequest = new AdRequest.Builder().build();
    AdView mAdView;

    //img upload var
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sMapFragment = SupportMapFragment.newInstance();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        llBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        //fabButton = (FloatingActionButton) findViewById(R.id.fab);

        BottomSheetBehavior behavior = BottomSheetBehavior.from(llBottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        // floatingactionbutton implementing on map

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fabButton.setOnClickListener(this);

        // google adMob adview implementing

        //mAdView = new AdView(this);
        mAdView = (AdView) findViewById(R.id.adView);
        //mAdView.setAdSize(AdSize.SMART_BANNER);
        //mAdView.setAdUnitId("ca-app-pub-9488666633576327/5185503697");
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //RelativeLayout layout = (RelativeLayout)findViewById(R.id.ad_test);
        //layout.addView(mAdView);
        //AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
        //mAdView.loadAd(adRequestBuilder.build());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));

        android.app.FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();

        sMapFragment.getMapAsync(this);
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

        return super.onOptionsItemSelected(item);
    }

    // Navigation drawer fragment selecting function
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        android.app.FragmentManager fm = getFragmentManager();

        android.support.v4.app.FragmentManager sFm = getSupportFragmentManager();

        int id = item.getItemId();

        if(sMapFragment.isAdded())
            sFm.beginTransaction().hide(sMapFragment).commit();


        if (id == R.id.nav_gallery) {
            if(!sMapFragment.isAdded())
                sFm.beginTransaction().add(R.id.map, sMapFragment).commit();
            else
                sFm.beginTransaction().show(sMapFragment).commit();

        } else if (id == R.id.nav_camera) {
            fm.beginTransaction().replace(R.id.content_frame, new ImportFragment()).commit();

            // Handle the camera action
        } else if (id == R.id.nav_slideshow) {
            fm.beginTransaction().replace(R.id.content_frame, new FreeBoard()).commit();

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    // Map listener functions

    @Override
    public void onMapReady(GoogleMap googleMap){
        Toast.makeText(getApplication(),"PokeMap v1.0. JBJ Corp.",Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplication(),"화면을 길게 눌러서 포켓몬을 추가해주세요~!",Toast.LENGTH_LONG).show();

        mMap = googleMap;

        // Map UI setting
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMapClickListener(this);

        // Add a marker in Sydney and move the camera
        LatLng sokcho = new LatLng(38.206983, 128.591848);
        LatLng pusan = new LatLng(35.179773,129.075005);
        mMap.addMarker(new MarkerOptions().position(sokcho).title("여기는 속초마을~").snippet("놀러오세요~~"));
        mMap.addMarker(new MarkerOptions().position(pusan).title("여기는 부산씨티!").snippet("놀러오이소~~"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sokcho));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(14));
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
            Toast.makeText(getApplication(),"현재 위치를 확인할 수 없습니다.",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onMapLongClick(LatLng point) {
        Toast.makeText(getApplication(),point.toString(),Toast.LENGTH_SHORT).show();
        Marker marker = mMap.addMarker(new MarkerOptions().position(point).title("요기는~~").snippet("호쉐호쉐 출몰지역"));

        llBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(llBottomSheet);
        behavior.setPeekHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 72.f, getResources().getDisplayMetrics()));
        behavior.setHideable(true);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    /*public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }*/

    @Override
    public void onMapClick(LatLng latLng) {
        llBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(llBottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);


    }

    // Image upload funciton
    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.pokemon_picture);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }
}
