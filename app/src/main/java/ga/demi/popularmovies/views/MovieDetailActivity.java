package ga.demi.popularmovies.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ga.demi.popularmovies.App;
import ga.demi.popularmovies.Constants;
import ga.demi.popularmovies.R;
import ga.demi.popularmovies.adapters.MovieReviewsAdapter;
import ga.demi.popularmovies.adapters.MovieVideosAdapter;
import ga.demi.popularmovies.api.RequestToApiMovieDB;
import ga.demi.popularmovies.data.dao.FavoriteMovieDao;
import ga.demi.popularmovies.data.models.FavoriteMovie;
import ga.demi.popularmovies.models.Result;
import ga.demi.popularmovies.models.ReviewsMovieModel;
import ga.demi.popularmovies.models.VideosMovieModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class MovieDetailActivity extends AppCompatActivity implements MovieVideosAdapter.ListVideosItemClickListener, MovieReviewsAdapter.ListReviewsItemClickListener {

    private RequestToApiMovieDB mRequestToApiMovieDB;
    private Result mMoviePoster;

    private FavoriteMovieDao mMovieDatabase;
    private FavoriteMovie mFavoriteMovie;
    private boolean mIsFavoriteMovie;

    private ImageView mMoviePosterIV;
    private TextView mMovieTitleTV;
    private TextView mMovieDateReleaseTV;
    private TextView mMovieAverageTV;
    private TextView mMovieOverviewTV;

    private MovieVideosAdapter mVideosAdapter;
    private RecyclerView mVideosRV;
    private ProgressBar mVideosPB;
    private TextView mErrorVideosApiTV;

    private MovieReviewsAdapter mReviewsAdapter;
    private RecyclerView mReviewsRV;
    private ProgressBar mReviewsPB;
    private TextView mErrorReviewsApiTV;

    private Button mMarkAsFavoriteB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mRequestToApiMovieDB = RequestToApiMovieDB.getInstanceRequestToApi();

        mMovieDatabase = App.getInstanceApp().getMovieDatabase().getFavoriteMovieDao();

        getSupportActionBar().setTitle("Movie detail");

        mMoviePosterIV = findViewById(R.id.iv_movie_poster);
        mMovieTitleTV = findViewById(R.id.tv_movie_title);
        mMovieDateReleaseTV = findViewById(R.id.tv_movie_date_release);
        mMovieAverageTV = findViewById(R.id.tv_movie_average);
        mMovieOverviewTV = findViewById(R.id.tv_movie_overview);

        mVideosRV = findViewById(R.id.rv_trailers);
        mVideosPB = findViewById(R.id.pb_movie_videos);
        mErrorVideosApiTV = findViewById(R.id.tv_error_videos_api_text);

        mReviewsRV = findViewById(R.id.rv_reviews);
        mReviewsPB = findViewById(R.id.pb_movie_reviews);
        mErrorReviewsApiTV = findViewById(R.id.tv_error_reviews_api_text);

        mMarkAsFavoriteB = findViewById(R.id.b_mark_as_favorite);

        Bundle movieBundle = getIntent().getExtras();
        if (movieBundle != null) {
            mMoviePoster = movieBundle.getParcelable(Result.class.getSimpleName());

            if (mMoviePoster != null) {
                String posterUrl = Constants.BASE_POSTER_URL + mMoviePoster.getPosterPath();
                Picasso.get().load(posterUrl).into(mMoviePosterIV);

                mMovieDatabase.getFavoriteMovieById(mMoviePoster.getId()).observe(this, favoriteMovies -> {
                    mIsFavoriteMovie = favoriteMovies != null;
                    setAsFavoriteButton(mIsFavoriteMovie);
                });

                mFavoriteMovie = new FavoriteMovie();
                mFavoriteMovie.id = mMoviePoster.getId();
                mFavoriteMovie.title = mMoviePoster.getTitle();
                mFavoriteMovie.posterPath = mMoviePoster.getPosterPath();
                mFavoriteMovie.releaseDate = mMoviePoster.getReleaseDate();
                mFavoriteMovie.voteAverage = mMoviePoster.getVoteAverage();
                mFavoriteMovie.overview = mMoviePoster.getOverview();

                mMovieTitleTV.setText(mMoviePoster.getTitle());
                mMovieDateReleaseTV.setText(mMoviePoster.getReleaseDate());
                mMovieAverageTV.setText(String.valueOf(mMoviePoster.getVoteAverage()));
                mMovieOverviewTV.setText(mMoviePoster.getOverview());
            }

            LinearLayoutManager layoutManagerForVideo = new LinearLayoutManager(this);
            mVideosRV.setLayoutManager(layoutManagerForVideo);
            mVideosRV.setHasFixedSize(true);

            LinearLayoutManager layoutManagerForReview = new LinearLayoutManager(this);
            mReviewsRV.setLayoutManager(layoutManagerForReview);
            mReviewsRV.setHasFixedSize(true);

            getMoviesVideosRequestApi(mMoviePoster.getId().toString());
            getMoviesReviewsRequestApi(mMoviePoster.getId().toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        mMarkAsFavoriteB.setOnClickListener((view) -> {
            if (mIsFavoriteMovie) {
                executorService.execute(() -> mMovieDatabase.deleteFavoriteMovie(mFavoriteMovie));
            } else {
                executorService.execute(() -> mMovieDatabase.insertFavoriteMovie(mFavoriteMovie));
            }
        });
    }

    private void showProgressBar(ProgressBar view, boolean show) {
        if (show) view.setVisibility(View.VISIBLE);
        else view.setVisibility(View.GONE);
    }

    private void showRecyclerView(RecyclerView view, boolean show) {
        if (show) view.setVisibility(View.VISIBLE);
        else view.setVisibility(View.GONE);
    }

    private void showErrorMessage(TextView view, boolean show) {
        if (show) view.setVisibility(View.VISIBLE);
        else view.setVisibility(View.GONE);
    }

    private void setVideosRecyclerView(List<VideosMovieModel.ResultVideos> movieVideosList) {
        mVideosAdapter = new MovieVideosAdapter(movieVideosList, this);
        mVideosRV.setAdapter(mVideosAdapter);
    }

    private void setReviewsRecyclerView(List<ReviewsMovieModel.ResultReviews> movieReviewsList) {
        mReviewsAdapter = new MovieReviewsAdapter(movieReviewsList, this);
        mReviewsRV.setAdapter(mReviewsAdapter);
    }

    @Override
    public void onListReviewsItemClick(String clickedItemUrl) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(clickedItemUrl));
        try {
            startActivity(webIntent);
        } catch (ActivityNotFoundException ex) {
            Log.e("MovieDetailActivity", ex.getMessage());
            Toast.makeText(getBaseContext(), getResources().getText(R.string.app_not_found_text), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onListVideosItemClick(String clickedItemId) {
        Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.BASE_VIDEO_URL + clickedItemId));
        try {
            startActivity(youtubeIntent);
        } catch (ActivityNotFoundException ex) {
            Log.e("MovieDetailActivity", ex.getMessage());
            Toast.makeText(getBaseContext(), getResources().getText(R.string.app_not_found_text), Toast.LENGTH_LONG).show();
        }
    }

    private void getMoviesVideosRequestApi(String idMovie) {
        showProgressBar(mVideosPB, true);
        showErrorMessage(mErrorVideosApiTV, false);
        mRequestToApiMovieDB.getMovieVideosRequest(idMovie)
                .enqueue(new Callback<VideosMovieModel>() {
                    @Override
                    public void onResponse(Call<VideosMovieModel> call, Response<VideosMovieModel> response) {
                        if (response.body() != null) {
                            setVideosRecyclerView(response.body().getResults());
                            showRecyclerView(mVideosRV, true);
                        } else {
                            showRecyclerView(mVideosRV, false);
                            showErrorMessage(mErrorVideosApiTV, true);
                        }
                        showProgressBar(mVideosPB, false);
                    }

                    @Override
                    public void onFailure(Call<VideosMovieModel> call, Throwable t) {
                        Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        showRecyclerView(mVideosRV, false);
                        showErrorMessage(mErrorVideosApiTV, true);
                        showProgressBar(mVideosPB, false);
                    }
                });
    }

    private void getMoviesReviewsRequestApi(String idMovie) {
        showProgressBar(mReviewsPB, true);
        showErrorMessage(mErrorReviewsApiTV, false);
        mRequestToApiMovieDB.getMovieReviewsRequest(idMovie)
                .enqueue(new Callback<ReviewsMovieModel>() {
                    @Override
                    public void onResponse(Call<ReviewsMovieModel> call, Response<ReviewsMovieModel> response) {
                        if (response.body() != null) {
                            setReviewsRecyclerView(response.body().getResults());
                            showRecyclerView(mReviewsRV, true);
                        } else {
                            showRecyclerView(mReviewsRV, false);
                            showErrorMessage(mErrorReviewsApiTV, true);
                        }
                        showProgressBar(mReviewsPB, false);
                    }

                    @Override
                    public void onFailure(Call<ReviewsMovieModel> call, Throwable t) {
                        Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        showRecyclerView(mReviewsRV, false);
                        showErrorMessage(mErrorReviewsApiTV, true);
                        showProgressBar(mReviewsPB, false);
                    }
                });
    }

    private void setAsFavoriteButton(boolean isFavorite) {
        if (isFavorite) {
            mMarkAsFavoriteB.setText(getResources().getText(R.string.favorite_text));
        } else {
            mMarkAsFavoriteB.setText(getResources().getText(R.string.mark_as_favorite_text));
        }
    }
}