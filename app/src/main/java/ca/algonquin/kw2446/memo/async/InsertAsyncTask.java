package ca.algonquin.kw2446.memo.async;

import android.os.AsyncTask;

import ca.algonquin.kw2446.memo.model.Memo;
import ca.algonquin.kw2446.memo.persistence.MemoDao;

public class InsertAsyncTask extends AsyncTask<Memo,Void,long[]> {
    private MemoDao memoDao;

    public interface AsyncResponse {
        long[] processFinish(long[] longs);
    }

    public AsyncResponse delegate = null;

    public InsertAsyncTask(AsyncResponse delegate, MemoDao memoDao) {
        this.delegate=delegate;
        this.memoDao = memoDao; }

    @Override
    protected long[] doInBackground(Memo... memos) {
        return memoDao.insertMemos(memos);
    }

    @Override
    protected void onPostExecute(long[] longs) {
        delegate.processFinish(longs);
    }
}
