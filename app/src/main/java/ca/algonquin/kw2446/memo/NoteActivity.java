package ca.algonquin.kw2446.memo;




import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ca.algonquin.kw2446.memo.model.Memo;


public class NoteActivity extends AppCompatActivity implements
        View.OnClickListener
        ,TextWatcher
{

    private static final String TAG = "NoteActivity";
    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;

    // UI components
    private LinedEditText letContent;
    private EditText etTitle;
    private TextView tvTitle;
    private RelativeLayout rlCheck, rlBack;
    private ImageButton ibCheck, ibBack;


    // vars
    private boolean isNewMemo;
    private Memo initialMemo, finalMemo;
    private GestureDetector mGestureDetector;
    private int mMode;
   // private NoteRepository mNoteRepository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        letContent = findViewById(R.id.letContent);
        etTitle = findViewById(R.id.etTitle);
        tvTitle = findViewById(R.id.tvTitle);
        ibCheck = findViewById(R.id.ibCheck);
        ibBack = findViewById(R.id.ibBack);
        rlCheck = findViewById(R.id.rlCheck);
        rlBack = findViewById(R.id.rlBack);

        //mNoteRepository = new NoteRepository(this);
        setListeners();
        setMemo(getSelectedNote());

        letContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }

            private GestureDetector gestureDetector = new GestureDetector(NoteActivity.this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    changeMode(EDIT_MODE_ENABLED);
                    return super.onDoubleTap(e);
                }
                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);
                    changeMode(EDIT_MODE_ENABLED);
                }
            });
        });
    }


    private void setListeners(){

        ibCheck.setOnClickListener(this);
        tvTitle.setOnClickListener(this);
        ibBack.setOnClickListener(this);
        etTitle.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {
            switch (view.getId()){
                case R.id.ibBack:{ finish();break; }
                case R.id.ibCheck:{ changeMode(EDIT_MODE_DISABLED);break; }
                case R.id.tvTitle:{
                    changeMode(EDIT_MODE_ENABLED);
                    etTitle.requestFocus();
                    etTitle.setSelection(etTitle.length());
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(mMode == EDIT_MODE_ENABLED){
            onClick(ibCheck);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mode", mMode);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMode = savedInstanceState.getInt("mode");
        if(mMode == EDIT_MODE_ENABLED){
            changeMode(mMode);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        tvTitle.setText(charSequence.toString()); }

    @Override
    public void afterTextChanged(Editable editable) { }


    private boolean getSelectedNote(){
        Intent intent=getIntent();
        mMode = EDIT_MODE_ENABLED;  //to set default to change mode
        isNewMemo = true;
        if(intent.hasExtra("selectedMemo")){
            initialMemo = intent.getParcelableExtra("selectedMemo");

            finalMemo =new Memo(initialMemo);
            finalMemo.setId(initialMemo.getId());

            mMode = EDIT_MODE_DISABLED;
            isNewMemo = false;
        }

        return isNewMemo;

    }

    private void setMemo(boolean isNewMemo){
        if(isNewMemo){
            initialMemo =new Memo();
            finalMemo =new Memo();
        }
        tvTitle.setText(initialMemo.getTitle());
        etTitle.setText(initialMemo.getTitle());
        letContent.setText(initialMemo.getContent());
        changeMode(isNewMemo?EDIT_MODE_ENABLED:EDIT_MODE_DISABLED);
    }

    private void changeMode(int toBeEditMode){

        if(toBeEditMode==EDIT_MODE_ENABLED){
            rlBack.setVisibility(View.GONE);
            rlCheck.setVisibility(View.VISIBLE);
            tvTitle.setVisibility(View.GONE);
            etTitle.setVisibility(View.VISIBLE);

        }
        else{
            rlBack.setVisibility(View.VISIBLE);
            rlCheck.setVisibility(View.GONE);
            tvTitle.setVisibility(View.VISIBLE);
            etTitle.setVisibility(View.GONE);
        }
        mMode = toBeEditMode;
        enableContentInteraction(mMode);

    }



    private void enableContentInteraction(int enable){
        letContent.setKeyListener(enable==EDIT_MODE_ENABLED?new EditText(this).getKeyListener():null);
        letContent.setFocusable(enable==EDIT_MODE_ENABLED);
        letContent.setFocusableInTouchMode(enable==EDIT_MODE_ENABLED);
        letContent.setCursorVisible(enable==EDIT_MODE_ENABLED);
        if(enable==EDIT_MODE_ENABLED)letContent.requestFocus();
        else letContent.clearFocus();
    }



    private void saveChanges(){
        if(isNewMemo){
            saveNewNote();
        }else{
            updateNote();
        }
    }

    public void updateNote() {
        //mNoteRepository.updateNoteTask(mNoteFinal);

    }

    public void saveNewNote() {
        //mNoteRepository.insertNoteTask(mNoteFinal);
    }

}



















