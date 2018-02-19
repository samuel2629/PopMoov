
package com.silho.ideo.popmoov.model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoMainObject implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<VideoObject> results = null;
    public final static Parcelable.Creator<VideoMainObject> CREATOR = new Creator<VideoMainObject>() {


        @SuppressWarnings({
            "unchecked"
        })
        public VideoMainObject createFromParcel(Parcel in) {
            return new VideoMainObject(in);
        }

        public VideoMainObject[] newArray(int size) {
            return (new VideoMainObject[size]);
        }

    }
    ;

    protected VideoMainObject(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.results, (VideoObject.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public VideoMainObject() {
    }

    /**
     * 
     * @param id
     * @param results
     */
    public VideoMainObject(Integer id, List<VideoObject> results) {
        super();
        this.id = id;
        this.results = results;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<VideoObject> getResults() {
        return results;
    }

    public void setResults(List<VideoObject> results) {
        this.results = results;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(results);
    }

    public int describeContents() {
        return  0;
    }

}
