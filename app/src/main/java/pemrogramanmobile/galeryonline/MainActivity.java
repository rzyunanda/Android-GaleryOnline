package pemrogramanmobile.galeryonline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pemrogramanmobile.galeryonline.Adapter.ListGaleryAdapter;
import pemrogramanmobile.galeryonline.Api.ApiGaleryClient;
import pemrogramanmobile.galeryonline.Model.Galery;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  implements ListGaleryAdapter.OnItemClick {

    RecyclerView rvGalery;
    ListGaleryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        ApiGaleryClient client =  (new Retrofit.Builder()
                .baseUrl("https://galeryapp.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build())
                .create(ApiGaleryClient.class);

        Call<GaleryData> call = client.getGaleryData();

        call.enqueue(new Callback<GaleryData>() {
            @Override
            public void onResponse(Call<GaleryData> call, Response<GaleryData> response) {
                GaleryData galeryData = response.body();
                List<Galery> results = galeryData.results;
                adapter.setDataGalery(new ArrayList<Galery>(results));


                rvGalery.setVisibility(View.VISIBLE);
            }


            @Override
            public void onFailure(Call<GaleryData> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal coy", Toast.LENGTH_SHORT).show();
                rvGalery.setVisibility(View.VISIBLE);
            }
        });
    }


}
