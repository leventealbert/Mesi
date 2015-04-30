package com.leventealbert.mesi;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class used to create a background task to download the json from the api.
 *
 * @author Levente Albert
 */
public class AsyncHttpTask extends AsyncTask<String, Void, String> {

    private String mJsonRequestBody;
    private String mMethod;

    private TaskListener mTaskListener;

    /**
     * constructor
     *
     * @param method String
     * @param listener TaskListener
     */
    public AsyncHttpTask(String method, TaskListener listener) {
        mMethod = method;
        mTaskListener = listener;
    }

    /**
     * constructor
     *
     * @param method String
     * @param json String
     * @param listener TaskListener
     */
    public AsyncHttpTask(String method, String json, TaskListener listener) {
        mJsonRequestBody = json;
        mMethod = method;
        mTaskListener = listener;
    }

    /**
     * Function to trigger the start of the background process
     *
     * @param params String
     * @return String
     */
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

    /**
     * function to be called when the result came back
     *
     * @param result String
     */
    @Override
    protected void onPostExecute(String result) {
        if (this.mTaskListener != null) {
            //calling the callback function
            mTaskListener.onFinished(result);
        }
    }

    /**
     * interfaceto be able to define the listerners for the callback
     */
    public interface TaskListener {
        void onFinished(String result);
    }
}
