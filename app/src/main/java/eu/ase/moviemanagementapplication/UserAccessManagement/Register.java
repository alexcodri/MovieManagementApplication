package eu.ase.moviemanagementapplication.UserAccessManagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import eu.ase.moviemanagementapplication.Model.UserInfo;
import eu.ase.moviemanagementapplication.MovieDetailActivity;
import eu.ase.moviemanagementapplication.MoviesActivity;
import eu.ase.moviemanagementapplication.R;
import eu.ase.moviemanagementapplication.UserAccessManagement.DataUAM.UserDBHelper;

import static eu.ase.moviemanagementapplication.UserAccessManagement.MainActivity.REQUEST_FOR_REGISTER;

public class Register extends AppCompatActivity {

    EditText et_register_username;
    EditText et_register_password;
    Button btnRegister;
   private UserDBHelper db;
    String username, password;
    private UserInfo user;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnRegister = findViewById(R.id.register_btn_register);
        et_register_username = findViewById(R.id.register_tv_username);
        et_register_password = findViewById(R.id.register_tv_password);
        db = new UserDBHelper(this);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MoviesActivity.class);
                username = et_register_username.getText().toString().toLowerCase().trim();
                password = et_register_password.getText().toString().trim();
                Boolean verifyUser = db.verifyUser(username);
                    if(verifyUserPassword(username,password)){
                if(!verifyUser){
                    saveUser(username,password);
                    Toast.makeText(getApplicationContext(), R.string.acc_succ_created,Toast.LENGTH_SHORT).show();
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Name",username.toLowerCase());
                    editor.apply();
                    startActivityForResult(intent, REQUEST_FOR_REGISTER);
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
                    databaseReference.push().setValue(username);

                }
                else
                {
                    Toast.makeText(getApplicationContext(), R.string.account_already_exists,Toast.LENGTH_SHORT).show();
                }
                    }else
                {
                    Toast.makeText(getApplicationContext(), R.string.pw_too_short,Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public boolean verifyUserPassword(String username,String password){
        if(password.length() < 3 || username.length()<3 )
            return false;
        else
            return true;
    }

    public void saveUser(String username, String password){
        db = new UserDBHelper(Register.this);
        user = new UserInfo();
        user.setUsername(username);
        user.setPassword(password);
        db.addUsers(user);
    }



}
