package pemrogramanmobile.galeryonline;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class CreateActivity extends AppCompatActivity {

    EditText edt_nama, edt_deskripsi, edt_path, edt_lat;
    ImageView imageView ;
    Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        edt_nama = findViewById(R.id.edt_nama);
        edt_deskripsi = findViewById(R.id.edt_deskripsi);
        edt_path = findViewById(R.id.edt_path);
        edt_lat = findViewById(R.id.edt_lat);
        imageView = findViewById(R.id.imageView);
    }



}
