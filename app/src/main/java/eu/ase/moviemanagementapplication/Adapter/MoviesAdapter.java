package eu.ase.moviemanagementapplication.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import eu.ase.moviemanagementapplication.Model.Movie;
import eu.ase.moviemanagementapplication.MovieDetailActivity;
import eu.ase.moviemanagementapplication.R;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyMovieViewHolder> {

    private final ArrayList<Movie> movieArrayList;
    private Context mContext;

    public MoviesAdapter(ArrayList<Movie> movieArrayList, Context mContext) {
        this.movieArrayList = movieArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MoviesAdapter.MyMovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.movie_card, viewGroup, false);

        MyMovieViewHolder movieViewHolder = new MyMovieViewHolder(view);
        return movieViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final MoviesAdapter.MyMovieViewHolder holder, int position) {


        final Movie movie = movieArrayList.get(position);
        holder.mMovie = movie;
        holder.mMovieTitle.setText(movie.getOriginalTitle());
        String posterURL = movie.getPosterPath();
        if (posterURL == null) {
            holder.mMovieTitle.setVisibility(View.INVISIBLE);
        }
        holder.mMovieTitle.setText(movieArrayList.get(position).getOriginalTitle());
        holder.mRating.setText(Double.toString(movieArrayList.get(position).getAverageVote()));

        Picasso.get()
                .load(movie.getPosterPath()).placeholder(R.drawable.loading).into(holder.mMovieThumbnail,
                new Callback() {
                    @Override
                    public void onSuccess() {
                        if (holder.mMovie.getId() != movie.getId()) {
                            holder.cleanUp();
                        } else {
                            holder.mMovieThumbnail.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.mMovieThumbnail.setVisibility(View.INVISIBLE);
                    }
                });

    }


    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    public void add(List<Movie> movies) {
        movieArrayList.clear();
        movieArrayList.addAll(movies);
        notifyDataSetChanged();
    }

    public class MyMovieViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public Movie mMovie;


        @BindView(R.id.movie_thumbnail)
        ImageView mMovieThumbnail;
        @BindView(R.id.movieTitle)
        TextView mMovieTitle;
        @BindView(R.id.rating)
        TextView mRating;


        public MyMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mView = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Intent intent = new Intent(mContext, MovieDetailActivity.class);
                    intent.putExtra("id",movieArrayList.get(pos).getId());
                    intent.putExtra("poster_path", movieArrayList.get(pos).getPosterPath());
                    intent.putExtra("original_title", movieArrayList.get(pos).getOriginalTitle());
                    intent.putExtra("overview", movieArrayList.get(pos).getMovieOverview());
                    intent.putExtra("release_date", movieArrayList.get(pos).getReleaseDate());
                    intent.putExtra("vote_average", Double.toString(movieArrayList.get(pos).getAverageVote()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });
        }


        public void cleanUp() {
            Picasso.get().cancelRequest(mMovieThumbnail);
            mMovieThumbnail.setVisibility(View.INVISIBLE);
            mMovieTitle.setVisibility(View.GONE);
        }
    }
}
