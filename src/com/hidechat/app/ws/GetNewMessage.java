package com.hidechat.app.ws;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.hidechat.app.ListMessages;
import com.hidechat.app.MainActivity;
import com.hidechat.app.dal.DALMessage;
import com.hidechat.app.entities.EntityMessage;
import com.hidechat.app.entities.EntityMessageCreateSOAP;
	
	public class GetNewMessage extends AsyncTask<EntityMessageCreateSOAP, String, SoapObject> {
		
		private String NAMESPACE = "http://tempuri.org/";
		private String URL = "http://schoolprojects.biz/hidechat/modules/messages/services/newmessage.php";
		private String METHOD_NAME_NEW_MESSAGE = "newMessage";
		private ProgressDialog progressDialog;
		private Context mCtx;
		EntityMessage messageLocal;
		SoapObject soapResponse;
		
		public GetNewMessage(Context mCtx, EntityMessage messageLocal){
	        this.mCtx = mCtx;
	        this.messageLocal = messageLocal;
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
		protected SoapObject doInBackground(EntityMessageCreateSOAP... strs) {
			// TODO Auto-generated method stub		
			
			EntityMessageCreateSOAP message = strs[0];
			
			final SoapObject requestObject = new SoapObject(NAMESPACE,
					METHOD_NAME_NEW_MESSAGE); // Create the outgoing message
			
			requestObject.addProperty("message", message);

			final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11); // Create soap envelop .use version 1.1
											// of soap
			envelope.dotNet = true;
			envelope.setOutputSoapObject(requestObject); // add the outgoing
															// object as the
															// request

			envelope.addMapping(NAMESPACE, EntityMessageCreateSOAP.NEW_MESSAGE_CLASS.getSimpleName(), EntityMessageCreateSOAP.NEW_MESSAGE_CLASS);

			// call and Parse Result.
			final HttpTransportSE transportSE = new HttpTransportSE(URL);

			transportSE.debug = false;

			try {
				transportSE.call(NAMESPACE + METHOD_NAME_NEW_MESSAGE, envelope);
				soapResponse = (SoapObject) envelope.bodyIn;

			} catch (Exception ex) {
				ex.printStackTrace();
				Log.e("ERROR", ex.getMessage());
			}

			return soapResponse;
		}
		
		@Override
		protected void onPostExecute(SoapObject result) {
			String c;
			if (result != null) {
				c = result.getProperty(0).toString();

				if (c != "0") {
					messageLocal.set_id(Integer.parseInt(c));
					DALMessage.save(mCtx, messageLocal, 0);
					Toast.makeText(mCtx, "Registed Message" ,Toast.LENGTH_LONG).show();
					((Activity)mCtx).finish();
				}else{
					Intent intent = new Intent(mCtx, ListMessages.class);
					mCtx.startActivity(intent);
					Toast.makeText(mCtx, "Message Failed" ,Toast.LENGTH_LONG).show();
				}
				progressDialog.cancel();
			}
		}
	}

