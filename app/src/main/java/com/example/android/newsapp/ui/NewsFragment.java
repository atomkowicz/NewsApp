package com.example.android.newsapp.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(android.R.id.list) NewsRecyclerView recyclerView;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.spinner) View spinner;
    @BindView(android.R.id.empty) TextView emptyTextView;
    @BindString(R.string.no_internet_connection) String noInternet;

    private NewsAdapter mAdapter;
    private int mImageSize;
    private boolean mItemClicked;
    LoaderManager loaderManager;

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
        // Bind views in fragment with external library
        ButterKnife.bind(this, view);
        recyclerView.setEmptyView(emptyTextView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        // Initialize async loaders to fetch news data
        loaderManager = getLoaderManager();
        startLoadingData();

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startLoadingData();
            }
        });

        return view;
    }

    private void startLoadingData() {
        if (isConnected()) {
            loaderManager.restartLoader(Constants.NEWS_LOADER_ID, null, this);
        } else {
            emptyTextView.setVisibility(View.VISIBLE);
            emptyTextView.setText(noInternet);
            spinner.setVisibility(View.GONE);
        }
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int i, Bundle bundle) {
        Log.i("NewsFragment", "onCreateLoader() called");
        spinner.setVisibility(View.VISIBLE);

        // Build query URL with base URL and parameters
        Uri baseUri = Uri.parse(Constants.GUARDIAN_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("q", "jjjjjjjjjjjjjjjj");
        uriBuilder.appendQueryParameter("show-fields", "body,thumbnail");
        uriBuilder.appendQueryParameter("page-size", Integer.toString(Constants.MAX_NEWS));
        uriBuilder.appendQueryParameter("api-key", Constants.API_KEY);

        Log.i("url: ", uriBuilder.toString());
        return new NewsLoader(super.getContext(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> earthquakes) {
        Log.i("NewsFragment", "onLoadFinished() called");
        spinner.setVisibility(View.GONE);

        // Clear previous data and fill list with newly fetch data
        mAdapter.clear();

        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
        }

        swipeContainer.setRefreshing(false);
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

    class NewsAdapter extends RecyclerView.Adapter<ViewHolder> implements ItemClickListener {
        @BindDrawable(R.drawable.empty_photo)
        Drawable emptyPhoto;

        public List<Article> mNewsList;
        public Context mContext;

        public NewsAdapter(Context context, List<Article> news) {
            super();
            mContext = context;
            mNewsList = news;
        }

        public void clear() {
            mNewsList.clear();
            notifyDataSetChanged();
        }

        public void addAll(List<Article> news) {
            mNewsList.addAll(news);
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
            holder.mOverlayTextView.setText(article.sectionName);
            holder.mDatePublishedTextView.setText(article.datePublished);

            Glide.with(mContext)
                    .load(article.imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(emptyPhoto)
                    .override(mImageSize, mImageSize)
                    .into(holder.mImageView);
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
                final Article currentArticle = mNewsList.get(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri bookURL = currentArticle.getUrl();

                // Create a new intent to view the book URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookURL);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);

                // View heroView = view.findViewById(android.R.id.icon);
                // DetailActivity.launch(getActivity(), mAdapter.mNewsList.get(position), heroView);
            }
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(android.R.id.text1) TextView mTitleTextView;
        @BindView(android.R.id.text2) TextView mDescriptionTextView;
        @BindView(R.id.datePublished) TextView mDatePublishedTextView;
        @BindView(R.id.overlaytext) TextView mOverlayTextView;
        @BindView(android.R.id.icon) ImageView mImageView;
        ItemClickListener mItemClickListener;

        public ViewHolder(View view, ItemClickListener itemClickListener) {
            super(view);
            ButterKnife.bind(this, view);
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
