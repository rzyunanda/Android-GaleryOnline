package pemrogramanmobile.galeryonline.Activity;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import pemrogramanmobile.galeryonline.Model.Galery;
import pemrogramanmobile.galeryonline.R;
import pemrogramanmobile.galeryonline.room.Database;
import pemrogramanmobile.galeryonline.room.Favorite;



public class DetailActivity extends AppCompatActivity{
    Galery galery;
    ImageView img,img_fav;
    TextView  tv_nama, tv_lokasi, tv_deskripsi;
    Database db;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        db = Room.databaseBuilder(this, Database.class, "galery.db")
                .allowMainThreadQueries()
                .build();

        img = findViewById(R.id.img_poster_detail);
        img_fav = findViewById(R.id.img_fav);
        tv_nama = findViewById(R.id.tv_judul_detail);
        tv_lokasi = findViewById(R.id.tv_lokasi_detail);
        tv_deskripsi = findViewById(R.id.tv_deskripsi);

        Intent intent = getIntent();
        if(intent != null){
           galery = intent.getParcelableExtra("movie_extra_key");
            tv_nama.setText(galery.nama);
            tv_lokasi.setText(galery.lokasi);
            tv_deskripsi.setText(galery.deskripsi);
            String img_url = galery.gambar_url;

            Glide.with(this)
                    .load(img_url)
                    .into(img);

        }

        if(db.galeryFavoriteDao().getGaleryFavoritesbyName(this.galery.getId()) == null){
            img_fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }else{
            img_fav.setImageResource(R.drawable.ic_favorite_black_24dp);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.menu_refresh).setVisible(false);
        menu.findItem(R.id.menu_share).setVisible(true);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.menu_share){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "terkirim bro");
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.sent_to)));
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeFav(View v){
        if(img_fav.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp).getConstantState()){
            img_fav.setImageResource(R.drawable.ic_favorite_black_24dp);
            saveGaleryFavoriteData(this.galery);
        }else{
            img_fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            db.galeryFavoriteDao().delete(this.galery.id);
        }
    }

    public void saveGaleryFavoriteData(Galery galery){
        Galery pm = galery;
        Favorite favorite = new Favorite();
        favorite.id = pm.getId();
        favorite.nama = pm.getNama();
        favorite.lokasi = pm.getLokasi();
        favorite.deskripsi = pm.getDeskripsi();
        favorite.gambar = pm.getGambar_url();
        favorite.lat = pm.getLat();
        favorite.lng = pm.getLng();

        db.galeryFavoriteDao().insertGaleryFavorites(favorite);
    }


}
