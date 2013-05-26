package com.mad;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.util.Log;
import android.net.wifi.*;

public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        System.out.println("hey");
        
        
        WifiManager wifiman = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiman.startScan();
        List<ScanResult> argh = wifiman.getScanResults();
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("{ \"locations\" : [");
        int arghSize = argh.size();
        for (int i = 0; i < arghSize; i++)
        {
        	sb.append(" {\"" + argh.get(i).BSSID + "\", \"" + argh.get(i).level + "\"}");
        	if (i < arghSize - 1)
        	{
        		sb.append(", ");
        	}
        }
        
        sb.append(" ]}");
        
        DefaultHttpClient httpclient = new DefaultHttpClient();

        //url with the post data
        HttpPost httpost = new HttpPost("http://wherethedocareyou.herokuapp.com/location/me/update");

        //passes the results to a string builder/entity
        StringEntity se;
		try {
		//	se = new StringEntity(sb.toString());
		    se = new StringEntity("test");
	        //sets the post request as the resulting string
	        httpost.setEntity(se);
	        //sets a request header so the page receving the request
	        //will know what to do with it
	        httpost.setHeader("Accept", "application/json");
	        httpost.setHeader("Content-type", "application/json");
        
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
       
}
