package com.hidechat.app.db;

import com.hidechat.app.MainActivity;
import com.hidechat.app.db.DBAdapter;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class DBAdapter extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME = "hidechat";
	private static final int DATABASE_VERSION = 73;
	private static final String TAG = "DBAdapter";
	
	private Context mCtx;

	private static SQLiteDatabase db;
	public static final String KEY_ID = "_id";
	public static final String[] columnsCount = new String[] { "count(*)" };
	public static final String[] columnsSum = new String[] { "sum(*)" };
	public static final String[] columnsMax = new String[] { "max(" + KEY_ID + ")" };
	
	private static final String user_TABLE_CREATE = "CREATE TABLE user(" + 
			"_id integer PRIMARY KEY, " +
			"firstname varchar(50), " + 
			"lastname varchar(50), " + 
			"username varchar(50), " + 
			"password varchar(50));";
	
	private static final String messages_TABLE_CREATE = "CREATE TABLE messages(" + 
			"_id integer PRIMARY KEY, " + 
			"uid_sender integer, " +
			"uid_receiver integer, " +
			"message varchar(250), " +
			"read integer, " +
			"date_sender datetime, " + 
			"date_receiver datetime, " + 
			"FOREIGN KEY (uid_sender) REFERENCES user(_id)," +
			"FOREIGN KEY (uid_receiver) REFERENCES user(_id));";
	
	private static DBAdapter instance;

	public DBAdapter(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public DBAdapter(Context ctx, String dbName, String sql, String tableName,
			int ver) {

		super(ctx, dbName, null, ver);
		Log.i(TAG, "Creating or opening database [ " + dbName + " ].");
	}

	public static DBAdapter getInstance(Context ctx) {
		if (instance == null) {
			instance = new DBAdapter(ctx);
			try {
				db = instance.getWritableDatabase();
			} catch (SQLiteException se) {
				Log.e(TAG, "Cound not create and/or open the database [ "
						+ DATABASE_NAME
						+ " ] that will be used for reading and writing.", se);
			}
		}
		return instance;
	}

	public static DBAdapter getInstance(Context ctx, String dbName, String sql,
			String tableName, int ver) {
		if (instance == null) {
			instance = new DBAdapter(ctx, dbName, sql, tableName, ver);
			try {
				Log.i(TAG, "Creating or opening the database [ " + dbName
						+ " ].");
				db = instance.getWritableDatabase();
			} catch (SQLiteException se) {
				Log.e(TAG, "Cound not create and/or open the database [ "
						+ dbName
						+ " ] that will be used for reading and writing.", se);
			}
		}
		return instance;
	}

	public void close() {
		if (instance != null) {
			Log.i(TAG, "Closing the database [ " + DATABASE_NAME + " ].");
			db.close();
			instance = null;
		}
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG, "Trying to create database table if it isn't existed.");
		try {
			
			db.execSQL(user_TABLE_CREATE);
			db.execSQL(messages_TABLE_CREATE);

			db.execSQL("insert into user values (36, 'Gilberto', 'Fonte', 'gf', '123')"); 
			db.execSQL("insert into user values (34, 'Carlos', 'Novo', 'cnovo', '123')"); 
			db.execSQL("insert into user values (35, 'Marco', 'Morais', 'mm', '123')"); 
			db.execSQL("insert into user values (37, 'Tiago', 'Monteiro', 'tm', '123')");

			Log.w(TAG, "Database before messages");



			Log.w(TAG, "Database create successfuly");

		} catch (SQLException ex) {
			Log.e(TAG,
					"Cound not create the database table according to the SQL statement ",
					ex);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("drop table if exists messages");
			db.execSQL("drop table if exists user");
			onCreate(db);
		} catch (SQLException ex) {
			Log.e(TAG, "Cound not drop the database table ", ex);
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void onConfigure(SQLiteDatabase db) {
		super.onConfigure(db);
		db.execSQL("PRAGMA foreign_keys=ON;");
	}

	public long insert(String table, ContentValues values) {
		return db.insert(table, null, values);
	}

	public Cursor get(String table, String[] columns, String where,
			String orderBy, long id) {
		Cursor cursor = db.query(true, table, columns, createWhere(where, id),
				null, null, null, orderBy, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	public Cursor getJoin(String inTables, String whereJoin,
			String[] columns, String orderBy) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(inTables);
		queryBuilder.appendWhere(whereJoin);

		Cursor c = queryBuilder.query(db, columns, null, null, null, null,
				orderBy);
		return c;
	}

	public int delete(String table, String where, long id) {
		return db.delete(table, createWhere(where, id), null);
	}

	public int update(String table, long id, ContentValues values, String where) {
		return db.update(table, values, createWhere(where, id), null);
	}

	public void execSQL(String query) {
		db.execSQL(query);
	}

	private String createWhere(String where, long id) {
		String whereFinal = where;
		if (id != -1) {
			if (where != null) {
				whereFinal = where + ", " + KEY_ID + "=" + id;
			} else {
				whereFinal = KEY_ID + "=" + id;
			}
		}
		return whereFinal;
	}
}

