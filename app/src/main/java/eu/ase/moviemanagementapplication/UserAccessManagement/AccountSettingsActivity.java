package eu.ase.moviemanagementapplication.UserAccessManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import eu.ase.moviemanagementapplication.Data.FavouriteDBHelper;
import eu.ase.moviemanagementapplication.R;
import eu.ase.moviemanagementapplication.UserAccessManagement.DataUAM.UserDBHelper;

public class AccountSettingsActivity extends AppCompatActivity {

    Button changePassword, deleteAcc;
    UserDBHelper udb;
    FavouriteDBHelper fdb;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        changePassword = findViewById(R.id.btn_change_password);
        deleteAcc = findViewById(R.id.delete_account);


        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
                startActivity(intent);
            }
        });

        deleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                username = preferences.getString("Name", "");
                udb = new UserDBHelper(AccountSettingsActivity.this);
                fdb = new FavouriteDBHelper(AccountSettingsActivity.this);
                udb.deleteUser(username);
                fdb.deleteMovies(username);
                Toast.makeText(getApplicationContext(),username+getString(R.string.was_deleted),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
