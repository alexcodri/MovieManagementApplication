package eu.ase.moviemanagementapplication.Menu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RatingBar;
import androidx.appcompat.app.AppCompatActivity;

import eu.ase.moviemanagementapplication.R;

public class RateAppActivity extends AppCompatActivity {

    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_app);
        ratingBar = findViewById(R.id.about_rb_feedback);
        ratingBar.setRating(load());
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                save(rating);

            }
        });

    }

    public void save(float f) {
        SharedPreferences sharedPreferences = getSharedPreferences("folder", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("rating", f);
        editor.commit();
    }

    public float load() {
        SharedPreferences sharedPreferences = getSharedPreferences("folder", MODE_PRIVATE);
        float f = sharedPreferences.getFloat("rating", 0f);
        return f;
    }


}
