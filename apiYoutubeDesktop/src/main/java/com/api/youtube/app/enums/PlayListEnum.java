package com.api.youtube.app.enums;

public enum PlayListEnum {

	podPahIdList("https://www.googleapis.com/auth/youtube"),
	flowIdList("LLCF0FjIlIGKhL4deDtMnX-g"),
	inteligenciaIdList("https://www.googleapis.com/auth/youtube"),
	venusIdList("https://www.googleapis.com/auth/youtube");
	
	private String idPlayList;

	PlayListEnum(String idPlayList) {
		this.idPlayList = idPlayList;
	}

	public String getIdPlayList() {
		return idPlayList;
	}
}
