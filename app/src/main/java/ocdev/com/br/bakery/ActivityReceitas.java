package ocdev.com.br.bakery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import ocdev.com.br.bakery.Constants.Constantes;
import ocdev.com.br.bakery.Fragments.ActivityReceitasFragment;
import ocdev.com.br.bakery.Fragments.DetailsPassoFragment;
import ocdev.com.br.bakery.Model.Result;

;

public class ActivityReceitas extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    private Result result;
    ActivityReceitasFragment activityReceitasFragment;
    android.support.v4.app.FragmentManager fragmentManager;
    boolean doispaineis;
    DetailsPassoFragment detailsPassoFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas);
        activityReceitasFragment = new ActivityReceitasFragment();
        detailsPassoFragment = new DetailsPassoFragment();


        if (findViewById(R.id.lista_master_detail) != null) {
            doispaineis = true;

            if (savedInstanceState == null) {
                result = getIntent().getParcelableExtra(getString(R.string.receitaitem));
                activityReceitasFragment.setListadeingredientes(result.getIngredientesArrayList());
                activityReceitasFragment.setListadepassos(result.getPassosArrayList(), result);
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.lista_passos_fragment, activityReceitasFragment, Constantes.PASSOS_FRAGMENTS)
                        .commit();

                detailsPassoFragment.setListadepassos(result.getPassosArrayList(), 0);

                fragmentManager.beginTransaction()
                        .add(R.id.passos_detalhes_fragment, detailsPassoFragment, Constantes.DETALHES_PASSOS_FRAGMENTS)
                        .commit();
            } else if (savedInstanceState != null) {

                result = savedInstanceState.getParcelable(Constantes.RESULT_INSTANCE);
                detailsPassoFragment.setListadepassos(result.getPassosArrayList(), activityReceitasFragment.getPosicaoatual());
                activityReceitasFragment.setListadepassos(result.getPassosArrayList(), result);
                activityReceitasFragment.setListadeingredientes(result.getIngredientesArrayList());

                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.lista_passos_fragment, activityReceitasFragment, Constantes.PASSOS_FRAGMENTS)
                        .commit();


                fragmentManager.beginTransaction()
                        .replace(R.id.passos_detalhes_fragment, detailsPassoFragment, Constantes.DETALHES_PASSOS_FRAGMENTS)
                        .commit();


            }


        } else {
            doispaineis = false;


            final Intent intent = getIntent();
            if (intent.hasExtra(getString(R.string.receitaitem))) {
                result = getIntent().getParcelableExtra(getString(R.string.receitaitem));
                activityReceitasFragment.setListadeingredientes(result.getIngredientesArrayList());
                activityReceitasFragment.setListadepassos(result.getPassosArrayList(), result);
                if (savedInstanceState == null) {


                    activityReceitasFragment.setRetainInstance(true);
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.addOnBackStackChangedListener(this);
                    fragmentManager.beginTransaction()
                            .add(R.id.receita_fragment, activityReceitasFragment, Constantes.PASSOS_FRAGMENTS)
                            .commit();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constantes.RESULT_INSTANCE, result);
    }


    public void shouldDisplayHomeUp() {
        //Enable Up button only  if there are entries in the back stack
        boolean canback = getSupportFragmentManager().getBackStackEntryCount() > 0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }

    @Override
    public void onBackStackChanged() {
    }

}



