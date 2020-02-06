package eu.ase.moviemanagementapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import eu.ase.moviemanagementapplication.R;


public class Movie implements Parcelable {

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    String baseImageURL = "https://image.tmdb.org/t/p/w500";
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("vote_average")
    private Double averageVote;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("id")
    private Integer id;
    @SerializedName("overview")
    private String movieOverview;
    @SerializedName("release_date")
    private String releaseDate;

    public Movie(String baseImageURL, String posterPath, Double averageVote, String backdropPath, String originalTitle, Integer id, String movieOverview, String releaseDate) {
        this.baseImageURL = baseImageURL;
        this.posterPath = posterPath;
        this.averageVote = averageVote;
        this.backdropPath = backdropPath;
        this.originalTitle = originalTitle;
        this.id = id;
        this.movieOverview = movieOverview;
        this.releaseDate = releaseDate;
    }


    protected Movie(Parcel in) {
        id = in.readInt();
        averageVote = in.readDouble();
        originalTitle = in.readString();
        backdropPath = in.readString();
        movieOverview = in.readString();
        releaseDate = in.readString();
        posterPath = in.readString();
    }

    public Movie() {

    }

    public String getPosterPath() {
        return "https://image.tmdb.org/t/p/w500" + posterPath;
    }


    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Double getAverageVote() {
        return averageVote;
    }

    public void setAverageVote(Double averageVode) {
        this.averageVote = averageVode;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }


    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(id);

        parcel.writeString(originalTitle);
        parcel.writeString(backdropPath);

        parcel.writeString(releaseDate);
        parcel.writeString(posterPath);
    }


}
