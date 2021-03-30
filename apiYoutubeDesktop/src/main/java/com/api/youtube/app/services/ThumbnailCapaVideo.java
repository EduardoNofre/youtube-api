package com.api.youtube.app.services;

import java.util.Map;

import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.VideoSnippet;

public class ThumbnailCapaVideo {

	public VideoSnippet addThumbnail(VideoSnippet snippet, Map<String, Thumbnail> thumbnails) {

		snippet.setThumbnails(thumbnails);

		return snippet;
	}
	
	
	// To do
	private  Map<String, Thumbnail> buscaThumbnailCapaVideo(){
	
		return null;
	}
}
