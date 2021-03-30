package com.api.youtube.app.services;

import com.api.youtube.app.enums.StatusEnum;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoStatus;

public class StatusVideo {

	
	public Video addStatusVideo() {
		
		Video videoObjectDefiningMetadata = new Video();

		VideoStatus status = new VideoStatus();

		status.setPrivacyStatus(StatusEnum.naolistado.getStatusEnum());
		
		videoObjectDefiningMetadata.setStatus(status);
		
		return videoObjectDefiningMetadata;
	}
}
