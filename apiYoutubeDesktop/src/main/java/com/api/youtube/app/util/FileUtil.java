package com.api.youtube.app.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {

	public  File getVideoFromUser() throws IOException {
		File[] listOfVideoFiles = getLocalVideoFiles();
		return getUserChoice(listOfVideoFiles);
	}

	private  File getUserChoice(File videoFiles[]) throws IOException {

		if (videoFiles.length < 1) {
			throw new IllegalArgumentException("No video files in this directory.");
		}

		for (int i = 0; i < videoFiles.length; i++) {
			System.out.println(" " + i + " = " + videoFiles[i].getName());
		}

		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		String inputChoice;

		do {
			System.out.print("Choose the number of the video file you want to upload: ");
			inputChoice = bReader.readLine();
		} while (!isValidIntegerSelection(inputChoice, videoFiles.length));

		return videoFiles[Integer.parseInt(inputChoice)];
	}

	private  File[] getLocalVideoFiles() throws IOException {

		File currentDirectory = new File("C:\\Users\\nofre\\OneDrive\\Área de Trabalho\\locos\\Podcast\\Podcast - VenusPodCast\\04 - VIVI FERNANDEZ\\corte");
		System.out.println("Video files from " + currentDirectory.getAbsolutePath() + ":");

		// Filters out video files. This list of video extensions is not comprehensive.
		FilenameFilter videoFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				String lowercaseName = name.toLowerCase();
				if (lowercaseName.endsWith(".webm") || lowercaseName.endsWith(".flv") || lowercaseName.endsWith(".f4v")
						|| lowercaseName.endsWith(".mov") || lowercaseName.endsWith(".mp4")) {
					return true;
				} else {
					return false;
				}
			}
		};
		return currentDirectory.listFiles(videoFilter);
	}

	private  boolean isValidIntegerSelection(String input, int max) {
		if (input.length() > 9)
			return false;

		boolean validNumber = false;
		// Only accepts positive numbers of up to 9 numbers.
		Pattern intsOnly = Pattern.compile("^\\d{1,9}$");
		Matcher makeMatch = intsOnly.matcher(input);

		if (makeMatch.find()) {
			int number = Integer.parseInt(makeMatch.group());
			if ((number >= 0) && (number < max)) {
				validNumber = true;
			}
		}
		return validNumber;
	}
}
