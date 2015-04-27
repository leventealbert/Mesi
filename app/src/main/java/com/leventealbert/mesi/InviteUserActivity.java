package com.leventealbert.mesi;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;


public class InviteUserActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_user);
        setToolBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_invite_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_send) {
            saveUser();
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveUser() {

        EditText firstName = (EditText) findViewById(R.id.invite_user_first_name);
        EditText lastName = (EditText) findViewById(R.id.invite_user_last_name);
        EditText email = (EditText) findViewById(R.id.invite_user_email);

        User user = new User(firstName.getText().toString(),lastName.getText().toString(),email.getText().toString());
        String json = new Gson().toJson(user);

        /*Sending invite*/
        new AsyncHttpTask("PUT", json, new AsyncHttpTask.TaskListener() {
            @Override
            public void onFinished(String result) {
                if (result.equals("")) {
                    Toast.makeText(InviteUserActivity.this, "Invitation was NOT successful!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InviteUserActivity.this, "Invitation sent!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).execute("http://mesi.leventealbert.com/api/addUser");
    }
}
