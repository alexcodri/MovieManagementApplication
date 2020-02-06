package eu.ase.moviemanagementapplication.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import eu.ase.moviemanagementapplication.Model.Movie;

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static List<Movie> fetchData(String Url) {
        ArrayList<Movie> movies = new ArrayList<>();

        try {
            URL url = new URL(Url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            String results = IOUtils.toString(inputStream);
            parseJson(results, movies);
            inputStream.close();
            urlConnection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return movies;
    }

    public static void parseJson(String data, ArrayList<Movie> list) {
        try {
            JSONObject mainJsonObject = new JSONObject(data);
            Log.v(TAG, mainJsonObject.toString());
            JSONArray array = mainJsonObject.getJSONArray("results");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                Movie movie = new Movie();
                movie.setId(jsonObject.getInt("id"));
                movie.setAverageVote(jsonObject.getDouble("vote_average"));
                movie.setOriginalTitle(jsonObject.getString("original_title"));
                movie.setBackdropPath(jsonObject.getString("backdrop_path"));
                movie.setMovieOverview(jsonObject.getString("overview"));
                movie.setReleaseDate(jsonObject.getString("release_date"));
                movie.setPosterPath(jsonObject.getString("poster_path"));
                list.add(movie);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static Boolean networkStatus(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        if (info != null && info.isConnected()) {
            return true;
        }
        return false;
    }

}
