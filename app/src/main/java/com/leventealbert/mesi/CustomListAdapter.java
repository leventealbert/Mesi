package com.leventealbert.mesi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.ViewHolder> {

    private LayoutInflater mInflator;
    private int mViewResource;

    private List<User> mData = Collections.emptyList();

    public CustomListAdapter(Context context, List<User> data, int viewResource){
        mInflator = LayoutInflater.from(context);
        mData = data;
        mViewResource = viewResource;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflator.inflate(mViewResource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User current = mData.get(position);
        holder.name.setText(current.fullName);
        holder.image.setImageResource(current.iconId);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.list_row_name);
            image = (ImageView) itemView.findViewById(R.id.list_row_image);
        }
    }

}
