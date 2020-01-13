package ga.demi.popularmovies.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ga.demi.popularmovies.data.dao.FavoriteMovieDao;
import ga.demi.popularmovies.data.models.FavoriteMovie;

@Database(entities = {FavoriteMovie.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract FavoriteMovieDao getFavoriteMovieDao();
}