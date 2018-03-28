package com.hidechat.app;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.hidechat.app.adapters.AdapterMessage;
import com.hidechat.app.entities.EntityMessage;

public class ListMessages extends Activity {

	private Context mCtx;
	
	private AdapterMessage adapter;
	private Cursor c_adap;
	private ListView list;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_messages);
		
		getOverflowMenu();
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		this.mCtx = this;
		this.list = (ListView) findViewById(R.id.listmessages);
		
		fillList();
		clickList();
	}
	
	private void fillList() {
		/*
		try {
			c_adap = DBMain.getAll(mCtx, DALUser.TABLE_NAME, DALUser.columns, DALUser.NAME);
			adapter = new AdapterUser(mCtx, DALUser.converte(c_adap));
			list.setAdapter(adapter);
		} catch (Exception ex) {
			throw new Error(ex.getMessage());
		}
		*/	
		/*
		try {
			c_adap = DBMain.getAll(mCtx, DALContacts.TABLE_NAME, DALContacts.columns, DALContacts.UID);
			adapter = new AdapterContacts(mCtx, DALContacts.converte(c_adap));
			list.setAdapter(adapter);
		} catch (Exception ex) {
			throw new Error(ex.getMessage());
		}
		*/
		/*try {
			c_adap = DBMain.getAll(mCtx, DALMessage.TABLE_NAME, DALMessage.columns, null);
			//c_adap = DBMain.getAllJoin(mCtx, DALMessage.TABLES_JOIN, DALMessage.WHERE_JOIN, DALMessage.columnsJoin, null);
			adapter = new AdapterMessage(mCtx, DALMessage.converte(c_adap));
			list.setAdapter(adapter);
		} catch (Exception ex) {
			throw new Error(ex.getMessage());
		}*/
	}
	
	private void clickList() {
		// TODO Auto-generated method stub
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				EntityMessage messageObj = (EntityMessage) list.getItemAtPosition(arg2);
				Log.d("HELLO","HELLO");
				Intent intentShowMessage = new Intent(ListMessages.this, FacialRecognitionActivity.class);
				intentShowMessage.putExtra("firstname", messageObj.getEntityUserReceiver(mCtx).getFirstname());
				intentShowMessage.putExtra("lastname", messageObj.getEntityUserReceiver(mCtx).getLastname());
				intentShowMessage.putExtra("date_sender", messageObj.getDate_sender());
				intentShowMessage.putExtra("message", messageObj.getMessage());
				startActivity(intentShowMessage);
				
				/*Intent intentShowMessage = new Intent(ListMessages.this, ReadMessage.class);
				intentShowMessage.putExtra("firstname", messageObj.getEntityUserReceiver(mCtx).getFirstname());
				intentShowMessage.putExtra("lastname", messageObj.getEntityUserReceiver(mCtx).getLastname());
				intentShowMessage.putExtra("date_sender", messageObj.getDate_sender());
				intentShowMessage.putExtra("message", messageObj.getMessage());
				startActivity(intentShowMessage);*/
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.messages, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.actionNewMessage:
			Intent intent = new Intent(ListMessages.this, NewMessage.class);
			startActivity(intent);
			break;
		case R.id.actionNewMessageLogout:
			finish();
			break;
		case android.R.id.home:
			finish();
			break;
		}
		return (super.onOptionsItemSelected(item));
	}
	
	private void getOverflowMenu() {

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
	}
}

