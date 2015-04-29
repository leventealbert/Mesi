package com.leventealbert.mesi;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;


public class MessageActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MessageListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private String mReceiverId;
    private MessageList mMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Bundle bundle = getIntent().getExtras();
        mReceiverId = bundle.getString("id");

        setToolBar("Mesi with " + bundle.getString("firstName"));

        /*mark messages read*/
        final HashMap<String,String> mess = new HashMap<>();
        mess.put("sender",mReceiverId);
        String json = new Gson().toJson(mess);
        new AsyncHttpTask("PUT", json, new AsyncHttpTask.TaskListener() {
            @Override
            public void onFinished(String result) {
                if (result.equals("")) {
                    Toast.makeText(MessageActivity.this, "Message was NOT marked read!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MessageActivity.this, "" + result, Toast.LENGTH_SHORT).show();
                    mLayoutManager.scrollToPosition(mMessages.size());
                }
            }
        }).execute("http://mesi.leventealbert.com/api/readMessages");

        mRecyclerView = (RecyclerView) findViewById(R.id.activity_message_list);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_message_swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.accent_color);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });

        loadMessages();

        mLayoutManager.scrollToPosition(mMessages.size());

        Button send = (Button) findViewById(R.id.activity_message_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        EditText text = (EditText) findViewById(R.id.activity_message_text);
        //text.setImeActionLabel("Send", KeyEvent.KEYCODE_ENTER);
        text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    sendMessage();
                }
                return true;
            }
        });
    }

    private void refreshItems() {
        //getting messages
        new AsyncHttpTask("GET", new AsyncHttpTask.TaskListener() {
            @Override
            public void onFinished(String result) {
                try {
                    MessageList messageList = new Gson().fromJson(result, MessageList.class);
                    BaseApplication.setMessages(messageList);

                    //all items loaded refresh layout
                    loadMessages();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).execute("http://mesi.leventealbert.com/api/messages");
    }

    private void loadMessages() {

        mMessages = new MessageList();

        for (Message message : BaseApplication.getMessages()) {

            if (message.getFromId().equals(mReceiverId) && message.getToId().equals(BaseApplication.getCurrentUserId()) ||
                    message.getToId().equals(mReceiverId) && message.getFromId().equals(BaseApplication.getCurrentUserId())) {
                mMessages.add(message);
            }
        }
        // Update the adapter and notify data set changed
        mAdapter = new MessageListAdapter(this, mMessages, R.layout.message_row);
        mRecyclerView.setAdapter(mAdapter);

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void sendMessage() {
        final EditText text = (EditText) findViewById(R.id.activity_message_text);

        if (!text.getText().toString().equals("")){
            final Message message = new Message(BaseApplication.getCurrentUserId(), mReceiverId, text.getText().toString(), true);
            String json = new Gson().toJson(message);

            /*Sending invite*/
            new AsyncHttpTask("PUT", json, new AsyncHttpTask.TaskListener() {
                @Override
                public void onFinished(String result) {
                    if (result.equals("")) {
                        Toast.makeText(MessageActivity.this, "Message was NOT successful!", Toast.LENGTH_SHORT).show();
                    } else {
                        mMessages.add(message);
                        mAdapter.notifyItemInserted(mMessages.size() - 1);
                        text.setText("");
                        mLayoutManager.scrollToPosition(mMessages.size());
                    }
                }
            }).execute("http://mesi.leventealbert.com/api/sendMessage");
        }
    }
}
