package ex.recommender.learn.similarity;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.common.Weighting;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;

public class SimilarityMatrix {
	public Refreshable getSimilarityMatrix(DataModel model, String matrix) throws IOException, TasteException {
		switch(matrix){
		case "EUCLIDEAN":
			return new EuclideanDistanceSimilarity(model);
		case "PEARSON": 
			return new PearsonCorrelationSimilarity(model);
		case "EUCLIDEAN_WEIGHT": 
			return new EuclideanDistanceSimilarity(model, Weighting.WEIGHTED);
		case "PEARSON_WEIGHT":
			return new PearsonCorrelationSimilarity(model, Weighting.WEIGHTED);
		case "LOGLIKELIHOOD":
			return new LogLikelihoodSimilarity(model);
		}
		return null;
	}
}
