package com.hidechat.app.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hidechat.app.GlobalSet;
import com.hidechat.app.R;
import com.hidechat.app.entities.EntityMessage;
import com.hidechat.app.entities.EntityUser;

public class AdapterMessage extends ArrayAdapter<EntityMessage> {

	private static LayoutInflater inflater = null;
	private ArrayList<EntityMessage> data;
	private EntityMessage item;
	private Context mCtx;
	private int idUser;
	
	public AdapterMessage(Context c, ArrayList<EntityMessage> d, int u) {
		super(c, R.layout.list_view_row_message, d);
		mCtx = c;
		data = d;
		idUser = u;
		inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return data.size();
	}

	public EntityMessage getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		item = data.get(position);
		return item.get_id();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		
		if (convertView == null) {
			vi = inflater.inflate(R.layout.list_view_row_message, null);
		}

		LinearLayout box = (LinearLayout) vi.findViewById(R.id.listViewRowMessagesBox);
		ImageView icon = (ImageView) vi.findViewById(R.id.listViewRowMessagesIcon);
		TextView txtDate = (TextView) vi.findViewById(R.id.listViewRowMessagesDate);
		TextView txtName = (TextView) vi.findViewById(R.id.listViewRowMessagesName);
		TextView txtStatus = (TextView) vi.findViewById(R.id.listViewRowMessagesStatus);

		item = data.get(position);
		
		if(item.getRead() == 0){
			box.setBackgroundResource(GlobalSet.mBoxes[0]);
			icon.setImageResource(GlobalSet.mIcon[0]);
			EntityUser sender = item.getEntityUserSender(mCtx);
			txtName.setText(sender.getFirstname() + " " + sender.getLastname());
			txtStatus.setTextColor(mCtx.getResources().getColor(R.color.new_message));
		}
		else if (item.getUid_sender() == idUser) { 
			box.setBackgroundResource(GlobalSet.mBoxes[1]);
			icon.setImageResource(GlobalSet.mIcon[1]);
			EntityUser receiver = item.getEntityUserReceiver(mCtx);
			txtName.setText(receiver.getFirstname() + " " + receiver.getLastname());
		}
		else if(item.getUid_receiver() == idUser){
			box.setBackgroundResource(GlobalSet.mBoxes[2]);
			icon.setImageResource(GlobalSet.mIcon[2]);
			EntityUser sender = item.getEntityUserSender(mCtx);
			txtName.setText(sender.getFirstname() + " " + sender.getLastname());
		}
		
		txtDate.setText(item.getDate_sender());
		txtStatus.setText(item.getStatus(idUser));
		return vi;
	}	
	
}

