package com.api.youtube.app.services;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Thumbnails.Set;
import com.google.api.services.youtube.model.ThumbnailListResponse;


public class UploadThumbnail { 

	private YouTube youtube;

	private static final String IMAGE_FILE_FORMAT = "image/png";

	public void addThumbNail(String idVideo) {

		File imageFile;
		try {
			imageFile = getImageFromUser();

			InputStreamContent mediaContent = new InputStreamContent(IMAGE_FILE_FORMAT,new BufferedInputStream(new FileInputStream("C:\\Users\\nofre\\OneDrive\\Área de Trabalho\\locos\\Podcast\\Podcast - podpah\\12-THIAGO VENTURA\\thumb\\01.png")));
			
			mediaContent.setLength(imageFile.length());
			
			Set thumbnailSet = youtube.thumbnails().set(idVideo, mediaContent);
			
			MediaHttpUploader uploader = thumbnailSet.getMediaHttpUploader();
			
			uploader.setDirectUploadEnabled(false);
			
			MediaHttpUploaderProgressListener progressListener = new MediaHttpUploaderProgressListener() {
				@Override
				public void progressChanged(MediaHttpUploader uploader) throws IOException {
					switch (uploader.getUploadState()) {
					// This value is set before the initiation request is
					// sent.
					case INITIATION_STARTED:
						System.out.println("Initiation Started");
						break;
						// This value is set after the initiation request
						//  completes.
					case INITIATION_COMPLETE:
						System.out.println("Initiation Completed");
						break;
						// This value is set after a media file chunk is
						// uploaded.
					case MEDIA_IN_PROGRESS:
						System.out.println("Upload in progress");
						System.out.println("Upload percentage: " + uploader.getProgress());
						break;
						// This value is set after the entire media file has
						//  been successfully uploaded.
					case MEDIA_COMPLETE:
						System.out.println("Upload Completed!");
						break;
						// This value indicates that the upload process has
						//  not started yet.
					case NOT_STARTED:
						System.out.println("Upload Not Started!");
						break;
					}
				}
			};
			
			uploader.setProgressListener(progressListener);
			
			ThumbnailListResponse setResponse = thumbnailSet.execute();
			
            System.out.println("\n================== Uploaded Thumbnail ==================\n");
            
            System.out.println("  - Url: " + setResponse.getItems().get(0).getDefault().getUrl());


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
	}

	private static File getImageFromUser() throws IOException {

		String path = "C:\\Users\\nofre\\OneDrive\\Área de Trabalho\\locos\\Podcast\\Podcast - podpah\\12-THIAGO VENTURA\\thumb\\01.png";

		System.out.print("Please enter the path of the image file to upload: ");
		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		path = bReader.readLine();

		if (path.length() < 1) {
			// Exit if the user does not provide a path to the image file.
			System.out.print("Path can not be empty!");
			System.exit(1);
		}

		return new File(path);
	}
}
