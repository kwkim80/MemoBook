package ca.algonquin.kw2446.memo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import ca.algonquin.kw2446.memo.adapter.MemoAdapter;
import ca.algonquin.kw2446.memo.model.Memo;
import ca.algonquin.kw2446.memo.util.VerticalSpacingItemDecorator;

public class MainActivity extends AppCompatActivity implements MemoAdapter.MemoItemClicked{

    private static final String TAG = "MainActivity";

    ArrayList<Memo> list;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    FloatingActionButton fabAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list=new ArrayList<>();
        insertFakeNotes();

        initialRecyclerView();

        setSupportActionBar((Toolbar)findViewById(R.id.tbMemo));

        fabAdd=findViewById(R.id.fabAdd);

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
