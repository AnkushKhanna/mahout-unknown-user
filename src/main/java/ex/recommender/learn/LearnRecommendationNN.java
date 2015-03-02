package ex.recommender.learn;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.common.Weighting;
import org.apache.mahout.cf.taste.impl.model.PlusAnonymousUserDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class LearnRecommendationNN {
	private int n;

	// private DataMpde
	/**
	 * Constructor.
	 * 
	 * @param k, number of dimensions of the matrix.
	 * */
	public LearnRecommendationNN(int n) {
		this.n = n;

	}

	public Recommender learn(DataModel model,Refreshable simMatrix, String recommenderType) throws IOException, TasteException {
		switch(recommenderType){
		case "USER":
			return this.learnUserSimilarity(model, simMatrix);
		case "ITEM":
			return this.learnItemSimilarity(model, simMatrix);
		}
		return null;
	}

	/**
	 * Learn NN recommendation from the trainPath using n neighbors specified in constructor. User Similarity.
	 * 
	 * @param trainPath, training path of the rating file.
	 * 
	 * @return recommender, which is the NNRecommender.
	 * */
	public Recommender learnUserSimilarity(DataModel model, Refreshable simMatrix) throws IOException, TasteException {
		UserSimilarity similarity = (UserSimilarity) simMatrix;
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(n, similarity, model);
		GenericUserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
		return recommender;
	}

	/**
	 * Learn NN recommendation from the trainPath using n neighbors specified in constructor. Item Similarity.
	 * 
	 * @param trainPath, training path of the rating file.
	 * 
	 * @return recommender, which is the NNRecommender.
	 * */
	public Recommender learnItemSimilarity(DataModel model, Refreshable simMatrix) throws IOException, TasteException {
		
		ItemSimilarity similarity = (ItemSimilarity) simMatrix;
		GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);
		return recommender;
	}

}
