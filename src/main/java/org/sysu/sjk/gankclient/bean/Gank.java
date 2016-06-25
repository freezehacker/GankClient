package org.sysu.sjk.gankclient.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 一条Gank
 * Created by sjk on 2016/6/25.
 */
public class Gank extends BaseBean implements Parcelable {


    /**
     * _id : 56cc6d23421aa95caa707a69
     * createdAt : 2015-08-06T07:15:52.65Z
     * desc : 类似Link Bubble的悬浮式操作设计
     * publishedAt : 2015-08-07T03:57:48.45Z
     * type : Android
     * url : https://github.com/recruit-lifestyle/FloatingView
     * used : true
     * who : mthli
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private boolean isRead = false; // 有没有看过，用来标记已经看过的item，初始值(默认)为false

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeString(this.createdAt);
        dest.writeString(this.desc);
        dest.writeString(this.publishedAt);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeString(this.who);
        dest.writeByte(isRead ? (byte) 1 : (byte) 0); // 没有writeBoolean这个函数，用writeByte来代替
        dest.writeByte(used ? (byte) 1 : (byte) 0);   // 同上
    }

    /**
     * 默认构造器，是为了下面Creator做准备
     */
    public Gank() {
    }

    /**
     * 有参构造器，是为了下面Creator做准备
     *
     * @param in Parcelable读取器，注意这里的次序要和上面写入的次序是一样的！
     */
    protected Gank(Parcel in) {
        this._id = in.readString();
        this.createdAt = in.readString();
        this.desc = in.readString();
        this.publishedAt = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.who = in.readString();
        this.isRead = (in.readByte() != 0);
        this.used = (in.readByte() != 0);
    }

    public static final Parcelable.Creator<Gank> CREATOR = new Parcelable.Creator<Gank>() {
        @Override
        public Gank createFromParcel(Parcel source) {
            return new Gank(source);
        }

        @Override
        public Gank[] newArray(int size) {
            return new Gank[size];
        }
    };
}
