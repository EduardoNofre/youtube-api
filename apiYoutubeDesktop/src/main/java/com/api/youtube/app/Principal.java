package com.api.youtube.app;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.api.youtube.app.enums.PlayListEnum;
import com.api.youtube.app.services.PlaylistService;
import com.api.youtube.app.services.StatusService;
import com.api.youtube.app.services.TagsService;
import com.api.youtube.app.services.ThumbnailService;
import com.api.youtube.app.services.TituloDescricaoService;
import com.api.youtube.app.services.VideoService;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;

public class Principal {

	public static void main(String[] args) throws Exception {

		System.out.println("Inicio " + new SimpleDateFormat("dd/MM/yyyy HH:MM:ss").format(new Date()));

		montagemDadosVideoParaUpload();

	}

	public static void montagemDadosVideoParaUpload() throws Exception {

		VideoService videoService = new VideoService();
		File fileVideo = videoService.preparaVideo();

		if (fileVideo.isFile() && fileVideo != null) {

			StatusService statusService = new StatusService();
			Video videoObjectDefiningMetadata = statusService.addStatusVideo();

			VideoSnippet snippet = new VideoSnippet();

			// titulo e descrição
			TituloDescricaoService atituloDescricaoService = new TituloDescricaoService();
			snippet = atituloDescricaoService.addTituloDescricaoVideo(snippet);

			// Tags
			TagsService tagsService = new TagsService();
			snippet = tagsService.addTags(snippet);

			Video dadosVideo = videoService.uploadVideo(fileVideo, videoObjectDefiningMetadata, snippet);

			if (dadosVideo != null) {

				PlaylistService playListService = new PlaylistService();

				PlaylistItem playlistItem = playListService.addVideoNaPayList(PlayListEnum.flowIdList.getIdPlayList(),dadosVideo.getId());

				if (playlistItem != null) {

					ThumbnailService ThumbnailService = new ThumbnailService();

					ThumbnailService.addThumbNailVideo(dadosVideo.getId());
				}
			}
		}
	}
}
