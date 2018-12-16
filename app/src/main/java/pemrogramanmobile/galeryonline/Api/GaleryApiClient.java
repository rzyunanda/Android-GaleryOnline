package pemrogramanmobile.galeryonline.Api;

import java.util.List;

import pemrogramanmobile.galeryonline.GaleryData;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GaleryApiClient {
    @GET("api/galeries")
    Call<GaleryData> getGaleryData();



}
