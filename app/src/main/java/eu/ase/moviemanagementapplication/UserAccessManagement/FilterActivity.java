package eu.ase.moviemanagementapplication.UserAccessManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import eu.ase.moviemanagementapplication.Data.FavouriteDBHelper;
import eu.ase.moviemanagementapplication.MovieUserActivity;
import eu.ase.moviemanagementapplication.R;

public class FilterActivity extends AppCompatActivity {

    private ListView movieList;
    FavouriteDBHelper favouriteDBHelper;
    private String username;
    ArrayList<String> arrayList;
    ArrayAdapter adapter;
    EditText editText;
    Button button;
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        editText = findViewById(R.id.et_query);
        movieList = findViewById(R.id.queriedList);
        arrayList = new ArrayList<>();
        button = findViewById(R.id.btn_search);
        favouriteDBHelper = new FavouriteDBHelper(FilterActivity.this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        username = preferences.getString("Name", "");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = editText.getText().toString();
                arrayList = favouriteDBHelper.queryData(username,value);
                adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,arrayList);
                movieList.setAdapter(adapter);
            }
        });
    }
}
