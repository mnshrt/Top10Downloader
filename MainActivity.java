package com.example.android.top10downloader;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  private Button btnParse = null;
    private ListView listApps = null;
    private  String mFileContents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       btnParse = (Button) findViewById(R.id.btnParse);
        listApps = (ListView) findViewById(R.id.xmlistView);
        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ParseApplications parseApplications = new ParseApplications(mFileContents);
            parseApplications.process();
                ArrayAdapter<Applications> arrayAdapter = new ArrayAdapter<Applications>(MainActivity.this,R.layout.list_item,parseApplications.getApplications());
                listApps.setAdapter(arrayAdapter);
            }
        });
        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private class DownloadData extends AsyncTask<String,Void,String>{



        @Override
        protected String doInBackground(String... params) {
            mFileContents = downloadXMLFile(params[0]);
            if(mFileContents==null){
                Log.d("Download data","Error downloading");
            }
            return mFileContents;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("DownloadData", "the result was " + result);

        }

        private String downloadXMLFile(String urlPath){
            StringBuilder tempBuffer = new StringBuilder();
            try{
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response= connection.getResponseCode();
                Log.d("DownloadData","The response was:"+ response);
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                 int charRead;
                char[] inputBuffer = new char[500];
                while (true){
                    charRead=isr.read(inputBuffer);
                    if(charRead<=0){
                        break;
                    }
                    tempBuffer.append(String.copyValueOf(inputBuffer,0,charRead));

                }
                return tempBuffer.toString();
            }catch (IOException e){
                Log.d("DownloadData","Exception reading data" +e.getMessage());
            }catch(SecurityException e){
                Log.e("DownloadData","Security exceptions needs permissions?"+e.getMessage());
            }
            return null;
        }
    }
}
