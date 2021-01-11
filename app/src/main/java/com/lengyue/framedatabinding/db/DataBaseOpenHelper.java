package com.lengyue.framedatabinding.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author linbin
 */
public class DataBaseOpenHelper extends SQLiteOpenHelper {
    private Class classz;
    private List<String> columnNames = new ArrayList<>();
    public DataBaseOpenHelper(@Nullable Context context, @Nullable String name, Class classz) {
        super(context, name, null, 1);
        this.classz = classz;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tableName = classz.getSimpleName();
        Field[] fields = classz.getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("create table ");
        stringBuilder.append(tableName);
        stringBuilder.append("(");
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            columnNames.add(field.getName());
            stringBuilder.append(field.getName());
            stringBuilder.append(" ");
            Class fieldType = field.getType();
            if (fieldType == boolean.class){
                stringBuilder.append("integer");
            }else if (fieldType == int.class){
                stringBuilder.append("integer");
            }else if (fieldType == double.class){
                stringBuilder.append("real");
            }else{
                stringBuilder.append("varchar");
            }

            if (i != fields.length - 1) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append(")");
        sqLiteDatabase.execSQL(stringBuilder.toString());
        sqLiteDatabase.close();
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
