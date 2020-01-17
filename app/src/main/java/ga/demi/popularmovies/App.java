package ga.demi.popularmovies;

import android.app.Application;

import androidx.room.Room;

import ga.demi.popularmovies.data.MovieDatabase;

public final class App extends Application {

    public static App instanceApp;

    private MovieDatabase movieDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        instanceApp = this;

        movieDatabase = Room.databaseBuilder(this, MovieDatabase.class, "movie_database").build();
    }

    public static App getInstanceApp() {
        return instanceApp;
    }

    public MovieDatabase getMovieDatabase() {
        return movieDatabase;
    }
}