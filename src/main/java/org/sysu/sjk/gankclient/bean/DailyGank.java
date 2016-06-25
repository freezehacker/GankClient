package org.sysu.sjk.gankclient.bean;

import java.util.List;

/**
 * Created by sjk on 2016/6/25.
 */
public class DailyGank extends BaseBean {

    private List<String> category;
    private boolean error;
    private Results results;    // Android, iOS, "瞎推荐"等

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    //-------------------------------------------------------------
    public static class Results {
        private List<Gank> Android;
        private List<Gank> iOS;
        //...

        public List<Gank> getAndroid() {
            return Android;
        }

        public List<Gank> getiOS() {
            return iOS;
        }

        public void setAndroid(List<Gank> android) {
            Android = android;
        }

        public void setiOS(List<Gank> iOS) {
            this.iOS = iOS;
        }
    }
}
