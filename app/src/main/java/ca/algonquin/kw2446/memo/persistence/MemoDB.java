package ca.algonquin.kw2446.memo.persistence;



import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import ca.algonquin.kw2446.memo.model.Memo;


@Database(entities = {Memo.class}, version = 1, exportSchema = true)
public abstract class MemoDB extends RoomDatabase {

    public static final String DATABASE_NAME = "MemoDB";

    private static MemoDB instance;

    static MemoDB getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MemoDB.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract MemoDao getMemoDao();


}