package ga.demi.popularmovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ga.demi.popularmovies.R;
import ga.demi.popularmovies.models.Result;

public final class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MoviePosterViewHolder> {

    private List<Result> mMoviePosterList;
    private ListItemClickListener mOnClickListener;

    public MoviePosterAdapter(List<Result> moviePosterList, ListItemClickListener onClickListener) {
        mMoviePosterList = moviePosterList;
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MoviePosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_poster_item, parent, false);
        MoviePosterViewHolder viewHolder = new MoviePosterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MoviePosterViewHolder holder, int position) {
        holder.bind(mMoviePosterList.get(position).getPosterPath());
    }

    @Override
    public int getItemCount() {
        return mMoviePosterList.size();
    }

    public void setWeatherData(List<Result> moviePosterList) {
        mMoviePosterList = moviePosterList;
        notifyDataSetChanged();
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    class MoviePosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mMoviePosterIV;

        public MoviePosterViewHolder(@NonNull View itemView) {
            super(itemView);

            mMoviePosterIV = itemView.findViewById(R.id.iv_movie_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnClickListener.onListItemClick(getAdapterPosition());
        }

        void bind(String posterLink) {
            String posterUrl = "https://image.tmdb.org/t/p/original/" + posterLink;
            Picasso.get().load(posterUrl).into(mMoviePosterIV);
        }
    }
}