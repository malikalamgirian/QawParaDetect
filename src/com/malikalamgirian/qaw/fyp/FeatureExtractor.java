/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malikalamgirian.qaw.fyp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import org.w3c.dom.*;

/**
 *
 * @author wasif
 */
public class FeatureExtractor {

    public FeatureExtractor(Properties properties) throws Exception {
        /*
         * Set state of proper Properties instance field
         */
        this.properties = properties;
        /*
         * This also sets instance variable "feature_Extracted_File_URL"
         */
        this.setFeature_Extracted_File_URL(properties);

        /*
         * call Process method
         */
        process();
    }
    /*
     * Declarations
     */
    private Properties properties;
    private String feature_Extracted_File_URL;
    // Xpath related
//    private XPathFactory afactory;
//    private XPath xpath;
//    private XPathExpression expr;

    private void process() throws Exception {
        /*
         * Local Declarations
         */
        Document unProcessed_Doc,
                processed_Doc;

        Element UnProcessed_Doc_Root,
                processed_Doc_Root,
                LcsOfCharacters_Based_Dice_Coefficient_Element,
                LcsOfLexemes_Based_Dice_Coefficient_Element,
                LcsBasedOnTokens_Based_Dice_Coefficient_Element,
                LcsOfCharacters_Based_Cosine_Similarity_Element,
                LcsOfLexemes_Based_Cosine_Similarity_Element,
                LcsBasedOnTokens_Based_Cosine_Similarity_Element,
                LcsOfCharacters_Based_Overlap_Coefficient_Element,
                LcsOfLexemes_Based_Overlap_Coefficient_Element,
                LcsBasedOnTokens_Based_Overlap_Coefficient_Element,
                TBAL_Based_Dice_Coefficient_Element,
                LBAL_Based_Dice_Coefficient_Element,
                TBAL_Based_Cosine_Similarity_Element,
                LBAL_Based_Cosine_Similarity_Element,
                TBAL_Based_Overlap_Coefficient_Element,
                LBAL_Based_Overlap_Coefficient_Element,
                LBALEventDetection_Element,
                characters_Based_Length_Ratio_Element,
                token_Based_Length_Ratio_Element,
                token_Based_Length_Difference_Element,
                characters_Based_Length_Difference_Element;

        Node pair_UnProcessed,
                pair_Processed;

        NodeList pairs_Of_UnProcessed_Doc,
                pairs_Processed_Doc,
                lcsOfCharacters_NodeList,
                lcsOfLexemes_NodeList,
                lcsBasedOnTokens_NodeList,
                posFilteredString1_NodeList,
                posFilteredString2_NodeList,
                TBALSequenceForString1_NodeList,
                TBALSequenceForString2_NodeList,
                LBALSequenceForString1_NodeList,
                LBALSequenceForString2_NodeList,
                LcsOfCharactersBasedEventDetection_NodeList,
                LcsOfLexemesBasedEventDetection_NodeList,
                LcsBasedOnTokensBasedEventDetection_NodeList,
                TBALBasedEventDetectionBasedOnString1AndString2Sequence_NodeList,
                TBALBasedEventDetectionBasedOnString1OrString2Sequence_NodeList,
                LBALSequenceForString1BasedEventDetection_NodeList,
                LBALSequenceForString2BasedEventDetection_NodeList,
                LBALEventDetection_NodeList,
                Polarity_NodeList,

                VerbAntonymMatch_NodeList,
                AdjectiveAntonymMatch_NodeList,
                NounAntonymMatch_NodeList,
                AdverbAntonymMatch_NodeList,

                VerbOrAdjectiveAntonymMatch_NodeList,
                VerbOrAdjectiveOrNounOrAdverbAntonymMatch_NodeList,

                IntraString1AntonymMatch_NodeList,
                IntraString2AntonymMatch_NodeList,
                IntraString1OrIntraString2AntonymMatch_NodeList,
                IntraString1AndIntraString2AntonymMatch_NodeList,
                CosineSimilarity_NodeList,
                DiceSimilarity_NodeList,
                OverlapCoefficient_NodeList,
                CosineSimilarity_Without_POS_NodeList,
                DiceSimilarity_Without_POS_NodeList,
                OverlapCoefficient_Without_POS_NodeList;

        String intraLexemeSeparatorCharacter,
                tokenizerChar,
                posFilteredString1Str,
                posFilteredString2Str;

        int pair_Quality,
                pair_Quality_Predictions_Successfully_Made = 0,
                pair_Quality_Predictions_Failed_Made = 0;


        try {
            System.out.println("FeaturesExtracted File URL is : " + getFeature_Extracted_File_URL());
            System.out.println("properties.getInput_XML_File_URL() is : " + properties.getInput_XML_File_URL());

            /*
             * set separator character
             */
            tokenizerChar = " ";

            /*
             * set intraLexemeSeparatorCharacter, for 'Tagged String'
             */
            intraLexemeSeparatorCharacter = "/";

            /*
             * 1.Get XML Document, for <code>properties.getInput_XML_File_URL()
             * , properties.getStandard_Similarity_Analysed_File_URL()()</code>
             */
            unProcessed_Doc = XMLProcessor.getXMLDocumentForXMLFile(properties.getInput_XML_File_URL());
            processed_Doc = XMLProcessor.getXMLDocumentForXMLFile(properties.getStandard_Similarity_Analysed_File_URL());

            /*
             * 2. Get Document Element
             */
            UnProcessed_Doc_Root = unProcessed_Doc.getDocumentElement();
            processed_Doc_Root = processed_Doc.getDocumentElement();

            /*
             * 3. Select all Required NodeList
             */
            int notEquals = 0;
            pairs_Of_UnProcessed_Doc = UnProcessed_Doc_Root.getElementsByTagName("Pair");

//             Select "Content Vectors"
            posFilteredString1_NodeList = processed_Doc_Root.getElementsByTagName("PosFilteredString1");
            posFilteredString2_NodeList = processed_Doc_Root.getElementsByTagName("PosFilteredString2");
//
//             Select NonMonotonic Alignment (LCS) Related
//            lcsOfCharacters_NodeList = processed_Doc_Root.getElementsByTagName("LcsOfCharacters");
//            lcsOfLexemes_NodeList = processed_Doc_Root.getElementsByTagName("LcsOfLexemes");
//            lcsBasedOnTokens_NodeList = processed_Doc_Root.getElementsByTagName("LcsBasedOnTokens");
//
//             BowBasedAlignment
            TBALSequenceForString1_NodeList = processed_Doc_Root.getElementsByTagName("TBALSequenceForString1");
            TBALSequenceForString2_NodeList = processed_Doc_Root.getElementsByTagName("TBALSequenceForString2");
            LBALSequenceForString1_NodeList = processed_Doc_Root.getElementsByTagName("LBALSequenceForString1");
            LBALSequenceForString2_NodeList = processed_Doc_Root.getElementsByTagName("LBALSequenceForString2");
//
//             Event Detection
//            LcsOfCharactersBasedEventDetection_NodeList = processed_Doc_Root.getElementsByTagName("LcsOfCharactersBasedEventDetection");
//            LcsOfLexemesBasedEventDetection_NodeList = processed_Doc_Root.getElementsByTagName("LcsOfLexemesBasedEventDetection");
//            LcsBasedOnTokensBasedEventDetection_NodeList = processed_Doc_Root.getElementsByTagName("LcsBasedOnTokensBasedEventDetection");

//            TBALBasedEventDetectionBasedOnString1AndString2Sequence_NodeList = processed_Doc_Root.getElementsByTagName("TBALBasedEventDetectionBasedOnString1AndString2Sequence");
//            TBALBasedEventDetectionBasedOnString1OrString2Sequence_NodeList = processed_Doc_Root.getElementsByTagName("TBALBasedEventDetectionBasedOnString1OrString2Sequence");

//            LBALSequenceForString1BasedEventDetection_NodeList = processed_Doc_Root.getElementsByTagName("LBALSequenceForString1BasedEventDetection");
//            LBALSequenceForString2BasedEventDetection_NodeList = processed_Doc_Root.getElementsByTagName("LBALSequenceForString2BasedEventDetection");
            LBALEventDetection_NodeList = processed_Doc_Root.getElementsByTagName("LBALEventDetection");

//            Polarity
            Polarity_NodeList = processed_Doc_Root.getElementsByTagName("Polarity");

//            Antonym Related
//            VerbAntonymMatch_NodeList = processed_Doc_Root.getElementsByTagName("VerbAntonymMatch");
//            AdjectiveAntonymMatch_NodeList = processed_Doc_Root.getElementsByTagName("AdjectiveAntonymMatch");
//            NounAntonymMatch_NodeList = processed_Doc_Root.getElementsByTagName("NounAntonymMatch");
//            AdverbAntonymMatch_NodeList = processed_Doc_Root.getElementsByTagName("AdverbAntonymMatch");
//            VerbOrAdjectiveAntonymMatch_NodeList = processed_Doc_Root.getElementsByTagName("VerbOrAdjectiveAntonymMatch");
            VerbOrAdjectiveOrNounOrAdverbAntonymMatch_NodeList = processed_Doc_Root.getElementsByTagName("VerbOrAdjectiveOrNounOrAdverbAntonymMatch");

//            IntraString1OrIntraString2AntonymMatch_NodeList = processed_Doc_Root.getElementsByTagName("IntraString1OrIntraString2AntonymMatch");

//            Standard Similarity
//            CosineSimilarity_NodeList = processed_Doc_Root.getElementsByTagName("CosineSimilarity");
//            DiceSimilarity_NodeList = processed_Doc_Root.getElementsByTagName("OverlapCoefficient");
//            OverlapCoefficient_NodeList = processed_Doc_Root.getElementsByTagName("DiceSimilarity");
//
//            CosineSimilarity_Without_POS_NodeList = processed_Doc_Root.getElementsByTagName("CosineSimilarityWithoutPOS");
//            DiceSimilarity_Without_POS_NodeList = processed_Doc_Root.getElementsByTagName("OverlapCoefficientWithoutPOS");
//            OverlapCoefficient_Without_POS_NodeList = processed_Doc_Root.getElementsByTagName("DiceSimilarityWithoutPOS");

            /*
             * 4. loop through all pairs
             */
            for (int i = 0; i < pairs_Of_UnProcessed_Doc.getLength(); i++) {
                /*
                 * Extract "Pair"
                 */
                pair_UnProcessed = pairs_Of_UnProcessed_Doc.item(i);

                /*
                 * Extract PosFilteredStrings
                 */
                posFilteredString1Str = posFilteredString1_NodeList.item(i).getTextContent();
                posFilteredString2Str = posFilteredString2_NodeList.item(i).getTextContent();

                /*
                 * Monotonic Alignment (LCS Based)
                 */
                /*
                 * Create Nodes
                 */
//                LcsOfCharacters_Based_Dice_Coefficient_Element = unProcessed_Doc.createElement("LcsOfCharactersBasedDiceCoefficient");
//                LcsOfLexemes_Based_Dice_Coefficient_Element = unProcessed_Doc.createElement("LcsOfLexemesBasedDiceCoefficient");
//                LcsBasedOnTokens_Based_Dice_Coefficient_Element = unProcessed_Doc.createElement("LcsBasedOnTokensBasedDiceCoefficient");
//
//                LcsOfCharacters_Based_Cosine_Similarity_Element = unProcessed_Doc.createElement("LcsOfCharactersBasedCosineSimilarity");
//                LcsOfLexemes_Based_Cosine_Similarity_Element = unProcessed_Doc.createElement("LcsOfLexemesBasedCosineSimilarity");
//                LcsBasedOnTokens_Based_Cosine_Similarity_Element = unProcessed_Doc.createElement("LcsBasedOnTokensBasedCosineSimilarity");
//
//                LcsOfCharacters_Based_Overlap_Coefficient_Element = unProcessed_Doc.createElement("LcsOfCharactersBasedOverlapCoefficient");
//                LcsOfLexemes_Based_Overlap_Coefficient_Element = unProcessed_Doc.createElement("LcsOfLexemesBasedOverlapCoefficient");
//                LcsBasedOnTokens_Based_Overlap_Coefficient_Element = unProcessed_Doc.createElement("LcsBasedOnTokensBasedOverlapCoefficient");
                /*
                 * Length Ratio
                 */
                characters_Based_Length_Ratio_Element = unProcessed_Doc.createElement("CharactersBasedLengthRatioOfContentVectors");
                token_Based_Length_Ratio_Element = unProcessed_Doc.createElement("TokenBasedLengthRatioOfContentVectors");
                /*
                 * Set Text Contents
                 */
//                LcsOfCharacters_Based_Dice_Coefficient_Element.setTextContent(Double.toString(getDiceCoefficient((float) lcsOfCharacters_NodeList.item(i).getTextContent().length(), (float) posFilteredString1Str.length(), (float) posFilteredString2Str.length())));
//                LcsBasedOnTokens_Based_Dice_Coefficient_Element.setTextContent(Double.toString(getDiceCoefficient((float) getNumberOfTokens(lcsBasedOnTokens_NodeList.item(i).getTextContent(), tokenizerChar), (float) getNumberOfTokens(posFilteredString1Str, tokenizerChar), (float) getNumberOfTokens(posFilteredString2Str, tokenizerChar))));
//                LcsOfLexemes_Based_Dice_Coefficient_Element.setTextContent(Double.toString(getDiceCoefficient((float) getNumberOfTokens(lcsOfLexemes_NodeList.item(i).getTextContent(), tokenizerChar), (float) getNumberOfTokens(posFilteredString1Str, tokenizerChar), (float) getNumberOfTokens(posFilteredString2Str, tokenizerChar))));

//                LcsOfCharacters_Based_Cosine_Similarity_Element.setTextContent(Double.toString(getCosineSimilarity((float) lcsOfCharacters_NodeList.item(i).getTextContent().length(), (float) posFilteredString1Str.length(), (float) posFilteredString2Str.length())));
//                LcsBasedOnTokens_Based_Cosine_Similarity_Element.setTextContent(Double.toString(getCosineSimilarity((float) getNumberOfTokens(lcsBasedOnTokens_NodeList.item(i).getTextContent(), tokenizerChar), (float) getNumberOfTokens(posFilteredString1Str, tokenizerChar), (float) getNumberOfTokens(posFilteredString2Str, tokenizerChar))));
//                LcsOfLexemes_Based_Cosine_Similarity_Element.setTextContent(Double.toString(getCosineSimilarity((float) getNumberOfTokens(lcsOfLexemes_NodeList.item(i).getTextContent(), tokenizerChar), (float) getNumberOfTokens(posFilteredString1Str, tokenizerChar), (float) getNumberOfTokens(posFilteredString2Str, tokenizerChar))));
                
//                LcsOfCharacters_Based_Overlap_Coefficient_Element.setTextContent(Double.toString(getOverlapCoefficient((float) lcsOfCharacters_NodeList.item(i).getTextContent().length(), (float) posFilteredString1Str.length(), (float) posFilteredString2Str.length())));
//                LcsBasedOnTokens_Based_Overlap_Coefficient_Element.setTextContent(Double.toString(getOverlapCoefficient((float) getNumberOfTokens(lcsBasedOnTokens_NodeList.item(i).getTextContent(), tokenizerChar), (float) getNumberOfTokens(posFilteredString1Str, tokenizerChar), (float) getNumberOfTokens(posFilteredString2Str, tokenizerChar))));
//                LcsOfLexemes_Based_Overlap_Coefficient_Element.setTextContent(Double.toString(getOverlapCoefficient((float) getNumberOfTokens(lcsOfLexemes_NodeList.item(i).getTextContent(), tokenizerChar), (float) getNumberOfTokens(posFilteredString1Str, tokenizerChar), (float) getNumberOfTokens(posFilteredString2Str, tokenizerChar))));

                /*
                 * Length Ratio
                 */
                characters_Based_Length_Ratio_Element.setTextContent(Double.toString(getLengthRatio((float) removePosFromTaggedString(posFilteredString1Str,tokenizerChar,intraLexemeSeparatorCharacter).length(), (float) removePosFromTaggedString(posFilteredString2Str,tokenizerChar,intraLexemeSeparatorCharacter).length())));
                token_Based_Length_Ratio_Element.setTextContent(Double.toString(getLengthRatio(getNumberOfTokens(posFilteredString1Str, tokenizerChar), getNumberOfTokens(posFilteredString2Str, tokenizerChar))));

                /*
                 * Length Difference of content vectors
                 */
//                characters_Based_Length_Difference_Element = unProcessed_Doc.createElement("CharactersBasedLengthDifferenceOfContentVectors");
//                token_Based_Length_Difference_Element = unProcessed_Doc.createElement("TokensBasedLengthDifferenceOfContentVectors");
                /*
                 * Set text contents
                 */
//                characters_Based_Length_Difference_Element.setTextContent(Integer.toString((Math.max(posFilteredString1Str.length(), posFilteredString2Str.length()) - Math.min(posFilteredString1Str.length(), posFilteredString2Str.length()))));
//                token_Based_Length_Difference_Element.setTextContent(Float.toString((Math.max(getNumberOfTokens(posFilteredString1Str, tokenizerChar), getNumberOfTokens(posFilteredString2Str, tokenizerChar)) - Math.min(getNumberOfTokens(posFilteredString1Str, tokenizerChar), getNumberOfTokens(posFilteredString2Str, tokenizerChar)))));

                /*
                 * Non-Monotonic Alignment (Bow Based)
                 */
                /*
                 * Create Nodes
                 */
                TBAL_Based_Dice_Coefficient_Element = unProcessed_Doc.createElement("TBALBasedDiceCoefficient");
                LBAL_Based_Dice_Coefficient_Element = unProcessed_Doc.createElement("LBALBasedDiceCoefficient");

                TBAL_Based_Cosine_Similarity_Element = unProcessed_Doc.createElement("TBALBasedCosineSimilarity");
                LBAL_Based_Cosine_Similarity_Element = unProcessed_Doc.createElement("LBALBasedCosineSimilarity");

//                TBAL_Based_Overlap_Coefficient_Element = unProcessed_Doc.createElement("TBALBasedOverlapCoefficient");
//                LBAL_Based_Overlap_Coefficient_Element = unProcessed_Doc.createElement("LBALBasedOverlapCoefficient");
                /*
                 * Set Text Contents, here we base them on String1
                 */
                TBAL_Based_Dice_Coefficient_Element.setTextContent( Double.toString(getDiceCoefficient( (float) Math.max(   getNumberOfTokens( TBALSequenceForString1_NodeList.item(i).getTextContent(), tokenizerChar) ,   getNumberOfTokens(  TBALSequenceForString2_NodeList.item(i).getTextContent(), tokenizerChar)    ),   (float) getNumberOfTokens(posFilteredString1Str, tokenizerChar), (float) getNumberOfTokens(posFilteredString2Str, tokenizerChar))));
                LBAL_Based_Dice_Coefficient_Element.setTextContent(Double.toString(getDiceCoefficient((float) Math.max( getNumberOfTokens(LBALSequenceForString1_NodeList.item(i).getTextContent(), tokenizerChar),getNumberOfTokens(LBALSequenceForString2_NodeList.item(i).getTextContent(), tokenizerChar)), (float) getNumberOfTokens(posFilteredString1Str, tokenizerChar), (float) getNumberOfTokens(posFilteredString2Str, tokenizerChar))));

                TBAL_Based_Cosine_Similarity_Element.setTextContent(Double.toString(getCosineSimilarity( (float) Math.max(   getNumberOfTokens( TBALSequenceForString1_NodeList.item(i).getTextContent(), tokenizerChar) ,   getNumberOfTokens(  TBALSequenceForString2_NodeList.item(i).getTextContent(), tokenizerChar)    ),   (float) getNumberOfTokens(posFilteredString1Str, tokenizerChar), (float) getNumberOfTokens(posFilteredString2Str, tokenizerChar))));
                LBAL_Based_Cosine_Similarity_Element.setTextContent(Double.toString(getCosineSimilarity((float) Math.max( getNumberOfTokens(LBALSequenceForString1_NodeList.item(i).getTextContent(), tokenizerChar),getNumberOfTokens(LBALSequenceForString2_NodeList.item(i).getTextContent(), tokenizerChar)), (float) getNumberOfTokens(posFilteredString1Str, tokenizerChar), (float) getNumberOfTokens(posFilteredString2Str, tokenizerChar))));

//                TBAL_Based_Overlap_Coefficient_Element.setTextContent(Double.toString(getOverlapCoefficient( (float) Math.max(   getNumberOfTokens( TBALSequenceForString1_NodeList.item(i).getTextContent(), tokenizerChar) ,   getNumberOfTokens(  TBALSequenceForString2_NodeList.item(i).getTextContent(), tokenizerChar)    ),   (float) getNumberOfTokens(posFilteredString1Str, tokenizerChar), (float) getNumberOfTokens(posFilteredString2Str, tokenizerChar))));
//                LBAL_Based_Overlap_Coefficient_Element.setTextContent(Double.toString(getOverlapCoefficient((float) Math.max( getNumberOfTokens(LBALSequenceForString1_NodeList.item(i).getTextContent(), tokenizerChar),getNumberOfTokens(LBALSequenceForString2_NodeList.item(i).getTextContent(), tokenizerChar)), (float) getNumberOfTokens(posFilteredString1Str, tokenizerChar), (float) getNumberOfTokens(posFilteredString2Str, tokenizerChar))));

                /*
                 * Add Event Detection
                 */
                /*
                 * LCS Based
                 */
//                pair_UnProcessed.appendChild(unProcessed_Doc.adoptNode(LcsOfCharactersBasedEventDetection_NodeList.item(i).cloneNode(true)));
//                pair_UnProcessed.appendChild(unProcessed_Doc.adoptNode(LcsOfLexemesBasedEventDetection_NodeList.item(i).cloneNode(true)));
//                pair_UnProcessed.appendChild(unProcessed_Doc.adoptNode(LcsBasedOnTokensBasedEventDetection_NodeList.item(i).cloneNode(true)));
                /*
                 * BOW Based
                 */
//                pair_UnProcessed.appendChild(unProcessed_Doc.adoptNode(TBALBasedEventDetectionBasedOnString1AndString2Sequence_NodeList.item(i).cloneNode(true)));
//                pair_UnProcessed.appendChild(unProcessed_Doc.adoptNode(TBALBasedEventDetectionBasedOnString1OrString2Sequence_NodeList.item(i).cloneNode(true)));
//                pair_UnProcessed.appendChild(unProcessed_Doc.adoptNode(LBALSequenceForString1BasedEventDetection_NodeList.item(i).cloneNode(true)));
//                pair_UnProcessed.appendChild(unProcessed_Doc.adoptNode(LBALSequenceForString2BasedEventDetection_NodeList.item(i).cloneNode(true)));
                pair_UnProcessed.appendChild(unProcessed_Doc.adoptNode(LBALEventDetection_NodeList.item(i).cloneNode(true)));

                /*
                 * Add Polarity
                 */
                pair_UnProcessed.appendChild(unProcessed_Doc.adoptNode(Polarity_NodeList.item(i).cloneNode(true)));

                /*
                 * Add Antonym Related
                 */
//                pair_UnProcessed.appendChild(unProcessed_Doc.adoptNode(VerbAntonymMatch_NodeList.item(i).cloneNode(true)));
//                pair_UnProcessed.appendChild(unProcessed_Doc.adoptNode(AdjectiveAntonymMatch_NodeList.item(i).cloneNode(true)));
//                pair_UnProcessed.appendChild(unProcessed_Doc.adoptNode(NounAntonymMatch_NodeList.item(i).cloneNode(true)));
//                pair_UnProcessed.appendChild(unProcessed_Doc.adoptNode(AdverbAntonymMatch_NodeList.item(i).cloneNode(true)));
//
//                pair_UnProcessed.appendChild(unProcessed_Doc.adoptNode(VerbOrAdjectiveAntonymMatch_NodeList.item(i).cloneNode(true)));
                pair_UnProcessed.appendChild(unProcessed_Doc.adoptNode(VerbOrAdjectiveOrNounOrAdverbAntonymMatch_NodeList.item(i).cloneNode(true)));
//                pair_UnProcessed.appendChild(unProcessed_Doc.adoptNode(IntraString1OrIntraString2AntonymMatch_NodeList.item(i).cloneNode(true)));

                /*
                 * Add Standard Similarity Related
                 */
//                pair_UnProcessed.appendChild(unProcessed_Doc.adoptNode(CosineSimilarity_NodeList.item(i).cloneNode(true)));
//                pair_UnProcessed.appendChild(unProcessed_Doc.adoptNode(OverlapCoefficient_NodeList.item(i).cloneNode(true)));
//                pair_UnProcessed.appendChild(unProcessed_Doc.adoptNode(DiceSimilarity_NodeList.item(i).cloneNode(true)));
//                pair_UnProcessed.appendChild(unProcessed_Doc.adoptNode(CosineSimilarity_Without_POS_NodeList.item(i).cloneNode(true)));
//                pair_UnProcessed.appendChild(unProcessed_Doc.adoptNode(OverlapCoefficient_Without_POS_NodeList.item(i).cloneNode(true)));
//                pair_UnProcessed.appendChild(unProcessed_Doc.adoptNode(DiceSimilarity_Without_POS_NodeList.item(i).cloneNode(true)));

                /*
                 * Add Calculated Elements
                 */
//                pair_UnProcessed.appendChild(LcsOfCharacters_Based_Dice_Coefficient_Element);
//                pair_UnProcessed.appendChild(LcsOfLexemes_Based_Dice_Coefficient_Element);
//                pair_UnProcessed.appendChild(LcsBasedOnTokens_Based_Dice_Coefficient_Element);
//
//                pair_UnProcessed.appendChild(LcsOfCharacters_Based_Cosine_Similarity_Element);
//                pair_UnProcessed.appendChild(LcsOfLexemes_Based_Cosine_Similarity_Element);
//                pair_UnProcessed.appendChild(LcsBasedOnTokens_Based_Cosine_Similarity_Element);
//
//                pair_UnProcessed.appendChild(LcsOfCharacters_Based_Overlap_Coefficient_Element);
//                pair_UnProcessed.appendChild(LcsOfLexemes_Based_Overlap_Coefficient_Element);
//                pair_UnProcessed.appendChild(LcsBasedOnTokens_Based_Overlap_Coefficient_Element);

                pair_UnProcessed.appendChild(TBAL_Based_Dice_Coefficient_Element);
                pair_UnProcessed.appendChild(LBAL_Based_Dice_Coefficient_Element);

                pair_UnProcessed.appendChild(TBAL_Based_Cosine_Similarity_Element);
                pair_UnProcessed.appendChild(LBAL_Based_Cosine_Similarity_Element);

//                pair_UnProcessed.appendChild(TBAL_Based_Overlap_Coefficient_Element);
//                pair_UnProcessed.appendChild(LBAL_Based_Overlap_Coefficient_Element);

                pair_UnProcessed.appendChild(characters_Based_Length_Ratio_Element);
                pair_UnProcessed.appendChild(token_Based_Length_Ratio_Element);
                
//                pair_UnProcessed.appendChild(characters_Based_Length_Difference_Element);
//                pair_UnProcessed.appendChild(token_Based_Length_Difference_Element);

                System.out.println((i + 1) + " / " + pairs_Of_UnProcessed_Doc.getLength() + "\n");
            }

            /*
             * 5. Transform the tree, to "feature_Extracted_File_URL"
             */
            XMLProcessor.transformXML(unProcessed_Doc, new StreamResult(new File(this.feature_Extracted_File_URL)));

        } catch (Exception e) {
            throw new Exception("FeatureExtractor : Process :"
                    + e + " : " + e.getMessage());
        }
    }

    /*
     * Helper Methods
     */
    private String getFeature_Extracted_File_URL() {
        return feature_Extracted_File_URL;
    }

    private void setFeature_Extracted_File_URL(Properties properties) throws Exception {
        /*
         * Local Declarations
         */
        String suffixToAdd,
                newURL;
        try {
            /*
             * add Suffix, "_features_extracted" for "Features Extracted"
             */
            suffixToAdd = "_best_system_features_extracted";

            /*
             * call FileSystemManager.addSuffixToFileURL
             */
            newURL = FileSystemManager.addSuffixToFileURL(properties.getInput_XML_File_URL(), suffixToAdd, null);

            /*
             * set instance state
             */
            feature_Extracted_File_URL = newURL;

            /*
             * set "properties" instance field value
             */
            properties.setFeature_Extracted_File_URL(newURL);

        } catch (Exception e) {
            throw new Exception("FeatureExtractor : setFeature_Extracted_File_URL : "
                    + e + " : " + e.getMessage());
        }
    }

    private Float getDiceCoefficient(Float x_Common_y, Float x, Float y) throws Exception {
        /*
         * Local Declarations
         */
        Float dice_Value_To_Return = null;

        try {
            dice_Value_To_Return = (2 * x_Common_y) / (x + y);

        } catch (Exception e) {
            throw new Exception("FeatureExtractor : getDiceCoefficient : "
                    + e + " : " + e.getMessage());
        }
        return dice_Value_To_Return;
    }

    private Float getCosineSimilarity(Float x_Common_y, Float x, Float y) throws Exception {
        /*
         * Local Declarations
         */
        Float cosine_Similarity_Value_To_Return = null;

        try {
	/*
         * May need to change the definiton...
         */
            cosine_Similarity_Value_To_Return = x_Common_y / ((float) Math.sqrt(x) * (float) Math.sqrt(y));

            /*
             * Test
             */
            if (cosine_Similarity_Value_To_Return > 1) {
                cosine_Similarity_Value_To_Return = cosine_Similarity_Value_To_Return + 0;
            }

        } catch (Exception e) {
            throw new Exception("FeatureExtractor : getCosineSimilarity : "
                    + e + " : " + e.getMessage());
        }
        return cosine_Similarity_Value_To_Return;
    }

    private Float getOverlapCoefficient(Float x_Common_y, Float x, Float y) throws Exception {
        /*
         * Local Declarations
         */
        Float overlap_Coefficient_Value_To_Return = null;

        try {
            overlap_Coefficient_Value_To_Return = x_Common_y / Math.min(x, y);

        } catch (Exception e) {
            throw new Exception("FeatureExtractor : getOverlapCoefficient : "
                    + e + " : " + e.getMessage());
        }

        return overlap_Coefficient_Value_To_Return;
    }

    private Float getLengthRatio(Float x, Float y) throws Exception {
        /*
         * Local Declarations
         */
        Float length_Ratio_To_Return = null;

        try {
            length_Ratio_To_Return = Math.min(x, y) / Math.max(x, y);

        } catch (Exception e) {
            throw new Exception("FeatureExtractor : getLengthRatio : "
                    + e + " : " + e.getMessage());
        }
        return length_Ratio_To_Return;
    }

    /*
     * Here the definition of Tokens and Lexemes is same.
     */
    private Float getNumberOfTokens(String string_To_Find_Number_Of_Tokens_Of, String tokenizer_Char) throws Exception {
        /*
         * Local Declarations
         */
        Float number_Of_Tokens_To_Return = null;

        try {
            number_Of_Tokens_To_Return = (float) string_To_Find_Number_Of_Tokens_Of.split(tokenizer_Char).length;

        } catch (Exception e) {
            throw new Exception("FeatureExtractor : getNumberOfTokens : "
                    + e + " : " + e.getMessage());
        }
        return number_Of_Tokens_To_Return;
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
            throw new Exception("FeatureExtractor : removePosFromTaggedString : "
                    + e + " : " + e.getMessage());
        }

        return pos_Removed_String;
    }

}
