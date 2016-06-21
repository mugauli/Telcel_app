package Utitilies;

import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Mugauli on 21/06/2016.
 */
public class DownloadTask extends AsyncTask<ArrayList<String>, Integer, String> {

    private Context context;
    private PowerManager.WakeLock mWakeLock;

    public DownloadTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(ArrayList<String>... params) {

        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            Log.e("URL",params[0].get(0));
            URL url = new URL(params[0].get(0));
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = connection.getContentLength();

            // download the file
            input = connection.getInputStream();
            Log.e("Nombre",params[0].get(1));
            output = new FileOutputStream("/sdcard/"+params[0].get(1));
            Log.e("Punto","0");
            byte data[] = new byte[4096];
            long total = 0;
            int count;
            Log.e("Punto","1");
            while ((count = input.read(data)) != -1) {
                Log.e("Punto","2");
                // allow canceling with back button
                if (isCancelled()) {
                    Log.e("Punto","2.1");
                    input.close();
                    return null;
                }
                Log.e("Punto","2.2");
                total += count;
                // publishing the progress....
                if (fileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));
                output.write(data, 0, count);
            }
            Log.e("Punto","3");
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
        return null;
    }

}