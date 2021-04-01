package com.api.youtube.app;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.api.youtube.app.services.UploadVideos;

public class Principal {

	
	static UploadVideos up = new UploadVideos();
	

	public static void main(String[] args) throws Exception {
		
		System.out.println("Inicio " + new SimpleDateFormat("dd/MM/yyyy HH:MM:ss").format(new Date()));
	
		up.uploadVideos();
		
//		System.out.println(c.getAccessToken());
//		System.out.println(c.getClientAuthentication());
//		System.out.println(c.getExpirationTimeMilliseconds());
	}
	
}
