package com.raksa.realmdemoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.raksa.realmdemoapp.model.SocialAccount;
import com.raksa.realmdemoapp.model.User;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmAsyncTask;


public class MainActivity extends AppCompatActivity {

	private static final String TAG = MainActivity.class.getSimpleName();

	private EditText etPersonName, etAge, etSocialAccountName, etStatus;

	private Realm myRealm;
	private RealmAsyncTask realmAsyncTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		etPersonName 		= (EditText) findViewById(R.id.etPersonName);
		etAge 				= (EditText) findViewById(R.id.etAge);
		etSocialAccountName = (EditText) findViewById(R.id.etSocialAccount);
		etStatus 			= (EditText) findViewById(R.id.etStatus);

		myRealm = Realm.getDefaultInstance();
		
	}

	// Add data to Realm using Main UI Thread. Be Careful: As it may BLOCK the UI.
	public void addUserToRealm_Synchronously(View view) {

		final String id = UUID.randomUUID().toString();
		//Manual way add User to realm
		/*try {
			myRealm.beginTransaction();

			User user = myRealm.createObject(User.class,id);
			user.setName(etPersonName.getText().toString());
			user.setAge(Integer.valueOf(etAge.getText().toString()));
			socialAccount.setName(etSocialAccountName.getText().toString());
			socialAccount.setStatus(etStatus.getText().toString());
			user.setSocialAccount(socialAccount);

			myRealm.commitTransaction();

		}catch (Exception e){
			myRealm.cancelTransaction();
		}*/
		myRealm.executeTransaction(new Realm.Transaction() {
			@Override
			public void execute(Realm realm) {
				SocialAccount socialAccount = myRealm.createObject(SocialAccount.class);
				socialAccount.setName(etSocialAccountName.getText().toString());
				socialAccount.setStatus(etStatus.getText().toString());

				User user = myRealm.createObject(User.class,id);
				user.setName(etPersonName.getText().toString());
				user.setAge(Integer.valueOf(etAge.getText().toString()));
				user.setSocialAccount(socialAccount);

				Toast.makeText(getApplicationContext(),"User was added successfully!",Toast.LENGTH_SHORT).show();
			}


		});


	}

	// Add Data to Realm in the Background Thread.
	public void addUserToRealm_ASynchronously(View view) {

	}

	public void displayAllUsers(View view) {

	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}
}
