
package com.silho.ideo.popmoov.model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainResultObject implements Parcelable
{

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private List<MovieObject> results = null;
    public final static Parcelable.Creator<MainResultObject> CREATOR = new Creator<MainResultObject>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MainResultObject createFromParcel(Parcel in) {
            return new MainResultObject(in);
        }

        public MainResultObject[] newArray(int size) {
            return (new MainResultObject[size]);
        }

    }
    ;

    protected MainResultObject(Parcel in) {
        this.page = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalResults = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalPages = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.results, (MovieObject.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public MainResultObject() {
    }

    /**
     * 
     * @param results
     * @param totalResults
     * @param page
     * @param totalPages
     */
    public MainResultObject(Integer page, Integer totalResults, Integer totalPages, List<MovieObject> results) {
        super();
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.results = results;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<MovieObject> getResults() {
        return results;
    }

    public void setResults(List<MovieObject> results) {
        this.results = results;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(page);
        dest.writeValue(totalResults);
        dest.writeValue(totalPages);
        dest.writeList(results);
    }

    public int describeContents() {
        return  0;
    }

}
