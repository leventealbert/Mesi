package com.leventealbert.mesi;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncHttpTask extends AsyncTask<String, Void, String> {

    private String mJsonRequestBody;
    private String mMethod;

    private TaskListener mTaskListener;

    public AsyncHttpTask(String method, TaskListener listener) {
        mMethod = method;
        mTaskListener = listener;
    }

    public AsyncHttpTask(String method, String json, TaskListener listener) {
        mJsonRequestBody = json;
        mMethod = method;
        mTaskListener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        InputStream inputStream = null;
        String result = "";
        HttpURLConnection conn;

        try {
                /* forming th java.net.URL object */
            URL url = new URL(params[0]);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("id", BaseApplication.getCurrentUserId());

            switch (mMethod){
                case "GET":
                    /* for Get request */
                    conn.setRequestMethod("GET");
                    break;
                case "PUT":
                    /* for PUT request */
                    conn.setRequestMethod("PUT");

                    if (!mJsonRequestBody.equals("")) {
                        conn.setDoOutput(true);

                        OutputStream os = conn.getOutputStream();
                        os.write(mJsonRequestBody.getBytes());
                        os.flush();
                    }
                    break;
                default :
                    return "";
            }

            int statusCode = conn.getResponseCode();

                /* 200 represents HTTP OK */
            if (statusCode == HttpURLConnection.HTTP_OK || statusCode == HttpURLConnection.HTTP_CREATED) {
                BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    response.append(line);
                }

                result = response.toString();
            }

        } catch (Exception e) {
            Log.d("ASYNC", e.getLocalizedMessage());
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if (this.mTaskListener != null) {
            mTaskListener.onFinished(result);
        }
    }

    public interface TaskListener {
        public void onFinished(String result);
    }
}
