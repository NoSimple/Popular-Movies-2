package ga.demi.popularmovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ga.demi.popularmovies.R;
import ga.demi.popularmovies.models.VideosMovieModel;

public final class MovieVideosAdapter extends RecyclerView.Adapter<MovieVideosAdapter.MovieVideosViewHolder> {

    private List<VideosMovieModel.ResultVideos> mMovieVideosList;
    private ListVideosItemClickListener mOnClickListener;

    public MovieVideosAdapter(List<VideosMovieModel.ResultVideos> movieVideosList, ListVideosItemClickListener onClickListener) {
        mMovieVideosList = movieVideosList;
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MovieVideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_video_item, parent, false);
        MovieVideosViewHolder viewHolder = new MovieVideosViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieVideosViewHolder holder, int position) {
        holder.bind(mMovieVideosList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mMovieVideosList.size();
    }

    public interface ListVideosItemClickListener {
        void onListVideosItemClick(String clickedItemId);
    }

    class MovieVideosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTrailerTitleTV;

        public MovieVideosViewHolder(@NonNull View itemView) {
            super(itemView);

            mTrailerTitleTV = itemView.findViewById(R.id.tv_trailer_title_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnClickListener.onListVideosItemClick(mMovieVideosList.get(getAdapterPosition()).getKey());
        }

        void bind(String nameText) {
            mTrailerTitleTV.setText(nameText);
        }
    }
}