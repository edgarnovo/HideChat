package com.hidechat.app;

import java.text.ParseException;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.hidechat.app.dal.DALMessage;
import com.hidechat.app.db.DBMain;
import com.hidechat.app.entities.EntityMessage;
import com.hidechat.app.entities.EntityUser;
import com.hidechat.app.interfaces.Interface;
import com.hidechat.app.util.Metodos;

public class ReadMessage extends Activity implements Interface{
	
	private Context mCtx;
	
	private String firstname;
	private String lastname;
	private String date_sender;
	private String message;
	
	private TextView nameTextView;
	private TextView date_senderTextView;
	private TextView body_textTextView;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_message);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);

		this.mCtx = this;
		
		int idMessage = getIntent().getIntExtra("idMessage", 0);
		
		EntityMessage message = DALMessage.getFirst(DBMain.getById(mCtx, DALMessage.TABLE_NAME, DALMessage.columns, idMessage));
		EntityUser sender = message.getEntityUserSender(mCtx);
		message.setRead(1);
		message.save(mCtx);
		
		date_senderTextView = (TextView) findViewById(R.id.ReadMessageTextData);
		nameTextView = (TextView) findViewById(R.id.ReadMessageTextNameSender);
		body_textTextView = (TextView) findViewById(R.id.ReadMessageTextMessage);
		
		date_senderTextView.setText(message.getDate_sender());
		nameTextView.setText(sender.getFirstname() + " " + sender.getLastname());
		body_textTextView.setText(message.getMessage());
	}
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_read_message, menu);
		return true;
	}*/
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		}
		return (super.onOptionsItemSelected(item));
	}

	@Override
	public void onAccelerationChanged(float x, float y, float z) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onShake(float force) {
		// TODO Auto-generated method stub
		finish();
	}
	
	/*private void getOverflowMenu() {

	    try {
	        ViewConfiguration config = ViewConfiguration.get(this);
	         Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
	         if (menuKeyField != null) {
	             menuKeyField.setAccessible(true);
	             menuKeyField.setBoolean(config, false);
	         }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}*/
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//Log.i("Sensor", "Service  distroy");
		
		//Check device supported Accelerometer senssor or not
		if (Metodos.isListening()) {
			
			//Start Accelerometer Listening
			Metodos.stopListening();
			
			//Toast.makeText(getBaseContext(), "onDestroy Accelerometer Stoped", 
			//	Toast.LENGTH_LONG).show();
        }
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		//Check device supported Accelerometer senssor or not
        if (Metodos.isListening()) {
        	
        	//Start Accelerometer Listening
        	Metodos.stopListening();
			
        	//Toast.makeText(getBaseContext(), "onStop Accelerometer Stoped", 
        	//	Toast.LENGTH_LONG).show();
        }
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//Toast.makeText(getBaseContext(), "onResume Accelerometer Started", 
        	//	Toast.LENGTH_LONG).show();
        
        //Check device supported Accelerometer senssor or not
        if (Metodos.isSupported(this)) {
        	
        	//Start Accelerometer Listening
        	Metodos.startListening(this);
        }
	}

}
