package ex.recommender;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.PlusAnonymousUserDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;

import ex.recommender.learn.LearnRecommendationNN;
import ex.recommender.learn.LearnRecommendationSVD;
import ex.recommender.learn.similarity.SimilarityMatrix;
import ex.recommender.predict.PredictRecommendation;

public class LearnPredict {
	public static void main(String[] args) throws IOException, TasteException {
		String folder = args[0];
		String train = args[1];
		String test = args[2];
		String submission = args[3];
		String recommenderType = args[4];
		String similarity = args[5];
		int n = Integer.valueOf(args[6]);
		
		DataModel model = new PlusAnonymousUserDataModel(new FileDataModel(new File(folder + File.separator + train)));
		Refreshable similartiyMatrix = new SimilarityMatrix().getSimilarityMatrix(model, similarity);
		
		LearnRecommendationNN learn = new LearnRecommendationNN(n);
		Recommender recommender = learn.learn(model, similartiyMatrix, recommenderType);

		PredictRecommendation predict = new PredictRecommendation(recommender);
		predict.predict(folder + File.separator + test, folder + File.separator
				+ submission+"_"+n);
	}
}
