package eu.ase.moviemanagementapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import eu.ase.moviemanagementapplication.Adapter.MoviesAdapter;
import eu.ase.moviemanagementapplication.Data.FavouriteDBHelper;
import eu.ase.moviemanagementapplication.Menu.AboutActivity;
import eu.ase.moviemanagementapplication.Menu.RateAppActivity;
import eu.ase.moviemanagementapplication.Menu.SettingsActivity;
import eu.ase.moviemanagementapplication.Model.Movie;
import eu.ase.moviemanagementapplication.Network.NetworkUtils;

public class MoviesActivity extends AppCompatActivity {

    private static final String TAG_LOG = MoviesActivity.class.getName();
    ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private String moviesURL;

    private ArrayList<Movie> alMovieList;

    private MoviesAdapter moviesAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.fetching_movies));
        progressDialog.setCancelable(false);
        progressDialog.show();

        recyclerView = findViewById(R.id.recycler_view);
        moviesAdapter = new MoviesAdapter(new ArrayList<>(), this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(moviesAdapter);
        moviesAdapter.notifyDataSetChanged();

        if (NetworkUtils.networkStatus(MoviesActivity.this)) {
            new GetMoviesAsync().execute();
        }


        swipeRefreshLayout = findViewById(R.id.main_movie_content);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_orange_dark));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                Toast.makeText(MoviesActivity.this, R.string.movie_refreshed, Toast.LENGTH_LONG).show();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent3 = new Intent(this,SettingsActivity.class);
                this.startActivity(intent3);
                return true;
            case R.id.menu_rate:
                Intent intent = new Intent(this, RateAppActivity.class);
                this.startActivity(intent);
                return true;
            case R.id.menu_about:
                Intent intent1 = new Intent(this, AboutActivity.class);
                this.startActivity(intent1);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class GetMoviesAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
            progressDialog.setMessage(getString(R.string.getting_data));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            progressDialog.setMessage(getString(R.string.received_data));
            progressDialog.show();
            progressDialog.setCancelable(true);
            progressDialog.dismiss();
            moviesAdapter.add(alMovieList);
            recyclerView.setAdapter(moviesAdapter);


        }

        @Override
        protected Void doInBackground(Void... voids) {

            String moviesURL = BuildConfig.THE_MOVIE_DB_API_TOKEN;


            alMovieList = new ArrayList<>();

            if (NetworkUtils.networkStatus(MoviesActivity.this)) {
                alMovieList = (ArrayList<Movie>) NetworkUtils.fetchData(moviesURL);
            } else {
                Toast.makeText(MoviesActivity.this, R.string.internet_access_err, Toast.LENGTH_LONG).show();
            }
            return null;
        }
    }
}
