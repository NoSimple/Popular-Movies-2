package ga.demi.popularmovies.api;

import ga.demi.popularmovies.Constants;
import ga.demi.popularmovies.models.PopularMovieModel;
import ga.demi.popularmovies.models.ReviewsMovieModel;
import ga.demi.popularmovies.models.VideosMovieModel;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RequestToApiMovieDB {

    private static RequestToApiMovieDB mInstanceRequestToApi;
    private Retrofit mRetrofit;

    public static RequestToApiMovieDB getInstanceRequestToApi() {
        if (mInstanceRequestToApi == null) {
            mInstanceRequestToApi = new RequestToApiMovieDB();
        }
        return mInstanceRequestToApi;
    }

    private RequestToApiMovieDB() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Call<PopularMovieModel> getMoviePostersPopularRequest() {
        return mRetrofit.create(IMovieInfoRequest.class).getMoviesPostersPopular(Constants.API_KEY);
    }

    public Call<PopularMovieModel> getMoviePostersTopRatedRequest() {
        return mRetrofit.create(IMovieInfoRequest.class).getMoviesPostersTopRated(Constants.API_KEY);
    }

    public Call<VideosMovieModel> getMovieVideosRequest(String idMovie) {
        return mRetrofit.create(IMovieInfoRequest.class).getMoviesVideos(idMovie, Constants.API_KEY);
    }

    public Call<ReviewsMovieModel> getMovieReviewsRequest(String idMovie) {
        return mRetrofit.create(IMovieInfoRequest.class).getMoviesReviews(idMovie, Constants.API_KEY);
    }
}