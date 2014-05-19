package com.example.android_json;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private EditText etMessage;
	private Button btnSend;
	private TextView tvRecvData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		etMessage = (EditText) findViewById(R.id.et_message);
		btnSend = (Button) findViewById(R.id.btn_sendData);
		tvRecvData = (TextView) findViewById(R.id.tv_recvData);
		
		/*	Send ��ư�� ������ �� ������ �����͸� �ְ� �޴´�. */
		btnSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String sMessage = etMessage.getText().toString();	//������ �޽����� �޾ƿ�.
				String result = SendByHttp(sMessage); //�޽����� ������ ����.
				String[][] parsedData = jsonParserList(result);	//���� �޽����� json �Ľ�. 
				
				tvRecvData.setText(result);	//���� �޽����� ȭ�鿡 �����ֱ�.
			}
		});
	}
	
	/*
	 * ������ �����͸� ������ �޼ҵ�.
	 */
	private String SendByHttp(String msg)
	{
		if(msg == null)
			msg = "";
		
		//String URL = "http://morecloudy.cafe24.com/sample/login.php";
		String URL = "http://localhost:8080/sample/login.php";
		
		DefaultHttpClient client = new DefaultHttpClient();
		try{
			/*	üũ�� id�� pwd�� ������ ����*/
			HttpPost post = new HttpPost(URL + "?msg=" + msg);
			
			/*�����ð� �ִ� 3��*/
			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 3000);
			HttpConnectionParams.setSoTimeout(params, 3000);
			
			/*������ ���� �� �������� �����͸� �޾ƿ��� ���� */
			HttpResponse response = client.execute(post);
			BufferedReader bufreader = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent(),"utf-8"));
			
			String line = null;
			String result = "";
			
			while((line = bufreader.readLine()) != null)
			{
				result += line;
			}
			
			return result;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			client.getConnectionManager().shutdown();
			return "";
		}
	}
	
	public String[][] jsonParserList(String pRecvServerPage)
	{
		Log.i("�������� ���� ��ü ���� : ", pRecvServerPage);
		try{
			JSONObject json = new JSONObject(pRecvServerPage);
			JSONArray jArr = json.getJSONArray("List");
			
			//�޾ƿ� pRecvServerPage�� �м��ϴ� �κ�.
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
				Log.i("JSON�� �м��� ������ : " + i + " : " , parseredData[i][0]);
				Log.i("JSON�� �м��� ������ : " + i + " : " , parseredData[i][1]);
				Log.i("JSON�� �м��� ������ : " + i + " : " , parseredData[i][2]);
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
