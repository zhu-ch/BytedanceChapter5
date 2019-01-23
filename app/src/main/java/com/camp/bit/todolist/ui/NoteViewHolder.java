package com.camp.bit.todolist.ui;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.camp.bit.todolist.NoteOperator;
import com.camp.bit.todolist.R;
import com.camp.bit.todolist.beans.Note;
import com.camp.bit.todolist.beans.Priority;
import com.camp.bit.todolist.beans.State;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created on 2019/1/23.
 *
 * @author xuyingyi@bytedance.com (Yingyi Xu)
 */
public class NoteViewHolder extends RecyclerView.ViewHolder {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT =
            new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.ENGLISH);

    private final NoteOperator operator;

    private CheckBox checkBox;
    private TextView contentText;
    private TextView dateText;
    private View deleteBtn;
    private RelativeLayout myLayout;

    public NoteViewHolder(@NonNull View itemView, NoteOperator operator) {
        super(itemView);
        this.operator = operator;

        checkBox = itemView.findViewById(R.id.checkbox);
        contentText = itemView.findViewById(R.id.text_content);
        dateText = itemView.findViewById(R.id.text_date);
        deleteBtn = itemView.findViewById(R.id.btn_delete);
        myLayout = itemView.findViewById(R.id.my_layout);

    }

    public void bind(final Note note) {
        contentText.setText(note.getContent());
        dateText.setText(SIMPLE_DATE_FORMAT.format(note.getDate()));

        checkBox.setOnCheckedChangeListener(null);
        checkBox.setChecked(note.getState() == State.DONE);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                note.setState(isChecked ? State.DONE : State.TODO);
                operator.updateNote(note);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operator.deleteNote(note);
            }
        });

        // TODO: 2019/1/23 改变背景颜色
        if (note.getPriority() == Priority.URGENT) {
            if (note.getState() == State.DONE) {
                myLayout.setBackgroundColor(Color.rgb(232, 232, 232));
                contentText.setTextColor(Color.GRAY);
                contentText.setPaintFlags(contentText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                dateText.setTextColor(Color.rgb(255, 127, 80));
                myLayout.setBackgroundColor(Color.rgb(238 ,197,145));
                contentText.setTextColor(Color.RED);
                contentText.setPaintFlags(contentText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
        } else {
            myLayout.setBackgroundColor(Color.WHITE);
            if (note.getState() == State.DONE) {
                myLayout.setBackgroundColor(Color.rgb(232, 232, 232));
                contentText.setTextColor(Color.GRAY);
                contentText.setPaintFlags(contentText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                contentText.setTextColor(Color.BLACK);
                contentText.setPaintFlags(contentText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }
}
