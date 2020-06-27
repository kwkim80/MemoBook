package ca.algonquin.kw2446.memo.async;

import android.os.AsyncTask;

import ca.algonquin.kw2446.memo.model.Memo;
import ca.algonquin.kw2446.memo.persistence.MemoDao;

public class UpdateAsyncTask extends AsyncTask<Memo,Void,Void> {
    private MemoDao memoDao;

    public UpdateAsyncTask(MemoDao memoDao) { this.memoDao = memoDao; }

    @Override
    protected Void doInBackground(Memo... memos) {
        memoDao.updateMemos(memos);
        return null;
    }
}
