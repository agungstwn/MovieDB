package com.agung.android.moviedb.model.videoResponse;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class VideoResponse{

	@SerializedName("id")
	private int id;

	@SerializedName("results")
	private List<VideosItem> videos;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setVideos(List<VideosItem> results){
		this.videos = results;
	}

	public List<VideosItem> getVideos(){
		return videos;
	}

	@Override
 	public String toString(){
		return 
			"VideoResponse{" + 
			"id = '" + id + '\'' + 
			",results = '" + videos + '\'' +
			"}";
		}
}