package ga.demi.popularmovies.api;

import ga.demi.popularmovies.models.PopularMovieModel;
import ga.demi.popularmovies.models.ReviewsMovieModel;
import ga.demi.popularmovies.models.VideosMovieModel;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RequestToApiMovieDB {

    private final String API_KEY = "";
    private final String BASE_URL = "https://api.themoviedb.org/3/movie/";

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
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Call<PopularMovieModel> getMoviePostersPopularRequest() {
        return mRetrofit.create(IMovieInfoRequest.class).getMoviesPostersPopular(API_KEY);
    }

    public Call<PopularMovieModel> getMoviePostersTopRatedRequest() {
        return mRetrofit.create(IMovieInfoRequest.class).getMoviesPostersTopRated(API_KEY);
    }

    public Call<VideosMovieModel> getMovieVideosRequest(String idMovie) {
        return mRetrofit.create(IMovieInfoRequest.class).getMoviesVideos(idMovie, API_KEY);
    }

    public Call<ReviewsMovieModel> getMovieReviewsRequest(String idMovie) {
        return mRetrofit.create(IMovieInfoRequest.class).getMoviesReviews(idMovie, API_KEY);
    }
}