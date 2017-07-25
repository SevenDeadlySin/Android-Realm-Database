package com.raksa.realmdemoapp;

import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.raksa.realmdemoapp.model.SocialAccount;
import com.raksa.realmdemoapp.model.User;

import java.util.UUID;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;


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

		final String id = UUID.randomUUID().toString();

		myRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User user = realm.createObject(User.class, id);
                user.setName(etPersonName.getText().toString());
                user.setAge(Integer.valueOf(etAge.getText().toString()));
                SocialAccount socialAccount = realm.createObject(SocialAccount.class);
                socialAccount.setName(etSocialAccountName.getText().toString());
                socialAccount.setStatus(etStatus.getText().toString());
                user.setSocialAccount(socialAccount);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(),"Successfully inserted into realm database",Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(getApplicationContext(),"Fail To Insert Into realm database!",Toast.LENGTH_SHORT).show();
            }
        });

	}

	public void displayAllUsers(View view) {

        RealmResults<User> results = myRealm.where(User.class).findAll();

        StringBuilder stringBuilder = new StringBuilder();

        for (User getuser:results){
            stringBuilder.append("ID: ").append(getuser.getId());
            stringBuilder.append("Name: ").append(getuser.getName());
            stringBuilder.append("Age: ").append(getuser.getAge());
            stringBuilder.append("SocialAccount: ").append(getuser.getSocialAccount().getName());
            stringBuilder.append("Status: ").append(getuser.getSocialAccount().getStatus());
            stringBuilder.append("\n\n");
        }

        Toast.makeText(getApplicationContext(),stringBuilder,Toast.LENGTH_LONG).show();

	}

	public void sampleQueryExample(View view) {

		myRealm.executeTransactionAsync(new Realm.Transaction() {
			@Override
			public void execute(Realm realm) {
				User deleteUser = realm.where(User.class)
						.contains("name","Raksa",Case.INSENSITIVE)
						.findFirst();
				deleteUser.deleteFromRealm();
			}
		});



	}

	@Override
	protected void onStop() {
		super.onStop();

        if (realmAsyncTask!=null&&!realmAsyncTask.isCancelled()){
            realmAsyncTask.cancel();
        }

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
        myRealm.close();
	}


}
