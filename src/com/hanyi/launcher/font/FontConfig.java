package com.hanyi.launcher.font;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Environment;
import android.widget.Toast;

import com.hanyi.launcher.R;
import com.launcherjellybean.android.Launcher;

public class FontConfig {
	public static final String CHANGE_FONT = "com.hanyi.launcher.CHANGE_FONT";
	public static final String CHANGE_FONT_EXTRA_FILE = "ttf_file";
	
	public static String SDCARD_DIR = Environment.getExternalStorageDirectory()
			.getPath();

	public static String TTF_DIR = SDCARD_DIR + "/hanyi/ttf";

	public static Typeface sTypeFac = null;
	
	public static String sCurrentFontFile = null;

	private static FontConfig sInstance = null;
	
	private Context mContext = null;
	private Launcher mLauncher = null;
	
	private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.hasExtra(CHANGE_FONT_EXTRA_FILE)) {
				String ttfFile = intent.getStringExtra(CHANGE_FONT_EXTRA_FILE);
				if (ttfFile != null) {
					try {
						Typeface tf = Typeface.createFromFile(ttfFile);
						sCurrentFontFile = ttfFile;
						sTypeFac = tf;
						saveCurrentFont();
						mLauncher.refreshFont();
					} catch (Exception e) {
					}
				}
			}
		}
	};

	private FontConfig() {
	}

	public static FontConfig ceateInstance(Launcher launcher) {
		if (sInstance == null) {
			sInstance = new FontConfig();
			sInstance.mLauncher = launcher;
			sInstance.mContext = launcher.getApplicationContext();
			sInstance.loadCurrentFont();
			IntentFilter filter = new IntentFilter(CHANGE_FONT);
			sInstance.mLauncher.registerReceiver(sInstance.mIntentReceiver, filter);
		}
		return sInstance;
	}
	
	public void destory() {
		mLauncher.unregisterReceiver(mIntentReceiver);
	}
	
	private void loadCurrentFont() {
		SharedPreferences sp = mContext.getSharedPreferences("font_info", Activity.MODE_WORLD_READABLE);  
		sCurrentFontFile = sp.getString("current_font", null);
		if (sCurrentFontFile != null) {
			File fontFile = new File(sCurrentFontFile);
			if (!fontFile.exists() || !fontFile.isFile()) {
				Toast.makeText(mContext, R.string.font_file_invalid, Toast.LENGTH_LONG).show();
			} else {
				try {
					sTypeFac = Typeface.createFromFile(sCurrentFontFile);
				} catch (Exception e) {
					sCurrentFontFile = null;
					sTypeFac = null;
					Toast.makeText(mContext, R.string.font_file_invalid, Toast.LENGTH_LONG).show();
				}
			}
		}
    }
	
	private void saveCurrentFont() {
		SharedPreferences sp = mContext.getSharedPreferences("font_info", Activity.MODE_WORLD_READABLE);  
		sp.edit().putString("current_font", sCurrentFontFile).commit();
	}

	public static FontConfig getInstance() {
		return sInstance;
	}

	public static boolean isSDCardMounted() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	public static void listDir(File dir, List<File> result) {
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				listDir(file, result);
			} else {
				result.add(file);
			}
		}
	}
}
