package com.camp.bit.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.camp.bit.todolist.beans.Priority;
import com.camp.bit.todolist.beans.State;
import com.camp.bit.todolist.db.TodoContract;
import com.camp.bit.todolist.db.TodoDbHelper;

public class NoteActivity extends AppCompatActivity {

    private EditText editText;
    private Button addBtn;
    private RadioButton commonBtn;
    private RadioButton urgentBtn;
    TodoDbHelper mDbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setTitle(R.string.take_a_note);

        editText = findViewById(R.id.edit_text);
        editText.setFocusable(true);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.showSoftInput(editText, 0);
        }

        addBtn = findViewById(R.id.btn_add);
        commonBtn = findViewById(R.id.common);
        urgentBtn = findViewById(R.id.urgent);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence content = editText.getText();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(NoteActivity.this,
                            "No content to add", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (commonBtn.isChecked() == false && urgentBtn.isChecked() == false) {
                    Toast.makeText(NoteActivity.this,
                            "Choose priority", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean succeed = saveNote2Database(content.toString().trim());
                if (succeed) {
                    Toast.makeText(NoteActivity.this,
                            "Note added", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                } else {
                    Toast.makeText(NoteActivity.this,
                            "Error", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDbHelper.close();
        mDbHelper = null;
    }

    private boolean saveNote2Database(String content) {
        // TODO 插入一条新数据，返回是否插入成功
        mDbHelper = new TodoDbHelper(this);//https://q.cnblogs.com/q/32567/
        db = mDbHelper.getWritableDatabase();
        if (db == null) {
            return false;
        }
        ContentValues values = new ContentValues();
        values.put(TodoContract.TodoEntry.COLUMN_DATE, System.currentTimeMillis());
        values.put(TodoContract.TodoEntry.COLUMN_STATE, State.TODO.intValue);
        values.put(TodoContract.TodoEntry.COLUMN_CONTENT, content);
        values.put(TodoContract.TodoEntry.COLUMN_PRIORITY,
                commonBtn.isChecked() ? Priority.COMMON.intValue : Priority.URGENT.intValue);
        long rowId = db.insert(TodoContract.TodoEntry.TABLE_NAME, null, values);
        return rowId != -1;
    }
}
