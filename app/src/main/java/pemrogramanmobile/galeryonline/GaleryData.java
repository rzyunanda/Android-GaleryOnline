package pemrogramanmobile.galeryonline;

import android.graphics.Movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import pemrogramanmobile.galeryonline.Model.Galery;

public class GaleryData {
    @SerializedName("results")
    @Expose
    public List<Galery> results = null;
}
