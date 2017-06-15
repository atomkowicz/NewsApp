package com.example.android.newsapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.newsapp.R;
import com.example.android.newsapp.utils.Constants;
import com.example.android.newsapp.utils.NewsLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * The news fragment which contains a list of filtered news
 * {@link com.example.android.newsapp.MainActivity}).
 */
public class NewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Article>> {

    private NewsAdapter mAdapter;
    private int mImageSize;
    private boolean mItemClicked;

    public NewsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Load a larger size image to make the activity transition to the detail screen smooth
        mImageSize = getResources().getDimensionPixelSize(R.dimen.image_size) * Constants.IMAGE_ANIM_MULTIPLIER;

        // Create a empty list of news that will be later populated
        // with fetched data from Guardian news feed
        mAdapter = new NewsAdapter(getActivity(), new ArrayList<Article>());

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        NewsRecyclerView recyclerView =
                (NewsRecyclerView) view.findViewById(android.R.id.list);
        recyclerView.setEmptyView(view.findViewById(android.R.id.empty));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        // Initialize async loaders to fetch news data
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(Constants.NEWS_LOADER_ID, null, this);

        return view;
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int i, Bundle bundle) {
        Log.i("NewsFragment", "onCreateLoader() called");

        // Build query URL with base URL and parameters
        Uri baseUri = Uri.parse(Constants.GUARDIAN_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("show-fields", "body,thumbnail");
        uriBuilder.appendQueryParameter("page-size", "20");
        uriBuilder.appendQueryParameter("api-key", Constants.API_KEY);

        return new NewsLoader(super.getContext(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> earthquakes) {
        Log.i("NewsFragment", "onLoadFinished() called");

        // Clear previous data and fill list with newly fetch data
        mAdapter.clear();

        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        Log.i("NewsFragment", "onLoaderReset() called");
        mAdapter.clear();
    }

    @Override
    public void onResume() {
        super.onResume();
        mItemClicked = false;
    }

    private class NewsAdapter extends RecyclerView.Adapter<ViewHolder> implements ItemClickListener {

        public List<Article> mNewsList;
        private Context mContext;

        public NewsAdapter(Context context, List<Article> news) {
            super();
            mContext = context;
            mNewsList = news;
        }

        public void clear() {
            mNewsList.clear();
            notifyDataSetChanged();
        }

        public void addAll(List<Article> attractions) {
            mNewsList.addAll(attractions);
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.list_row, parent, false);
            return new ViewHolder(view, this);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Article article = mNewsList.get(position);

            holder.mTitleTextView.setText(article.title);
            holder.mDescriptionTextView.setText(article.description);
            Glide.with(mContext)
                    .load(article.imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.empty_photo)
                    .override(mImageSize, mImageSize)
                    .into(holder.mImageView);

            holder.mOverlayTextView.setVisibility(View.GONE);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return mNewsList == null ? 0 : mNewsList.size();
        }

        @Override
        public void onItemClick(View view, int position) {
            if (!mItemClicked) {
                mItemClicked = true;
                View heroView = view.findViewById(android.R.id.icon);
                DetailActivity.launch(
                        getActivity(), mAdapter.mNewsList.get(position), heroView);
            }
        }
    }


    private static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView mTitleTextView;
        TextView mDescriptionTextView;
        TextView mOverlayTextView;
        ImageView mImageView;
        ItemClickListener mItemClickListener;

        public ViewHolder(View view, ItemClickListener itemClickListener) {
            super(view);
            mTitleTextView = (TextView) view.findViewById(android.R.id.text1);
            mDescriptionTextView = (TextView) view.findViewById(android.R.id.text2);
            mOverlayTextView = (TextView) view.findViewById(R.id.overlaytext);
            mImageView = (ImageView) view.findViewById(android.R.id.icon);
            mItemClickListener = itemClickListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
