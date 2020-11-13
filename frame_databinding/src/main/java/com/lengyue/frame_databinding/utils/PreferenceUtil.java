package com.lengyue.frame_databinding.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * @author linbin
 */
public final class PreferenceUtil {

    private String FILE_NAME = "my_pre";
    private Context context;

    private static class SingleTon{
        private static PreferenceUtil instance = new PreferenceUtil();
    }

    private PreferenceUtil() {

    }

    public static PreferenceUtil getInstance(){
        return SingleTon.instance;
    }

    public PreferenceUtil setFileName(String fileName){
        FILE_NAME = fileName;
        return this;
    }

    public PreferenceUtil setContext(Context context){
        this.context = context;
        return this;
    }
    
    /**
     * 这里是对基本数据类型进行的操作
     */
    public void put(String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.apply();
    }

    /**
     * 这里是根据key，获取数据。表名是 -- FILE_NAME
     * 第二个参数是  默认值
     */
    public Object get(String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }


    /**
     * 根据某个key值获取数据  表名 -- FILE_NAME
     */
    public void remove(String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 清楚 表名 -- FILE_NAME 里所有的数据
     */
    public void clear() {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 判断当前key值 是否存在于  表名--FILE_NAME 表里
     */
    public boolean contains(String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }


    /**
     * 返回表名 -- FILE_NAME里所有的数据，以Map键值对的方式
     */
    public Map<String, ?> getAll() {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }


    /**
     * 以下是保存类的方式，跟上面的FILE_NAME表不在一个表里
     */
    public <T extends Serializable> boolean putByClass(String key, T entity) {
        if (entity == null) {
            return false;
        }
        String prefFileName = entity.getClass().getName();
        SharedPreferences sp = context.getSharedPreferences(prefFileName, 0);
        SharedPreferences.Editor et = sp.edit();
        String json = GsonUtil.ser(entity);
        et.putString(key, json);
        return et.commit();
    }


    /**
     * 获取某个以class 为表名的。所有class 对象
     */
    public <T extends Serializable> List<T> getAllByClass(Class<T> clazz) {
        String prefFileName = clazz.getName();
        SharedPreferences sp = context.getSharedPreferences(prefFileName, 0);
        Map<String, String> values = (Map<String, String>) sp.getAll();
        List<T> results = new ArrayList<T>();
        if (values == null || values.isEmpty())
            return results;
        Collection<String> colles = values.values();
        for (String json : colles) {
            results.add(GsonUtil.deser(json, clazz));
        }
        return results;
    }

    /**
     * 获取以类名为表名的，某个key值上的value
     * 第二个参数是，类名class,也就是当前的表名
     */
    public <T extends Serializable> T getByClass(String key, Class<T> clazz) {
        String prefFileName = clazz.getName();
        SharedPreferences sp = context.getSharedPreferences(prefFileName, 0);

        String json = sp.getString(key, null);
        if (json == null) {
            return null;
        }
        return GsonUtil.deser(json, clazz);
    }

    /**
     * 在以类名为表名的表上，移除key值
     * 第二个参数是，类名class,也就是当前的表名
     */
    public <T extends Serializable> void removeByClass(String key, Class<T> clazz) {
        String prefFileName = clazz.getName();
        SharedPreferences sp = context.getSharedPreferences(prefFileName, 0);
        if (sp.contains(key)) {
            sp.edit().remove(key).apply();
        }
    }

    /**
     * 移除 某个以类名为表名上的所有的值
     */
    public <T extends Serializable> void clearByClass(Class<T> clazz) {
        String prefFileName = clazz.getName();
        SharedPreferences sp = context.getSharedPreferences(prefFileName, 0);
        sp.edit().clear().apply();
    }

}
