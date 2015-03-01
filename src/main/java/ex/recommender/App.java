package ex.recommender;

import java.io.File;
import java.io.IOException;

import ex.recommender.dataformat.DataIntoRecommendationFormat;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	String folder="/Users/ankushkhanna/Studies/mahout/recommender-unknown";
    	String inputTrain="train.csv";
    	String inputTest="test.csv";
    	String outputTrain="trainRecommender.csv";
    	String outputTestInfo="testInfo.csv";
    	String outputTest="testToBeRecommended.csv";
    	
        DataIntoRecommendationFormat dataIntoRecommFormat= new DataIntoRecommendationFormat();
        dataIntoRecommFormat.refractorDataTrain(folder+File.separator+inputTrain, folder+File.separator+outputTrain);
        dataIntoRecommFormat.refractorDataTest(folder+File.separator+inputTest, folder+File.separator+outputTestInfo,folder+File.separator+outputTest);
    }
}
