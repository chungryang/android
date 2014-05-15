package com.example.httptest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.net.ParseException;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	static TestHttpGet http = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		TextView tv = (TextView)findViewById(R.id.TextView);
		http = new TestHttpGet();
		String tmp = http.executeHttpGet();
		tv.setText(tmp);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//Get¹æ½Ä 
	public class TestHttpGet{
		public String executeHttpGet()
		{
			String sRes = "";
			try{
				HttpClient client = new DefaultHttpClient();
				
				HttpGet get = new HttpGet();
				get.setURI(new URI("http://naver.com"));
				HttpResponse response = null;
				  try {
				      response = client.execute(get);
				  } catch (ClientProtocolException e) {
				      // TODO Auto-generated catch block
				      e.printStackTrace();
				  } catch (IOException e) {
				      // TODO Auto-generated catch block
				      e.printStackTrace();
				  }
				HttpEntity resEntity = response.getEntity();
				
				 Log.i("testHttp", "sRes ===========>" + sRes);
				if(resEntity != null){
				     try {
				     sRes = EntityUtils.toString(resEntity);  
				     //sRes = URLDecoder.decode(sRes);
				      Log.i("testHttp", "sRes ===========>" + sRes);
				     
				      } catch (ParseException e) {
				          // TODO Auto-generated catch block
				          e.printStackTrace();
				      } catch (IOException e) {
				          // TODO Auto-generated catch block
				          e.printStackTrace();
				      }
				 
				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return sRes;
		}
	}

}
