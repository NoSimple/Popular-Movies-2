package ga.demi.popularmovies.api;

import ga.demi.popularmovies.models.PopularMovieModel;
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
        return mRetrofit.create(IMoviePostersRequest.class).getMoviesPostersPopular(API_KEY);
    }

    public Call<PopularMovieModel> getMoviePostersTopRatedRequest() {
        return mRetrofit.create(IMoviePostersRequest.class).getMoviesPostersTopRated(API_KEY);
    }
}