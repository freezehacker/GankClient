package org.sysu.sjk.gankclient.bean;

import java.util.List;

/**
 * Created by sjk on 2016/6/25.
 */
public class DateList {

    boolean error;
    List<String> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }
}
