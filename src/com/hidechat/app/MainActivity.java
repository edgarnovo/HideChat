package com.hidechat.app;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.hidechat.app.adapters.AdapterMessage;
import com.hidechat.app.dal.DALMessage;
import com.hidechat.app.db.DBMain;
import com.hidechat.app.entities.EntityMessage;
import com.hidechat.app.ws.GetMessage;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
	private Context mCtx;
	
	private AdapterMessage adapter;
	private Cursor c_adap;
	private ListView list;
	
	private static int REQUEST_CODE_LOGIN = 1;
	private static int REQUEST_CODE_FACIAL_RECOGNITION = 2;
	private static int REQUEST_CODE_GET_MESSAGE = 3;
	
	private static final String KEY_STATE_SAVED = null;
	private static final String KEY_STATE_ID_USER = null;
	
	
	private int idUser = 0;
	private int idMessage = 0;
	
	private ShareActionProvider mShareActionProvider;
	
	private SharedPreferences loginPreferences;
	private SharedPreferences.Editor loginPrefsEditor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.mCtx = this;
		this.list = (ListView) findViewById(R.id.listmessages);	
		
		if (savedInstanceState == null) {

			loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
			loginPrefsEditor = loginPreferences.edit();

			idUser = Integer.parseInt(loginPreferences.getString("idUser", "0"));
			Log.d ("USER>>>", "" +idUser);
			if (idUser == 0) {
				startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_CODE_LOGIN);
			}else{
				startActivity(new Intent(this, SplashActivity.class));
				new GetMessage(mCtx).execute(idUser+"");
			}
			
		}
		else{
			this.idUser = Integer.parseInt(savedInstanceState.getString(KEY_STATE_ID_USER));
			fillList();
		}
		
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				EntityMessage messageObj = (EntityMessage) list.getItemAtPosition(pos);
				openMessage(messageObj.get_id());
			}
		});
	}
	
	protected void openMessage(int id) {
		
		Intent face = new Intent(this, FacialRecognitionActivity.class);
		face.putExtra("idMessage", id);
		startActivityForResult(face, REQUEST_CODE_FACIAL_RECOGNITION);	
	}

	@Override
	protected void onSaveInstanceState (Bundle outState) {
	    super.onSaveInstanceState(outState);
	    outState.putString(KEY_STATE_SAVED, "saved");
	    outState.putString(KEY_STATE_ID_USER, String.valueOf(idUser));
	}
	
	
	
	public void fillList() {
		
		try {
			String where = DALMessage.WHERE_JOIN +  String.format(" and (%s.%s = %s or %s.%s = %s)", DALMessage.TABLE_NAME, DALMessage.UID_SENDER , idUser, DALMessage.TABLE_NAME, DALMessage.UID_RECEIVER , idUser);
			String order = String.format("%s.%s desc", DALMessage.TABLE_NAME, DALMessage.DATE_SENDER);
			

			c_adap = DBMain.getAllJoin(mCtx, DALMessage.TABLES_JOIN, where, DALMessage.columnsJoin, order);
			//
			
			//c_adap = DBMain.getAll(mCtx, DALMessage.TABLE_NAME, DALMessage.columns, null);
			adapter = new AdapterMessage(mCtx, DALMessage.converte(c_adap), idUser);
			list.setAdapter(adapter);
		} catch (Exception ex) {
			throw new Error(ex.getMessage());
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		mShareActionProvider = (ShareActionProvider) menu.findItem(
				R.id.menu_share).getActionProvider();

		// If you use more than one ShareActionProvider, each for a different
		// action,
		// use the following line to specify a unique history file for each one.
		// mShareActionProvider.setShareHistoryFileName("custom_share_history.xml");

		Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                R.drawable._splash);
		
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_STREAM, tmpFileToShare(icon));
		shareIntent.setType("image/jpeg");

		mShareActionProvider.setShareIntent(shareIntent);

		return true;
	}
	
	private Uri tmpFileToShare(Bitmap icon) {

		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		File f = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "splash.jpg");
		try {
			f.createNewFile();
			FileOutputStream fo = new FileOutputStream(f);
			fo.write(bytes.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Uri.parse("file:///sdcard/splash.jpg");
	}
	
	@Override
	protected void onResume() {
		fillList();
		super.onResume();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == REQUEST_CODE_LOGIN){
			if(resultCode == RESULT_OK){
				idUser = data.getIntExtra("idUser", 0);

				new GetMessage(mCtx).execute(idUser+"");
				
			}
			else{
				finish();
			}
		}
		
		if(requestCode == REQUEST_CODE_FACIAL_RECOGNITION){
			if(resultCode == RESULT_OK){
				idMessage = data.getIntExtra("idMessage", 0);
				
				Intent intentMessage = new Intent(this, ReadMessage.class);
				intentMessage.putExtra("idMessage", idMessage);
				startActivity(intentMessage);
			}
		
				
		}		

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.actionMainNewMessage:
			Intent intent = new Intent(MainActivity.this, NewMessage.class);
			intent.putExtra("userID", idUser);
			startActivity(intent);
			break;
		case R.id.actionMainNewFace:
			Intent intentNewFace = new Intent(this, NewFaceActivity.class);
			startActivity(intentNewFace);
			break;
		case R.id.actionMainLogout:
			SharedPreferences loginPreferences;
			SharedPreferences.Editor loginPrefsEditor;				
			loginPreferences = mCtx.getSharedPreferences("loginPrefs", 0);
			loginPrefsEditor = loginPreferences.edit();
			loginPrefsEditor.putString("idUser", "0");
			loginPrefsEditor.putString("username", "");
			loginPrefsEditor.putString("password", "");			
			loginPrefsEditor.commit();
			startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_CODE_LOGIN);
			break;
			
		}
		return (super.onOptionsItemSelected(item));
	}

	private void getOverflowMenu() {

		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
