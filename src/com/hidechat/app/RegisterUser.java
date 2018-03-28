package com.hidechat.app;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hidechat.app.entities.EntityUser;
import com.hidechat.app.entities.EntityUserRegisterSOAP;
import com.hidechat.app.ws.GetRegisterUser;

@SuppressLint("NewApi")
public class RegisterUser extends Activity {
	
private Context mCtx;
	
	private EntityUser user;
	private String FirstName, LastName, Login, Password;
	private EditText editTextFirstName, editTextLastName, editTextLogin, editTextPassword;
	private ImageView ivPhotoUser;
	private Button btnRegister, btnBack, btnPhoto;
	
	private final static int cameraData = 0;
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_user);
				
		this.mCtx = this;

		editTextFirstName = (EditText) findViewById(R.id.registerUserTxtFirstName);
		editTextLastName = (EditText) findViewById(R.id.registerUserTxtLastName);
		editTextLogin = (EditText) findViewById(R.id.registerUserTxtUsername);
		editTextPassword = (EditText) findViewById(R.id.registerUserTxtPassword);
		
		btnRegister = (Button) findViewById(R.id.registerUserBtnRegister);

		btnRegister.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			public void onClick(View view) {
				
				FirstName = editTextFirstName.getText().toString();
				LastName = editTextLastName.getText().toString();
				Login = editTextLogin.getText().toString();
				Password = editTextPassword.getText().toString();

				if(!FirstName.isEmpty() && !LastName.isEmpty() && !Login.isEmpty() && !Password.isEmpty())
		        {
					EntityUser usLocal = new EntityUser();
					usLocal.set_id(0);
					usLocal.setFirstname(FirstName);
					usLocal.setLastname(LastName);
					usLocal.setUsername(Login);
					usLocal.setPassword(Password);
					
					EntityUserRegisterSOAP us = new EntityUserRegisterSOAP();
					us.setFirstName(FirstName);
					us.setLastName(LastName);
					us.setUsername(Login);
					us.setPassword(Password);
					GetRegisterUser ws = new GetRegisterUser(mCtx, usLocal);
					ws.execute(us);
		        }
		        else
		        {
		            Toast.makeText(RegisterUser.this, "Names Request", Toast.LENGTH_SHORT).show();
		        }
			}
		});

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_user, menu);
		return true;
	}
	


	

}
