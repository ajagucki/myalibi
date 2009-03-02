package com.neutralspace.alibi;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 *  The dialog box that displays the "About myAlibi" info from myAlibi's menu
 */
public class AboutDialog extends Dialog {
    
    // TODO: Change to Alibi's credits website
    private final String URL_CREDITS = "http://www.google.com";

	public AboutDialog(Context context) {
		super(context);
        this.setCancelable(true);
        this.setContentView(R.layout.about_alibi);
        this.setTitle("About myAlibi");
	}
	
	/**
	 * Create the buttons and give them actions
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Button creditsButton = (Button) findViewById(R.id.open_credits);
		Button closeButton = (Button) findViewById(R.id.close_about);
		
		closeButton.setOnClickListener(new CloseAbout(this));
		creditsButton.setOnClickListener(new OpenCredits(this));
	}
	
	/**
	 *  The action taken when the close button is pressed:
	 *  Close the about dialog box
	 */
	private class CloseAbout implements View.OnClickListener{
		AboutDialog aboutDialog;

		public CloseAbout(AboutDialog aboutDialog) {
			this.aboutDialog = aboutDialog;
		}
		
		public void onClick(View v) {
			aboutDialog.cancel();			
		}
		
	}
	
	/**
	 * The action taken when the credits button is pressed:
	 * Open the credits URL.
	 */
	private class OpenCredits implements View.OnClickListener{
		AboutDialog aboutDialog;

		public OpenCredits(AboutDialog aboutDialog) {
			this.aboutDialog = aboutDialog;
		}

		/**
		 * Open the URL in the web browser
		 */
		public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL_CREDITS));
            v.getContext().startActivity(intent);
		}
		
	}
	
}
