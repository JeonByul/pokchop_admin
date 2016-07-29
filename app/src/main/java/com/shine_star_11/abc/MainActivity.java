package com.shine_star_11.abc;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.shine_star_11.abc.SignInActivity;
import com.shine_star_11.abc.pokemonPost;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, OnMapLongClickListener, GoogleMap.OnMapClickListener {

    SupportMapFragment sMapFragment;
    private GoogleMap mMap;
    LinearLayout llBottomSheet;
    Marker marker;
    List<ClipData.Item> items;

    FloatingActionButton fabButton;

    // Admob instance variables
    AdRequest adRequest = new AdRequest.Builder().build();
    AdView mAdView;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseUser CurrentUser;
    private String mUsername;
    private String mUseremail;
    private String mPhotoUrl;
    public static final String ANONYMOUS = "anonymous";
    private GoogleApiClient mGoogleApiClient;

    // Firebase db instaces
    private DatabaseReference mDatabase;
    private Button enroll;

    // pokemon list
    String[] textArr = {"#001.Bulbasaur","#002.Ivysaur","#003.Venusaur","#004.Charmander","#005,Charmeleon","#006.Charizard",
            "#007.Squirtle","#008.Wartortle","#009.Blastoise","#010.Caterpie","#011.Metapod","#012.Butterfree","#013.Weedle","#014.Kakuna",
            "#015.Beedrill"};

    //img upload var
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        sMapFragment = SupportMapFragment.newInstance();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        llBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        //fabButton = (FloatingActionButton) findViewById(R.id.fab);

        BottomSheetBehavior behavior = BottomSheetBehavior.from(llBottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);

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
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                TextView userinfo_name = (TextView) findViewById(R.id.drawer_user_name);
                TextView userinfo_email = (TextView) findViewById(R.id.drawer_user_email);
                String name = CurrentUser.getDisplayName().toString();
                String email = CurrentUser.getEmail().toString();
                Uri photoUrl = CurrentUser.getPhotoUrl();
                userinfo_name.setText(name);
                userinfo_email.setText(email);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));

        android.app.FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();

        sMapFragment.getMapAsync(this);

        // Initialize Firebase Auth, Sign in method for unauthenticated User
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }
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

        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mUsername = ANONYMOUS;
                startActivity(new Intent(this, SignInActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

        //return super.onOptionsItemSelected(item);
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
        mMap.addMarker(new MarkerOptions().position(sokcho).title("여기는 속초마을~").snippet("놀러오세요~~"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sokcho));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(14));
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
            Toast.makeText(getApplication(),"현재 위치를 확인할 수 없습니다.",Toast.LENGTH_SHORT).show();
        }
        final Query pokemonlist = mDatabase.child("pokemonPosts");

        pokemonlist.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                pokemonPost mapinfo = dataSnapshot.getValue(pokemonPost.class);
                mMap.addMarker(new MarkerOptions().position(new LatLng(mapinfo.enlat,mapinfo.enlng)).title(textArr[(int) mapinfo.pokenumber]).snippet(mapinfo.comment));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onMapLongClick(final LatLng point) {
        //Toast.makeText(getApplication(),point.toString(),Toast.LENGTH_SHORT).show();
        if(marker != null)
            marker.remove();

        marker = mMap.addMarker(new MarkerOptions().position(point).title("요기는~~").snippet("호쉐호쉐 출몰지역"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));

        llBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(llBottomSheet);
        behavior.setPeekHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 72.f, getResources().getDisplayMetrics()));
        behavior.setHideable(true);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        final Spinner mySpinner = (Spinner)findViewById(R.id.pokemon_name);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.pokemon_dbrow, R.id.pokemon_dbname, textArr);
        mySpinner.setAdapter(adapter);

        // pokemon enrolling

        final String enname = CurrentUser.getDisplayName().toString();
        final String enemail = CurrentUser.getEmail().toString();
        final Uri enphotoUrl = CurrentUser.getPhotoUrl();
        enroll = (Button) findViewById(R.id.pokemon_upload);
        enroll.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                llBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);
                BottomSheetBehavior behavior = BottomSheetBehavior.from(llBottomSheet);
                EditText comment = (EditText) findViewById(R.id.pokemon_content);
                String comment_str = comment.getText().toString();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                pokemonPost newpokemon;

                if(enphotoUrl==null)
                    newpokemon = new pokemonPost(comment_str, enemail, point.latitude, point.longitude, "", mySpinner.getSelectedItemPosition(), "", 3, enname, Calendar.getInstance().getTime().toString());
                else
                    newpokemon = new pokemonPost(comment_str, enemail, point.latitude, point.longitude, "", mySpinner.getSelectedItemPosition(), enphotoUrl.toString(), 3, enname, Calendar.getInstance().getTime().toString());

                mDatabase.child("pokemonPosts").push().setValue(newpokemon);

                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                mySpinner.setSelection(0);
                comment.setText("");
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
    }

    @Override
    public void onMapClick(LatLng latLng) {
        llBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(llBottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        if(marker != null)
            marker.remove();
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
