package com.hidechat.app.dal;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hidechat.app.dal.DALUser;
import com.hidechat.app.db.DBAdapter;
import com.hidechat.app.db.DBMain;
import com.hidechat.app.entities.EntityUser;

public class DALUser {
	
	public static final String TABLE_NAME = "user";
	public static final String FIRSTNAME = "firstname";
	public static final String LASTNAME = "lastname";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";

	// joins
	public static final String TABLE_NAME_JOIN_SENDER = "user_sender";
	public static final String TABLE_NAME_JOIN_RECEIVER = "user_receiver";

	public static final String[] columns = new String[] { DBAdapter.KEY_ID, FIRSTNAME, LASTNAME, USERNAME, PASSWORD };

	public DALUser(Context context) {
		
	}	

	public static ContentValues getContentValues(Context mCtx, Object obj) {
		ContentValues values = new ContentValues();
		
		EntityUser ent = (EntityUser) obj;
		
		if(ent.get_id() == 0)
			ent.set_id((int) DBMain.getAggregate(mCtx, TABLE_NAME, DBAdapter.columnsMax, null) + 1);
		values.put(DBAdapter.KEY_ID, ent.get_id());
		values.put(FIRSTNAME, ent.getFirstname());
		values.put(LASTNAME, ent.getLastname());
		values.put(USERNAME, ent.getUsername());
		values.put(PASSWORD, ent.getPassword());
		
		return values;
	}
	
	public static boolean save(Context mCtx, EntityUser obj, long id) {
		long result = 0;

		if (id == 0)
			result = DBMain.insert(mCtx, DALUser.TABLE_NAME, DALUser.getContentValues(mCtx, obj));
		else
			result = DBMain.update(mCtx, DALUser.TABLE_NAME, id, DALUser.getContentValues(mCtx, obj));

		return result >= 0;
	}

	public static EntityUser getFirst(Cursor c) {
		EntityUser single;
		
		//byte[] blob = c.getBlob(c.getColumnIndex(PHOTO));
		//Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
		
		c.moveToFirst();
		single = new EntityUser(
				c.getInt(0),
				c.getString(1),
				c.getString(2),
				c.getString(3),
				c.getString(4)
				);
		return single;
	}
	
	public static ArrayList<EntityUser> converte(Cursor c) {
		ArrayList<EntityUser> arr = new ArrayList<EntityUser>();
		EntityUser single;
		
		c.moveToFirst();
        while (c.isAfterLast() == false) {
    		single = new EntityUser(
    				c.getInt(0),
    				c.getString(1),
    				c.getString(2),
    				c.getString(3),
    				c.getString(4)
    				);
        	arr.add(single);
        	c.moveToNext();
        }
        c.close();
		
		return arr;
	}
}
