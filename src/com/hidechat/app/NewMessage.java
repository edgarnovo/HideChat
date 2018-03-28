package com.hidechat.app;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hidechat.app.dal.DALUser;
import com.hidechat.app.db.DBMain;
import com.hidechat.app.entities.EntityMessage;
import com.hidechat.app.entities.EntityMessageCreateSOAP;
import com.hidechat.app.entities.EntityUser;
import com.hidechat.app.ws.GetNewMessage;

public class NewMessage extends Activity {

	private Context mCtx;

	private String Username, Password;
	private EditText editTextUsername;
	private EditText editTextPassword;
	private CheckBox CheckBoxSaveLogin;
	private Button btnLogin;
	
	private ArrayList<EntityUser> listUser = new ArrayList<EntityUser>();
	private ArrayAdapter<String> adapUser = null;
	
	private Spinner spFriends;
	
	private EditText editTextMessage;
	private Button btnWS;
	
	private int idUser;
	
	private int idReceiver;

	private SharedPreferences loginPreferences;
	private SharedPreferences.Editor loginPrefsEditor;
	private Boolean saveLogin;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_message);
		
		//getOverflowMenu();
		
		this.mCtx = this;
		
		Bundle bundle = getIntent().getExtras();
		idUser = bundle.getInt("userID");
		Log.i("userID", idUser+"");
		
		editTextMessage = (EditText) findViewById(R.id.newMessageTxtText);
		spFriends = (Spinner) findViewById(R.id.newMessageFriends);
		
		
		
		
		listUser = DALUser.converte(DBMain.getAll(mCtx, DALUser.TABLE_NAME, DALUser.columns, DALUser.FIRSTNAME));
		ArrayList<String> listUserFirstName = new ArrayList<String>(); 
		listUserFirstName.add("...");
		for(EntityUser u : listUser)
			listUserFirstName.add(u.getFirstname());
		adapUser = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listUserFirstName);
		adapUser.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spFriends.setAdapter(adapUser);
		spFriends.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long arg3) {
				position--;
				if(position >= 0)
					idReceiver = listUser.get(position).get_id();
				else
					idReceiver = 0;  
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}

		}); 
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_friend, menu);
		return true;
	}
	
	@SuppressLint("NewApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.actionSend:
			
			String Sender = String.valueOf(idUser);
			int Receiver = idReceiver;
			String texte = editTextMessage.getText().toString();
			
			EntityMessage meLocal = new EntityMessage();
			//meLocal.set_id(idUser);
			meLocal.setUid_sender(idUser);
			meLocal.setUid_receiver(Receiver);
			meLocal.setMessage(texte);
			meLocal.setRead(1);		
			meLocal.setDate_sender("2012-12-12");
			meLocal.setDate_receiver("2012-12-12");
			
			EntityMessageCreateSOAP me = new EntityMessageCreateSOAP();
			me.setIdUserSender(Sender);
			me.setIdUserReceiver(""+Receiver);
			me.setMessage(texte);

			GetNewMessage wsNewMessage = new GetNewMessage(mCtx, meLocal);
			wsNewMessage.execute(me);
				//Intent intent = new Intent(this, SelectFriend.class);
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




