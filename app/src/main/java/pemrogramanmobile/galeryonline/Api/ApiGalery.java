package pemrogramanmobile.galeryonline.Api;

import pemrogramanmobile.galeryonline.GaleryData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiGalery {
    @GET("/api/galeries")
    Call<GaleryData> getGaleryData();



}
