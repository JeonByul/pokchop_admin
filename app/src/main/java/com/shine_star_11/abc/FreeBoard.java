package com.shine_star_11.abc;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.database.ValueEventListener;
import com.shine_star_11.abc.model.postitem;
import com.shine_star_11.abc.viewHolder.PostAdapter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by shine_star_11 on 7/19/16.
 */
public class FreeBoard extends Fragment {

    private FirebaseUser CurrentUser;
    String CurrentUserEmail;
    // Firebase db instaces
    private DatabaseReference mDatabase;
    // Admob instance variables
    public AdRequest adRequest = new AdRequest.Builder().build();
    public AdView mAdView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_freeboard, container,false);
        CurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        CurrentUserEmail = CurrentUser.getEmail().toString();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        final Query pokemonlist = mDatabase.child("pokemonPosts").orderByChild("pokenumber");
        final LinearLayout llScrollParent = (LinearLayout) view.findViewById(R.id.ll_scroll_free);

        pokemonlist.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                pokemonPost mapinfo = dataSnapshot.getValue(pokemonPost.class);
                if (mapinfo.pokenumber != 0 && CurrentUserEmail.equals(mapinfo.email)) {
                    postitem item = new postitem(mapinfo.created_at,mapinfo.enlat, mapinfo.enlng, (int) mapinfo.pokenumber,mapinfo.username,mapinfo.profile_img,mapinfo.comment);
                    View v = View.inflate(view.getContext(),R.layout.post_item,null);
                    ImageView timelineUserprofpic;
                    TextView timelineUsername,timelinePokemon,timelineComment,timelineCreatedAt;

                    timelineUserprofpic = (ImageView) v.findViewById(R.id.timeline_userprofpic);
                    timelineUsername = (TextView) v.findViewById(R.id.timeline_username);
                    timelinePokemon = (TextView) v.findViewById(R.id.timeline_pokemon);
                    timelineComment = (TextView) v.findViewById(R.id.timeline_comment);
                    timelineCreatedAt = (TextView) v.findViewById(R.id.timeline_createdAt);

                    timelineComment.setText(item.getComment());
                    timelineUsername.setText("by "+item.getUserName());
                    timelinePokemon.setText("No."+String.valueOf(item.getPokeNumber()));
                    timelineCreatedAt.setText("At "+item.getCreatedAt().substring(4,16));

                    /* URL url = null;
                    try {
                        url = new URL(mapinfo.profile_img);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                        timelineUserprofpic.setImageResource(R.mipmap.app_icon);
                    }
                    Bitmap bmp = null;
                    try {
                        if(url != null) {
                            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            timelineUserprofpic.setImageBitmap(bmp);
                        }
                        else
                            timelineUserprofpic.setImageResource(R.mipmap.app_icon);
                    } catch (IOException e) {
                        timelineUserprofpic.setImageResource(R.mipmap.app_icon);
                        e.printStackTrace();
                    }*/
                    timelineUserprofpic.setImageResource(0x7f030008 + Integer.valueOf(Integer.toHexString((int) mapinfo.pokenumber - 1), 16));
                    llScrollParent.addView(v);
                }
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

        //postitem
//        RecyclerView rvList = (RecyclerView) view.findViewById(R.id.rv_list);
//        Context context = view.getContext();
//
//        PostAdapter adapter = new PostAdapter(context, listItem);
//        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
//        rvList.setAdapter(adapter);

        return view;
    }
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
//        mAdView = (AdView) view.findViewById(R.id.adView2);
//        mAdView.loadAd(adRequest);
//        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
}
