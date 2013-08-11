package com.hanyi.launcher.font;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.hanyi.launcher.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TTFListActivity extends Activity {

	private ListView mListView;
	private TTFAdapter mAdapter;
	private List<File> mTTFFiles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.ttf_list_layout);

		mListView = (ListView) this.findViewById(R.id.listview);

		mTTFFiles = new ArrayList<File>();
		FontConfig.listDir(new File(FontConfig.TTF_DIR), mTTFFiles);
		mAdapter = new TTFAdapter(this, mTTFFiles);
		mListView.setAdapter(mAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Log.i("XXL", "=========click  " + mTTFFiles.get(position).getPath());
				Intent intent = new Intent();
				intent.setAction(FontConfig.CHANGE_FONT);
				intent.putExtra(FontConfig.CHANGE_FONT_EXTRA_FILE, mTTFFiles.get(position).getPath());
				sendBroadcast(intent);
				finish();
			}
		});
	}
}
