package com.hidechat.app;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hidechat.app.db.DBMain;
import com.hidechat.app.ws.GetLoginByUser;


public class LoginActivity extends Activity {

	private Context mCtx;

	private String Username, Password;
	private EditText editTextUsername;
	private EditText editTextPassword;
	private Button btnLogin;
	private TextView lnkCreate;

	private EditText editTextWS;
	private Button btnWS;

	private SharedPreferences loginPreferences;
	private SharedPreferences.Editor loginPrefsEditor;
	private Boolean saveLogin;


	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		
		
		startActivity(new Intent(this, SplashActivity.class));
		
		getActionBar().hide();
		getOverflowMenu();
		
		

		
		this.mCtx = this;
/*
		double cursor = DBMain.getCount(mCtx, "user", null);
		double cursor2 = DBMain.getCount(mCtx, "messages", null);

		if (cursor == 0) {
			Toast.makeText(mCtx, " 0 users", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(mCtx, cursor + " users", Toast.LENGTH_SHORT).show();
		}
		
		if (cursor2 == 0) {
			Toast.makeText(mCtx, " 0 messages", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(mCtx, cursor2 + " messages", Toast.LENGTH_SHORT).show();
		}
*/
		editTextUsername = (EditText) findViewById(R.id.loginActivityTxtUsername);
		editTextPassword = (EditText) findViewById(R.id.loginActivityTxtPassword);
		btnLogin = (Button) findViewById(R.id.loginActivityBtnLogin);
		lnkCreate = (TextView) findViewById(R.id.loginActivityLblCreate);

		loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
		loginPrefsEditor = loginPreferences.edit();

		saveLogin = loginPreferences.getBoolean("saveLogin", false);

		if (saveLogin == true) {
			editTextUsername
					.setText(loginPreferences.getString("username", ""));
			editTextPassword
					.setText(loginPreferences.getString("password", ""));
		}

		btnLogin.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			public void onClick(View view) {

				Username = editTextUsername.getText().toString();
				Password = editTextPassword.getText().toString();

				if (!Username.isEmpty() && !Password.isEmpty()) {

					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(editTextUsername.getWindowToken(), 0);

					loginPrefsEditor.putBoolean("saveLogin", true);
					loginPrefsEditor.putString("username", Username);
					loginPrefsEditor.putString("password", Password);
					loginPrefsEditor.commit();

					GetLoginByUser ws = new GetLoginByUser(mCtx);
					ws.execute(Username, Password);
				} else {
					Toast.makeText(LoginActivity.this, "Login Request",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		lnkCreate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, RegisterUser.class);
				startActivity(intent);			
			}
		});
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.actionLoginRegister:
			Intent intent = new Intent(this, RegisterUser.class);
			startActivity(intent);
			break;
		}
		return (super.onOptionsItemSelected(item));
	}
}
