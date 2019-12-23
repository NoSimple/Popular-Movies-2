package ga.demi.popularmovies.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ga.demi.popularmovies.R;
import ga.demi.popularmovies.adapters.MoviePosterAdapter;
import ga.demi.popularmovies.api.RequestToApiMovieDB;
import ga.demi.popularmovies.models.PopularMovieModel;
import ga.demi.popularmovies.models.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class MainActivity extends AppCompatActivity implements MoviePosterAdapter.ListItemClickListener {

    private RecyclerView mMoviePostersRV;
    private ProgressBar mMoviePostersPB;
    private TextView mErrorApiTV;

    private RequestToApiMovieDB mRequestToApiMovieDB;
    private List<Result> mMoviePosterList;

    private MoviePosterAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviePostersRV = findViewById(R.id.rv_movie_posters);
        mMoviePostersPB = findViewById(R.id.pb_movie_posters);
        mErrorApiTV = findViewById(R.id.tv_error_api_text);
        mErrorApiTV.setText(getResources().getText(R.string.error_connection_text));

        mRequestToApiMovieDB = RequestToApiMovieDB.getInstanceRequestToApi();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mMoviePostersRV.setLayoutManager(layoutManager);
        mMoviePostersRV.setHasFixedSize(true);

        getMoviesPopularRequestApi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.choice, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.most_popular_search): {
                getMoviesPopularRequestApi();
                break;
            }
            case (R.id.highest_rated_search): {
                getMoviesTopRatingRequestApi();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(Result.class.getSimpleName(), mMoviePosterList.get(clickedItemIndex));
        startActivity(intent);
    }

    private void showProgressBar(boolean show) {
        if (show) mMoviePostersPB.setVisibility(View.VISIBLE);
        else mMoviePostersPB.setVisibility(View.GONE);
    }

    private void showRecyclerView(boolean show) {
        if (show) mMoviePostersRV.setVisibility(View.VISIBLE);
        else mMoviePostersRV.setVisibility(View.GONE);
    }

    private void showErrorMessage(boolean show) {
        if (show) mErrorApiTV.setVisibility(View.VISIBLE);
        else mErrorApiTV.setVisibility(View.GONE);
    }

    private void setRecyclerView(List<Result> moviePosterList) {
        mAdapter = new MoviePosterAdapter(moviePosterList, this);
        mMoviePostersRV.setAdapter(mAdapter);
    }

    private void onPostExecute(List<Result> moviePosterList) {
        mAdapter.setWeatherData(moviePosterList);
    }

    private void getMoviesPopularRequestApi() {
        showProgressBar(true);
        showErrorMessage(false);
        mRequestToApiMovieDB.getMoviePostersPopularRequest()
                .enqueue(new Callback<PopularMovieModel>() {
                    @Override
                    public void onResponse(Call<PopularMovieModel> call, Response<PopularMovieModel> response) {
                        if (response.body() != null) {
                            mMoviePosterList = response.body().getResults();
                            if (mAdapter == null) {
                                setRecyclerView(mMoviePosterList);
                            } else {
                                onPostExecute(mMoviePosterList);
                            }
                            showRecyclerView(true);
                        } else {
                            showRecyclerView(false);
                            showErrorMessage(true);
                        }
                        showProgressBar(false);
                    }

                    @Override
                    public void onFailure(Call<PopularMovieModel> call, Throwable t) {
                        Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        showRecyclerView(false);
                        showErrorMessage(true);
                        showProgressBar(false);
                    }
                });
    }

    private void getMoviesTopRatingRequestApi() {
        showProgressBar(true);
        showErrorMessage(false);
        mRequestToApiMovieDB.getMoviePostersTopRatedRequest()
                .enqueue(new Callback<PopularMovieModel>() {
                    @Override
                    public void onResponse(Call<PopularMovieModel> call, Response<PopularMovieModel> response) {
                        if (response.body() != null) {
                            mMoviePosterList = response.body().getResults();
                            setRecyclerView(mMoviePosterList);
                            onPostExecute(mMoviePosterList);
                            showRecyclerView(true);
                        } else {
                            showRecyclerView(false);
                            showErrorMessage(true);
                        }
                        showProgressBar(false);
                    }

                    @Override
                    public void onFailure(Call<PopularMovieModel> call, Throwable t) {
                        Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        showRecyclerView(false);
                        showErrorMessage(true);
                        showProgressBar(false);
                    }
                });
    }
}