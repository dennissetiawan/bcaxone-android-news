package model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class ArticlesItem{

	@PrimaryKey(autoGenerate = true)
	private int articleID;

	@ColumnInfo(name = "publishedAt")
	@SerializedName("publishedAt")
	private String publishedAt;

	@ColumnInfo(name = "author")
	@SerializedName("author")
	private Object author;

	@ColumnInfo(name = "urlToImage")
	@SerializedName("urlToImage")
	private String urlToImage;

	@ColumnInfo(name = "description")
	@SerializedName("description")
	private String description;

	@ColumnInfo(name = "source")
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

	public void setPublishedAt(String publishedAt){
		this.publishedAt = publishedAt;
	}

	public String getPublishedAt(){
		return publishedAt;
	}

	public void setAuthor(Object author){
		this.author = author;
	}

	public Object getAuthor(){
		return author;
	}

	public void setUrlToImage(String urlToImage){
		this.urlToImage = urlToImage;
	}

	public String getUrlToImage(){
		return urlToImage;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setSource(Source source){
		this.source = source;
	}

	public Source getSource(){
		return source;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return content;
	}

	public int getArticleID() {
		return articleID;
	}

}