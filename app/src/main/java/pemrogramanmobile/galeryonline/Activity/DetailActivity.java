package pemrogramanmobile.galeryonline.Activity;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import pemrogramanmobile.galeryonline.Adapter.ListGaleryAdapter;
import pemrogramanmobile.galeryonline.DatabaseHelper;
import pemrogramanmobile.galeryonline.Model.Galery;
import pemrogramanmobile.galeryonline.R;
import pemrogramanmobile.galeryonline.room.Database;

import static pemrogramanmobile.galeryonline.DatabaseHelper.fav;

public class DetailActivity extends AppCompatActivity {
    ImageView img,img_fav;
    TextView  tv_nama, tv_lokasi, tv_deskripsi;
    ListGaleryAdapter adapter;
    Database db;


    DatabaseHelper database;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        database = new DatabaseHelper(this);

        db = Room.databaseBuilder(this, Database.class, "isce.db")
                .allowMainThreadQueries()
                .build();



        img = findViewById(R.id.img_poster_detail);
        img_fav = findViewById(R.id.img_fav);
        tv_nama = findViewById(R.id.tv_judul_detail);
        tv_lokasi = findViewById(R.id.tv_lokasi_detail);
        tv_deskripsi = findViewById(R.id.tv_deskripsi);



        Intent intent = getIntent();
        if(intent != null){


            Galery galery = intent.getParcelableExtra("movie_extra_key");
            tv_nama.setText(galery.nama);
            tv_lokasi.setText(galery.lokasi);
            tv_deskripsi.setText(galery.deskripsi);
            String img_url = galery.gambar_url;



            Glide.with(this)
                    .load(img_url)
                    .into(img);

            if(db.galeryFavoriteDao().getGaleryFavoritesbyName(galery.id) == null){
                img_fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }else{
                img_fav.setImageResource(R.drawable.ic_favorite_black_24dp);
            }



        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {



        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.menu_share).setVisible(true);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_refresh){

        }

        return super.onOptionsItemSelected(item);
    }

}
