package ga.demi.popularmovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ga.demi.popularmovies.R;
import ga.demi.popularmovies.models.ReviewsMovieModel;

public final class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.MovieReviewsViewHolder> {

    private List<ReviewsMovieModel.ResultReviews> mMovieReviewsList;
    private ListReviewsItemClickListener mOnClickListener;

    public MovieReviewsAdapter(List<ReviewsMovieModel.ResultReviews> movieReviewsList, ListReviewsItemClickListener onClickListener) {
        mMovieReviewsList = movieReviewsList;
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MovieReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_review_item, parent, false);
        MovieReviewsAdapter.MovieReviewsViewHolder viewHolder = new MovieReviewsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieReviewsViewHolder holder, int position) {
        holder.bind(mMovieReviewsList.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return mMovieReviewsList.size();
    }

    public interface ListReviewsItemClickListener {
        void onListReviewsItemClick(String clickedItemUrl);
    }

    class MovieReviewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mReviewTitleTV;

        public MovieReviewsViewHolder(@NonNull View itemView) {
            super(itemView);

            mReviewTitleTV = itemView.findViewById(R.id.tv_review_title_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnClickListener.onListReviewsItemClick(mMovieReviewsList.get(getAdapterPosition()).getUrl());
        }

        void bind(String authorText) {
            mReviewTitleTV.setText(authorText);
        }
    }
}