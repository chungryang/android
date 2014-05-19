package com.example.android_json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private EditText id;
	private EditText password;
	private Button btnSend;
	private TextView tvRecvData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		id = (EditText) findViewById(R.id.id);
		password = (EditText) findViewById(R.id.password);
		btnSend = (Button) findViewById(R.id.btn_sendData);
		tvRecvData = (TextView) findViewById(R.id.tv_recvData);
		
		/*	Send */
		btnSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
				String result = SendByHttp(); //
				//String[][] parsedData = jsonParserList(result);	// 
				
				tvRecvData.setText(result);	//
			}
		});
	}
	
	/*
	 *
	 */
	private String SendByHttp()
	{
		String result = "";
		
		try {
	        JSONObject json = new JSONObject();
	        json.put("id", id.getText());
	        json.put("pw", password.getText());
	        HttpParams httpParams = new BasicHttpParams();
	        HttpConnectionParams.setConnectionTimeout(httpParams,
	                3000);
	        HttpConnectionParams.setSoTimeout(httpParams, 3000);
	        HttpClient client = new DefaultHttpClient(httpParams);
	        
	        String url = "http://morecloudy.cafe24.com/sample/login.php";

	        HttpPost request = new HttpPost(url);
	        request.setEntity(new ByteArrayEntity(json.toString().getBytes(
	                "UTF8")));
	        request.setHeader("json", json.toString());
	        HttpResponse response = client.execute(request);
	        HttpEntity entity = response.getEntity();
	        // If the response does not enclose an entity, there is no need
	        if (entity != null) {
	            InputStream instream = entity.getContent();

	            result = convertStreamToString(instream);
	            Log.i("Read from server", result);
	            Log.i("minna!!!seks", "minna!!!seks");
		        
		        JSONObject getjson = new JSONObject(result);
		        
	            //JSONArray jArray = new JSONArray(result);
	            
	            //JSONObject userDetails = getjson.getJSONObject("List"); 
	            JSONArray jArray = getjson.getJSONArray("List");
	
	            //And then read attributes like             
//	            String msg1 = userDetails.getString("msg1"); 
//	            String msg2 = userDetails.getString("msg2");
//	            String msg3 = userDetails.getString("msg3");

//	            Log.i("json log ==> ", msg1);
//	            Log.i("json log ==> ", msg2);
//	            Log.i("json log ==> ", msg3);
	            
	            Log.i("json_length ==> ", Integer.toString(jArray.length()));
	            
	            
//				String[] jsonName = {"msg1", "msg2", "msg3"};
//				Log.i("jArraylength", Integer.toString(jArray.length()));
//	            for(int i = 0; i < jArray.length(); i++)
//				{
//	            	Log.i("jArraylength", Integer.toString(jArray.length()));
//					//json = jArray.getJSONObject(i);
//					if(json != null)
//					{
//						for(int j = 0; j < jsonName.length; j++)
//						{
//							Log.i("json log ==> ", json.getString(jsonName[j]));
//						}
//						
//					}
//				} 
	            Toast.makeText(this,  result,
	                    Toast.LENGTH_LONG).show();
	        }
	        
	          
	        
	    } catch (Throwable t) {
	        Toast.makeText(this, "Request failed: " + t.toString(),
	                Toast.LENGTH_LONG).show();
	    }
		
		return result;

	}
	
	private static String convertStreamToString(InputStream is)  {

	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}
	
	public String[][] jsonParserList(String pRecvServerPage)
	{
		Log.i(": ", pRecvServerPage);
		try{
			JSONObject json = new JSONObject(pRecvServerPage);
			JSONArray jArr = json.getJSONArray("List");
			
			String[] jsonName = {"msg1", "msg2", "msg3"};
			String[][] parseredData = new String[jArr.length()][jsonName.length];
			
			for(int i = 0; i < jArr.length(); i++)
			{
				json = jArr.getJSONObject(i);
				if(json != null)
				{
					for(int j = 0; j < jsonName.length; j++)
					{
						parseredData[i][j] = json.getString(jsonName[i]);
					}
				}
			}
			
			for(int i = 0; i < parseredData.length; i++)
			{
				Log.i("JSON Data : " + i + " : " , parseredData[i][0]);
				Log.i("JSON Data: " + i + " : " , parseredData[i][1]);
				Log.i("JSON Data: " + i + " : " , parseredData[i][2]);
			}
			return parseredData;
		}
		catch(JSONException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
