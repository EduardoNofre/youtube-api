package com.api.youtube.app.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import com.api.youtube.app.enums.ServicesApiYoutubeEnum;
import com.api.youtube.app.util.AuthorizeUtil;
import com.api.youtube.app.util.FileUtil;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.common.collect.Lists;

public class VideoService {

	private final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	private final JsonFactory JSON_FACTORY = new JacksonFactory();

	private YouTube youtube;

	private String VIDEO_FILE_FORMAT = "video/*";

	public Video uploadVideo(File fileVideo, Video videoObjectDefiningMetadata,VideoSnippet snippet) throws Exception {

		AuthorizeUtil authorizeUtil = new AuthorizeUtil();
		
		try {

			List<String> scopes = Lists.newArrayList(ServicesApiYoutubeEnum.youtubeAuht.getServiceYoutubeDescricao());

			Credential credential = authorizeUtil.authorize(scopes,ServicesApiYoutubeEnum.youtubeUploadJsonCredential.getServiceYoutubeDescricao());

			youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName("youtube-cmdline-uploadvideo-sample").build();

			videoObjectDefiningMetadata.setSnippet(snippet);

			InputStreamContent mediaContent = new InputStreamContent(VIDEO_FILE_FORMAT,new BufferedInputStream(new FileInputStream(fileVideo)));

			mediaContent.setLength(fileVideo.length());			

			YouTube.Videos.Insert videoInsert = youtube.videos().insert("snippet,statistics,status",videoObjectDefiningMetadata, mediaContent);

			MediaHttpUploader uploader = videoInsert.getMediaHttpUploader();

			uploader.setDirectUploadEnabled(false);

			MediaHttpUploaderProgressListener progressListener = new MediaHttpUploaderProgressListener() {
				public void progressChanged(MediaHttpUploader uploader) throws IOException {
					switch (uploader.getUploadState()) {
					case INITIATION_STARTED:
						System.out.println("Initiation Started");
						break;
					case INITIATION_COMPLETE:
						System.out.println("Initiation Completed");
						break;
					case MEDIA_IN_PROGRESS:
						System.out.println("Upload in progress");
						System.out.println("Upload percentage: " + uploader.getProgress());
						break;
					case MEDIA_COMPLETE:
						System.out.println("Upload Completed!");
						break;
					case NOT_STARTED:
						System.out.println("Upload Not Started!");
						break;
					}
				}
			};
			uploader.setProgressListener(progressListener);

			return videoInsert.execute();

		} catch (GoogleJsonResponseException e) {
			System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode() + " : "
					+ e.getDetails().getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IOException: " + e.getMessage());
			e.printStackTrace();
		} catch (Throwable t) {
			System.err.println("Throwable: " + t.getMessage());
			t.printStackTrace();
		}
		return null;
	}

	public File preparaVideo() {

		try {
			FileUtil fileUtil = new FileUtil();
			return fileUtil.getVideoFromUser();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
