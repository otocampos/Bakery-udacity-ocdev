package ocdev.com.br.bakery.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ocdev.com.br.bakery.Model.Result;
import ocdev.com.br.bakery.R;

import static ocdev.com.br.bakery.Utils.NetworkUtils.getThumb;

/**
 * Created by Oto on 06/01/2018.
 */

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ActivityAdapterViewHolder> {
    private final ActivityAdapterOnClickHandler mClickHandler;
    private Context context;
    private List<Result> mResultReceitasData;


    public MainActivityAdapter(ActivityAdapterOnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }

    public interface ActivityAdapterOnClickHandler {
        void onClick(int indice);
    }

    public class ActivityAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView txtReceitas;
        public final ImageView imgreceitas;
        public final TextView txtinfoReceitas;

        public ActivityAdapterViewHolder(View view) {
            super(view);
            txtReceitas = (TextView) view.findViewById(R.id.titulo_receita);
            imgreceitas = (ImageView) view.findViewById(R.id.thumb_video);
            txtinfoReceitas = (TextView) view.findViewById(R.id.info_receita);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }

    @Override
    public MainActivityAdapter.ActivityAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item_receitas;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ActivityAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MainActivityAdapter.ActivityAdapterViewHolder holder, int position) {
        Result result = mResultReceitasData.get(position);

        holder.txtReceitas.setText(result.getName());
holder.txtinfoReceitas.setText(String.valueOf(result.getIngredientesArrayList().size())+" "+context.getString(R.string.ingredientes));
        Glide.with(context).load(getThumb(result)).into(holder.imgreceitas);
    }

    @Override
    public int getItemCount() {
        if (null == mResultReceitasData) return 0;
        return mResultReceitasData.size();
    }

    public void setReceitasData(List<Result> resultdata) {
        mResultReceitasData = resultdata;
        notifyDataSetChanged();
    }





}
