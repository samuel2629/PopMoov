package com.silho.ideo.popmoov.ui;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.silho.ideo.popmoov.R;
import com.silho.ideo.popmoov.adapter.MovieCursorAdapter;
import com.silho.ideo.popmoov.adapter.MovieMainAdapter;
import com.silho.ideo.popmoov.adapter.MyRecyclerAdapter;
import com.silho.ideo.popmoov.database.MovieAsFavContract;
import com.silho.ideo.popmoov.model.MainResultObject;
import com.silho.ideo.popmoov.model.MovieObject;
import com.silho.ideo.popmoov.retrofitApi.MovieService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String MOVIES_LIST_SAVED = "list_movies_saved_instance_state";
    private static final String IS_POPULAR_OR_TOP = "popular_or_top_rated";
    private static final int MOVIE_LOADER = 123;

    public static final String BASE_PATH_POSTER = "http://image.tmdb.org/t/p/w185/";
    public static final String MOVIE_ID = "movie_id";
    public static final String PATH_POSTER = "poster_path";
    public static final String ORIGINAL_TITLE = "original_title";
    public static final String SYNOSPSIS = "plot_synopsis";
    public static final String RATING_USER = "users_rating";
    public static final String DATE_RELEASE = "release_date";

    private RecyclerView mRecyclerView;
    private MenuItem mToggleItem;
    private List<MovieObject> mMoviesObject;
    private SharedPreferences mSharedPreference;
    private int mCategoryToggled = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.gridview);

        mSharedPreference = this.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);

        if(savedInstanceState == null || !savedInstanceState.containsKey(MOVIES_LIST_SAVED)){
            if(isOnline())
                topRatedMoviesCall();
            else
                Toast.makeText(this, R.string.no_connection, Toast.LENGTH_SHORT).show();
        } else {
            mMoviesObject = savedInstanceState.getParcelableArrayList(MOVIES_LIST_SAVED);
            mCategoryToggled = savedInstanceState.getInt(IS_POPULAR_OR_TOP);
            populateData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //getLoaderManager().restartLoader(MOVIE_LOADER, null, this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MOVIES_LIST_SAVED, (ArrayList<? extends Parcelable>) mMoviesObject);
        outState.putInt(IS_POPULAR_OR_TOP, mCategoryToggled);
        super.onSaveInstanceState(outState);
    }

    private void topRatedMoviesCall() {
        MovieService.getTopRatedMovies().topRatedMovies().enqueue(new Callback<MainResultObject>() {
            @Override
            public void onResponse(Call<MainResultObject> call, Response<MainResultObject> response) {

                mMoviesObject = response.body().getResults();
                populateData();
            }

            @Override
            public void onFailure(Call<MainResultObject> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.call_problem, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void populateData() {
        MovieMainAdapter movieMainAdapter = new MovieMainAdapter(this, mMoviesObject);
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else{
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }
        mRecyclerView.setAdapter(movieMainAdapter);
    }

    private void popularMoviesCall() {
        MovieService.getPopularMovies().popularMovies().enqueue(new Callback<MainResultObject>() {
            @Override
            public void onResponse(Call<MainResultObject> call, Response<MainResultObject> response) {

                mMoviesObject = response.body().getResults();
                populateData();
            }

            @Override
            public void onFailure(Call<MainResultObject> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.call_problem, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        mToggleItem = menu.findItem(R.id.popularRatedtoggle);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.popularRatedtoggle:
                if(mCategoryToggled == 0) {
                    mToggleItem.setTitle(R.string.top_rated_movies);
                    popularMoviesCall();
                    mCategoryToggled = 1;
                    mSharedPreference.edit().putInt(getString(R.string.isTopRated), mCategoryToggled).apply();
                } else if(mCategoryToggled == 1) {
                    mToggleItem.setTitle(R.string.popular_movies);
                    topRatedMoviesCall();
                    mCategoryToggled = 0;
                    mSharedPreference.edit().putInt(getString(R.string.isPopular), mCategoryToggled).apply();
                }
            case R.id.markAsFavToggle:
                getLoaderManager().initLoader(MOVIE_LOADER, null, this);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("StaticFieldLeak")
    public Loader<Cursor> onCreateLoader(int id, final Bundle loaderArgs){
        return new AsyncTaskLoader<Cursor>(this) {

            Cursor mTaskData = null;

            @Override
            protected void onStartLoading() {
                if(mTaskData != null)
                    deliverResult(mTaskData);
                else forceLoad();
            }

            @Override
            public Cursor loadInBackground() {
                try{
                    return getContentResolver().query(MovieAsFavContract.MovieAsFavEntry.CONTENT_URI,
                            null, null, null, null);

                } catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(Cursor data) {
                mTaskData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        MovieCursorAdapter movieCursorAdapter = new MovieCursorAdapter(this, cursor);
        mRecyclerView.setAdapter(movieCursorAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mRecyclerView.setAdapter(new MovieCursorAdapter(this, null));
    }

}
