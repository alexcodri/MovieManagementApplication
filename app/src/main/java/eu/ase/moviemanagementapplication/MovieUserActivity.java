package eu.ase.moviemanagementapplication;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import eu.ase.moviemanagementapplication.Data.FavouriteDBHelper;
import eu.ase.moviemanagementapplication.Menu.SettingsActivity;

public class MovieUserActivity extends AppCompatActivity {

    private ListView movieList;
    FavouriteDBHelper favouriteDBHelper;
    private String username;
    ArrayList<String> arrayList;
    ArrayAdapter adapter;
    TextView tv_username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_user);
        tv_username = findViewById(R.id.tv_username);
        movieList = findViewById(R.id.movieList);
        favouriteDBHelper = new FavouriteDBHelper(MovieUserActivity.this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        username = preferences.getString("Name", "");
        tv_username.setText(username.toUpperCase()+ getString(R.string.s_list));
        arrayList = new ArrayList<>();
        viewDataInList();

    }


    public void viewDataInList(){

        arrayList = favouriteDBHelper.viewData(username.toLowerCase());
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        movieList.setAdapter(adapter);


    }



}
