package com.example.smartdental4.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.smartdental4.R;
import com.example.smartdental4.controllers.URLConnectionHelper;

/**
 * A login screen that offers login via email/password.
 */
public class ShareActivity extends Activity implements LoaderCallbacks<Cursor> {

	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	private static final String[] DUMMY_CREDENTIALS = new String[] {
			"foo@example.com:hello", "bar@example.com:world" };
	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// UI references.
	private AutoCompleteTextView mEmailView;
	private EditText mPasswordView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
		
		
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("bundle");
		
		// Set up the login form.
		mEmailView = (AutoCompleteTextView) findViewById(R.id.username);
//		mEmailView.setText(bundle.getString("username"));
		populateAutoComplete();

		mPasswordView = (EditText) findViewById(R.id.share_content);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});
		mPasswordView.setText(bundle.getString("shareContent"));
		
		Button mEmailSignInButton = (Button) findViewById(R.id.share_post_button);
		mEmailSignInButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				attemptLogin();
			}
		});

	}

	private void populateAutoComplete() {
		getLoaderManager().initLoader(0, null, this);
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		String email = mEmailView.getText().toString();
		String password = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password, if the user entered one.
		if (TextUtils.isEmpty(password) ) {
			mPasswordView.setError(getString(R.string.error_incorrect_share_content));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(email)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} 

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// perform the user login attempt.
			mAuthTask = new UserLoginTask(email, password);
			mAuthTask.execute((Void) null);
		}
	}
	
	public void toDiaryView(View v){
		Intent diaryView = new Intent();
		diaryView.setClass(this, DiaryActivity.class);
		startActivity(diaryView);
		finish();
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(this,
				// Retrieve data rows for the device user's 'profile' contact.
				Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
						ContactsContract.Contacts.Data.CONTENT_DIRECTORY),
				ProfileQuery.PROJECTION,

				// Select only email addresses.
				ContactsContract.Contacts.Data.MIMETYPE + " = ?",
				new String[] { ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE },

				// Show primary email addresses first. Note that there won't be
				// a primary email address if the user hasn't specified one.
				ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		List<String> emails = new ArrayList<String>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			emails.add(cursor.getString(ProfileQuery.ADDRESS));
			cursor.moveToNext();
		}

		addEmailsToAutoComplete(emails);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursorLoader) {

	}

	private interface ProfileQuery {
		String[] PROJECTION = { ContactsContract.CommonDataKinds.Email.ADDRESS,
				ContactsContract.CommonDataKinds.Email.IS_PRIMARY, };

		int ADDRESS = 0;
		int IS_PRIMARY = 1;
	}

	private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
		// Create adapter to tell the AutoCompleteTextView what to show in its
		// dropdown list.
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				ShareActivity.this,
				android.R.layout.simple_dropdown_item_1line,
				emailAddressCollection);

		mEmailView.setAdapter(adapter);
	}
	
	

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	@SuppressLint("SimpleDateFormat")
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

		private final String mEmail;
		private final String mPassword;

		UserLoginTask(String email, String password) {
			mEmail = email;
			mPassword = password;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			}

			for (String credential : DUMMY_CREDENTIALS) {
				String[] pieces = credential.split(":");
				if (pieces[0].equals(mEmail)) {
					// Account exists, return true if the password matches.
					return pieces[1].equals(mPassword);
				}
			}

			// TODO: register the new account here.
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			
			String url = "http://59.66.137.62:8000/mergeJson";
			String res = "{\"type\":\"orthodontics\"," +
					"\"title\":\"" + "Õý»û·ÖÏí" + "\",";
			Date now = new Date();
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			res += ("\"time\":\"" + date.format(now) + "\",");
			res += ("\"author\":\""+mEmail+"\",");
			res += ("\"img\":\"img/avatar.png\",");
			res += ("\"favor\":\"0\",");
			res += ("\"content\":\"" + 
				mPassword + "\"");
			res += ("}");
			
			new Thread(new Test(url, res)).start();;
		    
		}
		
		private class Test implements Runnable {
	    	private String url;
	    	private String res;
	    	public Test(String url, String res) {
	    		this.url = url;
	    		this.res = res;
	    	}

			@Override
			public void run() {
				String str = URLConnectionHelper.sendPost(this.url, this.res);
				if (str.trim().equals("updateSuccess")) {
					Intent statView = new Intent(ShareActivity.this, ForumActivity.class);
					startActivity(statView);
					finish();
				} else {
					finish();
				}
			}
	    }

		@Override
		protected void onCancelled() {
			mAuthTask = null;
		}
	}
	
}

