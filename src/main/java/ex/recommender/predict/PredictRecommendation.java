package ex.recommender.predict;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.mahout.cf.taste.common.NoSuchUserException;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.PlusAnonymousUserDataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.Recommender;

import ex.recommender.data.AnonymousPrediction;

public class PredictRecommendation {

	private PlusAnonymousUserDataModel plusAnonymousUserDataModel;
	private Recommender recommender;
	private final static Logger LOGGER = Logger.getLogger(PredictRecommendation.class.getName());
	public PredictRecommendation(Recommender recommender) {
		this.recommender = recommender;
		this.plusAnonymousUserDataModel =  (PlusAnonymousUserDataModel) recommender.getDataModel();
	}

	/**
	 * Predicting the rating for unknown non-overlapping user from training set.
	 * Rating with 99 are missing info and 55 are rating to be predicted.
	 * Creates GenericPreferece list for creating AnonymousUser 
	 * 
	 * @param	testPath, path to test file.
	 * @param	outputPath, path to the output folder.
	 * */
	public void predict(String testPath, String outputPath) throws NumberFormatException, IOException, TasteException {
		LOGGER.info("START PREDICTING");
		File input = new File(testPath);
		BufferedReader reader = new BufferedReader(new FileReader(input));
		String line;
		// Ignoring the header
		reader.readLine();
		PrintWriter writer = new PrintWriter(outputPath);
		writer.write("UserId,Rating1,Rating2,Rating3\n");
		while ((line = reader.readLine()) != null) {
			String[] points = line.split(",");
			String user = points[0].replace("\"", "");
			List<GenericPreference> preference = new ArrayList<>();
			List<AnonymousPrediction> prediction = new ArrayList<>();
			for (int i = 1; i < points.length; i++) {
				float rating = Float.parseFloat(points[i].replace("\"", ""));
				if (rating != 99 && rating != 55) {
					preference.add(new GenericPreference(PlusAnonymousUserDataModel.TEMP_USER_ID, i, rating));
				} else if (rating == 55) {
					prediction.add(new AnonymousPrediction(Integer.valueOf(user), i));
				}
			}
			predict(preference, prediction, user, writer);
		}
		reader.close();
		writer.flush();
		writer.close();
	}

	private void predict(List<GenericPreference> preferences, List<AnonymousPrediction> predictions, String user, PrintWriter writer) throws TasteException {
		
		PreferenceArray anonymousPrefs = new GenericUserPreferenceArray(preferences);
		writer.write(user);
		this.plusAnonymousUserDataModel.setTempPrefs(anonymousPrefs);
		for (AnonymousPrediction pred : predictions) {
			
			float value = 0;
			try {
				value = this.recommender.estimatePreference(PlusAnonymousUserDataModel.TEMP_USER_ID, pred.getItemId());
			} catch (NoSuchUserException e) {
				System.out.println(user + " " + PlusAnonymousUserDataModel.TEMP_USER_ID);
			}
			if(value == 0){
				LOGGER.warning(user+"  "+PlusAnonymousUserDataModel.TEMP_USER_ID);
			}
			if(value == Float.NaN){
				LOGGER.warning(user+"  "+PlusAnonymousUserDataModel.TEMP_USER_ID);
				value = 0;
			}
			if(("NaN").equals(String.valueOf(value))){
				LOGGER.warning(user+"  "+PlusAnonymousUserDataModel.TEMP_USER_ID);	
				value = 0;
			}
			writer.write("," + value);
			
		}
		plusAnonymousUserDataModel.clearTempPrefs();
		
		writer.write("\n");
	}
}
