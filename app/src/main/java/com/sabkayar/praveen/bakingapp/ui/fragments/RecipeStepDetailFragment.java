package com.sabkayar.praveen.bakingapp.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.sabkayar.praveen.bakingapp.R;
import com.sabkayar.praveen.bakingapp.model.Step;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeStepDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeStepDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeStepDetailFragment extends Fragment implements Player.EventListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String PLAY_BACK_POSITION = "play_back_position";
    private static final String LOG_TAG = RecipeStepDetailFragment.class.getSimpleName();
    ;

    // TODO: Rename and change types of parameters
    private List<Step> mSteps;
    private int mPosition;

    private OnFragmentInteractionListener mListener;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.simpleExoPlayerView)
    SimpleExoPlayerView mExoPlayerView;
    @BindView(R.id.tv_step_detail)
    TextView mStepDetailTextView;
    @BindView(R.id.imv_recipe)
    ImageView mRecipeImageView;


    public RecipeStepDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeStepDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeStepDetailFragment newInstance(List<Step> param1, int param2) {
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, (ArrayList<? extends Parcelable>) param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSteps = getArguments().getParcelableArrayList(ARG_PARAM1);
            mPosition = getArguments().getInt(ARG_PARAM2);
        }
        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(PLAY_BACK_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);
        ButterKnife.bind(this, rootView);

        boolean isLandscape = mStepDetailTextView == null;

        if (!isLandscape) {
            mStepDetailTextView.setText(mSteps.get(mPosition).getDescription());
            Objects.requireNonNull(getActivity()).setTitle(mPosition + " : " + mSteps.get(mPosition).getShortDescription());
        } else {
            Objects.requireNonNull(getActivity()).setTitle("Step " + mPosition + " : " + mSteps.get(mPosition).getShortDescription());
        }


        String imageUrl = mSteps.get(mPosition).getThumbnailURL();
        String videoUrl = mSteps.get(mPosition).getVideoURL();
        if (TextUtils.isEmpty(videoUrl) && TextUtils.isEmpty(imageUrl)) {
            mRecipeImageView.setVisibility(View.VISIBLE);
            mExoPlayerView.setVisibility(View.INVISIBLE);
            mExoPlayerView.setDefaultArtwork(getResources().getDrawable(R.drawable.error_placeholder));
        } else if (TextUtils.isEmpty(videoUrl) && !TextUtils.isEmpty(imageUrl)) {
            mRecipeImageView.setVisibility(View.VISIBLE);
            mExoPlayerView.setVisibility(View.INVISIBLE);
            Picasso.get().load(imageUrl).placeholder(R.drawable.progress_animation)
                    .error(R.drawable.error_placeholder).into(mRecipeImageView);
        }


        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public Lifecycle getLifecycle() {
        return super.getLifecycle();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == Player.STATE_BUFFERING)
            mProgressBar.setVisibility(View.VISIBLE);
        else if (playbackState == Player.STATE_READY)
            mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        Log.d(LOG_TAG, error.toString());
        mRecipeImageView.setVisibility(View.VISIBLE);
        mExoPlayerView.setVisibility(View.INVISIBLE);
        String imageUrl = mSteps.get(mPosition).getThumbnailURL();
        String videoUrl = mSteps.get(mPosition).getVideoURL();
        if (TextUtils.isEmpty(videoUrl) && TextUtils.isEmpty(imageUrl)) {
            mExoPlayerView.setDefaultArtwork(getResources().getDrawable(R.drawable.error_placeholder));
        } else if (TextUtils.isEmpty(videoUrl) && !TextUtils.isEmpty(imageUrl)) {
            Picasso.get().load(imageUrl).placeholder(R.drawable.progress_animation)
                    .error(R.drawable.error_placeholder).into(mRecipeImageView);
        } else {
            mExoPlayerView.setDefaultArtwork(getResources().getDrawable(R.drawable.error_placeholder));
        }
    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onStart() {
        super.onStart();
        initializeExoplayer();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private ExoPlayer mExoPlayer;
    private long playbackPosition = 0L;
    //private String dashUrl = "http://rdmedia.bbc.co.uk/dash/ondemand/bbb/2/client_manifest-separate_init.mpd";
    private BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
    private AdaptiveTrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);


    private void initializeExoplayer() {
        // Create an instance of the ExoPlayer.
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);

        prepareExoplayer();
        mExoPlayerView.setPlayer(mExoPlayer);
        mExoPlayer.seekTo(playbackPosition);
        mExoPlayer.setPlayWhenReady(true);
        mExoPlayer.addListener(this);
    }

    private void releaseExoplayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }


    private void prepareExoplayer() {
        // Prepare the MediaSource.
        String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
        Uri progressiveUri = Uri.parse(mSteps.get(mPosition).getVideoURL());
        // Create a data source factory.
        DataSource.Factory dataSourceFactory =
                new DefaultHttpDataSourceFactory(userAgent);
        // Create a progressive media source pointing to a stream uri.
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(progressiveUri);

        // Prepare the player with the progressive media source.
        mExoPlayer.prepare(mediaSource);

        //For dash format
      /*  // Create a data source factory.
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSourceFactory(userAgent);
       // Create a DASH media source pointing to a DASH manifest uri.
        MediaSource mediaSource = new DashMediaSource.Factory(dataSourceFactory).createMediaSource(progressiveUri);
        mExoPlayer.prepare(mediaSource);
*/


    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        playbackPosition = mExoPlayer.getCurrentPosition();
        outState.putLong(PLAY_BACK_POSITION, playbackPosition);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseExoplayer();
    }
}
