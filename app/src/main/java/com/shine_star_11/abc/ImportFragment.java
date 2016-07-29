package com.shine_star_11.abc;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by shine_star_11 on 7/19/16.
 */
public class ImportFragment extends Fragment {

    // Firebase db instaces
    private DatabaseReference mDatabase;
    // Admob instance variables
    public AdRequest adRequest = new AdRequest.Builder().build();
    public AdView mAdView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_import, container,false);

        return view;
    }
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        mAdView = (AdView) view.findViewById(R.id.adView2);
        mAdView.loadAd(adRequest);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
}
