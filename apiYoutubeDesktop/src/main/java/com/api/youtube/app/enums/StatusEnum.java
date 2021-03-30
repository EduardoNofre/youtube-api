package com.api.youtube.app.enums;

public enum StatusEnum {

	privado("private"), publico("public"), naolistado("unlisted");

	private String statusDescricao;

	StatusEnum(String statusDescricao) {
		this.statusDescricao = statusDescricao;
	}

	public String getStatusEnum() {
		return statusDescricao;
	}
}
