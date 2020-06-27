package ca.algonquin.kw2446.memo.persistence;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ca.algonquin.kw2446.memo.async.DeleteAsyncTask;
import ca.algonquin.kw2446.memo.async.InsertAsyncTask;
import ca.algonquin.kw2446.memo.async.UpdateAsyncTask;
import ca.algonquin.kw2446.memo.model.Memo;

public class MemoRepository implements InsertAsyncTask.AsyncResponse {

    private MemoDB memoDB;
    private Context context;
    public MemoRepository(Context context) {

        context=context;
        memoDB = MemoDB.getInstance(context);
    }

    public long[] insertMemoTask(Memo memo){
        AsyncTask<Memo, Void, long[]> task= new InsertAsyncTask(this, memoDB.getMemoDao()).execute(memo);
        long[] result=null;
        try {
            result= task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void updateMemoTask(Memo memo){
        new UpdateAsyncTask(memoDB.getMemoDao()).execute(memo);
    }

    public LiveData<List<Memo>> retrieveMemosTask() {
        return memoDB.getMemoDao().getMemos();
    }

    public void deleteMemoTask(Memo memo){
        new DeleteAsyncTask(memoDB.getMemoDao()).execute(memo);
    }

    @Override
    public long[] processFinish(long[] longs) {
        long[] temp=longs;
        return temp;
    }
}
