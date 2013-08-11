package com.hanyi.launcher.font;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class FontTextView extends TextView {

	public FontTextView(Context context) {
		super(context);
		if (FontConfig.sTypeFac != null) {
			setTypeface(FontConfig.sTypeFac);
		}
	}

	public FontTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (FontConfig.sTypeFac != null) {
			setTypeface(FontConfig.sTypeFac);
		}
	}

	public FontTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if (FontConfig.sTypeFac != null) {
			setTypeface(FontConfig.sTypeFac);
		}
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		super.setText(text, type);
	}
}
