package ca.algonquin.kw2446.memo.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.StringTokenizer;

@Entity(tableName = "memos")
public class Memo implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="title")
    private String title;
    @ColumnInfo(name = "content")
    private String content;
    @ColumnInfo(name = "category")
    private String category;
    @ColumnInfo(name="timestamp")
    private String timestamp;

    @Ignore
    public Memo() { this("No Title",""); }
    @Ignore
    public Memo(String title, String content) { this(title,content,""); }
    @Ignore
    public Memo(String title, String content, String category) { this(title,content,category,""); }

    public Memo(String title, String content, String category, String timestamp) {
       setTitle(title);setContent(content);setCategory(category);setTimestamp(timestamp);
    }

    @Ignore
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

    @NonNull
    @Override
    public String toString() {
        return "Memo{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", category='" + category + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
