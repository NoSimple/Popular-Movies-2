package ga.demi.popularmovies.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ga.demi.popularmovies.R;
import ga.demi.popularmovies.models.Result;

public final class MovieDetailActivity extends AppCompatActivity {

    private Result mMoviePoster;

    private ImageView mMoviePosterIV;
    private TextView mMovieTitleTV;
    private TextView mMovieDateReleaseTV;
    private TextView mMovieTimeTV;
    private TextView mMovieAverageTV;
    private TextView mMovieOverviewTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        getSupportActionBar().setTitle("Movie detail");

        mMoviePosterIV = findViewById(R.id.iv_movie_poster);
        mMovieTitleTV = findViewById(R.id.tv_movie_title);
        mMovieDateReleaseTV = findViewById(R.id.tv_movie_date_release);
        mMovieTimeTV = findViewById(R.id.tv_movie_time);
        mMovieAverageTV = findViewById(R.id.tv_movie_average);
        mMovieOverviewTV = findViewById(R.id.tv_movie_overview);

        Bundle movieBundle = getIntent().getExtras();
        if (movieBundle != null) {
            mMoviePoster = movieBundle.getParcelable(Result.class.getSimpleName());

            String posterUrl = "https://image.tmdb.org/t/p/w185/" + mMoviePoster.getPosterPath();
            Picasso.get().load(posterUrl).into(mMoviePosterIV);

            mMovieTitleTV.setText(mMoviePoster.getTitle());
            mMovieDateReleaseTV.setText(mMoviePoster.getReleaseDate());
            mMovieAverageTV.setText(String.valueOf(mMoviePoster.getVoteAverage()));
            mMovieOverviewTV.setText(mMoviePoster.getOverview());
        }
    }
}