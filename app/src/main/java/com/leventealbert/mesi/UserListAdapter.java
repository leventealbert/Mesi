package com.leventealbert.mesi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder>{

    private LayoutInflater mInflator;
    private int mViewResource;
    private Context mContext;

    private List<User> mData = Collections.emptyList();

    public UserListAdapter(Context context, List<User> data, int viewResource){
        mInflator = LayoutInflater.from(context);
        mData = data;
        mViewResource = viewResource;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflator.inflate(mViewResource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User current = mData.get(position);
        holder.name.setText(current.getFullName());

        if (current.getIsOnline()) {
            holder.subTitle.setText("online");
            holder.subTitle.setTextColor(mContext.getResources().getColor(R.color.accent_color));
        } else {
            holder.subTitle.setText("offline");
            holder.subTitle.setTextColor(mContext.getResources().getColor(R.color.secondary_text));
        }

        if (current.getNewMessagesCount() > 0) {
            holder.messages.setText("" + current.getNewMessagesCount());
        } else {
            holder.messages.setVisibility(View.INVISIBLE);
        }

        Picasso.with(mContext).load(current.getAvatar())
                .transform(new RoundedTransformation(40, 4))
                .error(R.drawable.ic_account_circle_grey600_48dp)
                .placeholder(R.drawable.ic_account_circle_grey600_48dp)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        ImageView image;
        TextView name;
        TextView subTitle;
        TextView messages;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            image = (ImageView) itemView.findViewById(R.id.list_row_image);
            name = (TextView) itemView.findViewById(R.id.list_row_name);
            subTitle = (TextView) itemView.findViewById(R.id.list_row_subtile);
            messages = (TextView) itemView.findViewById(R.id.list_row_messages);
        }

        @Override
        public void onClick(View v) {
            User current = mData.get(getPosition());

            Intent intent = new Intent(v.getContext(), MessageActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString("id", current.getId());
            bundle.putString("firstName", current.getFirstName());

            intent.putExtras(bundle);

            v.getContext().startActivity(intent);
        }
    }
}
