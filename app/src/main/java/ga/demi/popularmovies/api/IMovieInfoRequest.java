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

    @GET("{id}/videos/")
    Call<VideosMovieModel> getMoviesVideos(@Query("api_key") String key, @Path("id") String idMovie);

    @GET("{id}/reviews/")
    Call<ReviewsMovieModel> getMoviesReviews(@Query("api_key") String key, @Path("id") String idMovie);
}