package com.leventealbert.mesi;

import android.os.Bundle;
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
        final ReadMessage mess = new ReadMessage(mReceiverId);
        String json = new Gson().toJson(mess);
        new AsyncHttpTask("PUT", json, new AsyncHttpTask.TaskListener() {
            @Override
            public void onFinished(String result) {
                if (result.equals("")) {
                    Toast.makeText(MessageActivity.this, "Message was NOT marked read!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MessageActivity.this, "" + result, Toast.LENGTH_LONG).show();
                    mLayoutManager.scrollToPosition(mMessages.size());
                }
            }
        }).execute("http://mesi.leventealbert.com/api/readMessages");

        mRecyclerView = (RecyclerView) findViewById(R.id.activity_message_list);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mMessages = new MessageList();

        for (Message message : BaseApplication.getMessages()) {

            if (message.getFromId().equals(mReceiverId) && message.getToId().equals(BaseApplication.getCurrentUserId()) ||
                    message.getToId().equals(mReceiverId) && message.getFromId().equals(BaseApplication.getCurrentUserId())) {
                mMessages.add(message);
            }
        }

        mAdapter = new MessageListAdapter(this, mMessages, R.layout.message_row);
        mRecyclerView.setAdapter(mAdapter);

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

    private class ReadMessage {
        private String sender;

        public ReadMessage(String sender){
            this.sender = sender;
        }
    }
}
