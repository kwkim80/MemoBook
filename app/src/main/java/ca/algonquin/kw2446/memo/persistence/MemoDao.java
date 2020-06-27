package ca.algonquin.kw2446.memo.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ca.algonquin.kw2446.memo.model.Memo;

@Dao
public interface MemoDao {

    @Insert
    long[] insertMemos(Memo... memos);

    @Query("SELECT * FROM memos")
    LiveData<List<Memo>> getMemos();

    @Delete
    int delete(Memo... memos);

    @Update
    int updateMemos(Memo... memos);
}
