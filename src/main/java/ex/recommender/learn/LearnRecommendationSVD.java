package ex.recommender.learn;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.PlusAnonymousUserDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.svd.FilePersistenceStrategy;
import org.apache.mahout.cf.taste.impl.recommender.svd.PersistenceStrategy;
import org.apache.mahout.cf.taste.impl.recommender.svd.RatingSGDFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;

public class LearnRecommendationSVD {
	private int k;
	private int iteration;
	
	/**
	 * Constructor.
	 * 
	 * @param	k, number of dimensions of the matrix.
	 * @param	iteration, iteration for learning the matrix.
	 * */
	public LearnRecommendationSVD(int k, int iteration) {
		this.k = k;
		this.iteration = iteration;
	}
	
	/**
	 * Learn SVD matrix from the trainPath and store it to the persistLearnPath.
	 * 
	 * @param	trainPath, training path of the rating file.
	 * @param	persistLearningPath, path to the persistence learning SVD file appended with 
	 * k (dimension of the matrix) and iteration (number of times matrix would be lerned). 	
	 * 
	 * @return	recommender, which is the SVDRecommender.
	 *  
	 *  
	 * */
	public Recommender learn(String trainPath, String persistLearnPath)
			throws IOException, TasteException {
		DataModel model = new PlusAnonymousUserDataModel(new FileDataModel(new File(trainPath)));
		PersistenceStrategy persisit = new FilePersistenceStrategy(new File(
				persistLearnPath + "_" + k + "_" + iteration));
		Recommender recommender = new SVDRecommender(model,
				new RatingSGDFactorizer(model, k, iteration), persisit);
		
		return recommender;
	}

}
