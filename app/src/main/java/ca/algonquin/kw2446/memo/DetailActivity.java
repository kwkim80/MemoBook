package ca.algonquin.kw2446.memo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ca.algonquin.kw2446.memo.model.Memo;
import ca.algonquin.kw2446.memo.util.Utility;

public class DetailActivity extends AppCompatActivity implements TextWatcher {

    private static final String TAG = "DetaileActivity";
    private static final boolean EDIT_MODE_ENABLED = true;
    private static final boolean EDIT_MODE_DISABLED = false;

    RelativeLayout rlCheck, rlBack;
    ImageButton ibCheck, ibBack;
    LinedEditText letContent;
    TextView tvTitle;
    EditText etTitle;


    private Memo initialNote, finalNote;
    private boolean edit_Mode;
    private boolean isNewMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        rlCheck=findViewById(R.id.rlCheck);
        rlBack=findViewById(R.id.rlBack);
        ibCheck=findViewById(R.id.ibCheck);
        ibBack=findViewById(R.id.ibBack);
        letContent=findViewById(R.id.letContent);
        tvTitle=findViewById(R.id.tvTitle);
        etTitle=findViewById(R.id.etTitle);

        setMemo(getSelectedNote());

        ibCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { if(edit_Mode) changeMode(EDIT_MODE_DISABLED); }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });

        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edit_Mode) changeMode(EDIT_MODE_ENABLED);
                etTitle.requestFocus();
                etTitle.setSelection(etTitle.length());
            }
        });

        letContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
                public boolean onTouch(View v, MotionEvent event) {
                     return gestureDetector.onTouchEvent(event);
                }
                private GestureDetector gestureDetector = new GestureDetector(DetailActivity.this, new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        if(!edit_Mode) changeMode(EDIT_MODE_ENABLED);
                        return super.onDoubleTap(e);
                    }
                    @Override
                    public void onLongPress(MotionEvent e) {
                        super.onLongPress(e);
                        if(!edit_Mode) changeMode(EDIT_MODE_ENABLED);
                    }
            });
        });

        etTitle.addTextChangedListener(this);
    }

    private boolean getSelectedNote(){
        Intent intent=getIntent();
        edit_Mode = EDIT_MODE_ENABLED;  //to set default to change mode
        isNewMemo = true;
        if(intent.hasExtra("selectedMemo")){
            initialNote = intent.getParcelableExtra("selectedMemo");
            finalNote =new Memo(initialNote);
            finalNote.setId(initialNote.getId());
            edit_Mode = EDIT_MODE_DISABLED;
            isNewMemo = false;
        }
        return isNewMemo;
    }

    private void setMemo(boolean isNewMemo){
        if(isNewMemo){
            initialNote=new Memo();
            finalNote=new Memo();
        }
        tvTitle.setText(initialNote.getTitle());
        etTitle.setText(initialNote.getTitle());
        letContent.setText(initialNote.getContent());
        changeMode(edit_Mode);
    }

    private void changeMode(boolean isEditMode){

            edit_Mode = isEditMode;
           rlBack.setVisibility(edit_Mode?View.GONE:View.VISIBLE);
           rlCheck.setVisibility(edit_Mode?View.VISIBLE:View.GONE);
           tvTitle.setVisibility(edit_Mode?View.GONE:View.VISIBLE);
           etTitle.setVisibility(edit_Mode?View.VISIBLE:View.GONE);

            enableContentInteraction();

    }
    private void enableContentInteraction( ){
        letContent.setKeyListener(edit_Mode?new EditText(this).getKeyListener():null);
        letContent.setFocusable(edit_Mode);
        letContent.setFocusableInTouchMode(edit_Mode);
        letContent.setCursorVisible(edit_Mode);
        if(edit_Mode)letContent.requestFocus();
        else letContent.clearFocus();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("mode", edit_Mode);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        edit_Mode = savedInstanceState.getBoolean("mode");
        if(edit_Mode == EDIT_MODE_ENABLED){
            changeMode(edit_Mode);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        tvTitle.setText(s.toString()); }
    @Override
    public void afterTextChanged(Editable s) { }

    @Override
    public void onBackPressed() {
        if(edit_Mode == EDIT_MODE_ENABLED){
         ibCheck.callOnClick();
        }
        else{
            super.onBackPressed();
        }
    }
}
