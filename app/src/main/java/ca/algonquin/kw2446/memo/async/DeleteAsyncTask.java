package ca.algonquin.kw2446.memo.async;

import android.os.AsyncTask;

import ca.algonquin.kw2446.memo.model.Memo;
import ca.algonquin.kw2446.memo.persistence.MemoDao;

public class DeleteAsyncTask extends AsyncTask<Memo, Void, Void> {

    private MemoDao memoDao;

    public DeleteAsyncTask(MemoDao memoDao) { this.memoDao = memoDao; }

    @Override
    protected Void doInBackground(Memo... memos) {
        memoDao.delete(memos);
        return null;
    }
}
