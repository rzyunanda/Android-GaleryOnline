package pemrogramanmobile.galeryonline.room;
import android.arch.persistence.room.RoomDatabase;

@android.arch.persistence.room.Database(entities  = {galery.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract galeryDao dataFotoDao();
    public abstract FavoritDao pesertaFavoriteDao();
}
