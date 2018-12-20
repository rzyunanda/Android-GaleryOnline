package pemrogramanmobile.galeryonline.Adapter;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import pemrogramanmobile.galeryonline.Activity.FavoritActivity;
import pemrogramanmobile.galeryonline.Model.Galery;
import pemrogramanmobile.galeryonline.R;
import pemrogramanmobile.galeryonline.room.Favorite;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {
    onFavoriteItemClicked clickHandler;

    public void setClickHandler(onFavoriteItemClicked clickHandler){
        this.clickHandler = clickHandler;
    }


    public ArrayList<Favorite> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(ArrayList<Favorite> favoriteList) {
        this.favoriteList = favoriteList;
    }

    private ArrayList<Favorite> favoriteList;

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.row_favorite,viewGroup,false);
        FavoriteAdapter.FavoriteHolder holder = new FavoriteAdapter.FavoriteHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder favoriteHolder, int i) {
        final Favorite favorite  = favoriteList.get(i);
        favoriteHolder.fav_nama.setText(favorite.nama);
        favoriteHolder.fav_lokasi.setText(favorite.lokasi);

        String url = favorite.gambar;

        Glide.with(favoriteHolder.itemView)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .into(favoriteHolder.img);

    }

    @Override
    public int getItemCount() {
        if(favoriteList == null){
            return 0;
        }
        return favoriteList.size();
    }

    public class FavoriteHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView fav_nama,fav_lokasi;

        public FavoriteHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.fav_img_poster);
          //  fav = itemView.findViewById(R.id.fav);
            fav_nama = itemView.findViewById(R.id.fav_tv_nama);
            fav_lokasi = itemView.findViewById(R.id.fav_tv_lokasi);
        }
    }
    public interface onFavoriteItemClicked{
        void favoriteItemClicked(Favorite p);
    }
}
