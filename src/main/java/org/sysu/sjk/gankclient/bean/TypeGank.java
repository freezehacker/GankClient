package org.sysu.sjk.gankclient.bean;

import java.util.List;

/**
 * Created by sjk on 2016/6/26.
 */
public class TypeGank extends BaseBean {

    boolean error;
    List<Gank> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Gank> getResults() {
        return results;
    }

    public void setResults(List<Gank> results) {
        this.results = results;
    }
}
