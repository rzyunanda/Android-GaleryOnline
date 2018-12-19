package pemrogramanmobile.galeryonline.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavoritDao {
    @Query("SELECT * FROM favorites WHERE id = :id")
    Favorite getGaleryFavoritesbyName(int id);

    @Query("DELETE FROM favorites WHERE id = :id")
    void delete(int id);

    @Query("SELECT * FROM favorites")
    List<Favorite> getGaleryFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGaleryFavorites(Favorite favorite);
}
