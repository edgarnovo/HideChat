package com.hidechat.app.entities;

import java.io.Serializable;

import android.content.Context;

import com.hidechat.app.dal.DALMessage;
import com.hidechat.app.dal.DALUser;
import com.hidechat.app.db.DBMain;
import com.hidechat.app.entities.EntityUser;

public class EntityMessage implements Serializable {

	private int _id;
	private int uid_sender;
	private int uid_receiver;
	private String message;
	private int read;
	private String date_sender;
	private String date_receiver;
	private String user_sender;
	private String user_receiver;

	public EntityMessage() {
		super();
		this._id = 0;
	}
	
	public EntityMessage(int _id, int uid_sender, int uid_receiver, String message, int read, String date_sender, String date_receiver) {
		super();
		this._id = _id;
		this.uid_sender = uid_sender;
		this.uid_receiver = uid_receiver;
		this.message = message;
		this.read = read;
		this.date_sender = date_sender;
		this.date_receiver = date_receiver;
	}
	
	public EntityMessage(int _id, int uid_sender, int uid_receiver, String message, int read, String date_sender, String date_receiver, String user_sender, String user_receiver) {
		super();
		this._id = _id;
		this.uid_sender = uid_sender;
		this.uid_receiver = uid_receiver;
		this.message = message;
		this.read = read;
		this.date_sender = date_sender;
		this.date_receiver = date_receiver;
		this.user_sender = user_sender;
		this.user_receiver = user_receiver;
	}
	
	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int getUid_sender() {
		return uid_sender;
	}

	public void setUid_sender(int uid_sender) {
		this.uid_sender = uid_sender;
	}

	public int getUid_receiver() {
		return uid_receiver;
	}

	public void setUid_receiver(int uid_receiver) {
		this.uid_receiver = uid_receiver;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getRead() {
		return read;
	}

	public void setRead(int read) {
		this.read = read;
	}

	public String getDate_sender() {
		return date_sender;
	}

	public void setDate_sender(String date_sender) {
		this.date_sender = date_sender;
	}

	public String getDate_receiver() {
		return date_receiver;
	}

	public void setDate_receiver(String date_receiver) {
		this.date_receiver = date_receiver;
	}

	public String getUser_sender() {
		return user_sender;
	}

	public String getUser_receiver() {
		return user_receiver;
	}

	public String getStatus(int idUser) {
		String result = "";
		if(this.read == 0)
			result = "New";
		else if(this.uid_sender == idUser)
			result = "Send";
		else if(this.uid_receiver == idUser)
			result = "Read";
		
		return result;
	}

	public EntityUser getEntityUserSender(Context mCtx) {
		return DALUser.getFirst(DBMain.getById(mCtx, DALUser.TABLE_NAME, DALUser.columns, this.uid_sender));
	}

	public EntityUser getEntityUserReceiver(Context mCtx) {
		return DALUser.getFirst(DBMain.getById(mCtx, DALUser.TABLE_NAME, DALUser.columns, this.uid_receiver));
	}


	private boolean verifRequiredFields(Context mCtx)
	{
		/*if(this.getDesign().length() == 0){
			Toast.makeText(mCtx, mCtx.getString(R.string.msg_required_place_design), Toast.LENGTH_SHORT).show();
			return false;
		}
		if(this.getIdCateg() == 0){
			Toast.makeText(mCtx, mCtx.getString(R.string.msg_required_place_categ), Toast.LENGTH_SHORT).show();
			return false;
		}		
		if(this.getIdCountry() == 0){
			Toast.makeText(mCtx, mCtx.getString(R.string.msg_required_place_country), Toast.LENGTH_SHORT).show();
			return false;
		}
		if(this.getLat() == 0){
			Toast.makeText(mCtx, mCtx.getString(R.string.msg_required_place_lat), Toast.LENGTH_SHORT).show();
			return false;
		}
		if(this.getLon() == 0){
			Toast.makeText(mCtx, mCtx.getString(R.string.msg_required_place_lon), Toast.LENGTH_SHORT).show();
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
			result = DBMain.insert(mCtx, DALMessage.TABLE_NAME, DALMessage.getContentValues(mCtx, this));
		else
			result = DBMain.update(mCtx, DALMessage.TABLE_NAME, this._id, DALMessage.getContentValues(mCtx, this));

		return result >= 0;
	}

	public boolean delete(Context mCtx) {
		long result = 0;
		
		if(this._id > 0)
			result = DBMain.deleteById(mCtx, DALMessage.TABLE_NAME, this._id);

		return result > 0;
	}

}
