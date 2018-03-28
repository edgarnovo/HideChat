package com.hidechat.app.entities;

	import java.util.Hashtable;

	import org.ksoap2.serialization.KvmSerializable;
	import org.ksoap2.serialization.PropertyInfo;
	import org.ksoap2.serialization.SoapObject;

	import android.R.bool;
	import android.graphics.Bitmap;
	import android.util.Log;

	public class EntityMessageCreateSOAP  implements KvmSerializable{
		
		public static Class NEW_MESSAGE_CLASS = EntityMessageCreateSOAP.class;
		//private String _id;
		private String idUserSender;
		private String idUserReceiver;
		private String message;
		
		public EntityMessageCreateSOAP()
	    {
	    }
	 
	    public EntityMessageCreateSOAP(SoapObject obj)
	    {
	    	//this._id = obj.getProperty(0).toString();
	        this.idUserSender = obj.getProperty(0).toString();
	        this.idUserReceiver = obj.getProperty(1).toString();
	        this.message = obj.getProperty(2).toString();
	    }
	    
	    /*public String getId() {
			return _id;
	    }
	    
	    public void setId(String _id) {
	    	this._id = _id;
	    }*/
	    
		public String getIdUserSender() {
			return idUserSender;
		}

		public void setIdUserSender(String idUserSender) {
			this.idUserSender = idUserSender;
		}

		public String getIdUserReceiver() {
			return idUserReceiver;
		}

		public void setIdUserReceiver(String idUserReceiver) {
			this.idUserReceiver = idUserReceiver;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		@Override
		public Object getProperty(int index) {
			// TODO Auto-generated method stub
			Object object = null;
	        switch (index)
	        {
		        /*case 0:
		        {
		            object = this._id;
		            break;
		        }*/
		        case 0:
		        {
		            object = this.idUserSender;
		            break;
		        }
		        case 1:
		        {
		            object = this.idUserReceiver;
		            break;
		        }
		        case 2:
		        {
		            object = this.message;
		            break;
		        }
	        }
	        return object;
		}

		@Override
		public int getPropertyCount() {
			// TODO Auto-generated method stub
			return 3;
		}

		@Override
		public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo propertyInfo) {
			// TODO Auto-generated method stub
			switch (index)
	        {
		        /*case 0:
		        {
		        	propertyInfo.name = "id";
		        	propertyInfo.type = PropertyInfo.STRING_CLASS;
		            break;
		        }*/
		        case 0:
		        {
		        	propertyInfo.name = "idUserSender";
		        	propertyInfo.type = PropertyInfo.STRING_CLASS;
		            break;
		        }
		        case 1:
		        {
		        	propertyInfo.name = "idUserReceiver";
		        	propertyInfo.type = PropertyInfo.STRING_CLASS;
		            break;
		        }
		        case 2:
		        {
		        	propertyInfo.name = "message";
		        	propertyInfo.type = PropertyInfo.STRING_CLASS;
		            break;
		        }
	        }
		}

		@Override
		public void setProperty(int index, Object obj) {
			// TODO Auto-generated method stub
			switch (index)
	        {
		        /*case 0:
		        {
		            this._id = obj.toString();
		            break;
		        }*/
		        case 0:
		        {
		            this.idUserSender = obj.toString();
		            break;
		        }
		        case 1:
		        {
		            this.idUserReceiver = obj.toString();
		            break;
		        }
		        case 2:
		        {
		            this.message = obj.toString();
		            break;
		        }
	        }
		}
	}
