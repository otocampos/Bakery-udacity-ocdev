package ocdev.com.br.bakery.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import ocdev.com.br.bakery.Model.Passos;
import ocdev.com.br.bakery.R;

/**
 * Created by Oto on 07/01/2018.
 */

public class ReceitasAdapter extends RecyclerView.Adapter<ReceitasAdapter.ReceitasAdapterViewHolder> {
    private Context context;
    private List<Passos> PassosData;

    public interface OnClickPassos {
        void getClickPassos(int position);
    }

    private OnClickPassos mOnclickPassos;

    public ReceitasAdapter(OnClickPassos mOnclickPassos) {
        this.mOnclickPassos = mOnclickPassos;
    }


    public class ReceitasAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView txtnumeropasso;
        public final ImageView imgpasso;
        public final ProgressBar pbpassos;

        public ReceitasAdapterViewHolder(View view) {
            super(view);
            txtnumeropasso = (TextView) view.findViewById(R.id.txtnumeropasso);
            imgpasso = (ImageView) view.findViewById(R.id.imgpasso);
            pbpassos = (ProgressBar) view.findViewById(R.id.pbpassos);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mOnclickPassos.getClickPassos(adapterPosition);
        }
    }

    @Override
    public ReceitasAdapter.ReceitasAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        int layoutIdForListItem = R.layout.item_passo_passo;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ReceitasAdapter.ReceitasAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ReceitasAdapterViewHolder holder, int position) {
        Passos passo = PassosData.get(position);
        if (passo.getId() == 0) {
            holder.txtnumeropasso.setText(context.getResources().getString(R.string.txt_intro));
        } else {
            holder.txtnumeropasso.setText(context.getResources().getString(R.string.txt_passo) + passo.getId());
        }
        if (passo.getVideoURL().isEmpty()) {
            holder.imgpasso.setImageResource(R.drawable.pie);
            holder.pbpassos.setVisibility(View.GONE);
        } else {
            Glide.with(context)
                    .load(passo.getVideoURL())
                    .thumbnail(0.5f)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {


                            holder.imgpasso.setImageResource(R.drawable.pie);

                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.pbpassos.setVisibility(View.GONE);


                            return false;
                        }


                    })
                    .into(holder.imgpasso);
        }

    }

    @Override
    public int getItemCount() {
        if (null == PassosData) return 0;
        return PassosData.size();
    }
    public void setPassosData(List<Passos> passosData) {
        PassosData = passosData;
    }
}
