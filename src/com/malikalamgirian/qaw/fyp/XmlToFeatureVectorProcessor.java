/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malikalamgirian.qaw.fyp;

import com.malikalamgirian.qaw.fyp.AntonymMatchDetector;
import com.malikalamgirian.qaw.fyp.ContradictionAndPolarityAnalyser;
import com.malikalamgirian.qaw.fyp.EventDetector;
import com.malikalamgirian.qaw.fyp.MonotoneAligner;
import com.malikalamgirian.qaw.fyp.NonMonotoneAligner;
import com.malikalamgirian.qaw.fyp.PosBasedFilter;
import com.malikalamgirian.qaw.fyp.PosTagger;
import com.malikalamgirian.qaw.fyp.Properties;
import com.malikalamgirian.qaw.fyp.StandardSimilarityAnalyser;
import com.malikalamgirian.qaw.fyp.StopWordRemover;
import org.w3c.dom.NodeList;

/**
 *
 * @author wasif
 */
public class XmlToFeatureVectorProcessor {

    public XmlToFeatureVectorProcessor(Properties properties) throws Exception {

        /*
         * Set state from Properties instance
         */
        this.input_XML_File_URL = properties.getInput_XML_File_URL();
        this.open_Amplify_Folder_URL = properties.getOpen_Amplify_Folder_URL();
        this.properties = properties;

        /*
         * call Process method
         */
        process();

    }

    /*
     * Declarations
     */
    private Properties properties;
    private String input_XML_File_URL,
            open_Amplify_Folder_URL;
    /*
     * Helper Classes' Declarations
     */
    PosTagger posTagger;
    StopWordRemover stopWordRemover;
    PosBasedFilter posBasedFilter;
    MonotoneAligner monotoneAligner;
    NonMonotoneAligner nonMonotonerAligner;
    EventDetectorForNonMonotoneAlignedOnlyForBestSystem eventDetector;
    ContradictionAndPolarityAnalyser contradictionAndPolarityAnalyser;
    AntonymMatchDetector antonymRelationshipDetector;
    StandardSimilarityAnalyser standardSimilarityAnalyser;

    /*
     * Main processing method
     * This method calls all the "helper classes"
     * Each "helper class" performs a processing step independently
     * Each "helper class" traverses an XML file itself independently
     */
    private void process() throws Exception {
        /*
         * Local Declarations
         */
        NodeList pairs;

        try {
            System.out.println("I am into Main Process method.");


            /*
             * 1. Pos Tagging step
             * Call PosTagger helper class
             */
            posTagger = new PosTagger(properties);
            posTagger = null;
            System.gc();

            /*
             * 2. Stop-Words Removal step
             * Call StopWordRemover helper class
             */
            stopWordRemover = new StopWordRemover(properties);
            stopWordRemover = null;
            System.gc();

            /*
             * 3. Filteration step
             * Call PosBasedFilter helper class
             */
            posBasedFilter = new PosBasedFilter(properties);
            posBasedFilter = null;
            System.gc();

            /*
             * 4. Longest Common Subsequence Based Monotonic Alignment step
             * Call MonotoneAligner helper class
             */
            monotoneAligner = new MonotoneAligner(properties);
            monotoneAligner = null;
            System.gc();

            /*
             * 5. Bag of Words Based Non-Monotonic Alignment step
             * Call NonMonotoneAligner helper class
             */
            nonMonotonerAligner = new NonMonotoneAligner(properties);
            nonMonotonerAligner = null;
            System.gc();

            /*
             * 6. Event Detection step
             * Call EventDetector helper class
             */
            eventDetector = new EventDetectorForNonMonotoneAlignedOnlyForBestSystem(properties);
            eventDetector = null;
            System.gc();

            /*
             * 7. Contradiction And Polarity Analysis step
             * Call ContradictionAndPolarityAnalyser helper class
             */
            contradictionAndPolarityAnalyser = new ContradictionAndPolarityAnalyser(properties);
            contradictionAndPolarityAnalyser = null;
            System.gc();

            /*
             * 8. Antonym Relationship Detection step
             * Call 'AntonymMatchDetector' helper class
             */
             antonymRelationshipDetector = new AntonymMatchDetector(properties);
             antonymRelationshipDetector = null;
             System.gc();

            /*
             * 9. Standard Similarity Detection step
             * Call 'StandardSimilarityAnalyser' helper class
             */
             standardSimilarityAnalyser = new StandardSimilarityAnalyser(properties);
             standardSimilarityAnalyser = null;
             System.gc();
             
        } catch (Exception e) {
            throw new Exception("Process :"
                    + e + " : " + e.getMessage());
        }

    }
}
