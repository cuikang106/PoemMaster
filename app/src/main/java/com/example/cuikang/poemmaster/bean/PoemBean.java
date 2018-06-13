package com.example.cuikang.poemmaster.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by IDesign_Lab on 2018/6/8.
 */
public class PoemBean implements Parcelable {
    /**
     * strains : ["平平平仄仄，平仄仄平平。","仄仄平平仄，平平仄仄平。","平平平仄仄，平仄仄平平。","平仄仄平仄，平平仄仄平。"]
     * author : 太宗皇帝
     * paragraphs : ["秦川雄帝宅，函谷壮皇居。","绮殿千寻起，离宫百雉余。","连甍遥接汉，飞观迥凌虚。","云日隐层阙，风烟出绮疏。"]
     * title : 帝京篇十首 一
     */
    private int sequence;
    private String author;
    private String title;
    private List<String> strains;
    private List<String> paragraphs;

    public String getSequence() { return sequence+""; }

    public void setSequence(int sequence) {
        this.sequence=sequence;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getStrains() {
        return strains;
    }

    public void setStrains(List<String> strains) {
        this.strains = strains;
    }

    public List<String> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<String> paragraphs) {
        this.paragraphs = paragraphs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeString(this.title);
        dest.writeStringList(this.strains);
        dest.writeStringList(this.paragraphs);
    }

    public PoemBean() {
    }

    protected PoemBean(Parcel in) {
        this.author = in.readString();
        this.title = in.readString();
        this.strains = in.createStringArrayList();
        this.paragraphs = in.createStringArrayList();
    }

    public static final Parcelable.Creator<PoemBean> CREATOR = new Parcelable.Creator<PoemBean>() {
        @Override
        public PoemBean createFromParcel(Parcel source) {
            return new PoemBean(source);
        }

        @Override
        public PoemBean[] newArray(int size) {
            return new PoemBean[size];
        }
    };
}
