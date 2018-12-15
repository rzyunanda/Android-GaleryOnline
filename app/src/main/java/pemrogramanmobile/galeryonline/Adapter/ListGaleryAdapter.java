package pemrogramanmobile.galeryonline.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pemrogramanmobile.galeryonline.Model.Galery;
import pemrogramanmobile.galeryonline.R;

public class ListGaleryAdapter extends RecyclerView.Adapter<ListGaleryAdapter.GaleryHolder>{
    ArrayList<Galery> dataGalery;
    OnItemClick handler;



    public void setDataGalery(ArrayList<Galery> dataGalery) {
        this.dataGalery = dataGalery;
        notifyDataSetChanged();
    }

    public void setHandler(OnItemClick clickHandler){
        handler = clickHandler;
    }

    public interface OnItemClick{
        void click(Galery g);
    }

    @NonNull
    @Override
    public GaleryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_row_galery,viewGroup,false);
        GaleryHolder holder = new GaleryHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull GaleryHolder holder, int i) {
        Galery galery = dataGalery.get(i);

        holder.textTitle.setText(galery.title);
        holder.lokasi.setText(String.valueOf(galery.lokasi));


        Glide.with(holder.itemView)
                .load(galery.gambar_url)
                .into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class GaleryHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView textTitle,lokasi;

        public GaleryHolder(View v) {
            super(v);
            imgPoster = itemView.findViewById(R.id.img_poster);
            textTitle = itemView.findViewById(R.id.text_title);
            lokasi = itemView.findViewById(R.id.tv_lokasi);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Galery g = dataGalery.get(position);
                    handler.click(g);
                }
            });
        }
    }
}
