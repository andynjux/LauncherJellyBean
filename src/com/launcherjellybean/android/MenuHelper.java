
package com.launcherjellybean.android;

import com.hanyi.launcher.R;
import com.hanyi.launcher.font.FontConfig;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MenuHelper implements View.OnClickListener, View.OnKeyListener {

    private static final int ANIMATION_TIME = 400;

    private View mMenuRootView;
    private LayoutInflater mInflater;
    private PopupWindow mPopupWindow;
    private long mPressedTime;

    private Context mContext;

    private int mMenuViewWidth;
    private int mMenuViewHeight;

    private View mParentView;
    
    private Launcher mLauncher;

    public MenuHelper(Launcher context, View parentView) {
    	mLauncher = context;
        mContext = context.getApplicationContext();
        mParentView = parentView;

        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                .getMetrics(dm);
        mMenuViewWidth = dm.widthPixels;

        mMenuViewHeight = mContext.getResources().getDimensionPixelSize(R.dimen.menu_desk_height);

        setupViews();
    }

    public void showDeskMenu() {
        showViewAtBottom();
    }

    private void showViewAtBottom() {
        if (Math.abs(System.currentTimeMillis() - mPressedTime) > ANIMATION_TIME) {
            mPressedTime = System.currentTimeMillis();

            if (mPopupWindow == null) {
                Resources res = mContext.getResources();
                int width = mMenuViewWidth;
                int height = mMenuViewHeight;

                PopupWindow popupWindow = new PopupWindow(mMenuRootView, width, height);
                popupWindow.setFocusable(true);
                popupWindow.setTouchable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable(res));
                popupWindow.setAnimationStyle(R.style.MenuToggleAnimation);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                    }
                });
                mPopupWindow = popupWindow;
            }
            mPopupWindow.showAtLocation(mParentView, Gravity.BOTTOM, 0, 0);
        }
    }

    private void setupViews() {
        mInflater = LayoutInflater.from(mContext);
        mMenuRootView = mInflater.inflate(R.layout.menu_layout, null);

        int width = mMenuViewWidth;
        int height = mMenuViewHeight;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, height);
        mMenuRootView.setLayoutParams(params);

        mMenuRootView.setFocusableInTouchMode(true);
        mMenuRootView.setOnKeyListener(this);
        
        TextView appTextView = (TextView)mMenuRootView.findViewById(R.id.menu_app_manage);
        appTextView.setOnClickListener(this);
        appTextView.setTypeface(FontConfig.sTypeFac);
        
        TextView fontTextView = (TextView)mMenuRootView.findViewById(R.id.menu_fonts);
        fontTextView.setOnClickListener(this);
        fontTextView.setTypeface(FontConfig.sTypeFac);
        
        TextView systemTextView = (TextView)mMenuRootView.findViewById(R.id.menu_system_settings);
        systemTextView.setOnClickListener(this);
        systemTextView.setTypeface(FontConfig.sTypeFac);
        
        TextView wallpaperTextView = (TextView)mMenuRootView.findViewById(R.id.menu_wallpaper);
        wallpaperTextView.setOnClickListener(this);
        wallpaperTextView.setTypeface(FontConfig.sTypeFac);
    }

    @Override
    public void onClick(View v) {
        dismissPopupWindowImmediately();
        
        mLauncher.onMenuPressed(v);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (mPopupWindow == null) {
            return false;
        }
        if (!mPopupWindow.isShowing()) {
            return false;
        }
        if (keyCode == KeyEvent.KEYCODE_MENU && event.getAction() == KeyEvent.ACTION_UP) {
            dismissPopupWindow();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            dismissPopupWindow();
            return true;
        }
        return false;
    }

    public void dismissPopupWindow() {
        if (Math.abs(System.currentTimeMillis() - mPressedTime) > ANIMATION_TIME) {
            mPressedTime = System.currentTimeMillis();
            if (mPopupWindow != null && mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
            }
        }
    }

    public void dismissPopupWindowImmediately() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

	public void changeFont() {
		changeFont(mMenuRootView);
	}
	
	private void changeFont(View view) {
		if (view == null) return;
		if (FontConfig.sTypeFac == null) return;
		if (view instanceof TextView) {
			((TextView)view).setTypeface(FontConfig.sTypeFac);
		} else if (view instanceof EditText) {
			((EditText)view).setTypeface(FontConfig.sTypeFac);
		} else if (view instanceof ViewGroup) {
			ViewGroup viewGroup = (ViewGroup)view;
			final int count = viewGroup.getChildCount();
			for (int i=0;i<count;i++) {
				changeFont(viewGroup.getChildAt(i));
			}
		}
	}
}
