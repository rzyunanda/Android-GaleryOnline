package pemrogramanmobile.galeryonline.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private Context context;
    private ArrayList<Galery> listGalery;
    OnItemClick handler;

    public ArrayList<Galery> getListGalery() {
        return listGalery;
    }

    public void setListGalery(ArrayList<Galery> listGalery) {
        this.listGalery = listGalery;
    }



    @NonNull
    @Override
    public ListGaleryAdapter.GaleryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_galery,viewGroup,false);
        return new GaleryHolder (itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ListGaleryAdapter.GaleryHolder holder, int i) {
         Galery galery = listGalery.get(i);

         holder.nama.setText(galery.nama);
         holder.lokasi.setText(galery.lokasi);

         String gambar_url = galery.gambar_url;

         Glide.with(holder.itemView)
              .load(gambar_url)
              .into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        if(listGalery != null) {
            return listGalery.size();
        }
        return 0;
    }

    public class GaleryHolder extends RecyclerView.ViewHolder {
        TextView nama;
        TextView lokasi;
        ImageView imgPhoto;

        public GaleryHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_poster);
            nama = itemView.findViewById(R.id.tv_nama);
            lokasi = itemView.findViewById(R.id.tv_lokasi);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Galery m = listGalery.get(position);
                    handler.click(m);
                }
            });
        }

    }
    public interface OnItemClick{
        void click(Galery m);
    }
    public void setHandler(OnItemClick clickHandler){
        handler = clickHandler;
    }

}
