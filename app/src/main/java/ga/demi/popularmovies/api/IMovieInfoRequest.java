package ga.demi.popularmovies.api;

import ga.demi.popularmovies.models.PopularMovieModel;
import ga.demi.popularmovies.models.ReviewsMovieModel;
import ga.demi.popularmovies.models.VideosMovieModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IMovieInfoRequest {
    @GET("popular/")
    Call<PopularMovieModel> getMoviesPostersPopular(@Query("api_key") String key);

    @GET("top_rated/")
    Call<PopularMovieModel> getMoviesPostersTopRated(@Query("api_key") String key);

    @GET("{id}/videos")
    Call<VideosMovieModel> getMoviesVideos(@Path("id") String idMovie, @Query("api_key") String key);

    @GET("{id}/reviews")
    Call<ReviewsMovieModel> getMoviesReviews(@Path("id") String idMovie, @Query("api_key") String key);
}