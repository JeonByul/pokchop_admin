package com.shine_star_11.abc.viewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.shine_star_11.abc.R;
import com.shine_star_11.abc.model.postitem;

import java.util.ArrayList;

/**
 * Created by hyunjunian on 7/30/16.
 */
public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private Context mContext;
    private ArrayList<postitem> postitems;

    public PostAdapter(Context context, ArrayList<postitem> listItem) {
        mContext = context;
        postitems = listItem;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View baseView = View.inflate(mContext, R.layout.post_item,null);
        PostViewHolder postViewHolder = new PostViewHolder(baseView);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        postitem item = postitems.get(position);

        holder.timelineComment.setText(item.getComment());
        holder.timelinePokemon.setText(String.valueOf(item.getPokeNumber()));
        holder.timelineUsername.setText(item.getUserName());
    }

    @Override
    public int getItemCount() {
        return postitems.size();
    }
}
