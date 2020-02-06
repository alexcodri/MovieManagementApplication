package eu.ase.moviemanagementapplication.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import eu.ase.moviemanagementapplication.Data.FavouriteDBHelper;
import eu.ase.moviemanagementapplication.MovieUserActivity;
import eu.ase.moviemanagementapplication.R;
import eu.ase.moviemanagementapplication.UserAccessManagement.AccountSettingsActivity;
import eu.ase.moviemanagementapplication.UserAccessManagement.FilterActivity;
import eu.ase.moviemanagementapplication.UserAccessManagement.GmapsActivity;
import eu.ase.moviemanagementapplication.UserAccessManagement.GraphActivity;
import eu.ase.moviemanagementapplication.UserAccessManagement.LiveUsersActivity;
import eu.ase.moviemanagementapplication.UserAccessManagement.MainActivity;

public class SettingsActivity extends AppCompatActivity {

    Button settings, showFavourites,disconnect,filter,liveUsers,graph,maps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settings = findViewById(R.id.account_settings);
        showFavourites = findViewById(R.id.show_favourites);
        disconnect = findViewById(R.id.disconnect);
         filter = findViewById(R.id.query);
         liveUsers = findViewById(R.id.show_data);
         graph = findViewById(R.id.graph);
         maps = findViewById(R.id.maps);

         maps.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getApplicationContext(), GmapsActivity.class);
                 startActivity(intent);
             }
         });


         graph.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
                 startActivity(intent);
             }
         });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AccountSettingsActivity.class);

                startActivity(intent);
            }
        });

        showFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieUserActivity.class);
                startActivity(intent);
            }
        });

        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FilterActivity.class);
                startActivity(intent);
            }
        });

        liveUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LiveUsersActivity.class);
                startActivity(intent);
            }
        });

    }


}
