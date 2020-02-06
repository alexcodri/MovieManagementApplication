package eu.ase.moviemanagementapplication.UserAccessManagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import eu.ase.moviemanagementapplication.MoviesActivity;
import eu.ase.moviemanagementapplication.R;
import eu.ase.moviemanagementapplication.UserAccessManagement.DataUAM.UserDBHelper;

public class ChangePassword extends AppCompatActivity {


    private String newPass;
    private Button btnChangePw;
    private String username;
    UserDBHelper udb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        EditText et_pw = findViewById(R.id.et_change_password);
        btnChangePw = findViewById(R.id.btn_reset_password);
        udb = new UserDBHelper(ChangePassword.this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        username = preferences.getString("Name", "");
        btnChangePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newPass = et_pw.getText().toString();
                if(newPass.length() > 3) {
                    udb.changePassword(username, newPass);
                    Toast.makeText(getApplicationContext(), R.string.pw_reset, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MoviesActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), R.string.err_pw,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
