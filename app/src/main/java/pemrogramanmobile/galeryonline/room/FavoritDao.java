package pemrogramanmobile.galeryonline.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

@Dao
public class FavoritDao {
    @Query("SELECT * FROM favorites WHERE id = :id")
    Favorite getPesertaFavoritesbyName(int id);

    @Query("DELETE FROM favorites WHERE id = :id")
    void delete(int id);

    @Query("SELECT * FROM favorites")
    List<Favorite> getPesertaFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPesertaFavorites(Favorite favorite);
}
