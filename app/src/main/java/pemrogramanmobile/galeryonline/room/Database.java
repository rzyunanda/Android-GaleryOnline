package pemrogramanmobile.galeryonline.room;
import android.arch.persistence.room.RoomDatabase;

@android.arch.persistence.room.Database(entities = {Favorite.class},version = 1,exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract FavoritDao galeryFavoriteDao();
}
