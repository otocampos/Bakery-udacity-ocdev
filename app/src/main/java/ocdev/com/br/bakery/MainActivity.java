package ocdev.com.br.bakery;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.List;

import ocdev.com.br.bakery.Adapters.MainActivityAdapter;
import ocdev.com.br.bakery.Model.Result;
import ocdev.com.br.bakery.Utils.ConfigTela;
import ocdev.com.br.bakery.Utils.NetworkUtils;
import ocdev.com.br.bakery.Utils.OpenJsonUtils;

public class MainActivity extends AppCompatActivity implements MainActivityAdapter.ActivityAdapterOnClickHandler {
    private String JsonReceitas;
    private RecyclerView mRecyclerview;
    private MainActivityAdapter adapter;
    private FetchReceitas fetchReceitas;
    private ProgressBar progressBar;
    private TextView txt_erro_internet;
    private Button btnrecarregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        fetchReceitas = new FetchReceitas();
        txt_erro_internet = (TextView) findViewById(R.id.tv_error_message_display);
        btnrecarregar = (Button) findViewById(R.id.btn_recarregar);
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview_receitas);
        adapter = new MainActivityAdapter(this);
        ShowData();

    }

    public void ShowData() {
        if (NetworkUtils.isNetworkConnected(this)) {
            fetchReceitas.execute();
            txt_erro_internet.setVisibility(View.GONE);
            btnrecarregar.setVisibility(View.GONE);
        } else {
            txt_erro_internet.setVisibility(View.VISIBLE);
            txt_erro_internet.setText(R.string.erro_internet);
            btnrecarregar.setVisibility(View.VISIBLE);
            btnrecarregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowData();
                }
            });

        }
    }

    @Override
    public void onClick(int indice) {
        Result itemclicado = fetchReceitas.getResults().get(indice);
        Intent intent = new Intent(getApplicationContext(), ActivityReceitas.class);
        intent.putExtra(getString(R.string.receitaitem), itemclicado);
        startActivity(intent);
    }

    public class FetchReceitas extends AsyncTask<Void, Void, List<Result>> {


        private List<Result> results;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Result> doInBackground(Void... voids) {
            OpenJsonUtils openJsonUtils = new OpenJsonUtils();
            URL receitasrequest = NetworkUtils.buildUrl();
            try {
                JsonReceitas = NetworkUtils
                        .getResponseFromHttpUrl(receitasrequest);
                return openJsonUtils.getListaResultados(JsonReceitas);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(List<Result> results) {
            progressBar.setVisibility(View.INVISIBLE);
            this.results = results;
            adapter.setReceitasData(results);


            mRecyclerview.setLayoutManager(ConfigTela.RecuperarEstilodoRecyclerview(MainActivity.this));

            mRecyclerview.setAdapter(adapter);
        }

        public List<Result> getResults() {
            return results;
        }

    }


}

