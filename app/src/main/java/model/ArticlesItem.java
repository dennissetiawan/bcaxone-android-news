package model;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Articles",
		indices = {@Index(value = {"url"},unique = true)}
)
public class ArticlesItem{

	@ColumnInfo(name = "articleID")
	@PrimaryKey(autoGenerate = true)
	private int articleID;

	@ColumnInfo(name = "publishedAt")
	@SerializedName("publishedAt")
	private String publishedAt;

	@ColumnInfo(name = "author")
	@SerializedName("author")
	private String author;

	@ColumnInfo(name = "urlToImage")
	@SerializedName("urlToImage")
	private String urlToImage;

	@ColumnInfo(name = "description")
	@SerializedName("description")
	private String description;

	@Embedded(prefix = "source_")
	@SerializedName("source")
	private Source source;

	@ColumnInfo(name = "title")
	@SerializedName("title")
	private String title;


	@ColumnInfo(name = "url")
	@SerializedName("url")
	private String url;

	@ColumnInfo(name = "content")
	@SerializedName("content")
	private String content;

	@ColumnInfo(name = "category")
	private String category;

	public int getArticleID() {
		return articleID;
	}

	public void setArticleID(int articleID) {
		this.articleID = articleID;
	}

	public String getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getUrlToImage() {
		return urlToImage;
	}

	public void setUrlToImage(String urlToImage) {
		this.urlToImage = urlToImage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}