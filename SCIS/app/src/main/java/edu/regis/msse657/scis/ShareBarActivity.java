/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

package edu.regis.msse657.scis;

import java.io.File;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 *
 * Main class of the ShareBar Example for SocialAuth Android SDK. <br>
 *
 * The main objective of this example is to create a bar of social media
 * providers Facebook, Twitter and others. It enables user to access the
 * respective provider on single click and update the status.
 *
 * The class first creates a bar in main.xml. It then adds bar to SocialAuth
 * Android Library <br>
 *
 * Then it adds providers Facebook, Twitter and others to library object by
 * addProvider method and finally enables the providers by calling enable method<br>
 *
 * After successful authentication of provider, it receives the response in
 * responseListener and then automatically update status by updatestatus()
 * method.
 *
 * It's Primarly use is to share message but developers can use to access other
 * functionalites like getting profile , contacts , sharing images etc.
 *
 * Now you can use share -bar to share message via email and mms.See example
 * below
 *
 * This example shows how you can use addconfig method to define key and secrets
 * in code.Therefore we have remove oauthconsumers.properties.
 *
 * <br>
 *
 * @author vineet.aggarwal@3pillarglobal.com
 *
 */

public class ShareBarActivity extends Activity {

	public static final String ARG_ITEM_STATUS = "status";

	// SocialAuth Component
	SocialAuthAdapter adapter;
	boolean status;

	// Android Components
	Button update;
	EditText edit;
    TextInputLayout layout;

    // Remember last message sent
    String lastMessageSent = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		String status = getIntent().getStringExtra(ARG_ITEM_STATUS);


		// Default status message
        layout = (TextInputLayout) findViewById(R.id.layoutTxt);

		edit = (EditText) findViewById(R.id.editTxt);
		edit.setText(status + " #thisisregis");

		LinearLayout bar = (LinearLayout) findViewById(R.id.linearbar);
		bar.setBackgroundResource(R.drawable.bar_gradient);

		// Add Bar to library
		adapter = new SocialAuthAdapter(new ResponseListener());

		// Please note : Update status functionality is only supported by
		// Facebook, Twitter, Linkedin, MySpace, Yahoo and Yammer.

		// Add providers
		adapter.addProvider(Provider.FACEBOOK, R.drawable.facebook);
		adapter.addProvider(Provider.TWITTER, R.drawable.twitter);
		adapter.addProvider(Provider.LINKEDIN, R.drawable.linkedin);

		// Add email and mms providers
		adapter.addProvider(Provider.EMAIL, R.drawable.email);
		adapter.addProvider(Provider.MMS, R.drawable.mms);

		// For twitter use add callback method. Put your own callback url here.
		adapter.addCallBack(Provider.TWITTER, "http://socialauth.in/socialauthdemo/socialAuthSuccessAction.do");

		// Add keys and Secrets
		try {
			adapter.addConfig(Provider.FACEBOOK, "297841130364674", "dc9c59d0c72d4f2533580e80ba4c2a59", null);
			adapter.addConfig(Provider.TWITTER, "5jwyYJia583EEczmdAmlOA", "j0rQkJjTjwVdv7HFiE4zz2qKJKzqjksR2aviVU8fSc",
					null);
			adapter.addConfig(Provider.LINKEDIN, "bh82t52rdos6", "zQ1LLrGbhDZ36fH8", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		adapter.enable(bar);
	}

	/**
	 * Listens Response from Library
	 *
	 */

	private final class ResponseListener implements DialogListener {
		@Override
		public void onComplete(Bundle values) {

			// Variable to receive message status
			Log.d("Share-Bar", "Authentication Successful");

			// Get name of provider after authentication
			final String providerName = values.getString(SocialAuthAdapter.PROVIDER);
			Log.d("Share-Bar", "Provider Name = " + providerName);
			Toast.makeText(ShareBarActivity.this, providerName + " connected", Toast.LENGTH_SHORT).show();

			update = (Button) findViewById(R.id.update);

			// Please avoid sending duplicate message. Social Media Providers
			// block duplicate messages.

			update.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

                    // Validate the status message. It should not be empty, and it should not be
                    // the same as the last message sent.
                    String status = edit.getText().toString();

                    if (status.isEmpty()) {
                        layout.setError("Oops - you forgot to enter a status message.");
                        return;
                    } else if (status.equals(lastMessageSent)) {
                        layout.setError("Whoa - you forgot to change your status message.");
                        return;
                    }

                    layout.setError(null);

					// Call updateStatus to share message via oAuth providers
					// adapter.updateStatus(edit.getText().toString(), new
					// MessageListener(), false);

					// call to update on all connected providers at once
					adapter.updateStatus(status, new MessageListener(), true);
                    lastMessageSent = status;
				}
			});

			// Share via Email Intent
			if (providerName.equalsIgnoreCase("share_mail")) {
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",
						"vineet.aggarwal@3pillarglobal.com", null));
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Test");
				File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
						"image5964402.png");
				Uri uri = Uri.fromFile(file);
				emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
				startActivity(Intent.createChooser(emailIntent, "Test"));
			}

			// Share via mms intent
			if (providerName.equalsIgnoreCase("share_mms")) {
				File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
						"image5964402.png");
				Uri uri = Uri.fromFile(file);

				Intent mmsIntent = new Intent(Intent.ACTION_SEND, uri);
				mmsIntent.putExtra("sms_body", "Test");
				mmsIntent.putExtra(Intent.EXTRA_STREAM, uri);
				mmsIntent.setType("image/png");
				startActivity(mmsIntent);
			}
		}

		@Override
		public void onError(SocialAuthError error) {
			error.printStackTrace();
			Log.d("Share-Bar", error.getMessage());
		}

		@Override
		public void onCancel() {
			Log.d("Share-Bar", "Authentication Cancelled");
		}

		@Override
		public void onBack() {
			Log.d("Share-Bar", "Dialog Closed by pressing Back Key");

		}
	}

	// To get status of message after authentication
	private final class MessageListener implements SocialAuthListener<Integer> {
		@Override
		public void onExecute(String provider, Integer t) {
			Integer status = t;
			if (status.intValue() == 200 || status.intValue() == 201 || status.intValue() == 204)
				Toast.makeText(ShareBarActivity.this, "Message posted on " + provider, Toast.LENGTH_LONG).show();
			else
				Toast.makeText(ShareBarActivity.this, "Message not posted on" + provider, Toast.LENGTH_LONG).show();
		}

		@Override
		public void onError(SocialAuthError e) {

		}
	}
}