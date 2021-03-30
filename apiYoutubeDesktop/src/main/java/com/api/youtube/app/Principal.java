package com.api.youtube.app;

import com.api.youtube.app.services.UploadVideos;

public class Principal {

	
	static UploadVideos up = new UploadVideos();
	

	public static void main(String[] args) throws Exception {
		System.out.println("Inicio");
	
		up.uploadVideos();
		
//		System.out.println(c.getAccessToken());
//		System.out.println(c.getClientAuthentication());
//		System.out.println(c.getExpirationTimeMilliseconds());
	}
	


}
