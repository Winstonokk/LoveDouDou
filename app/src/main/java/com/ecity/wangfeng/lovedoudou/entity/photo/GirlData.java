package com.ecity.wangfeng.lovedoudou.entity.photo;

import java.util.List;

/**
 * @version 1.0
 * Created by Administrator on 2016/11/29.
 */

public class GirlData {

    private boolean isError;
    private List<PhotoGirl> results;

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public List<PhotoGirl> getResults() {
        return results;
    }

    public void setResults(List<PhotoGirl> results) {
        this.results = results;
    }
}
