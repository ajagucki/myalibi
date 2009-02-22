package com.neutralspace.alibi;

import android.app.Dialog;
import android.content.Context;

public class AboutDialog extends Dialog{

	public AboutDialog(Context context) {
		super(context);
        this.setCancelable(true);
        this.setContentView(R.layout.about_alibi);
        this.setTitle("About myAlibi");
        this.show();
	}

}
