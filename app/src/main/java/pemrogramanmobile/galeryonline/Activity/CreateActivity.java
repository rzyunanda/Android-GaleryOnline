package pemrogramanmobile.galeryonline.Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import okhttp3.ResponseBody;
import pemrogramanmobile.galeryonline.Api.GaleryApiClient;
import pemrogramanmobile.galeryonline.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateActivity extends AppCompatActivity {

    Intent intent;
    EditText edt_nama, edt_deskripsi, edt_lat,edt_lokasi;
    ImageView imageView ;
    Bitmap bm;
    Button btn_img, btn_tambah,btn_lokasi;
    public final int REQUEST_CAMERA = 0;
    public final int SELECT_FILE = 1;

    int bitmap_size = 40; // image quality 1 - 100;
    int max_resolution_image = 800;
    private static  final  int REQUEST_LOCATION =1;

    LocationManager locationManager;
    String latitude, longtitude;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        edt_nama = findViewById(R.id.edt_nama);
        edt_deskripsi = findViewById(R.id.edt_deskripsi);

        edt_lat = findViewById(R.id.edt_lat);
        edt_lokasi = findViewById(R.id.edt_lokasi);
        imageView = findViewById(R.id.imageView);
        btn_img = findViewById(R.id.btn_choose_image);
        btn_tambah = findViewById(R.id.btn_tambah);
        btn_lokasi = findViewById(R.id.btn_lokasi);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        btn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


    }

        private void selectImage() {
            imageView.setImageResource(0);
            final CharSequence[] items = {"Take Photo", "Choose from Library",
                    "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add Photo!");
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (items[item].equals("Take Photo")) {
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 0);
                    } else if (items[item].equals("Choose from Library")) {
                        intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FILE);
                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        btn_lokasi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    buildAlertMessageNoGPS();
                }
                else
                {
                    getLocation();
                }

            }
        });


        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nama = edt_nama.getText().toString();
                String deskripsi = edt_deskripsi.getText().toString();
                String lokasi = edt_lokasi.getText().toString();
                Double lat = Double.valueOf(latitude);
                Double lng = Double.valueOf(longtitude);
                String gambar = getBase64String(imageView);


                if(TextUtils.isEmpty(edt_nama.getText().toString())){
                    edt_nama.setError(getResources().getString(R.string.msg_cannot_allow_empty_field));
                }else if(TextUtils.isEmpty(edt_deskripsi.getText().toString())) {
                    edt_deskripsi.setError(getResources().getString(R.string.msg_cannot_allow_empty_field));
                }
                else if(TextUtils.isEmpty(edt_lokasi.getText().toString())) {
                    edt_lokasi.setError(getResources().getString(R.string.msg_cannot_allow_empty_field));
                }else{

                    GaleryApiClient client = (new Retrofit.Builder()
                            .baseUrl("https://galeryapp.herokuapp.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build())
                            .create(GaleryApiClient.class);

                    Call<ResponseBody> call = client.newFoto(nama,lokasi, deskripsi,lat, lng,gambar);
                    Log.e("gambar", gambar);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            ResponseBody s = response.body();
                            Toast.makeText(CreateActivity.this, "berhasil disimpan", Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(CreateActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });


                }
            }
        });
    }

    private void buildAlertMessageNoGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(CreateActivity.this);
        builder.setMessage("Hidupkan GPS mu")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
    }

    private void getLocation() {
        if((ActivityCompat.checkSelfPermission(CreateActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED )
                &&
                (ActivityCompat.checkSelfPermission(CreateActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(CreateActivity.this, new String [] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else{
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location!=null){
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                latitude = String.valueOf(lat);
                longtitude = String.valueOf(lng);
                edt_lat.setText("Latitude" + latitude +", longitute"+ longtitude);
            }
            else{
                Toast.makeText(CreateActivity.this,"lokasi gagal", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getBase64String(ImageView imageView) {

        BitmapDrawable d = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = d.getBitmap();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }




}
