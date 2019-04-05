package ocdev.com.br.bakery.Fragments;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

import ocdev.com.br.bakery.Constants.Constantes;
import ocdev.com.br.bakery.Model.Passos;
import ocdev.com.br.bakery.R;
import ocdev.com.br.bakery.Utils.ConfigTela;
import ocdev.com.br.bakery.Utils.NetworkUtils;

/**
 * Created by Oto on 08/01/2018.
 */

public class DetailsPassoFragment extends Fragment implements ExoPlayer.EventListener {
    private TextView txtdescricao;
    private List<Passos> listadepassos = new ArrayList<>();
    private int posicaopasso;
    View rootview;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private ImageView btnproximareceita;
    private ImageView btnreceitaanterior;
    private LinearLayout layoutexoplayer;
    private RelativeLayout rlayoutexoplayer;
    private android.support.v7.app.ActionBar actionBar;

    public DetailsPassoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.detalhes_passos_fragments, container, false);
        txtdescricao = (TextView) rootview.findViewById(R.id.txtdescricaodetalhes);
        mPlayerView = (SimpleExoPlayerView) rootview.findViewById(R.id.playerView);
        btnproximareceita = (ImageView) rootview.findViewById(R.id.btnproximarceita);
        btnreceitaanterior = (ImageView) rootview.findViewById(R.id.btnreceitaanterior);

        if (savedInstanceState != null) {
            ArrayList<Passos> list = savedInstanceState.getParcelableArrayList(Constantes.LISTA_PASSOS);
            int posicao = savedInstanceState.getInt(Constantes.LISTA_PASSOS_POSICAO);
            ExibirDados(list, posicao);
        } else {
            ExibirDados(listadepassos, posicaopasso);

        }
        btnproximareceita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExibirDados(listadepassos, posicaopasso + Constantes.PROX_RECEITA);
            }
        });
        btnreceitaanterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExibirDados(listadepassos, posicaopasso - Constantes.PROX_RECEITA);
            }
        });
        return rootview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        layoutexoplayer = (LinearLayout) rootview.findViewById(R.id.layoutexoplayer);
        rlayoutexoplayer = (RelativeLayout) rootview.findViewById(R.id.rlayoutexoplayer);
        if (savedInstanceState != null) {
            ArrayList<Passos> list = savedInstanceState.getParcelableArrayList(Constantes.LISTA_PASSOS);
            int posicao = savedInstanceState.getInt(Constantes.LISTA_PASSOS_POSICAO);
            this.listadepassos = list;
            this.posicaopasso = posicao;
            ExibirDados(list, posicao);
        } else {
            ExibirDados(listadepassos, posicaopasso);
        }
        if (ConfigTela.OrientacaoTela(getActivity()) == Constantes.LANDSCAPE && rootview.findViewById(R.id.id_full_screen_size) != null) {
            actionBar.hide();
        }

    }

    public void setListadepassos(List<Passos> listadepassos, int posicaopasso) {
        this.listadepassos = listadepassos;
        this.posicaopasso = posicaopasso;
    }


    private void initializePlayer(String mediauri) {
        Context mcontext = getActivity().getApplicationContext();

        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(mcontext, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            mExoPlayer.addListener(this);
            String userAgent = Util.getUserAgent(mcontext, Constantes.APLICATTION_NAME);
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(mediauri), new DefaultDataSourceFactory(
                    mcontext, userAgent), new DefaultExtractorsFactory(), null, null);

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(false);
        } else {
            String userAgent = Util.getUserAgent(mcontext, Constantes.APLICATTION_NAME);
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(mediauri), new DefaultDataSourceFactory(
                    mcontext, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(false);
            if (mediauri.isEmpty()) {
                mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                        (getResources(), R.drawable.novideo));
                layoutexoplayer.setVisibility(View.GONE);
                rlayoutexoplayer.setVisibility(View.GONE);
            } else {
                layoutexoplayer.setVisibility(View.VISIBLE);
                rlayoutexoplayer.setVisibility(View.VISIBLE);
            }
            if (!NetworkUtils.isNetworkConnected(mcontext)) {
                mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                        (getResources(), R.drawable.nointernet));
            }
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelableArrayList(Constantes.LISTA_PASSOS, (ArrayList<Passos>) listadepassos);
        outState.putInt(Constantes.LISTA_PASSOS_POSICAO, posicaopasso);
        super.onSaveInstanceState(outState);
    }

    public void ExibirDados(List<Passos> listadepassos, int posicaopasso) {

        this.listadepassos = listadepassos;
        this.posicaopasso = posicaopasso;
        txtdescricao = (TextView) rootview.findViewById(R.id.txtdescricaodetalhes);
        btnproximareceita = (ImageView) rootview.findViewById(R.id.btnproximarceita);
        btnreceitaanterior = (ImageView) rootview.findViewById(R.id.btnreceitaanterior);
        txtdescricao.setText(listadepassos.get(posicaopasso).getShortDescription() + "\n\n" +
                listadepassos.get(posicaopasso).getDescription());
        initializePlayer(listadepassos.get(posicaopasso).getVideoURL());
        if (ConfigTela.OrientacaoTela(getActivity()) == Constantes.LANDSCAPE && !listadepassos.get(posicaopasso).getVideoURL().isEmpty()) {
            this.listadepassos = listadepassos;
            this.posicaopasso = posicaopasso;
            EsconderElementos();
        } else {

            txtdescricao.setVisibility(View.VISIBLE);
        }
        if (getActivity().findViewById(R.id.lista_master_detail) != null) {
            txtdescricao.setVisibility(View.VISIBLE);
        }


        if (posicaopasso < listadepassos.size() - 2 || posicaopasso > 0) {
            btnproximareceita.setVisibility(View.VISIBLE);
            btnreceitaanterior.setVisibility(View.VISIBLE);
        }
        if (posicaopasso == listadepassos.size() - 1) {
            btnproximareceita.setVisibility(View.INVISIBLE);
        }
        if (posicaopasso == 0) {
            btnreceitaanterior.setVisibility(View.INVISIBLE);
        }
    }

    public void EsconderElementos() {
        txtdescricao.setVisibility(View.GONE);
        btnreceitaanterior.setVisibility(View.GONE);
        btnproximareceita.setVisibility(View.GONE);

    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

   

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
}
