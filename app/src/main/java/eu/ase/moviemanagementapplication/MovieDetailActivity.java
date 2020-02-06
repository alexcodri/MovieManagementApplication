package eu.ase.moviemanagementapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import java.util.ArrayList;
import butterknife.ButterKnife;
import eu.ase.moviemanagementapplication.Data.FavouriteDBHelper;
import eu.ase.moviemanagementapplication.Model.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    TextView nameofMovie, plotSyno, userRat, releaseDate;
    int movie_id;
    ImageView imageView;
    String username;
    private Movie mMovie;
    private ArrayList<Movie> movieArrayList;
    private FavouriteDBHelper favouriteDBHelper;
    private Movie favMovie;
    private final AppCompatActivity appCompatActivity = MovieDetailActivity.this;

    private String thumbnail, movieName,synopsis,dateOfRelease, rating;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        initCollapsingToolbar();
        imageView = findViewById(R.id.thumbnail_img_header);
        nameofMovie = findViewById(R.id.movieTitle);
        plotSyno = findViewById(R.id.plotsynopsis);
        userRat = findViewById(R.id.rating);
        releaseDate = findViewById(R.id.releaseDate);

        movie_id = getIntent().getExtras().getInt("id");
        thumbnail = getIntent().getExtras().getString("poster_path");
        movieName = getIntent().getExtras().getString("original_title");
        synopsis = getIntent().getExtras().getString("overview");
        rating = getIntent().getExtras().getString("vote_average");
        dateOfRelease = getIntent().getExtras().getString("release_date");


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        username = preferences.getString("Name", "");

        Glide.with(this).load(thumbnail)
                .placeholder(R.drawable.loading)
                .into(imageView);

        nameofMovie.setText(movieName);
        plotSyno.setText(synopsis);
        userRat.setText(rating);
        releaseDate.setText(dateOfRelease);

        initFavoriteButton();

    }


    private void initFavoriteButton() {
        MaterialFavoriteButton materialFavoriteButton = findViewById(R.id.favoriteButtonContent);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int movie_id = getIntent().getExtras().getInt("id");
        favouriteDBHelper = new FavouriteDBHelper(MovieDetailActivity.this);

        materialFavoriteButton.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        if (favorite) {
                            SharedPreferences.Editor editor = getSharedPreferences("MovieDetailActivity", MODE_PRIVATE).edit();
                            if (!favouriteDBHelper.checkIfFavourite(username, movie_id)) {
                                editor.putBoolean("Favorite Added", true);
                                materialFavoriteButton.setAnimateFavorite(true);
                                editor.commit();
                                Toast.makeText(getApplicationContext(), R.string.add_to_favourites, Toast.LENGTH_SHORT).show();
                                saveFavorite(username);
                            } else {
                                {   favouriteDBHelper.deleteFavorites(username,movie_id);
                                    editor = getSharedPreferences("MovieDetailActivity", MODE_PRIVATE).edit();
                                    editor.putBoolean("Favorite Removed", true);
                                    materialFavoriteButton.setAnimateUnfavorite(true);
                                    editor.commit();
                                    Toast.makeText(getApplicationContext(), R.string.removed_from_fav, Toast.LENGTH_SHORT).show();
                                }

                            }

                        }

                    }
                }
        );
    }



    public void saveFavorite(String username){
        favouriteDBHelper = new FavouriteDBHelper(appCompatActivity);
        favMovie = new Movie();
        favMovie.setId(movie_id);
        favMovie.setOriginalTitle(String.format("%s",nameofMovie.getText()));
        favMovie.setPosterPath(thumbnail);
        favMovie.setAverageVote(Double.parseDouble(rating));
        favMovie.setMovieOverview(synopsis);
        favouriteDBHelper.addFavorites(favMovie,username);
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbar_layout);

        collapsingToolbarLayout.setTitle(" ");
        AppBarLayout appBarLayout = findViewById(R.id.appBarLayout);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
            boolean isShown = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + i == 0) {
                    collapsingToolbarLayout.setTitle(String.format("%s",R.string.movie_Details_toolb));
                    isShown = true;
                } else if (isShown) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShown = false;
                }


            }
        });

    }


}

