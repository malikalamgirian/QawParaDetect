/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malikalamgirian.qaw.fyp;

import java.io.File;
import weka.core.*;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.functions.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author wasif
 */
public class QawParaphraseDetector {

    public QawParaphraseDetector() throws Exception {
        /*
         * Train classifier
         */
        this.setTrainingDataset(null);
        this.trainMachineLearningClassifier();
    }

    /*
     * Declarations
     */
    private SentencePairToTextDataset sentencePairToTextDataset = null;
    private SentencePair[] sentencePairArray = null;
    private TxtToXmlConvertor txtToXmlConvertor = null;
    private Properties properties = null;
    private XmlToFeatureVectorProcessor xmlToFeatureVectorProcessor = null;
    private FeatureExtractor featureExtractor = null;
    private NonMonotonicFeatureSelector nonMonotonicFeatureSelector = null;
    private CsvToArff csvToArff = null;
    private String text_File_To_Process = null;
    private double predicted_Pair_Quality;
    private boolean predicted_Pair_Quality_To_Return;
    private String training_Dataset_File_URL = null;
    private SGD classifier = null;


    /*
     * @Param : arff_training_Set_File_URL
     *          If null, training dataset is set from environment variable.
     *          Otherwise training dataset for classifier model is set as the provided argument.
     */
    public void setTrainingDataset(String arff_training_Set_File_URL) {
        if (arff_training_Set_File_URL == null) {
            /*
             * Set training dataset from environmental variables, QawParaDetect_Train_Data
             */
            training_Dataset_File_URL = System.getenv("QawParaDetect_Train_Data");
        } else {
            /*
             * In this case set training dataset from the given URL
             */
            training_Dataset_File_URL = arff_training_Set_File_URL;
        }
    }

    public void trainMachineLearningClassifier() throws Exception {
        /*
         * Local Declarations
         */

        try {
            /*
             * Set training set
             */
            Instances train = DataSource.read(training_Dataset_File_URL);
            train.setClassIndex(train.numAttributes() - 1);

            // train classifier
            classifier = new SGD();

            /*
             * Set options
             */
            String[] options = Utils.splitOptions("-F 1 -L 0.005 -R 1.0E-4 -E 140 -N");
            classifier.setOptions(options);

            /*
             * Build Classifier
             */
            classifier.buildClassifier(train);

        } catch (Exception e) {
            throw new Exception("trainMachineLearningClassifier : Machine learning classifier training error.");
        }
    }

    public Boolean classifyInstance(SentencePair sentencePairEntered) throws Exception {
        /*
         * Local Declarations
         */

        try {
            /*
             * Checks on sentencePairEntered
             */
            if (sentencePairEntered.getString1() == null || sentencePairEntered.getString2() == null
                    || sentencePairEntered.getString1().equals("") || sentencePairEntered.getString2().equals("")) {
                throw new Exception("Strings entered not proper. ");
            }

            /*
             * Set instance as the only value for array
             */
            sentencePairArray = new SentencePair[1];
            sentencePairArray[0] = sentencePairEntered;

            /*
             * Convert array to TEXT dataset
             */
            text_File_To_Process = now("MMMMM_yyyy_dd_hh_mm_ss");
            FileSystemManager.createDirectory(text_File_To_Process);
            text_File_To_Process = text_File_To_Process + File.separatorChar + text_File_To_Process;
            sentencePairToTextDataset = new SentencePairToTextDataset();
            sentencePairToTextDataset.setSentencePair(sentencePairArray);
            sentencePairToTextDataset.setText_File_Path_To_Save(text_File_To_Process + ".txt");
            sentencePairToTextDataset.convert();

            /*
             * Convert TEXT dataset to XML
             */
            txtToXmlConvertor = new TxtToXmlConvertor(sentencePairToTextDataset.getText_File_Path_To_Save());

            /*
             * Convert XML dataset to arff file
             */
            /*
             * Set properties for XmlToFeatureVectorProcessor
             */
            properties = new Properties();
            properties.setInput_XML_File_URL(txtToXmlConvertor.getXml_Output_File_URL());

            /*
             * call XmlToFeatureVectorProcessor
             *
             * this processes XML to "standard_Similarity_Analysed_File_URL"
             */
            xmlToFeatureVectorProcessor = new XmlToFeatureVectorProcessor(properties);
            xmlToFeatureVectorProcessor = null;
            System.gc();

            /*
             * Extract features from "standard_Similarity_Analysed_File_URL"
             */
            featureExtractor = new FeatureExtractor(properties);
            featureExtractor = null;
            System.gc();

            /*
             * Select features
             */
            nonMonotonicFeatureSelector = new NonMonotonicFeatureSelector(properties);
            nonMonotonicFeatureSelector = null;
            System.gc();

            /*
             * Convert CSV file to ARFF file
             */
            csvToArff = new CsvToArff(properties.getNon_Monotonic_Features_Selected_File_URL());

            Instances test = DataSource.read(csvToArff.getOutput_ARFF_File_Path_Name());
            test.setClassIndex(test.numAttributes() - 1);

            /*
             * Classify the first instance
             */
            predicted_Pair_Quality = classifier.classifyInstance(test.firstInstance());

            /*
             * Return as classification
             */
            if (predicted_Pair_Quality == 0) {
                predicted_Pair_Quality_To_Return = false;
            } else if (predicted_Pair_Quality == 1) {
                predicted_Pair_Quality_To_Return = true;
            }


        } catch (Exception e) {
            throw new Exception("classifyInstance: Instance classification error. ");
        }

        return predicted_Pair_Quality_To_Return;


    }

    public static String now(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(cal.getTime());
    }
}
