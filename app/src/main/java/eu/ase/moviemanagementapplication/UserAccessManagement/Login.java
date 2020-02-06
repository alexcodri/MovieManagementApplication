package eu.ase.moviemanagementapplication.UserAccessManagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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

import eu.ase.moviemanagementapplication.MovieDetailActivity;
import eu.ase.moviemanagementapplication.MoviesActivity;
import eu.ase.moviemanagementapplication.R;
import eu.ase.moviemanagementapplication.UserAccessManagement.DataUAM.UserDBHelper;

import static eu.ase.moviemanagementapplication.UserAccessManagement.MainActivity.REQUEST_FOR_LOGIN;

public class Login extends AppCompatActivity {

    Button btLogin;
    EditText editTextLogin, editTextPassword;
    String username,password;
    UserDBHelper userDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btLogin = findViewById(R.id.login_btn_login);
        editTextLogin = findViewById(R.id.login_et_username);
        editTextPassword = findViewById(R.id.login_et_password);
    userDBHelper = new UserDBHelper(this);


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editTextLogin.getText().toString();
                password = editTextPassword.getText().toString();
                boolean checkUser = userDBHelper.checkUser(username,password);
                if(checkUser){
                    Toast.makeText(Login.this, getString(R.string.welcome) + editTextLogin.getText().toString().toUpperCase(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MoviesActivity.class);
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Name",username.toLowerCase());
                    editor.apply();
                    startActivityForResult(intent, REQUEST_FOR_LOGIN);

                }else{
                    Toast.makeText(getApplicationContext(), R.string.incorrect_acc_pass,Toast.LENGTH_SHORT).show();
                }
                   }
        });

    }

}
