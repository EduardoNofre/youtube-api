package com.api.youtube.app.enums;

public enum ServicesApiYoutubeEnum {

	youtubeUpload("https://www.googleapis.com/auth/youtube"),
	youtubeplayList("https://www.googleapis.com/auth/youtube"),
	youtubeUploadJsonCredential(".credentials/youtube-api-uploadvideo.json"),
	youtubeplayListJsonCredential(".credentials/youtube-api-playlistupdates.json"),
	youtubeThumbNailJsonCredential(".credentials/youtube-api-uploadthumbnail.json\"");
	

	private String serviceYoutubeDescricao;

	ServicesApiYoutubeEnum(String serviceYoutubeDescricao) {
		this.serviceYoutubeDescricao = serviceYoutubeDescricao;
	}

	public String getServiceYoutubeDescricao() {
		return serviceYoutubeDescricao;
	}
}
