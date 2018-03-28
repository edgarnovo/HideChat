package com.hidechat.app.ws;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.hidechat.app.MainActivity;
import com.hidechat.app.R;
import com.hidechat.app.dal.DALMessage;
import com.hidechat.app.entities.EntityMessageSoap;

public class GetMessage extends AsyncTask<String, String, SoapObject> {
	private static String NAMESPACE = "http://tempuri.org/";
	private static String METHOD_NAME = "getMessage";
	private static String SOAP_ACTION = "http://schoolprojects.biz/hidechat/modules/messages/services/getmessages.php?wsdl.getMessage#getMessage";
	private static String URL = "http://schoolprojects.biz/hidechat/modules/messages/services/getmessages.php";
	
	private SoapObject soapResponse;
	private Context mCtx;
	
	public GetMessage(Context mCtx)
	{
		this.mCtx = mCtx;
	}
	
	
	protected SoapObject doInBackground(String... strs) {
		String id = strs[0];
		try {
		final SoapObject requestObject = new SoapObject(NAMESPACE, METHOD_NAME); // Create the outgoing message
		
		requestObject.addProperty("idUserReceiver", id);
		
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 
		
		envelope.dotNet = true;
		envelope.setOutputSoapObject(requestObject); 
		
		envelope.addMapping(NAMESPACE, EntityMessageSoap.CLASS_ENTITY.getSimpleName(), EntityMessageSoap.CLASS_ENTITY);

		// call and Parse Result.
		final HttpTransportSE transportSE = new HttpTransportSE(URL, 10000);
		
		transportSE.debug = false;

		
		System.setProperty("http.keepAlive", "false");
		
		transportSE.call(NAMESPACE + METHOD_NAME, envelope);
		soapResponse = (SoapObject) envelope.bodyIn;

		} catch (Exception ex) {
			ex.printStackTrace();
			Log.e("ERROR", ex.getMessage());

		}

		return soapResponse;

	}

	@Override
	protected void onPostExecute(SoapObject result) {
		
		EntityMessageSoap ex = new EntityMessageSoap(result);
		
		if(ex.getMessage()!=null)
		{
			DALMessage.save(mCtx, ex.getMessage(), 0);
		}
		else
		{
			Toast.makeText(this.mCtx, this.mCtx.getResources().getString(R.string.no_messages), Toast.LENGTH_LONG).show();
		}
		
		((MainActivity) mCtx).fillList();
	}
}
