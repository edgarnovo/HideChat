package com.hidechat.app.ws;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.hidechat.app.dal.DALUser;
import com.hidechat.app.entities.EntityUser;
import com.hidechat.app.entities.EntityUserRegisterSOAP;

public class GetRegisterUser extends
		AsyncTask<EntityUserRegisterSOAP, String, SoapObject> {

	private String NAMESPACE = "http://tempuri.org/";
	private String URL = "http://schoolprojects.biz/hidechat/modules/users/services/adduser.php";
	private String METHOD_NAME_ADD_USER = "addUser";
	private ProgressDialog progressDialog;
	private Context mCtx;
	EntityUser userLocal;
	SoapObject soapResponse;
	


	public GetRegisterUser(Context mCtx, EntityUser userLocal) {
		this.mCtx = mCtx;
		this.userLocal = userLocal;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = new ProgressDialog(this.mCtx);
		progressDialog.setMessage("A guardar informação, aguarde...");
		progressDialog.setIndeterminate(true);
		progressDialog.show();
	}

	@Override
	protected SoapObject doInBackground(EntityUserRegisterSOAP... strs) {
		// TODO Auto-generated method stub

		EntityUserRegisterSOAP user = strs[0];

		final SoapObject requestObject = new SoapObject(NAMESPACE,
				METHOD_NAME_ADD_USER); // Create the outgoing message

		requestObject.addProperty("user", user);

		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11); // Create soap envelop .use version 1.1
										// of soap
		envelope.dotNet = true;
		envelope.setOutputSoapObject(requestObject); // add the outgoing
														// object as the
														// request

		envelope.addMapping(NAMESPACE,
				EntityUserRegisterSOAP.USER_REGISTER_CLASS.getSimpleName(),
				EntityUserRegisterSOAP.USER_REGISTER_CLASS);

		// call and Parse Result.
		final HttpTransportSE transportSE = new HttpTransportSE(URL);

		transportSE.debug = false;

		try {
			transportSE.call(NAMESPACE + METHOD_NAME_ADD_USER, envelope);
			soapResponse = (SoapObject) envelope.bodyIn;

		} catch (Exception ex) {
			ex.printStackTrace();
			Log.e("ERROR", ex.getMessage());
		}

		return soapResponse;
	}

	@Override
	protected void onPostExecute(SoapObject result) {
		String idRemote;
		if (result != null) {
			idRemote = result.getProperty(0).toString();
			if (idRemote != "0") {
				userLocal.set_id(Integer.parseInt(idRemote));
				DALUser.save(mCtx, userLocal, 0);
				Toast.makeText(mCtx, "Registed Successfull", Toast.LENGTH_LONG).show();
				((Activity)mCtx).finish();
			} else {
				Toast.makeText(mCtx, "Registed Failed", Toast.LENGTH_LONG).show();
			}
			progressDialog.cancel();
		}
	}
}
