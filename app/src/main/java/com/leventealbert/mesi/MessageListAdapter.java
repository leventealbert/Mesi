package com.leventealbert.mesi;

import android.content.Context;
import android.content.Intent;
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

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder>{

    private LayoutInflater mInflator;
    private int mViewResource;
    private Context mContext;

    private List<Message> mData = Collections.emptyList();

    public MessageListAdapter(Context context, List<Message> data, int viewResource){
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
        Message current = mData.get(position);
        holder.message.setText(current.getMessage());
        holder.date.setText(current.getTimeStamp());

        Picasso.with(mContext).load(BaseApplication.getAvatar(current.getFromId()))
                .transform(new RoundedTransformation(40, 4))
                .error(R.drawable.ic_account_circle_grey600_48dp)
                .placeholder(R.drawable.ic_account_circle_grey600_48dp)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView message;
        TextView date;

        public ViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.message_row_image);
            message = (TextView) itemView.findViewById(R.id.message_row_message);
            date = (TextView) itemView.findViewById(R.id.message_row_date);
        }
    }
}
