package com.hidechat.app.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hidechat.app.R;
import com.hidechat.app.entities.EntityUser;

public class AdapterUser extends ArrayAdapter<EntityUser> {

	private static LayoutInflater inflater = null;
	private ArrayList<EntityUser> data;
	private EntityUser item;
	
	public AdapterUser(Context c, ArrayList<EntityUser> d) {
		super(c, R.layout.list_view_row_user, d);
		data = d;
		inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return data.size();
	}

	public EntityUser getItem(int position) {
		return data.get(position);
	}

	/*public long getItemId(int position) {
		item = data.get(position);
		return item.get_id();
	}*/

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;

		if (convertView == null) {
			vi = inflater.inflate(R.layout.list_view_row_user, null);
		}

		ImageView imgUser = (ImageView) vi.findViewById(R.id.listViewRowUserPhoto);
		TextView txtFirstName = (TextView) vi.findViewById(R.id.listViewRowUserFirstName);
		TextView txtLastName = (TextView) vi.findViewById(R.id.listViewRowUserLastName);

		item = data.get(position);
		txtFirstName.setText(item.getFirstname());
		txtLastName.setText(item.getLastname());
		//imgUser.setImageBitmap(item.getPhoto());
		return vi;
	}	
	
}