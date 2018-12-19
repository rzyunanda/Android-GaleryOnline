package pemrogramanmobile.galeryonline.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import pemrogramanmobile.galeryonline.Model.Galery;

@Dao
public interface galeryDao {
    //query
    @Query("Select * From galery")
    //method
    List<galery> getAllDataFoto();

    @Insert
    void insertDataFoto(galery...galeries);
    //artinya bisa banyak data sekaligus. kata awal merupakan objek dan kata kedua adalah data

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDataFoto(galery galeries);

    @Query("SELECT * FROM galery WHERE id =:id")
    Galery selectFoto(int id);


    @Query("UPDATE galery SET fav= :fav WHERE id = :id")
    int updateFav(int id, int fav);

    @Query("SELECT * FROM galery where fav=1")
    List<galery> getDataFavorit();
}
