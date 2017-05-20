/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malikalamgirian.qaw.fyp;

import java.io.File;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import uk.ac.shef.wit.simmetrics.similaritymetrics.*;
import uk.ac.shef.wit.simmetrics.tokenisers.*;
import uk.ac.shef.wit.simmetrics.wordhandlers.*;

/**
 *
 * @author wasif
 */
public class StandardSimilarityAnalyser {

    public StandardSimilarityAnalyser(Properties properties) throws Exception {
        /*
         * Set state of proper Properties instance field
         */
        this.properties = properties;

        /*
         * This also sets instance variable "standard_Similarity_Analysed_File_URL"
         */
        this.setStandard_Similarity_Analysed_File_URL(properties);

        /*
         * call Process method
         */
        process();
    }
    /*
     * Declarations
     */
    private Properties properties;
    private String standard_Similarity_Analysed_File_URL;

    /*
     * Applies 'String Similarity Measures' to "PosFilteredString1" and "PosFilteredString2"
     */
    private void process() throws Exception {
        /*
         * Local Declarations
         */
        Document doc;

        Element root,
                StandardSimilarityMetricsBase,
                CosineSimilarityElement,
                DiceSimilarityElement,
                OverlapCoefficientElement,
                CosineSimilarity_Without_POS_Element,
                DiceSimilarity_Without_POS_Element,
                OverlapCoefficient_Without_POS_Element;

        Node pair;

        NodeList pairs,
                posFilteredString1,
                posFilteredString2;

        String posFilteredStr1,
                posFilteredStr2,
                posFilteredStr1_Without_POS,
                posFilteredStr2_Without_POS,
                tokenizerChar,
                intraLexemeSeparatorCharacter;

//        CosineSimilarity cosine_Similarity = null;
//        OverlapCoefficient overlap_Similarity = null;
//        DiceSimilarity dice_Similarity = null;


        try {
            System.out.println("properties.getAntonym_Match_Detector_File_URL() File URL is : " + properties.getAntonym_Match_Detector_File_URL());
            System.out.println("properties.getStandard_Similarity_Analysed_File_URL() is : " + properties.getStandard_Similarity_Analysed_File_URL());

            /*
             * set separator character
             */
            tokenizerChar = " ";

            /*
             * set intraLexemeSeparatorCharacter, for 'Tagged String'
             */
            intraLexemeSeparatorCharacter = "/";

            /*
             * Initialize Similarity Measures
             */
//            cosine_Similarity = new CosineSimilarity();
//            overlap_Similarity = new OverlapCoefficient();
//            dice_Similarity = new DiceSimilarity();

            /*
             * 1.Get XML Document, for <code>properties.getAntonym_Match_Detector_File_URL()</code>
             */
            doc = XMLProcessor.getXMLDocumentForXMLFile(properties.getAntonym_Match_Detector_File_URL());

            /*
             * 2. Get Document Element
             */
//            root = doc.getDocumentElement();

            /*
             * 3. Select all "PosFilteredString1", "PosFilteredString2", "Pair" pairs
             */
//            posFilteredString1 = root.getElementsByTagName("PosFilteredString1");
//            posFilteredString2 = root.getElementsByTagName("PosFilteredString2");
//            pairs = root.getElementsByTagName("Pair");

            /*
             * 4. loop through all pairs
             */
//            for (int i = 0; i < pairs.getLength(); i++) {
//                /*
//                 * Get 'Pair'
//                 */
//                pair = pairs.item(i);
//
//                /*
//                 * Get Text content of strings
//                 */
//                posFilteredStr1 = posFilteredString1.item(i).getTextContent();
//                posFilteredStr2 = posFilteredString2.item(i).getTextContent();
//
//                /*
//                 * Convert 'posFilteredStr1', 'posFilteredStr2' to lower-case letters
//                 */
//                posFilteredStr1 = posFilteredStr1.toLowerCase();
//                posFilteredStr2 = posFilteredStr2.toLowerCase();
//
//                /*
//                 * Remove POS from posFilteredStr1, posFilteredStr2
//                 */
//                posFilteredStr1_Without_POS = removePosFromTaggedString(posFilteredStr1, tokenizerChar, intraLexemeSeparatorCharacter);
//                posFilteredStr2_Without_POS = removePosFromTaggedString(posFilteredStr2, tokenizerChar, intraLexemeSeparatorCharacter);
//
//                /*
//                 * Create Base Node, StandardSimilarityMetricsBase
//                 */
//                StandardSimilarityMetricsBase = doc.createElement("StandardSimilarityMetrics");
//
//                /*
//                 * Create Similarity Nodes with POS
//                 */
//                CosineSimilarityElement = doc.createElement("CosineSimilarity");
//                OverlapCoefficientElement = doc.createElement("OverlapCoefficient");
//                DiceSimilarityElement = doc.createElement("DiceSimilarity");
//
//                /*
//                 * Create Similarity Nodes without POS
//                 */
//                CosineSimilarity_Without_POS_Element = doc.createElement("CosineSimilarityWithoutPOS");
//                OverlapCoefficient_Without_POS_Element = doc.createElement("OverlapCoefficientWithoutPOS");
//                DiceSimilarity_Without_POS_Element = doc.createElement("DiceSimilarityWithoutPOS");
//
//                /*
//                 * Set TEXT contents for Similarity Nodes with POS
//                 */
//                CosineSimilarityElement.setTextContent(Float.toString(cosine_Similarity.getSimilarity(posFilteredStr1, posFilteredStr2)));
//                OverlapCoefficientElement.setTextContent(Float.toString(overlap_Similarity.getSimilarity(posFilteredStr1, posFilteredStr2)));
//                DiceSimilarityElement.setTextContent(Float.toString(dice_Similarity.getSimilarity(posFilteredStr1, posFilteredStr2)));
//
//                /*
//                 * Set TEXT contents for Similarity Nodes without POS
//                 */
//                CosineSimilarity_Without_POS_Element.setTextContent(Float.toString(cosine_Similarity.getSimilarity(posFilteredStr1_Without_POS, posFilteredStr2_Without_POS)));
//                OverlapCoefficient_Without_POS_Element.setTextContent(Float.toString(overlap_Similarity.getSimilarity(posFilteredStr1_Without_POS, posFilteredStr2_Without_POS)));
//                DiceSimilarity_Without_POS_Element.setTextContent(Float.toString(dice_Similarity.getSimilarity(posFilteredStr1_Without_POS, posFilteredStr2_Without_POS)));
//
//                /*
//                 * Add nodes with POS
//                 */
//                StandardSimilarityMetricsBase.appendChild(CosineSimilarityElement);
//                StandardSimilarityMetricsBase.appendChild(OverlapCoefficientElement);
//                StandardSimilarityMetricsBase.appendChild(DiceSimilarityElement);
//
//                /*
//                 * Add nodes without POS
//                 */
//                StandardSimilarityMetricsBase.appendChild(CosineSimilarity_Without_POS_Element);
//                StandardSimilarityMetricsBase.appendChild(OverlapCoefficient_Without_POS_Element);
//                StandardSimilarityMetricsBase.appendChild(DiceSimilarity_Without_POS_Element);
//
//                /*
//                 * Add base to pair
//                 */
//                pair.appendChild(StandardSimilarityMetricsBase);
//
//                System.out.println((i + 1) + " / " + pairs.getLength() + "\n");
//            }

            /*
             * 5. Transform the tree, to "standard_Similarity_Analysed_File_URL"
             */
            XMLProcessor.transformXML(doc, new StreamResult(new File(this.standard_Similarity_Analysed_File_URL)));


        } catch (Exception e) {
            throw new Exception("StandardSimilarityAnalysed : Process :"
                    + e + " : " + e.getMessage());
        }
    }

    /*
     * Helper Methods
     */
    private String getStandard_Similarity_Analysed_File_URL() {
        return standard_Similarity_Analysed_File_URL;
    }

    private void setStandard_Similarity_Analysed_File_URL(Properties properties) throws Exception {
        /*
         * Local Declarations
         */
        String suffixToAdd,
                newURL;

        try {
            /*
             * add Suffix, "_ssa" for "Standard Similarity Analysed"
             */
            suffixToAdd = "_ssa";

            /*
             * call FileSystemManager.addSuffixToFileURL
             */
            newURL = FileSystemManager.addSuffixToFileURL(properties.getAntonym_Match_Detector_File_URL(), suffixToAdd, null);

            /*
             * set instance state
             */
            this.standard_Similarity_Analysed_File_URL = newURL;

            /*
             * set "properties" instance field value
             */
            properties.setStandard_Similarity_Analysed_File_URL(newURL);

        } catch (Exception e) {
            throw new Exception("StandardSimilarityAnalysed : setStandard_Similarity_Analysed_File_URL : "
                    + e + " : " + e.getMessage());
        }
    }

    private String removePosFromTaggedString(String tagged_String, String tokenizerChar, String intraLexemeSeparatorCharacter) throws Exception {
        /*
         * Local Declarations
         */
        String pos_Removed_String = "",
                lexemes[] = null,
                token = null;

        try {
            /*
             * Get lexemes
             */
            lexemes = tagged_String.split(tokenizerChar);

            /*
             * loop and make Token based string
             */
            for (int i = 0; i < lexemes.length; i++) {
                /*
                 * Extract token
                 */
                try{
                    token = lexemes[i].substring(0, lexemes[i].lastIndexOf(intraLexemeSeparatorCharacter));
                }
                catch(IndexOutOfBoundsException ex){
                    /*
                     * Case for no intraLexemeSeparatorCharacter found.
                     */
                     token =  lexemes[i].substring(0, lexemes[i].length());
                }

                /*
                 * Make String
                 */
                pos_Removed_String += token + tokenizerChar;

            }

            /*
             * Remove last tokenizerChar
             */
            pos_Removed_String = pos_Removed_String.substring(0, pos_Removed_String.length() - 1);


        } catch (Exception e) {
            throw new Exception("StandardSimilarityAnalysed : removePosFromTaggedString : "
                    + e + " : " + e.getMessage());
        }

        return pos_Removed_String;
    }
}
