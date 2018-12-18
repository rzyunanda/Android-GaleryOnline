package pemrogramanmobile.galeryonline.Api;

import java.util.List;

import okhttp3.ResponseBody;
import pemrogramanmobile.galeryonline.GaleryData;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GaleryApiClient {
    @GET("api/galeries")
    Call<GaleryData> getGaleryData();

    @FormUrlEncoded
    @POST("api/daftar")
    Call<ResponseBody> newFoto(
            @Field("nama") String nama,
            @Field("deskripsi") String deskripsi,
            @Field("lokasi") String lokasi,
            @Field("lat") Double lat,
            @Field("lng") Double lng,
            @Field("gambar") String gambar
    );



}
