package com.api.youtube.app.util;

import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.FileCredentialStore;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

public class AuthorizeUtil {

	private final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	private final JsonFactory JSON_FACTORY = new JacksonFactory();

	public Credential authorize(List<String> scopes,String servicoTipoJsonCredential ) throws Exception {

		 GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,new InputStreamReader(AuthorizeUtil.class.getResourceAsStream("/client_secrets.json")));

		// Checks that the defaults have been replaced (Default = "Enter X here").
		if (clientSecrets.getDetails().getClientId().startsWith("Enter") || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
			System.out.println("Enter Client ID and Secret from https://console.developers.google.com/project/_/apiui/credential"
							+ "into youtube-cmdline-uploadvideo-sample/src/main/resources/client_secrets.json");
			System.exit(1);
		}

		FileCredentialStore credentialStore = new FileCredentialStore(new File(System.getProperty("user.home"), servicoTipoJsonCredential), JSON_FACTORY);
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,clientSecrets, scopes).setCredentialStore(credentialStore).build();
		LocalServerReceiver localReceiver = new LocalServerReceiver.Builder().setPort(8099).build();

		return new AuthorizationCodeInstalledApp(flow, localReceiver).authorize("user");
	}

}
