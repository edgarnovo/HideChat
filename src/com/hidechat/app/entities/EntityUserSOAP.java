package com.hidechat.app.entities;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import android.R.bool;
import android.graphics.Bitmap;
import android.util.Log;

public class EntityUserSOAP  implements KvmSerializable{
	
	public static Class USER_CLASS = EntityUserSOAP.class;
	private Boolean userAccept;
	private String _id;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	
	public EntityUserSOAP()
    {
    }
 
    public EntityUserSOAP(SoapObject obj)
    {
    	if((Boolean) obj.getProperty(0) == false){
    		this.userAccept = (Boolean) obj.getProperty(0);
    	}else{
    		this.userAccept = (Boolean) obj.getProperty(0);
	    	this._id = obj.getProperty(1).toString();
	        this.firstName = obj.getProperty(2).toString();
	        Log.i("firstName", obj.getProperty(2).toString()+"");
	        this.lastName = obj.getProperty(3).toString();
	        Log.i("lastName", obj.getProperty(3).toString()+"");
	        this.username = obj.getProperty(4).toString();
	        Log.i("username", obj.getProperty(4).toString()+"");
	        this.password = obj.getProperty(5).toString();
	        Log.i("password", obj.getProperty(5).toString()+"");
    	}
    }

    public Boolean getAccept() {
		return userAccept;
    }
    
    public void setAccept(Boolean userAccept) {
    	this.userAccept = userAccept;
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
	            object = this.userAccept;
	            break;
	        }
	        case 1:
	        {
	            object = this._id;
	            break;
	        }
	        case 2:
	        {
	            object = this.firstName;
	            break;
	        }
	        case 3:
	        {
	            object = this.lastName;
	            break;
	        }
	        case 4:
	        {
	            object = this.username;
	            break;
	        }
	        case 5:
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
		return 6;
	}

	@Override
	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo propertyInfo) {
		// TODO Auto-generated method stub
		switch (index)
        {
	        case 0:
	        {
	        	propertyInfo.name = "success";
	        	propertyInfo.type = PropertyInfo.BOOLEAN_CLASS;
	            break;
	        }
	        case 1:
	        {
	        	propertyInfo.name = "id";
	        	propertyInfo.type = PropertyInfo.STRING_CLASS;
	            break;
	        }
	        case 2:
	        {
	        	propertyInfo.name = "firstName";
	        	propertyInfo.type = PropertyInfo.STRING_CLASS;
	            break;
	        }
	        case 3:
	        {
	        	propertyInfo.name = "lastName";
	        	propertyInfo.type = PropertyInfo.STRING_CLASS;
	            break;
	        }
	        case 4:
	        {
	        	propertyInfo.name = "username";
	        	propertyInfo.type = PropertyInfo.STRING_CLASS;
	            break;
	        }
	        case 5:
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
	            this.userAccept = (Boolean) obj;
	            break;
	        }
	        case 1:
	        {
	            this._id = obj.toString();
	            break;
	        }
	        case 2:
	        {
	            this.firstName = obj.toString();
	            break;
	        }
	        case 3:
	        {
	            this.lastName = obj.toString();
	            break;
	        }
	        case 4:
	        {
	            this.username = obj.toString();
	            break;
	        }
	        case 5:
	        {
	            this.password = obj.toString();
	            break;
	        }
        }
	}
}

