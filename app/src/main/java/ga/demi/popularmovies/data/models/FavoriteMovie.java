package ga.demi.popularmovies.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavoriteMovie {

    @PrimaryKey
    public int id;
    public String title;
    public String posterPath;
    public String releaseDate;
    public double voteAverage;
    public String overview;
}