package com.camp.bit.todolist.db;

import android.provider.BaseColumns;

/**
 * Created on 2019/1/22.
 *
 * @author xuyingyi@bytedance.com (Yingyi Xu)
 */
public final class TodoContract {

    // TODO 定义表结构和 SQL 语句常量
    private TodoContract() {
    }

    public static class TodoEntry implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_STATE = "state";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_URGENCY = "urgency";
    }

    //建表
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TodoEntry.TABLE_NAME +
                    "(" +
                    TodoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TodoEntry.COLUMN_DATE + " INTEGER," +
                    TodoEntry.COLUMN_STATE + " INTEGER," +
                    TodoEntry.COLUMN_CONTENT + " TEXT" +
                    ")";

    //删表
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TodoEntry.TABLE_NAME;

    //更新
    //e.g. ALTER TABLE new_table ADD COLUMN sex Text;
    public static final String SQL_UPDATE =
            "ALTER TABLE " + TodoEntry.TABLE_NAME + " ADD COLUMN " + TodoEntry.COLUMN_URGENCY + " INTEGER";
}
