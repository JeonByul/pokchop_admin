package com.shine_star_11.abc.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.shine_star_11.abc.R;

/**
 * Created by hyunjunian on 7/30/16.
 */
public class PostViewHolder extends RecyclerView.ViewHolder {
    public ImageView timelineUserprofpic;
    public TextView timelineUsername,timelinePokemon,timelineComment;
    //public AdView adView2;
    public PostViewHolder(View itemView) {
        super(itemView);
        timelineUserprofpic = (ImageView) itemView.findViewById(R.id.timeline_userprofpic);
        timelineUsername = (TextView) itemView.findViewById(R.id.timeline_username);
        timelinePokemon = (TextView) itemView.findViewById(R.id.timeline_pokemon);
        timelineComment = (TextView) itemView.findViewById(R.id.timeline_comment);
        //adView2 = (AdView) itemView.findViewById(R.id.adView2);
    }

}
