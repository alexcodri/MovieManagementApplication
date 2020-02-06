package eu.ase.moviemanagementapplication.UserAccessManagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import eu.ase.moviemanagementapplication.MoviesActivity;
import eu.ase.moviemanagementapplication.R;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_FOR_LOGIN = 200;
    public static final int REQUEST_FOR_REGISTER = 201;

    Button btnLogin;
    Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();

    }

    public void initComponents() {

        btnLogin = findViewById(R.id.button_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivityForResult(intent, REQUEST_FOR_LOGIN);
            }
        });

        btnRegister = findViewById(R.id.button_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);

                startActivityForResult(intent, REQUEST_FOR_REGISTER);
            }
        });


    }
}
