package com.hidechat.app.dal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.hidechat.app.dal.DALMessage;
import com.hidechat.app.dal.DALUser;
import com.hidechat.app.db.DBAdapter;
import com.hidechat.app.db.DBMain;
import com.hidechat.app.entities.EntityMessage;

public class DALMessage {
	public static final String TABLE_NAME = "messages";
	public static final String UID_SENDER = "uid_sender";
	public static final String UID_RECEIVER = "uid_receiver";
	public static final String MESSAGE = "message";
	public static final String READ = "read";
	public static final String DATE_SENDER = "date_sender";
	public static final String DATE_RECEIVER = "date_receiver";

	public static final String[] columns = new String[] { 
		DBAdapter.KEY_ID,
		UID_SENDER,
		UID_RECEIVER, 
		MESSAGE, 
		READ,
		DATE_SENDER, 
		DATE_RECEIVER
	};

	public static final String TABLES_JOIN = String.format("%s, %s, %s", 
				TABLE_NAME,
				String.format("%s as %s", DALUser.TABLE_NAME, DALUser.TABLE_NAME_JOIN_SENDER),
				String.format("%s as %s", DALUser.TABLE_NAME, DALUser.TABLE_NAME_JOIN_RECEIVER));
	
	public static final String WHERE_JOIN = String.format("%s = %s and %s = %s", 
			String.format("%s.%s", TABLE_NAME, UID_SENDER), 
			String.format("%s.%s", DALUser.TABLE_NAME_JOIN_SENDER, DBAdapter.KEY_ID),
			String.format("%s.%s", TABLE_NAME, UID_RECEIVER), 
			String.format("%s.%s", DALUser.TABLE_NAME_JOIN_RECEIVER, DBAdapter.KEY_ID));

	public static final String[] columnsJoin = new String[] { 
		String.format("%s.%s", TABLE_NAME, DBAdapter.KEY_ID),
		String.format("%s.%s", TABLE_NAME, UID_SENDER), 
		String.format("%s.%s", TABLE_NAME, UID_RECEIVER),
		String.format("%s.%s", TABLE_NAME, MESSAGE),
		String.format("%s.%s", TABLE_NAME, READ), 
		String.format("%s.%s", TABLE_NAME, DATE_SENDER),
		String.format("%s.%s", TABLE_NAME, DATE_RECEIVER),

		String.format("%s.%s", DALUser.TABLE_NAME_JOIN_SENDER, DALUser.FIRSTNAME),
		String.format("%s.%s", DALUser.TABLE_NAME_JOIN_SENDER, DALUser.LASTNAME),
		String.format("%s.%s", DALUser.TABLE_NAME_JOIN_RECEIVER, DALUser.FIRSTNAME),
		String.format("%s.%s", DALUser.TABLE_NAME_JOIN_RECEIVER, DALUser.LASTNAME)
	};

	public DALMessage(Context context) {
		
	}	

	public static ContentValues getContentValues(Context mCtx, Object obj) {
		ContentValues values = new ContentValues();
		
		EntityMessage ent = (EntityMessage) obj;
		
		//if(ent.get_id() == 0)
			//ent.set_id((int)DBMain.getAggregate(mCtx, TABLE_NAME, DBAdapter.columnsMax, null) + 1);
		values.put(DBAdapter.KEY_ID, ent.get_id());
		values.put(UID_SENDER, ent.getUid_sender());
		values.put(UID_RECEIVER, ent.getUid_receiver());
		values.put(MESSAGE, ent.getMessage());
		values.put(READ, ent.getRead());
		values.put(DATE_SENDER, ent.getDate_sender());
		values.put(DATE_RECEIVER, ent.getDate_receiver());
		return values;
	}
	
	public static boolean save(Context mCtx, EntityMessage obj, long id) {
		long result = 0;

		if (id == 0)
			result = DBMain.insert(mCtx, DALMessage.TABLE_NAME, DALMessage.getContentValues(mCtx, obj));
		else
			result = DBMain.update(mCtx, DALMessage.TABLE_NAME, id, DALMessage.getContentValues(mCtx, obj));

		return result >= 0;
	}	

	public static EntityMessage getFirst(Cursor c) {
		EntityMessage single;
		
		c.moveToFirst();
		single = new EntityMessage(
				c.getInt(0), 
				c.getInt(1), 
				c.getInt(2), 
				c.getString(3),
				c.getInt(4),
				c.getString(5), 
				c.getString(6),
				c.getColumnCount() > 7 ? String.format("%s %s", c.getString(7), c.getString(8)) : null, 
				c.getColumnCount() > 7 ? String.format("%s %s", c.getString(9), c.getString(10)) : null
				);
		return single;
	}
	
	public static ArrayList<EntityMessage> converte(Cursor c) {
		ArrayList<EntityMessage> arr = new ArrayList<EntityMessage>();
		EntityMessage single;
		
		c.moveToFirst();
        while (c.isAfterLast() == false) {
    		single = new EntityMessage(
    				c.getInt(0), 
    				c.getInt(1), 
    				c.getInt(2), 
    				c.getString(3),
    				c.getInt(4),
    				c.getString(5), 
    				c.getString(6),
    				c.getColumnCount() > 7 ? String.format("%s %s", c.getString(7), c.getString(8)) : null, 
    				c.getColumnCount() > 7 ? String.format("%s %s", c.getString(9), c.getString(10)) : null
    				);
        	arr.add(single);
        	c.moveToNext();
        }
        c.close();
		
		return arr;
	}
	
}

