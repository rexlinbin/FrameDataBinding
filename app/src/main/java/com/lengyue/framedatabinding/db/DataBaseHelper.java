package com.lengyue.framedatabinding.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DataBaseHelper {
    private DataBaseOpenHelper sqLiteOpenHelper;
    private Class classz;
    public DataBaseHelper(Context context, String name, Class classz){
        sqLiteOpenHelper = new DataBaseOpenHelper(context, name, classz);
        this.classz = classz;
    }

    public SQLiteOpenHelper getSqLiteOpenHelper() {
        return sqLiteOpenHelper;
    }

    public void insert(Object data){
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        ContentValues contentValues = getContentValues(data);
        db.insert(classz.getSimpleName(), null, contentValues);
        db.close();
    }

    private ContentValues getContentValues(Object data){
        Class classz = data.getClass();
        Field[] fields = classz.getDeclaredFields();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            Class fieldType = field.getType();
            try {
                Method method = classz.getDeclaredMethod("get" + getMethodName(field.getName()));
                method.setAccessible(true);
                if (fieldType == int.class){
                    contentValues.put(field.getName(), (int) method.invoke(data));
                }else if (fieldType == double.class){
                    contentValues.put(field.getName(), (double) method.invoke(data));
                }else if (fieldType == boolean.class){
                    contentValues.put(field.getName(), (boolean) method.invoke(data));
                }else {
                    contentValues.put(field.getName(), (String) method.invoke(data));
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return contentValues;
    }

    /**
     * 将属性名称的首字母变成大写
     * */
    private String getMethodName(String fieldName) {
        byte[] bytes = fieldName.getBytes();
        bytes[0] = (byte) (bytes[0] - 'a' + 'A');
        return new String(bytes);
    }
}
