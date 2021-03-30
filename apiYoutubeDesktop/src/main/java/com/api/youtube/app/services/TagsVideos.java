package com.api.youtube.app.services;

import java.util.ArrayList;
import java.util.List;

import com.google.api.services.youtube.model.VideoSnippet;

public class TagsVideos {

	public VideoSnippet addTags(VideoSnippet snippet) {

		List<String> tags = buscaTagsVideos();

		snippet.setTags(tags);

		return snippet;
	}

	private List<String> buscaTagsVideos() {

		List<String> tagss = new ArrayList<String>();
		tagss.add("test");
		tagss.add("example");
		tagss.add("java");
		tagss.add("YouTube Data API V3");
		tagss.add("erase me");

		return tagss;
	}
}
