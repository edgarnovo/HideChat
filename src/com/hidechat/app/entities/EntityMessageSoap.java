package com.hidechat.app.entities;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import android.util.Log;

public class EntityMessageSoap implements KvmSerializable {

	public static Class<EntityMessageSoap> CLASS_ENTITY = EntityMessageSoap.class;

	private String success;
	public EntityMessage message;

	public EntityMessageSoap() {
		super();
	}

	public EntityMessageSoap(SoapObject soapObjectParent) {
		super();
		SoapObject soapObject = (SoapObject)soapObjectParent.getProperty(0);
        
		this.success = soapObject.getProperty(0).toString();
		
		if(this.success == "true")
		{
			SoapObject messageObject = (SoapObject)soapObject.getProperty(1);
			
			EntityMessage em = new EntityMessage();
			em.set_id(Integer.parseInt((String) messageObject.getProperty(0)));
			em.setUid_sender(Integer.parseInt((String) messageObject.getProperty(1)));
			em.setUid_receiver(Integer.parseInt((String) messageObject.getProperty(2)));
			em.setDate_sender(messageObject.getProperty(3).toString());
			em.setRead(0);
	    	em.setMessage(messageObject.getProperty(4).toString());
	    	
	        this.message = em;
		}
		
	}
	
	public EntityMessage getMessage()
	{
		return this.message;
	}
	
	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		Object object = null;
		switch (arg0) {
			case 0: 
				object = this.success;
				break;
			
			case 1: 
				object = this.message;
				break;

		}
		return object;
	}

	@Override
	public int getPropertyCount() {
		return 2;
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		switch (arg0) {
			case 0: 
				arg2.name = "success";
				arg2.type = PropertyInfo.STRING_CLASS;
				break;
			
			case 1: 
				arg2.name = "message";
				arg2.type = PropertyInfo.OBJECT_CLASS;
				break;
		}

	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		switch (arg0) {
			case 0: 
				this.success = arg1.toString();
				break;
			
			case 1: 
				this.message = (EntityMessage)arg1;
				break;
			
		}
	}
	

}
