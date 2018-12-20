package pemrogramanmobile.galeryonline.Activity;

import android.arch.persistence.room.Room;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pemrogramanmobile.galeryonline.Adapter.FavoriteAdapter;
import pemrogramanmobile.galeryonline.Model.Galery;
import pemrogramanmobile.galeryonline.R;
import pemrogramanmobile.galeryonline.room.Database;
import pemrogramanmobile.galeryonline.room.Favorite;
import pemrogramanmobile.galeryonline.room.galery;

public class FavoritActivity extends AppCompatActivity implements FavoriteAdapter.onFavoriteItemClicked {
    FavoriteAdapter favoriteAdapter;
    RecyclerView fav_view;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorit);

        db = Room.databaseBuilder(this, Database.class,"galery.db")
                .allowMainThreadQueries()
                .build();

        List<Favorite> favoriteList = db.galeryFavoriteDao().getGaleryFavorites();
        //Toast.makeText(FavoritActivity.this,String.valueOf(favoriteList.get(0).gambar),Toast.LENGTH_LONG).show();

        List<Galery> galeryModels = new ArrayList<>();

        favoriteAdapter = new FavoriteAdapter();
        favoriteAdapter.setClickHandler(this);



        fav_view = findViewById(R.id.list_favorite);
        fav_view.setLayoutManager(new LinearLayoutManager(FavoritActivity.this));
        favoriteAdapter.setFavoriteList(new ArrayList<Favorite>(favoriteList));

        fav_view.setAdapter(favoriteAdapter);



    }


    @Override
    public void favoriteItemClicked(Favorite p) {

    }
}
