package com.hidechat.app.ws;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.hidechat.app.entities.EntityUserSOAP;

public class GetLoginByUser extends AsyncTask<String, String, SoapObject> {
	
	private String NAMESPACE = "http://tempuri.org/";
	private String URL = "http://schoolprojects.biz/hidechat/modules/users/services/makelogin.php";
	private String METHOD_NAME_GET_LOGIN = "login";
	private ProgressDialog progressDialog;
	private Context mCtx;
	SoapObject soapResponse;
	
	public GetLoginByUser(Context mCtx){
        this.mCtx = mCtx;
    }
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = new ProgressDialog(this.mCtx);
        progressDialog.setMessage("A carregar informação, aguarde...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
	}
	
	@Override
	protected SoapObject doInBackground(String... strs) {
		// TODO Auto-generated method stub

		String username = strs[0];
		String password = strs[1];
		
		final SoapObject requestObject = new SoapObject(NAMESPACE,
				METHOD_NAME_GET_LOGIN); // Create the outgoing message

		requestObject.addProperty("username", username);
		requestObject.addProperty("password", password);
		
		
		
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11); // Create soap envelop .use version 1.1
										// of soap
		envelope.dotNet = true;
		envelope.setOutputSoapObject(requestObject); // add the outgoing
														// object as the
														// request
		envelope.addMapping(NAMESPACE, EntityUserSOAP.USER_CLASS.getSimpleName(), EntityUserSOAP.USER_CLASS);

		// call and Parse Result.
		final HttpTransportSE transportSE = new HttpTransportSE(URL);

		transportSE.debug = false;

		try {
			transportSE.call(NAMESPACE + METHOD_NAME_GET_LOGIN, envelope);
			soapResponse = (SoapObject) envelope.bodyIn;

		} catch (Exception ex) {
			ex.printStackTrace();
			Log.e("ERROR", ex.getMessage());
		}

		return soapResponse;
	}
	
	@Override
	protected void onPostExecute(SoapObject result) {
		EntityUserSOAP c;
		
		if (result != null) {
			c = new EntityUserSOAP((SoapObject) soapResponse.getProperty(0));
			
			/*Bundle bundle = new Bundle();
			bundle.putString("firstName", c.getFirstName());
			bundle.putString("lastName", c.getLastName());
			bundle.putString("username", c.getUsername());
			bundle.putString("password", c.getPassword());
			bundle.putString("lastLogin", c.getLastLogin());*/
			
			if(c.getAccept() == true){
				Intent intentData = new Intent();
				intentData.putExtra("idUser", Integer.parseInt(c.getId()));
				((Activity) mCtx).setResult(Activity.RESULT_OK, intentData);
				((Activity)mCtx).finish();

				SharedPreferences loginPreferences;
				SharedPreferences.Editor loginPrefsEditor;				
				loginPreferences = mCtx.getSharedPreferences("loginPrefs", 0);
				loginPrefsEditor = loginPreferences.edit();
				loginPrefsEditor.putString("idUser", c.getId());
				loginPrefsEditor.commit();				
				
				Toast.makeText(mCtx, "Olá " + c.getFirstName() + " " + c.getLastName() , Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(mCtx, "Login Errado", Toast.LENGTH_SHORT).show();
			}
			
			progressDialog.cancel();
		}

	}

}
