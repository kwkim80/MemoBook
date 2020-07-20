package ca.algonquin.kw2446.memo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import ca.algonquin.kw2446.memo.adapter.MemoAdapter;
import ca.algonquin.kw2446.memo.model.Memo;
import ca.algonquin.kw2446.memo.persistence.MemoRepository;
import ca.algonquin.kw2446.memo.util.VerticalSpacingItemDecorator;

public class MainActivity extends AppCompatActivity implements MemoAdapter.MemoItemClicked{

    private static final String TAG = "MainActivity";

    ArrayList<Memo> list;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private MemoRepository mRepository;
    private SwipeRefreshLayout sRefresh;

    //FloatingActionButton fabAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list=new ArrayList<>();
        //insertFakeNotes();

        initialRecyclerView();
        mRepository=new MemoRepository(this);
        this.retrieveMemos();
        //fabAdd=findViewById(R.id.fabAdd);

        Toolbar toolbar=(Toolbar)findViewById(R.id.tbMemo);
        setSupportActionBar(toolbar);
        setTitle("Memo Book");

        sRefresh=findViewById(R.id.sRefresh);
        sRefresh.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
        sRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sRefresh.setRefreshing(true);
                retrieveMemos();
                sRefresh.setRefreshing(false);
            }
        });


    }

    @Override
    public void onItemClicked(int i) {
        Intent intent=new Intent(this,DetailActivity.class);
        intent.putExtra("selectedMemo",list.get(i));
        startActivity(intent);
    }

    private void insertFakeNotes(){
        for(int i = 0; i < 1000; i++){
            Memo memo = new Memo();
            memo.setTitle("title #" + i);
            memo.setContent("content #: " + i);
            memo.setTimestamp("Jan 2019");
            memo.setCategory("etc");
            list.add(memo);
        }
      //  adapter.notifyDataSetChanged();
    }
    private void retrieveMemos() {

        mRepository.retrieveMemosTask().observe(this, new Observer<List<Memo>>() {
            @Override
            public void onChanged(@Nullable List<Memo> memos) {
                if(list.size() > 0){
                    list.clear();
                }
                if(memos != null){
                    list.addAll(memos);
                }
                adapter.notifyDataSetChanged();
            }
        });

    }
    private void initialRecyclerView(){
        recyclerView=findViewById(R.id.rvList);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        recyclerView.addItemDecoration(itemDecorator);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new MemoAdapter(this,list);
        recyclerView.setAdapter(adapter);

        
    }

    public void memoAdd(View v){
        Intent intent=new Intent(this,DetailActivity.class);
        startActivity(intent);
    }

    public void deleteMemo(Memo memo){
            list.remove(memo);
            adapter.notifyDataSetChanged();

            mRepository.deleteMemoTask(memo);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback=new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteMemo(list.get(viewHolder.getAdapterPosition()));
        }


    };



}
