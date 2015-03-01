package ex.recommender.dataformat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class DataIntoRecommendationFormat {

	/**
	 * Read data from inputPath in format "","joke.1","joke.2",.....,"joke.100" Values which are 99 are ignored.
	 * 
	 * Final format is user, joke, rating
	 * 
	 * @param inputPath, input path of the training file
	 * @param outputPath, final path where the file fomat would be written
	 * 
	 * @throws IOException
	 * */
	public void refractorDataTrain(String inputPath, String outputPath)
			throws IOException {
		File input = new File(inputPath);
		BufferedReader reader = new BufferedReader(new FileReader(input));
		String line;
		// Ignoring the header
		reader.readLine();
		PrintWriter writer = new PrintWriter(new File(outputPath));
		while ((line = reader.readLine()) != null) {
			String[] points = line.split(",");
			String user = points[0].replace("\"", "");
			for (int i = 1; i < points.length; i++) {
				double rating = Double.parseDouble(points[i].replace("\"", ""));
				if (rating != 99) {
					writer.write(user + "," + i + "," + rating + "\n");
				}
			}
		}
		reader.close();
		writer.flush();
		writer.close();
	}

	/**
	 * Takes inputPath of test file. Transfer it into information file providing information about the user activity and to be recommended file.
	 * 
	 * File format for output info file: user, joke, rating File format for output recommender file: user, joke
	 * 
	 * @param inputPath, input path of the file
	 * @param outputInfoPath, output path of information file of user activity
	 * @param outputRecommenderPath, output path of recommender file.
	 * @throws IOException
	 * 
	 **/
	public void refractorDataTest(String inputPath, String outputInfoPath,
			String outputRecommenderPath) throws IOException {
		File input = new File(inputPath);
		BufferedReader reader = new BufferedReader(new FileReader(input));
		String line;
		// Ignoring the header
		reader.readLine();
		PrintWriter infoWriter = new PrintWriter(new File(outputInfoPath));
		PrintWriter recommWriter = new PrintWriter(new File(
				outputRecommenderPath));

		while ((line = reader.readLine()) != null) {
			String[] points = line.split(",");
			String user = points[0].replace("\"", "");
			for (int i = 1; i < points.length; i++) {
				double rating = Double.parseDouble(points[i].replace("\"", ""));
				if (rating != 99 && rating != 55) {
					infoWriter.write(user + "," + i + "," + rating + "\n");
				} else if (rating == 55) {
					recommWriter.write(user + "," + rating + "\n");
				}
			}
		}
		reader.close();
		infoWriter.flush();
		infoWriter.close();
		recommWriter.flush();
		recommWriter.close();
	}

}
