package com.hidechat.app.entities;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import android.R.bool;
import android.graphics.Bitmap;
import android.util.Log;

public class EntityUserRegisterSOAP  implements KvmSerializable{
	
	public static Class USER_REGISTER_CLASS = EntityUserSOAP.class;
	private String _id;
	private String firstName;
	private String lastName;
	private String username;
	private String password;	
	
	public EntityUserRegisterSOAP()
    {
    }
 
    public EntityUserRegisterSOAP(SoapObject obj)
    {
    	this._id = obj.getProperty(0).toString();
        this.firstName = obj.getProperty(1).toString();
        this.lastName = obj.getProperty(2).toString();
        this.username = obj.getProperty(3).toString();
        this.password = obj.getProperty(4).toString();
    }
    
    public String getId() {
		return _id;
    }
    
    public void setId(String _id) {
    	this._id = _id;
    }
    
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Object getProperty(int index) {
		// TODO Auto-generated method stub
		Object object = null;
        switch (index)
        {
	        case 0:
	        {
	            object = this._id;
	            break;
	        }
	        case 1:
	        {
	            object = this.firstName;
	            break;
	        }
	        case 2:
	        {
	            object = this.lastName;
	            break;
	        }
	        case 3:
	        {
	            object = this.username;
	            break;
	        }
	        case 4:
	        {
	            object = this.password;
	            break;
	        }
        }
        return object;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo propertyInfo) {
		// TODO Auto-generated method stub
		switch (index)
        {
	        case 0:
	        {
	        	propertyInfo.name = "id";
	        	propertyInfo.type = PropertyInfo.STRING_CLASS;
	            break;
	        }
	        case 1:
	        {
	        	propertyInfo.name = "firstName";
	        	propertyInfo.type = PropertyInfo.STRING_CLASS;
	            break;
	        }
	        case 2:
	        {
	        	propertyInfo.name = "lastName";
	        	propertyInfo.type = PropertyInfo.STRING_CLASS;
	            break;
	        }
	        case 3:
	        {
	        	propertyInfo.name = "username";
	        	propertyInfo.type = PropertyInfo.STRING_CLASS;
	            break;
	        }
	        case 4:
	        {
	        	propertyInfo.name = "password";
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
	        case 0:
	        {
	            this._id = obj.toString();
	            break;
	        }
	        case 1:
	        {
	            this.firstName = obj.toString();
	            break;
	        }
	        case 2:
	        {
	            this.lastName = obj.toString();
	            break;
	        }
	        case 3:
	        {
	            this.username = obj.toString();
	            break;
	        }
	        case 4:
	        {
	            this.password = obj.toString();
	            break;
	        }
        }
	}
}

