package ga.demi.popularmovies.api;

import ga.demi.popularmovies.models.PopularMovieModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IMoviePostersRequest {
    @GET("popular/")
    Call<PopularMovieModel> getMoviesPostersPopular(@Query("api_key") String key);

    @GET("top_rated/")
    Call<PopularMovieModel> getMoviesPostersTopRated(@Query("api_key") String key);
}