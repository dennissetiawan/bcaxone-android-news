package model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Source {

	@ColumnInfo(name = "name")
	@SerializedName("name")
	private String name;

	@SerializedName("id")
	@NonNull
	@ColumnInfo(name = "sourceId")
	@PrimaryKey
	private String sourceId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NonNull
	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(@NonNull String sourceId) {
		this.sourceId = sourceId;
	}
}