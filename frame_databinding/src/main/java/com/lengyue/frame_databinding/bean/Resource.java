package com.lengyue.frame_databinding.bean;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * 这个用来拓展LiveData
 * @author linbin
 */
public class Resource<T> {
    /**
     * 状态
     * 这里有多个状态 0表示加载中；1表示成功；2表示联网失败；3表示接口虽然走通，但走的失败（如：关注失败）；4表示进度，只有下载文件和上传图片时才会有
     */
    private int state;
    private static final int LOADING = 0;
    private static final int SUCCESS = 1;
    private static final int ERROR = 2;
    private static final int FAIL = 3;
    private static final int PROGRESS = 4;

    private String errorMsg;
    private String errorCode;
    public T data;
    private Throwable error;

    /**
     * 这里和文件和进度有关了
     * percent表示进度 total表示总大小
     */
    private int percent;
    private long total;

    private Resource(int state, T data, String errorCode, String errorMsg) {
        this.state = state;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    private Resource(int state, Throwable error) {
        this.state = state;
        this.error = error;
    }

    private Resource(int state, int percent, long total) {
        this.state = state;
        this.percent = percent;
        this.total = total;
    }


    public static <T> Resource<T> loading(String showMsg) {
        return new Resource<>(LOADING, null, null, showMsg);
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(SUCCESS, data, null, null);
    }

    public static <T> Resource<T> response(ResponModel<T> data) {
        if (data != null) {
            if (data.isSuccess()) {
                return new Resource<>(SUCCESS, data.getRespData(), null, null);
            }
            return Resource.failure(data.getCode(), data.getMsg());
        }
        return new Resource<>(ERROR, null, null, null);
    }


    public static <T> Resource<T> failure(String code, String msg) {
        return new Resource<>(FAIL, null, code, msg);
    }

    public static <T> Resource<T> error(Throwable t) {
        return new Resource<>(ERROR, t);
    }

    public static <T> Resource<T> progress(int percent, long total) {
        return new Resource<>(PROGRESS, percent, total);
    }

    public void handler(OnHandleCallback<T> callback) {
        switch (state) {
            case LOADING:
                callback.onLoading(errorMsg);
                break;
            case SUCCESS:
                callback.onSuccess(data);
                break;
            case FAIL:
                callback.onFailure(errorCode, errorMsg);
                break;
            case ERROR:
                callback.onError(error);
                break;
            case PROGRESS:
                callback.onProgress(percent, total);
                break;
            default:
                break;
        }

        if (state != LOADING) {
            callback.onCompleted();
        }
    }


    public void handler(OnHandleCallback<T> callback, SmartRefreshLayout smartRefreshLayout) {
        switch (state) {
            case LOADING:
                callback.onLoading(errorMsg);
                break;
            case SUCCESS:
                callback.onSuccess(data);
                smartRefreshLayout.finishRefresh();
                smartRefreshLayout.finishLoadMore();
                break;
            case FAIL:
                callback.onFailure(errorCode, errorMsg);
                smartRefreshLayout.finishRefresh(false);
                smartRefreshLayout.finishLoadMore(false);
                break;
            case ERROR:
                callback.onError(error);
                smartRefreshLayout.finishRefresh(false);
                smartRefreshLayout.finishLoadMore(false);
                break;
            case PROGRESS:
                callback.onProgress(percent, total);
                break;
            default:
                break;
        }

        if (state != LOADING) {
            callback.onCompleted();
        }
    }


    public interface OnHandleCallback<T> {
        /**
         * 加载中
         * @param showMessage loading对话框显示的文字
         */
        void onLoading(String showMessage);

        /**
         * 成功
         * @param data 数据
         */
        void onSuccess(T data);

        /**
         * 失败
         * @param code code
         * @param msg 信息
         */
        void onFailure(String code, String msg);

        /**
         * 错误
         * @param error 错误
         */
        void onError(Throwable error);

        /**
         * 完成
         */
        void onCompleted();

        /**
         * 进度
         * @param percent 进度
         * @param total 总大小
         */
        void onProgress(int percent, long total);
    }


}