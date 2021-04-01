package com.api.youtube.app.services;

import java.util.Calendar;

import com.api.youtube.app.dto.TituloDescricaoModel;
import com.google.api.services.youtube.model.VideoSnippet;

public class TituloDescricaoService {

	public VideoSnippet addTituloDescricaoVideo(VideoSnippet snippet) {

		TituloDescricaoModel tituloDescricaoModel = buscaTituloDescricao();
		Calendar cal = Calendar.getInstance();
		snippet.setTitle(tituloDescricaoModel.getTitulo() + cal.getTime());
		snippet.setDescription(tituloDescricaoModel.getDescricao() + "on " + cal.getTime());
		
		return snippet;
	}

	private TituloDescricaoModel buscaTituloDescricao() {

		TituloDescricaoModel tituloDescricaoModel = new TituloDescricaoModel();
		tituloDescricaoModel.setTitulo("Test Upload via Java on ");
		tituloDescricaoModel.setDescricao("Video uploaded via YouTube Data API V3 using the Java library ");

		return tituloDescricaoModel;
	}

}
