package ex.recommender.learn;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.PlusAnonymousUserDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.svd.FilePersistenceStrategy;
import org.apache.mahout.cf.taste.impl.recommender.svd.PersistenceStrategy;
import org.apache.mahout.cf.taste.impl.recommender.svd.RatingSGDFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class LearnRecommendationNN {
	private int n;
	//private DataMpde
	/**
	 * Constructor.
	 * 
	 * @param k, number of dimensions of the matrix.
	 * */
	public LearnRecommendationNN(int n) {
		this.n = n;

	}

	/**
	 * Learn NN recommendation from the trainPath using n neighbors specified in constructor.
	 * 
	 * @param trainPath, training path of the rating file.
	 * 
	 * @return recommender, which is the NNRecommender.
	 * */
	public Recommender learn(String trainPath, String persistLearnPath) throws IOException, TasteException {
		DataModel model = new PlusAnonymousUserDataModel(new FileDataModel(new File(trainPath)));
		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(n, similarity, model);
		GenericUserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
		
		return recommender;
	}

}
