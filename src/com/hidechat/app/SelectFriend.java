package com.hidechat.app;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.ListView;

import com.hidechat.app.adapters.AdapterUser;
import com.hidechat.app.dal.DALUser;
import com.hidechat.app.db.DBMain;

public class SelectFriend extends Activity {

	private Context mCtx;
	
	private AdapterUser adapter;
	private Cursor c_adap;
	private ListView list;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_friends);
		
		getOverflowMenu();
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		this.mCtx = this;
		this.list = (ListView) findViewById(R.id.listfriends);
		list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		fillList();
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
		try {
			//c_adap = DBMain.getAll(mCtx, DALMessages.TABLE_NAME, DALMessages.columns, DALMessages.DELIVER_DATE);
			c_adap = DBMain.getAll(mCtx, DALUser.TABLE_NAME, DALUser.columns, null);
			adapter = new AdapterUser(mCtx, DALUser.converte(c_adap));
			list.setAdapter(adapter);
		} catch (Exception ex) {
			throw new Error(ex.getMessage());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send_message, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.actionSendMessage:
			//Intent intent = new Intent(SelectFriend.this, NewMessage.class);
			//startActivity(intent);
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

