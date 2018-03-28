package com.hidechat.app.entities;

import java.io.Serializable;

import android.content.Context;
import android.graphics.Bitmap;

import com.hidechat.app.dal.DALUser;
import com.hidechat.app.db.DBMain;

public class EntityUser implements Serializable {

	private int _id;
	private String firstname;
	private String lastname;
	private String username;
	private String password;

	public EntityUser() {
		super();
		this._id = 0;
	}
	
	public EntityUser(int _id, String firstname, String lastname, String username, String password) {
		super();
		this._id = _id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
	}
	
	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
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

	private boolean verifRequiredFields(Context mCtx)
	{
		/*if(this.getDesign().length() == 0){
			Toast.makeText(mCtx, mCtx.getString(R.string.msg_required_categ_design), Toast.LENGTH_SHORT).show();
			return false;
		}
		*/
		return true;
	}
	
	public boolean save(Context mCtx) {
		long result = 0;

		if(!verifRequiredFields(mCtx))
			return false;
		
		if (this._id == 0)
			result = DBMain.insert(mCtx, DALUser.TABLE_NAME, DALUser.getContentValues(mCtx, this));
		else
			result = DBMain.update(mCtx, DALUser.TABLE_NAME, this._id, DALUser.getContentValues(mCtx, this));

		return result >= 0;
	}

	public boolean delete(Context mCtx) {
		long result = 0;
		
		if(this._id > 0)
			result = DBMain.deleteById(mCtx, DALUser.TABLE_NAME, this._id);

		return result > 0;
	}
	
}
