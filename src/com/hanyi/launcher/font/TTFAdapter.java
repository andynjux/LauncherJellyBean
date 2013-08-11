package com.hanyi.launcher.font;

import java.io.File;
import java.util.List;

import com.hanyi.launcher.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TTFAdapter extends BaseAdapter {

	private LayoutInflater mInflater;

	private List<File> mData;

	public TTFAdapter(Context context, List<File> data) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mData = data;
	}

	@Override
	public int getCount() {
		return mData == null ? 0 : mData.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.ttf_list_item_layout, null);
		}
		TextView itemView = (TextView)convertView;
		itemView.setText(mData.get(position).getName());
		return convertView;
	}

}
