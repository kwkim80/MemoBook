package ca.algonquin.kw2446.memo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.StringTokenizer;

public class Memo implements Parcelable {

    private int id;
    private String title;
    private String content;
    private String category;
    private String timestamp;

    public Memo() { this("New Title",""); }
    public Memo(String title, String content) { this(title,content,""); }
    public Memo(String title, String content, String category) { this(title,content,category,""); }
    public Memo(String title, String content, String category, String regDate) {
       setTitle(title);setContent(content);setCategory(category);setTimestamp(regDate);
    }

    public Memo(Memo memo) {
        this(memo.title,memo.content, memo.category,memo.timestamp);
    }

    protected Memo(Parcel in) {
        id = in.readInt();
        title = in.readString();
        content = in.readString();
        category = in.readString();
        timestamp = in.readString();
    }

    public static final Creator<Memo> CREATOR = new Creator<Memo>() {
        @Override
        public Memo createFromParcel(Parcel in) {
            return new Memo(in);
        }

        @Override
        public Memo[] newArray(int size) {
            return new Memo[size];
        }
    };

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(category);
        dest.writeString(timestamp);
    }
}
