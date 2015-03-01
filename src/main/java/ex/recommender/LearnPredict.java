package ex.recommender;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.Recommender;

import ex.recommender.learn.LearnRecommendationNN;
import ex.recommender.learn.LearnRecommendationSVD;
import ex.recommender.predict.PredictRecommendation;

public class LearnPredict {
	public static void main(String[] args) throws IOException, TasteException {
		String folder = "/Users/ankushkhanna/Studies/mahout/recommender-unknown";
		String train = "trainRecommender.csv";

		String test = "test.csv";
		String submission = "submission.csv";
		String persisitFile = "SVD";
		int k = 20;
		int iteration = 200;
		//LearnRecommendationSVD learn = new LearnRecommendationSVD(k, iteration);
		LearnRecommendationNN learn = new LearnRecommendationNN(10);
		Recommender recommender = learn.learn(folder + File.separator + train,
				folder + File.separator + persisitFile);

		PredictRecommendation predict = new PredictRecommendation(recommender);
		predict.predict(folder + File.separator + test, folder + File.separator
				+ submission);
	}
}
