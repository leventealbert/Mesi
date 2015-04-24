package com.leventealbert.mesi;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncHttpTask extends AsyncTask<String, Void, String> {

    private BaseFragment mFragment;

    public AsyncHttpTask(BaseFragment fragment) {
        mFragment = fragment;
    }

    @Override
    protected void onPreExecute() {
        //mFragment.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    protected String doInBackground(String... params) {
        InputStream inputStream = null;
        String result = "";
        HttpURLConnection urlConnection;

        try {
                /* forming th java.net.URL object */
            URL url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();

                /* for Get request */
            urlConnection.setRequestMethod("GET");
            int statusCode = urlConnection.getResponseCode();

                /* 200 represents HTTP OK */
            if (statusCode == 200) {
                BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
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
        //mFragment.getActivity().setProgressBarIndeterminateVisibility(false);

        if (!result.equals("")) {
            mFragment.setAdapter(result);
        } else {
            Log.e("ASYNC", "Failed to fetch data!");
        }
    }
}
