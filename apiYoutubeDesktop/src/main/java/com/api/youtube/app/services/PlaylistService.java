package com.api.youtube.app.services;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import com.api.youtube.app.dto.PlaylistVideoModel;
import com.api.youtube.app.enums.ServicesApiYoutubeEnum;
import com.api.youtube.app.util.AuthorizeUtil;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.PlaylistSnippet;
import com.google.api.services.youtube.model.PlaylistStatus;
import com.google.api.services.youtube.model.ResourceId;
import com.google.common.collect.Lists;

public class PlaylistService {
	
	private YouTube youtube;
	
	private final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	private final JsonFactory JSON_FACTORY = new JacksonFactory();

	public PlaylistItem addVideoNaPayList(String playlistId, String videoId) throws IOException {
		
		AuthorizeUtil authorizeUtil = new AuthorizeUtil();

		List<String> scopes = Lists.newArrayList(ServicesApiYoutubeEnum.youtubeAuht.getServiceYoutubeDescricao());

		try {

			Credential credential = authorizeUtil.authorize(scopes,ServicesApiYoutubeEnum.youtubeplayListJsonCredential.getServiceYoutubeDescricao());
			
			youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName("youtube-cmdline-playlistupdates-sample").build();

			ResourceId resourceId = new ResourceId();
			
			resourceId.setKind("youtube#video");
			
			resourceId.setVideoId(videoId);

			PlaylistItemSnippet playlistItemSnippet = new PlaylistItemSnippet();
			
			playlistItemSnippet.setTitle("First video in the test playlist");
			
			playlistItemSnippet.setPlaylistId(playlistId);
			
			playlistItemSnippet.setResourceId(resourceId);

			PlaylistItem playlistItem = new PlaylistItem();
			
			playlistItem.setSnippet(playlistItemSnippet);

			YouTube.PlaylistItems. Insert playlistItemsInsertCommand = youtube.playlistItems().insert("snippet,contentDetails", playlistItem);

			return playlistItemsInsertCommand.execute();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public Playlist criaPlayList(YouTube youtube) throws IOException {

		AuthorizeUtil authorizeUtil = new AuthorizeUtil();
		Playlist playlist = null;

		List<String> scopes = Lists.newArrayList(ServicesApiYoutubeEnum.youtubeAuht.getServiceYoutubeDescricao());

		try {

			Credential credential = authorizeUtil.authorize(scopes,ServicesApiYoutubeEnum.youtubeplayListJsonCredential.getServiceYoutubeDescricao());
			
			youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName("youtube-cmdline-playlistupdates-sample").build();

			PlaylistVideoModel playlistVideoModel = new PlaylistVideoModel();

			playlistVideoModel.setTitulo("Playlist Java" + Calendar.getInstance().getTime());
			playlistVideoModel.setDescricao("Usando o Java para Criar playlist YouTube API v3");

			PlaylistSnippet playlistSnippet = new PlaylistSnippet();
			playlistSnippet.setTitle(playlistVideoModel.getTitulo());
			playlistSnippet.setDescription(playlistVideoModel.getDescricao());

			PlaylistStatus playlistStatus = new PlaylistStatus();
			playlistStatus.setPrivacyStatus("private");

			Playlist youTubePlaylist = new Playlist();
			youTubePlaylist.setSnippet(playlistSnippet);
			youTubePlaylist.setStatus(playlistStatus);

			YouTube.Playlists.Insert playlistInsertCommand = youtube.playlists().insert("snippet,status",youTubePlaylist);
			
			return playlistInsertCommand.execute();

		} catch (GoogleJsonResponseException e) {
			System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode() + " : "+ e.getDetails().getMessage());
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

}
