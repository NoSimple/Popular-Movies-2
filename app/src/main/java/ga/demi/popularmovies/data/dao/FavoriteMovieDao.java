package ga.demi.popularmovies.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ga.demi.popularmovies.data.models.FavoriteMovie;

@Dao
public interface FavoriteMovieDao {

    @Query("SELECT * FROM favoritemovie")
    LiveData<List<FavoriteMovie>> getFavoriteMovieAll();

    @Query("SELECT * FROM favoritemovie WHERE id = :id")
    LiveData<FavoriteMovie> getFavoriteMovieById(int id);

    @Insert
    void insertFavoriteMovie(FavoriteMovie favoriteMovie);

    @Update
    void updateFavoriteMovie(FavoriteMovie favoriteMovie);

    @Delete
    void deleteFavoriteMovie(FavoriteMovie favoriteMovie);
}