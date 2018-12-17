package pemrogramanmobile.galeryonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pemrogramanmobile.galeryonline.Adapter.ListGaleryAdapter;
import pemrogramanmobile.galeryonline.Api.GaleryApiClient;
import pemrogramanmobile.galeryonline.Model.Galery;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  implements ListGaleryAdapter.OnItemClick {
    ProgressBar pbGalery;
    RecyclerView rvGalery;
    ListGaleryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pbGalery = findViewById(R.id.progress_movie);
        pbGalery.setVisibility(View.VISIBLE);

        adapter = new ListGaleryAdapter();
        adapter.setHandler(this);



        rvGalery = findViewById(R.id.rv_galery);
        rvGalery.setLayoutManager(new LinearLayoutManager(this));
        rvGalery.setAdapter(adapter);
        rvGalery.setVisibility(View.VISIBLE);

        ambilData();


    }


    private void ambilData() {
        rvGalery.setVisibility(View.INVISIBLE);
        pbGalery.setVisibility(View.VISIBLE);

        GaleryApiClient client =  (new Retrofit.Builder()
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
                adapter.setListGalery(new ArrayList<Galery>(galeri));

                rvGalery.setAdapter(adapter);

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

}
