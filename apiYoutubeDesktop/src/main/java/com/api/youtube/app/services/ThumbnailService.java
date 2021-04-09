package com.api.youtube.app.services;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.api.youtube.app.enums.ServicesApiYoutubeEnum;
import com.api.youtube.app.util.AuthorizeUtil;
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
import com.google.api.services.youtube.YouTube.Thumbnails.Set;
import com.google.common.collect.Lists;


public class ThumbnailService { 

	private YouTube youtube;

	private static final String IMAGE_FILE_FORMAT = "image/png";
	
	private final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	private final JsonFactory JSON_FACTORY = new JacksonFactory();

	public void addThumbNailVideo(String idVideo) throws Exception {

	    // An OAuth 2 access scope that allows for full read/write access.
	    List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");

	    AuthorizeUtil authorizeUtil = new AuthorizeUtil();
	    
	    try {
	    	
	    	
	      // Authorization.
	    	Credential credential = authorizeUtil.authorize(scopes,ServicesApiYoutubeEnum.youtubeThumbNailJsonCredential.getServiceYoutubeDescricao());

	      // YouTube object used to make all API requests.
	      youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName("youtube-cmdline-addfeaturedvideo-sample").build();


	      // Get the user selected local image file to upload.
	      File imageFile = getImageFromUser();
	      System.out.println("You chose " + imageFile + " to upload.");

	      InputStreamContent mediaContent = new InputStreamContent(
	          IMAGE_FILE_FORMAT, new BufferedInputStream(new FileInputStream(imageFile)));
	      mediaContent.setLength(imageFile.length());

	      // Create a request to set the selected mediaContent as the thumbnail of the selected video.
	      Set thumbnailSet = youtube.thumbnails().set(idVideo, mediaContent);

	      // Set the upload type and add event listener.
	      MediaHttpUploader uploader = thumbnailSet.getMediaHttpUploader();

	      /*
	       * Sets whether direct media upload is enabled or disabled. True = whole media content is
	       * uploaded in a single request. False (default) = resumable media upload protocol to upload
	       * in data chunks.
	       */
	      uploader.setDirectUploadEnabled(false);

	      MediaHttpUploaderProgressListener progressListener = new MediaHttpUploaderProgressListener() {
	        @Override
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

	      // Execute upload and set thumbnail.
	      thumbnailSet.execute();


	    } catch (GoogleJsonResponseException e) {
	      System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode() + " : "
	          + e.getDetails().getMessage());
	      e.printStackTrace();

	    } catch (IOException e) {
	      System.err.println("IOException: " + e.getMessage());
	      e.printStackTrace();
	    }
	  }

	private static File getImageFromUser() throws IOException {

		String path = "";

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
