package com.leventealbert.mesi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;


public class LoginActivity extends BaseActivity {

    private EditText mUsername;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setToolBar("Please Login", false);

        // Set up the login form.
        mUsername = (EditText) findViewById(R.id.activity_login_username);

        mPassword = (EditText) findViewById(R.id.activity_login_password);
        mPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mSignInButton = (Button) findViewById(R.id.activity_login_sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

    }

    public void attemptLogin() {
        // Reset errors.
        mUsername.setError(null);
        mPassword.setError(null);

        // Store values at the time of the login attempt.
        final String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPassword.setError(getString(R.string.error_field_required));
            focusView = mPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mUsername.setError(getString(R.string.error_field_required));
            focusView = mUsername;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // kick off a background task to
            // perform the user login attempt.
            final HashMap<String, String> mess = new HashMap<>();
            mess.put("username", username);
            mess.put("password", password);

            String json = new Gson().toJson(mess);
            new AsyncHttpTask("PUT", json, new AsyncHttpTask.TaskListener() {
                @Override
                public void onFinished(String result) {
                    if (!result.equals("") && !result.equals("null")) {
                        HashMap<String, String> map = new Gson().fromJson(result, HashMap.class);
                        if (!map.get("id").equals("")) {
                            Toast.makeText(LoginActivity.this, "Welcome, " + username + "!", Toast.LENGTH_SHORT).show();
                            BaseApplication.setPref(getBaseContext(), "CurrentUserId", map.get("id"));
                            BaseApplication.setCurrentUserId(map.get("id"));

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, getString(R.string.error_invalid_credentials), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, getString(R.string.error_invalid_credentials), Toast.LENGTH_SHORT).show();
                    }

                }
            }).execute("http://mesi.leventealbert.com/api/login");
        }
    }
}
