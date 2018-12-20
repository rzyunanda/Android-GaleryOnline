package pemrogramanmobile.galeryonline.Activity;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.design.widget.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

import pemrogramanmobile.galeryonline.Adapter.ListGaleryAdapter;
import pemrogramanmobile.galeryonline.Api.GaleryApiClient;
import pemrogramanmobile.galeryonline.GaleryData;
import pemrogramanmobile.galeryonline.Model.Galery;
import pemrogramanmobile.galeryonline.R;
import pemrogramanmobile.galeryonline.room.Database;
import pemrogramanmobile.galeryonline.room.RomGalery;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  implements ListGaleryAdapter.OnItemClick, BottomNavigationView.OnNavigationItemSelectedListener    {
    ProgressBar pbGalery;
    RecyclerView rvGalery;
    ListGaleryAdapter adapter;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pbGalery = findViewById(R.id.progress_movie);
        pbGalery.setVisibility(View.VISIBLE);

        adapter = new ListGaleryAdapter();
        adapter.setHandler(this);

        db = Room.databaseBuilder(this, Database.class, "galery.db")
                .allowMainThreadQueries()
                .build();


        rvGalery = findViewById(R.id.rv_galery);
        rvGalery.setLayoutManager(new LinearLayoutManager(this));
        rvGalery.setAdapter(adapter);
        rvGalery.setVisibility(View.VISIBLE);

        ambilData();

        BottomNavigationView bottomNavigation = findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigation.getChildAt(0);

        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, displayMetrics);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }

    }


    private void ambilData() {

        rvGalery.setVisibility(View.INVISIBLE);
        pbGalery.setVisibility(View.VISIBLE);

        if(isConnected()) {
            GaleryApiClient client = (new Retrofit.Builder()
                    .baseUrl("https://galeryapp.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build())
                    .create(GaleryApiClient.class);

            Call<GaleryData> call = client.getGaleryData();

            call.enqueue(new Callback<GaleryData>() {
                @Override
                public void onResponse(Call<GaleryData> call, Response<GaleryData> response) {
                    GaleryData data = response.body();
                    List<Galery> galeri = data.data;
                    ArrayList<Galery> listgalery = new ArrayList<Galery>(galeri);
                    adapter.setListGalery(listgalery);
                    rvGalery.setAdapter(adapter);

                    saveGaleryData(listgalery);

                    pbGalery.setVisibility(View.INVISIBLE);
                    rvGalery.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<GaleryData> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Gagal coy", Toast.LENGTH_SHORT).show();
                    pbGalery.setVisibility(View.INVISIBLE);
                    rvGalery.setVisibility(View.VISIBLE);
                }
            });
        }else {
            Toast.makeText(MainActivity.this,"tidak konek",Toast.LENGTH_LONG).show();
            List<RomGalery> galeryRom = db.getGaleryDao().getGalery();
            List<Galery> galeriModels = new ArrayList<>();

            for (RomGalery rg: galeryRom ){
                Galery galeryModel = new Galery(
                    rg.id,
                    rg.nama,
                    rg.lokasi,
                    rg.gambar_url,
                    rg.deskripsi,
                    rg.lat,
                    rg.lng
                );
                galeriModels.add(galeryModel);
            }

            adapter.setListGalery(new ArrayList<Galery>(galeriModels));
            rvGalery.setAdapter(adapter);

            pbGalery.setVisibility(View.INVISIBLE);
            rvGalery.setVisibility(View.VISIBLE);

        }

    }

    private void saveGaleryData(ArrayList<Galery> galeri) {
        for (Galery pm : galeri){
            RomGalery ga = new RomGalery() ;
            ga.id = pm.getId();
            ga.nama = pm.getNama();
            ga.lokasi = pm.getLokasi();
            ga.gambar_url = pm.getGambar_url();
            ga.deskripsi = pm.getDeskripsi();
            ga.lat = pm.getLat();
            ga.lng = pm.getLng();

            db.getGaleryDao().insertgalery(ga);

        }
    }


    @Override
    public void click(Galery g) {
        Intent detailActivityIntent = new Intent(this, DetailActivity.class);
        detailActivityIntent.putExtra("movie_extra_key", g);
        startActivity(detailActivityIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_refresh){
            ambilData();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        android.support.v4.app.Fragment fragment = null;
        switch (menuItem.getItemId()){
            case R.id.nav_add:
                Intent intent = new Intent(this, CreateActivity.class);
                startActivity(intent);
                break;
            case R.id.favorite:
                Intent in = new Intent(this, FavoritActivity.class);
                startActivity(in);
                break;


        }
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    public Boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public void send(View v){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "terkirim bro");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.sent_to)));
    }
}
