package ocdev.com.br.bakery.Fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ocdev.com.br.bakery.Adapters.ReceitasAdapter;
import ocdev.com.br.bakery.Constants.Constantes;
import ocdev.com.br.bakery.Widget.IngredientesWidgetService;
import ocdev.com.br.bakery.Model.Ingredientes;
import ocdev.com.br.bakery.Model.Passos;
import ocdev.com.br.bakery.Model.Result;
import ocdev.com.br.bakery.R;
import ocdev.com.br.bakery.Utils.ConfigTela;
import ocdev.com.br.bakery.data.WidgetContract;

public class ActivityReceitasFragment extends Fragment implements ReceitasAdapter.OnClickPassos, LoaderManager.LoaderCallbacks<Cursor> {
    private List<Ingredientes> listadeingredientes = new ArrayList<>();
    private List<Passos> listadepassos = new ArrayList<>();
    private Result result;
    private RecyclerView mRecyclerViewPassos;
    private ReceitasAdapter mReceitasAdapter;
    private Switch aSwitchwidget;
    private Uri uri_receita;
    private static final int ID_DETAIL_LOADER = 353;
    public static final int INDEX_INGREDIENTE_ID = 0;
    private int posicaoatual;
    TextView txtreceitas;
    TextView txttituloreceita;
    ImageView btn_plus, btn_minus;
    LinearLayoutManager layoutManager;

    public ActivityReceitasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_activity_receitas, container, false);
        mRecyclerViewPassos = (RecyclerView) rootview.findViewById(R.id.recycler_passos);
        mReceitasAdapter = new ReceitasAdapter(this);

        mReceitasAdapter.setPassosData(listadepassos);


        if (getActivity().findViewById(R.id.lista_master_detail) == null) {
            txttituloreceita = (TextView) rootview.findViewById(R.id.titulonomereceita);

            layoutManager
                    = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            mRecyclerViewPassos.setLayoutManager(layoutManager);
            txttituloreceita.setText(result.getName());

        } else {

            GridLayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), ConfigTela.GetRecyclerviewColunasListadePassoa(getActivity()));
            mRecyclerViewPassos.setLayoutManager(layoutManager);

        }
        btn_plus = (ImageView) rootview.findViewById(R.id.btn_ingrediente_plus);
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_plus.setVisibility(View.GONE);
                btn_minus.setVisibility(View.VISIBLE);
                txtreceitas.setMaxLines(100);
            }
        });
        btn_minus = (ImageView) rootview.findViewById(R.id.btn_ingrediente_minus);
        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_minus.setVisibility(View.GONE);
                btn_plus.setVisibility(View.VISIBLE);
                txtreceitas.setMaxLines(2);
            }
        });

        mRecyclerViewPassos.setAdapter(mReceitasAdapter);


        txtreceitas = (TextView) rootview.findViewById(R.id.lista_ingredientes);

        if (listadeingredientes != null && listadeingredientes.size() > 0) {
            for (Ingredientes ingredientes : listadeingredientes) {
                txtreceitas.append(ingredientes.getQuantity() + " " + ingredientes.getMeasure() + " " + ingredientes.getIngredient() + "\n\n");
            }
        }

        aSwitchwidget = (Switch) rootview.findViewById(R.id.switch1widget);
        aSwitchwidget.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    DeleteWidgetinContentProvider();
                    AddWidgetinContentProvider();
                    Toast.makeText(getActivity().getBaseContext(), getResources().getString(R.string.widget_adicionado), Toast.LENGTH_LONG).show();


                } else {

                    DeleteWidgetinContentProvider();
                    Toast.makeText(getActivity().getBaseContext(), getResources().getString(R.string.widget_deletado), Toast.LENGTH_LONG).show();

                }
            }
        });

        return rootview;
    }

    public void setListadeingredientes(List<Ingredientes> listadeingredientes) {
        this.listadeingredientes = listadeingredientes;
    }

    public void setListadepassos(List<Passos> listadepassos, Result result) {
        this.listadepassos = listadepassos;
        this.result = result;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("posicao", posicaoatual);
    }



    @Override
    public void getClickPassos(int position) {
        this.posicaoatual = position;
        DetailsPassoFragment detailsPassoFragment = new DetailsPassoFragment();

        if (getActivity().findViewById(R.id.lista_master_detail) != null) {

            detailsPassoFragment.setListadepassos(listadepassos, position);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.passos_detalhes_fragment, detailsPassoFragment, Constantes.DETALHES_PASSOS_FRAGMENTS)
                    .commit();


        } else {

            detailsPassoFragment.setListadepassos(listadepassos, position);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.receita_fragment, detailsPassoFragment, Constantes.DETALHES_PASSOS_FRAGMENTS)
                    .addToBackStack(Constantes.PASSOS_FRAGMENTS)
                    .commit();
        }
    }

    public void AddWidgetinContentProvider() {
        updateIngredientesContentProvider();
        IngredientesWidgetService.startActionUpdateIngredientesWidgets(getActivity().getApplicationContext());
    }

    public void DeleteWidgetinContentProvider() {
        deleteIngredientesContentProvider();
        IngredientesWidgetService.startActionUpdateIngredientesWidgets(getActivity().getApplicationContext());


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void deleteIngredientesContentProvider() {
        int deletedRows = 0;
        deletedRows = getActivity().getContentResolver().delete(WidgetContract.WidgetEntry.CONTENT_URI, "1", null);

    }


    public void updateIngredientesContentProvider() {
        ContentValues contentValues = new ContentValues();
        Uri uri = null;
        if (listadeingredientes != null && listadeingredientes.size() > 0) {
            for (Ingredientes ingredientes : listadeingredientes) {
                contentValues.put(WidgetContract.WidgetEntry.COLUMN_QUANTIDADE, ingredientes.getQuantity());
                contentValues.put(WidgetContract.WidgetEntry.COLUMN_MEDIDA, ingredientes.getMeasure());
                contentValues.put(WidgetContract.WidgetEntry.COLUMN_INGREDIENTE, ingredientes.getIngredient());
                contentValues.put(WidgetContract.WidgetEntry.COLUMN_ID_RECEITA, result.getId());

                uri = getActivity().getContentResolver().insert(WidgetContract.WidgetEntry.CONTENT_URI, contentValues);

            }

            if (uri != null) {
            }
        }
    }



    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle loaderArgs) {
        uri_receita = WidgetContract.WidgetEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(result.getId())).build();
        switch (loaderId) {
            case ID_DETAIL_LOADER:

                return new CursorLoader(getActivity().getBaseContext(),
                        uri_receita,
                        new String[]{WidgetContract.WidgetEntry.COLUMN_ID_RECEITA},
                        null,
                        null,
                        null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        boolean cursorHasValidData = false;
        if (data != null && data.moveToFirst()) {
            /* We have valid data, continue on to bind the data to the UI */
            cursorHasValidData = true;
        }

        if (!cursorHasValidData) {
            return;
        }
        String iddareceita = data.getString(INDEX_INGREDIENTE_ID);

        if (iddareceita.equals(String.valueOf(result.getId()))) {

            aSwitchwidget.setChecked(true);
        } else {
            aSwitchwidget.setChecked(false);
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public int getPosicaoatual() {
        return posicaoatual;
    }
}



