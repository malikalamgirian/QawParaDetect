/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malikalamgirian.qaw.fyp;

import edu.smu.tspell.wordnet.Synset;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

/**
 *
 * @author wasif
 */
public class AntonymMatchDetector {

    public AntonymMatchDetector(Properties properties) throws Exception {
        /*
         * Set state of proper Properties instance field
         */
        this.properties = properties;
        /*
         * This also sets instance variable "antonym_Match_Detector_File_URL"
         */
        this.setAntonym_Match_Detector_File_URL(properties);
        /*
         * Instantiate WordNetManager
         */
        wordNet = new WordNetManager();

        /*
         * call Process method
         */
        process();
    }
    /*
     * Declarations
     */
    private Properties properties;
    private String antonym_Match_Detector_File_URL;
    private WordNetManager wordNet;

    /*
     * Antonym Match Detection uses WordNetManager helper class, which impelments
     * various static methods.
     */
    private void process() throws Exception {
        /*
         * Local Declarations
         */
        Document doc;

        Element root,
                antonymDetectionBase,
                verbAntonymMatchElement,
                adjectiveAntonymMatchElement,
                nounAntonymMatchElement,
                adverbAntonymMatchElement,
                verbOrAdjectiveAntonymMatchElement,
                verbOrAdjectiveOrNounOrAdverbAntonymMatchElement,
                intraString1AntonymMatchElement,
                intraString2AntonymMatchElement,
                intraString1OrIntraString2AntonymMatchElement;

        Node pair;

        NodeList pairs,
                TBUnalignedLexemesSequenceForString1,
                TBUnalignedLexemesSequenceForString2;

        String intraLexemeSeparatorCharacter,
                tokenizerChar,
                TBUnalignedLexemesSequenceForString1Str,
                TBUnalignedLexemesSequenceForString2Str;

        AntonymMatchType str1ToStr2AntonymMatchType,
                str2ToStr1AntonymMatchType,
                intraString1AntonymMatchType,
                intraString2AntonymMatchType;

        int pair_Quality,
                pair_Quality_Predictions_Successfully_Made = 0,
                pair_Quality_Predictions_Failed_Made = 0;


        try {
            System.out.println("AntonymMatchDetector File URL is : " + getAntonym_Match_Detector_File_URL());
            System.out.println("properties.getContradiction_And_Polarity_Analyser_File_URL() is : " + properties.getContradiction_And_Polarity_Analyser_File_URL());

            /*
             * set separator character
             */
            tokenizerChar = " ";

            /*
             * set intraLexemeSeparatorCharacter, for 'Tagged String'
             */
            intraLexemeSeparatorCharacter = "/";

            /*
             * 1.Get XML Document, for <code>properties.getContradiction_And_Polarity_Analyser_File_URL()</code>
             */
            doc = XMLProcessor.getXMLDocumentForXMLFile(properties.getContradiction_And_Polarity_Analyser_File_URL());

            /*
             * 2. Get Document Element
             */
            root = doc.getDocumentElement();

            /*
             * 3. Select all "Pair", 'PosFilteredString1' and 'PosFilteredString2'
             */
            pairs = root.getElementsByTagName("Pair");
            TBUnalignedLexemesSequenceForString1 = root.getElementsByTagName("TBUnalignedLexemesSequenceForString1");
            TBUnalignedLexemesSequenceForString2 = root.getElementsByTagName("TBUnalignedLexemesSequenceForString2");

            /*
             * Creates following structure
             *
             * <AntonymDetection>
             * 
             *  <VerbAntonymMatch>
             *      true/false
             *  </VerbAntonymMatch>
             *
             *  <AdjectiveAntonymMatch>
             *      true/false
             *  <AdjectiveAntonymMatch>
             *
             *  <NounAntonymMatch>
             *      true/false
             *  <NounAntonymMatch>
             *
             *  <AdverbAntonymMatch>
             *      true/false
             *  <AdverbAntonymMatch>
             *
             *  <VerbOrAdjectiveAntonymMatch>
             *      true/false
             *  <VerbOrAdjectiveAntonymMatch>
             *
             *  <VerbOrAdjectiveOrNounOrAdverbAntonymMatch>
             *      true/false
             *  <VerbOrAdjectiveOrNounOrAdverbAntonymMatch>
             *
             *  <IntraString1AntonymMatch>
             *      true/false
             *  <IntraString1AntonymMatch>
             *
             *  <IntraString2AntonymMatch>
             *      true/false
             *  <IntraString2AntonymMatch>
             *
             *  <IntraString1OrIntraString2AntonymMatch>
             *      true/false
             *  <IntraString1OrIntraString2AntonymMatch>
             *
             * </AntonymDetection>
             *
             */

            /*
             * 4. loop through all pairs
             */
            for (int i = 0; i < pairs.getLength(); i++) {
                /*
                 * Get 'Pair'
                 */
                pair = pairs.item(i);

                /*
                 * Get pair Quality
                 */
                pair_Quality = Integer.parseInt(pair.getAttributes().getNamedItem("Quality").getTextContent().toString());

                /*
                 * Instantiate AntonymMatchType types
                 */
                str1ToStr2AntonymMatchType = new AntonymMatchType();
                str2ToStr1AntonymMatchType = new AntonymMatchType();
//                intraString1AntonymMatchType = new AntonymMatchType();
//                intraString2AntonymMatchType = new AntonymMatchType();

                /*
                 * Extract text-contents from 'PosFilteredString1' and 'PosFilteredString2'
                 */
                TBUnalignedLexemesSequenceForString1Str = TBUnalignedLexemesSequenceForString1.item(i).getTextContent().toLowerCase();
                TBUnalignedLexemesSequenceForString2Str = TBUnalignedLexemesSequenceForString2.item(i).getTextContent().toLowerCase();

                /*
                 * Create required nodes
                 */
                antonymDetectionBase = doc.createElement("AntonymDetection");
//                verbAntonymMatchElement = doc.createElement("VerbAntonymMatch");
//                adjectiveAntonymMatchElement = doc.createElement("AdjectiveAntonymMatch");
//                nounAntonymMatchElement = doc.createElement("NounAntonymMatch");
//                adverbAntonymMatchElement = doc.createElement("AdverbAntonymMatch");
//                verbOrAdjectiveAntonymMatchElement = doc.createElement("VerbOrAdjectiveAntonymMatch");
                verbOrAdjectiveOrNounOrAdverbAntonymMatchElement = doc.createElement("VerbOrAdjectiveOrNounOrAdverbAntonymMatch");
//                intraString1AntonymMatchElement = doc.createElement("IntraString1AntonymMatch");
//                intraString2AntonymMatchElement = doc.createElement("IntraString2AntonymMatch");
//                intraString1OrIntraString2AntonymMatchElement = doc.createElement("IntraString1OrIntraString2AntonymMatch");

                /*
                 * Call local private helper method to prepare text contents
                 *
                 * str1ToStr2AntonymMatchType
                 * str2ToStr1AntonymMatchType
                 * intraString1AntonymMatchType
                 * intraString2AntonymMatchType
                 *
                 */
                antonymDetector(TBUnalignedLexemesSequenceForString1Str, TBUnalignedLexemesSequenceForString2Str,
                        str1ToStr2AntonymMatchType, tokenizerChar,
                        intraLexemeSeparatorCharacter, pair_Quality);

                antonymDetector(TBUnalignedLexemesSequenceForString2Str, TBUnalignedLexemesSequenceForString1Str,
                        str2ToStr1AntonymMatchType, tokenizerChar,
                        intraLexemeSeparatorCharacter, pair_Quality);

//                intraStringAntonymDetector(posFilteredString1Str, posFilteredString1Str,
//                        intraString1AntonymMatchType, tokenizerChar,
//                        intraLexemeSeparatorCharacter, pair_Quality);
//
//                intraStringAntonymDetector(posFilteredString2Str, posFilteredString2Str,
//                        intraString2AntonymMatchType, tokenizerChar,
//                        intraLexemeSeparatorCharacter, pair_Quality);

                /*
                 * TEST
                 *
                 * 28 August (i)
                 * 
                 * Call methods to check antonyms in same string (intra--string)
                 * as in (i) of AntonymDetector rough notes of 28 August.
                 */
//                if (intraString1AntonymMatchType.getAdjectiveAntonymMatch() == true
//                        || intraString1AntonymMatchType.getAdverbAntonymMatch() == true
//                        || intraString1AntonymMatchType.getNounAntonymMatch() == true
//                        || intraString1AntonymMatchType.getVerbAntonymMatch() == true) {
//                    /*
//                     * If any type of antonym is found, in string1
//                     */
//                    System.out.println("Intra-string1 antonym match found : \n"
//                            + intraString1AntonymMatchType.toString());
//                }
//
//                if (intraString2AntonymMatchType.getAdjectiveAntonymMatch() == true
//                        || intraString2AntonymMatchType.getAdverbAntonymMatch() == true
//                        || intraString2AntonymMatchType.getNounAntonymMatch() == true
//                        || intraString2AntonymMatchType.getVerbAntonymMatch() == true) {
//                    /*
//                     * If any type of antonym is found, in string2
//                     */
//                    System.out.println("Intra-string2 antonym match found : \n"
//                            + intraString2AntonymMatchType.toString());
//                }

                /*
                 * TEST
                 *
                 * 28 August (ii)
                 *
                 * Bidirectional antonym match Detection TEST
                 * --- This checks if both string are having bidirectionally same
                 * --- Antonym Detection matches or not. 
                 *
                 */
//                Boolean antonymFoundStr1ToStr2 = Boolean.FALSE,
//                        antonymFoundStr2ToStr1 = Boolean.FALSE;
//
//                // Strict Bidirectionality Mismatch Test
//                if (str1ToStr2AntonymMatchType.getAdjectiveAntonymMatch() != str2ToStr1AntonymMatchType.getAdjectiveAntonymMatch()
//                        || str1ToStr2AntonymMatchType.getAdverbAntonymMatch() != str2ToStr1AntonymMatchType.getAdverbAntonymMatch()
//                        || str1ToStr2AntonymMatchType.getNounAntonymMatch() != str2ToStr1AntonymMatchType.getNounAntonymMatch()
//                        || str1ToStr2AntonymMatchType.getVerbAntonymMatch() != str2ToStr1AntonymMatchType.getVerbAntonymMatch()) {
//                    /*
//                     * If there there is any bidirectional mis-match found
//                     */
//                    System.out.println("Bidirectional Strict (POS paired) antonym mis-match found : \n"
//                            + "Str1 --> Str2 : \n" + str1ToStr2AntonymMatchType.toString() + "\n"
//                            + "Str2 --> Str1 : \n" + str2ToStr1AntonymMatchType.toString() + "\n");
//                }
//
//                // Loose Bidirectionality Mismatch Test, this relaxes the POS paired test
//                antonymFoundStr1ToStr2 = (str1ToStr2AntonymMatchType.getAdjectiveAntonymMatch()
//                        || str1ToStr2AntonymMatchType.getAdverbAntonymMatch()
//                        || str1ToStr2AntonymMatchType.getNounAntonymMatch()
//                        || str1ToStr2AntonymMatchType.getVerbAntonymMatch());
//                antonymFoundStr2ToStr1 = (str2ToStr1AntonymMatchType.getAdjectiveAntonymMatch()
//                        || str2ToStr1AntonymMatchType.getAdverbAntonymMatch()
//                        || str2ToStr1AntonymMatchType.getNounAntonymMatch()
//                        || str2ToStr1AntonymMatchType.getVerbAntonymMatch());
//
//                if (antonymFoundStr1ToStr2 != antonymFoundStr2ToStr1) {
//                    /*
//                     * If there there is overall- bidirectional mis-match found
//                     */
//                    System.out.println("Bidirectional Loose (overall) antonym mis-match found : \n"
//                            + "Str1 --> Str2 : \n" + str1ToStr2AntonymMatchType.toString() + "\n"
//                            + "Str2 --> Str1 : \n" + str2ToStr1AntonymMatchType.toString() + "\n");
//                }

                /*
                 * TEST
                 *
                 * 29th August
                 *
                 * IF either of 'string1' or 'string2' is having intra-string
                 * antonym match, what should be the "predicted output pair quality"
                 *
                 * 
                 */
//                Boolean antonymMatchFoundInString1 = Boolean.FALSE,
//                        antonymMatchFoundInString2 = Boolean.FALSE;
//
//                // assign evaluated values
//                antonymMatchFoundInString1 = intraString1AntonymMatchType.getAdjectiveAntonymMatch()
//                        || intraString1AntonymMatchType.getAdverbAntonymMatch()
//                        || intraString1AntonymMatchType.getNounAntonymMatch()
//                        || intraString1AntonymMatchType.getVerbAntonymMatch();
//
//                antonymMatchFoundInString2 = intraString2AntonymMatchType.getAdjectiveAntonymMatch()
//                        || intraString2AntonymMatchType.getAdverbAntonymMatch()
//                        || intraString2AntonymMatchType.getNounAntonymMatch()
//                        || intraString2AntonymMatchType.getVerbAntonymMatch();
//
//                // if intra-string antonym match is found
//                if (antonymMatchFoundInString1 || antonymMatchFoundInString2) {
//
//                    // if antonym match found in any direction
//                    if (antonymFoundStr1ToStr2 || antonymFoundStr2ToStr1) {
//
//                        // predicted pairquality should be 1
//                        System.out.println("Intra-String Antonym Match Found : " + "\n"
//                                + "Predicted Pair Quality : 1" + "\n"
//                                + "Pair Quality : " + pair_Quality);
//
//                        // show mismatch
//                        if (pair_Quality == 0) {
//                            System.out.println("Difference between predicted and real Pair Quality.\n"
//                                    + "PosFilteredString1 : " + posFilteredString1Str + "\n"
//                                    + "PosFilteredString2 : " + posFilteredString2Str + "\n");
//
//                            //increment failure counter
//                            ++pair_Quality_Predictions_Failed_Made;
//                        } else {
//                            System.out.println("Prediction Success.\n"
//                                    + "PosFilteredString1 : " + posFilteredString1Str + "\n"
//                                    + "PosFilteredString2 : " + posFilteredString2Str + "\n");
//
//                            //increment success counter
//                            ++pair_Quality_Predictions_Successfully_Made;
//                        }
//                    }
//                } else {
//
//                    // if antonym match found in any direction
//                    if (antonymFoundStr1ToStr2 || antonymFoundStr2ToStr1) {
//
//                        // predicted pairquality should be 0
//                        System.out.println("Predicted Pair Quality : 0" + "\n"
//                                + "Pair Quality : " + pair_Quality);
//
//                        // show mismatch
//                        if (pair_Quality == 1) {
//                            System.out.println("Difference between predicted and real Pair Quality.\n"
//                                    + "PosFilteredString1 : " + posFilteredString1Str + "\n"
//                                    + "PosFilteredString2 : " + posFilteredString2Str + "\n");
//
//                            //increment failure counter
//                            ++pair_Quality_Predictions_Failed_Made;
//
//                        } else {
//                            System.out.println("Prediction Success.\n"
//                                    + "PosFilteredString1 : " + posFilteredString1Str + "\n"
//                                    + "PosFilteredString2 : " + posFilteredString2Str + "\n");
//
//                            //increment success counter
//                            ++pair_Quality_Predictions_Successfully_Made;
//                        }
//                    }
//                }

                /*
                 * Set text contents
//                 */
//                verbAntonymMatchElement.setTextContent(Boolean.toString(str1ToStr2AntonymMatchType.getVerbAntonymMatch() || str2ToStr1AntonymMatchType.getVerbAntonymMatch()));
//                adjectiveAntonymMatchElement.setTextContent(Boolean.toString(str1ToStr2AntonymMatchType.getAdjectiveAntonymMatch() || str2ToStr1AntonymMatchType.getAdjectiveAntonymMatch()));
//                nounAntonymMatchElement.setTextContent(Boolean.toString(str1ToStr2AntonymMatchType.getNounAntonymMatch() || str2ToStr1AntonymMatchType.getNounAntonymMatch()));
//                adverbAntonymMatchElement.setTextContent(Boolean.toString(str1ToStr2AntonymMatchType.getAdverbAntonymMatch() || str2ToStr1AntonymMatchType.getAdverbAntonymMatch()));
//                verbOrAdjectiveAntonymMatchElement.setTextContent(Boolean.toString((str1ToStr2AntonymMatchType.getVerbAntonymMatch() || str1ToStr2AntonymMatchType.getAdjectiveAntonymMatch() || str2ToStr1AntonymMatchType.getVerbAntonymMatch() || str2ToStr1AntonymMatchType.getAdjectiveAntonymMatch())));
                verbOrAdjectiveOrNounOrAdverbAntonymMatchElement.setTextContent(Boolean.toString(str1ToStr2AntonymMatchType.getVerbAntonymMatch() || str1ToStr2AntonymMatchType.getAdjectiveAntonymMatch() || str1ToStr2AntonymMatchType.getNounAntonymMatch() || str1ToStr2AntonymMatchType.getAdverbAntonymMatch() || str2ToStr1AntonymMatchType.getAdjectiveAntonymMatch() || str2ToStr1AntonymMatchType.getAdverbAntonymMatch() || str2ToStr1AntonymMatchType.getNounAntonymMatch() || str2ToStr1AntonymMatchType.getVerbAntonymMatch()));
//                intraString1AntonymMatchElement.setTextContent(Boolean.toString(intraString1AntonymMatchType.getAdjectiveAntonymMatch() || intraString1AntonymMatchType.getAdverbAntonymMatch() || intraString1AntonymMatchType.getNounAntonymMatch() || intraString1AntonymMatchType.getVerbAntonymMatch()));
//                intraString2AntonymMatchElement.setTextContent(Boolean.toString(intraString2AntonymMatchType.getAdjectiveAntonymMatch() || intraString2AntonymMatchType.getAdverbAntonymMatch() || intraString2AntonymMatchType.getNounAntonymMatch() || intraString2AntonymMatchType.getVerbAntonymMatch()));
//                intraString1OrIntraString2AntonymMatchElement.setTextContent(Boolean.toString(intraString1AntonymMatchType.getAdjectiveAntonymMatch() || intraString1AntonymMatchType.getAdverbAntonymMatch() || intraString1AntonymMatchType.getNounAntonymMatch() || intraString1AntonymMatchType.getVerbAntonymMatch() || intraString2AntonymMatchType.getAdjectiveAntonymMatch() || intraString2AntonymMatchType.getAdverbAntonymMatch() || intraString2AntonymMatchType.getNounAntonymMatch() || intraString2AntonymMatchType.getVerbAntonymMatch()));

                /*
                 * Append children
                 */
//                antonymDetectionBase.appendChild(verbAntonymMatchElement);
//                antonymDetectionBase.appendChild(adjectiveAntonymMatchElement);
//                antonymDetectionBase.appendChild(nounAntonymMatchElement);
//                antonymDetectionBase.appendChild(adverbAntonymMatchElement);
//                antonymDetectionBase.appendChild(verbOrAdjectiveAntonymMatchElement);
                antonymDetectionBase.appendChild(verbOrAdjectiveOrNounOrAdverbAntonymMatchElement);
//                antonymDetectionBase.appendChild(intraString1AntonymMatchElement);
//                antonymDetectionBase.appendChild(intraString2AntonymMatchElement);
//                antonymDetectionBase.appendChild(intraString1OrIntraString2AntonymMatchElement);

                /*
                 * Append base to Pair
                 */
                pair.appendChild(antonymDetectionBase);

                System.out.println((i + 1) + " / " + pairs.getLength() + "\n");
            }

            /*
             * TEST
             *
             * 30th August, 2010
             *
             * Statistics Output
             *
             */
//            System.out.println("\nTotal Pair Quality Predictions Made : " + Integer.toString(pair_Quality_Predictions_Failed_Made + pair_Quality_Predictions_Successfully_Made) + "\n"
//                    + "pair_Quality_Predictions_Successfully_Made : " + Integer.toString(pair_Quality_Predictions_Successfully_Made) + "\n"
//                    + "pair_Quality_Predictions_Failed_Made : " + Integer.toString(pair_Quality_Predictions_Failed_Made) + "\n"
//                    + "Total Pair Quality 'Predicted Vs Real' Difference : success - fail : " + Integer.toString(pair_Quality_Predictions_Successfully_Made - pair_Quality_Predictions_Failed_Made) + "\n"
//                    + "Success Ratio : success / total : " + Double.toString(100 * (pair_Quality_Predictions_Successfully_Made) /(pair_Quality_Predictions_Successfully_Made + pair_Quality_Predictions_Failed_Made)) + "\n"
//                    + "Failure Ratio : fail / total : " + Double.toString(100 * (pair_Quality_Predictions_Failed_Made) /(pair_Quality_Predictions_Successfully_Made + pair_Quality_Predictions_Failed_Made)) + "\n"
//                    );

            /*
             * 5. Transform the tree, to "antonym_Match_Detector_File_URL"
             */
            XMLProcessor.transformXML(doc, new StreamResult(new File(this.antonym_Match_Detector_File_URL)));

        } catch (Exception e) {
            throw new Exception("AntonymMatchDetector : Process :"
                    + e + " : " + e.getMessage());
        }

    }

    /*
     * Helper Methods
     */
    private String getAntonym_Match_Detector_File_URL() {
        return antonym_Match_Detector_File_URL;
    }

    private void setAntonym_Match_Detector_File_URL(Properties properties) throws Exception {
        /*
         * Local Declarations
         */
        String suffixToAdd,
                newURL;
        try {
            /*
             * add Suffix, "_ad" for "Antonym Detected"
             */
            suffixToAdd = "_ad";

            /*
             * call FileSystemManager.addSuffixToFileURL
             */
            newURL = FileSystemManager.addSuffixToFileURL(properties.getContradiction_And_Polarity_Analyser_File_URL(), suffixToAdd, null);

            /*
             * set instance state
             */
            antonym_Match_Detector_File_URL = newURL;

            /*
             * set "properties" instance field value
             */
            properties.setAntonym_Match_Detector_File_URL(newURL);

        } catch (Exception e) {
            throw new Exception("AntonymMatchDetector : setAntonym_Match_Detector_File_URL : "
                    + e + " : " + e.getMessage());
        }
    }

    private void antonymDetector(String string1, String string2,
            AntonymMatchType antonymMatchType, String tokenizerChar,
            String intraLexemeSeparatorCharacter, int pair_Quality) throws Exception {
        /*
         * Local Declarations
         */
        String[] lexemesString1,
                lexemesString2;

        String tokenString1,
                tokenString2,
                firstTwoCharactersOfPOS;

        String antonymsArray[];

        try {
            /*
             * Get lexemes from strings
             */
            lexemesString1 = string1.split(tokenizerChar);
            lexemesString2 = string2.split(tokenizerChar);

            /*
             * If any String is empty, antonym match would not be found,
             * So return
             */
            if (string1.equalsIgnoreCase("") || string2.equalsIgnoreCase("")) {
                return;
            }

            /*
             * Loop through lexemes, of both strings
             * and try finding the 'token' based antonyms.
             */
            for (int i = 0; i < lexemesString1.length; i++) {
                /*
                 * Extract 'token' and 'first two chracters of POS' from token_POS
                 */
                tokenString1 = lexemesString1[i].substring(0, lexemesString1[i].indexOf(intraLexemeSeparatorCharacter));

                try {
                    firstTwoCharactersOfPOS = lexemesString1[i].substring(lexemesString1[i].indexOf(intraLexemeSeparatorCharacter) + 1,
                            lexemesString1[i].indexOf(intraLexemeSeparatorCharacter) + 3);
                } catch (IndexOutOfBoundsException e) {
                    firstTwoCharactersOfPOS = null;
                }

                /*
                 * Get antonyms list for tokenString1, by calling appropriate
                 * WordNetManager class method
                 */
                antonymsArray = getAntonyms(tokenString1, firstTwoCharactersOfPOS, true);
                //wordNet.getAntonyms(tokenString1, null, true);
                //wordNet.getExtendedAntonyms(tokenString1, null, true);

                /*
                 * If antonymsList is empty, continue Loop, because there can not be any match
                 */
                if (antonymsArray.length == 0) {
                    continue;
                }

                /*
                 * For testing
                 */
//                if (tokenString1.equalsIgnoreCase("hyperthermia")) {
//                    tokenString1 = tokenString1;
//                }

                for (int j = 0; j < lexemesString2.length; j++) {
                    /*
                     * Extract 'token' from token_POS
                     */
                    tokenString2 = lexemesString2[j].substring(0, lexemesString2[j].indexOf(intraLexemeSeparatorCharacter));

                    /*
                     * Test if 'tokenString2' matches any of the 'tokenString1's' antonyms
                     * if it does, set proper Boolean to TRUE.
                     */
                    if (isTokenFoundInAntonymList(antonymsArray, tokenString2)) {
                        /*
                         * Set proper Boolean to TRUE
                         */
                        if (firstTwoCharactersOfPOS.startsWith("n") || firstTwoCharactersOfPOS.startsWith("N")) {
                            antonymMatchType.setNounAntonymMatch(Boolean.TRUE);
                        } else if (firstTwoCharactersOfPOS.startsWith("v") || firstTwoCharactersOfPOS.startsWith("V")) {
                            antonymMatchType.setVerbAntonymMatch(Boolean.TRUE);
                        } else if (firstTwoCharactersOfPOS.startsWith("j") || firstTwoCharactersOfPOS.startsWith("J")) {
                            antonymMatchType.setAdjectiveAntonymMatch(Boolean.TRUE);
                        } else if (firstTwoCharactersOfPOS.startsWith("r") || firstTwoCharactersOfPOS.startsWith("R")) {
                            antonymMatchType.setAdverbAntonymMatch(Boolean.TRUE);
                        }

                        System.out.println("Pair Quality : " + pair_Quality + "\n\t"
                                + "String1 : " + string1 + "\n\t"
                                + "String2 : " + string2 + "\n\t"
                                + "Antonyms : " + tokenString1 + ", " + tokenString2 + "\n\t"
                                + "Antonym type : " + firstTwoCharactersOfPOS + "\n");
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("AntonymMatchDetector : antonymDetector :"
                    + e + " : " + e.getMessage());
        }
    }

    private void intraStringAntonymDetector(String string1, String string2,
            AntonymMatchType antonymMatchType, String tokenizerChar,
            String intraLexemeSeparatorCharacter, int pair_Quality) throws Exception {
        /*
         * Local Declarations
         */
        String[] lexemesString1,
                lexemesString2;

        String tokenString1,
                tokenString2,
                first_Two_Characters_Of_String_1_POS,
                first_Two_Characters_Of_String_2_POS;

        String antonymsArray[];

        try {
            /*
             * Get lexemes from strings
             */
            lexemesString1 = string1.split(tokenizerChar);
            lexemesString2 = string2.split(tokenizerChar);

            /*
             * Loop through lexemes, of both strings
             * and try finding the 'token' based antonyms.
             */
            for (int i = 0; i < lexemesString1.length; i++) {
                /*
                 * Extract 'token' and 'first two chracters of POS' from token_POS
                 */
                tokenString1 = lexemesString1[i].substring(0, lexemesString1[i].indexOf(intraLexemeSeparatorCharacter));

                try {
                    first_Two_Characters_Of_String_1_POS = lexemesString1[i].substring(lexemesString1[i].indexOf(intraLexemeSeparatorCharacter) + 1,
                            lexemesString1[i].indexOf(intraLexemeSeparatorCharacter) + 3);
                } catch (IndexOutOfBoundsException e) {
                    first_Two_Characters_Of_String_1_POS = null;
                }

                /*
                 * Get antonyms list for tokenString1, by calling appropriate
                 * WordNetManager class method
                 */
                antonymsArray = wordNet.getAntonyms(tokenString1, first_Two_Characters_Of_String_1_POS, true);

                /*
                 * If antonymsList is empty, continue Loop, because there can not be any match
                 */
                if (antonymsArray.length == 0) {
                    continue;
                }

                for (int j = 0; j < lexemesString2.length; j++) {
                    /*
                     * Extract 'token' and 'first two chracters of POS' from token_POS
                     */
                    tokenString2 = lexemesString2[j].substring(0, lexemesString2[j].indexOf(intraLexemeSeparatorCharacter));
//                    first_Two_Characters_Of_String_2_POS = lexemesString2[j].substring(lexemesString2[j].indexOf(intraLexemeSeparatorCharacter) + 1,
//                        lexemesString2[j].indexOf(intraLexemeSeparatorCharacter) + 3);
                    /*
                     * Test if 'tokenString2' matches any of the 'tokenString1's' antonyms,
                     *
                     * AND 
                     * 
                     * POS Constraint is satisfied by the 'source' and 'target' lexemes
                     *
                     * if it does, set proper Boolean to TRUE.
                     */
                    if (isTokenFoundInAntonymList(antonymsArray, tokenString2)) {
                        /*
                         * Set proper Boolean to TRUE
                         */
                        if (first_Two_Characters_Of_String_1_POS.startsWith("n") || first_Two_Characters_Of_String_1_POS.startsWith("N")) {
                            antonymMatchType.setNounAntonymMatch(Boolean.TRUE);
                        } else if (first_Two_Characters_Of_String_1_POS.startsWith("v") || first_Two_Characters_Of_String_1_POS.startsWith("V")) {
                            antonymMatchType.setVerbAntonymMatch(Boolean.TRUE);
                        } else if (first_Two_Characters_Of_String_1_POS.startsWith("j") || first_Two_Characters_Of_String_1_POS.startsWith("J")) {
                            antonymMatchType.setAdjectiveAntonymMatch(Boolean.TRUE);
                        } else if (first_Two_Characters_Of_String_1_POS.startsWith("r") || first_Two_Characters_Of_String_1_POS.startsWith("R")) {
                            antonymMatchType.setAdverbAntonymMatch(Boolean.TRUE);
                        }

                        System.out.println("Pair Quality : " + pair_Quality + "\n\t"
                                + "String1 : " + string1 + "\n\t"
                                + "String2 : " + string2 + "\n\t"
                                + "Antonyms : " + tokenString1 + ", " + tokenString2 + "\n\t"
                                + "Antonym type : " + first_Two_Characters_Of_String_1_POS + "\n");
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("AntonymMatchDetector : intraStringAntonymDetector :"
                    + e + " : " + e.getMessage());
        }
    }

    /*
     * Gets antonyms unified by following criterion
     *
     * 1. String[] WordNetManager.getAntonyms(word_form,POS,true)
     * 2. String[] WordNetManager.getAntonyms(word_form,null,true)
     * 3. String[] WordNetManager.getExtendedAntonyms(word_form,POS,true)
     *
     * 4. Finding wordforms, for all string arrays.
     */
    private String[] getAntonyms(String word_To_Get_Antonyms_Of,
            String first_Two_Characters_Of_POS, boolean applyMorphology) throws Exception {
        /*
         * Local Declarations
         */
        List<String> antonyms_List = null;

        String[] antonymsToReturn = null;

        Synset[] antonymSynsets = null;

        try {
            /*
             * Initialize antonyms_List
             */
            antonyms_List = new ArrayList<String>();

            /*
             * 1. getAntonyms with POS constraint
             */
            addStringArrayToList(wordNet.getAntonyms(word_To_Get_Antonyms_Of, first_Two_Characters_Of_POS, applyMorphology), antonyms_List);

            /*
             * 2. getAntonyms without POS constraint i.e 'null'
             */
            addStringArrayToList(wordNet.getAntonyms(word_To_Get_Antonyms_Of, null, applyMorphology), antonyms_List);

            /*
             * 3. getExtendedAntonyms with POS constraint
             */
//            addStringArrayToList(wordNet.getExtendedAntonyms(word_To_Get_Antonyms_Of, null, applyMorphology), antonyms_List);

            /*
             * Convert List<String> into String[]
             */
//            antonymsToReturn = new String[antonyms_List.size()];
//            antonyms_List.toArray(antonymsToReturn);
//
//            /*
//             * 4. Loop and Add wordForms[]
//             */
//            for (int i = 0; i < antonymsToReturn.length; i++) {
//                /*
//                 * get and add WordForms[]
//                 */
//                antonymSynsets = wordNet.getSynsets(antonymsToReturn[i], null, applyMorphology);
//
//                /*
//                 * Loop and add
//                 */
//                for (int j = 0; j < antonymSynsets.length; j++) {
//                    /*
//                     * Add 'WordForms[]' to List<String>
//                     */
//                    addStringArrayToList(antonymSynsets[j].getWordForms(), antonyms_List);
//                }
//            }

            /*
             * Convert List<String> into String[]
             */
            antonymsToReturn = new String[antonyms_List.size()];
            antonyms_List.toArray(antonymsToReturn);

        } catch (Exception e) {
            throw new Exception("AntonymMatchDetector :  getAntonyms :"
                    + e + " : " + e.getMessage());
        }

//        System.out.println("\nUnified Antonyms of " + word_To_Get_Antonyms_Of + " : \n\t" + antonyms_List.toString());

        return antonymsToReturn;
    }

    /*
     * Add String[] to List<String>, only adds 'distinct' element
     */
    private void addStringArrayToList(String[] string_Array_To_Add, List<String> list_To_Add_String_Array_To) throws Exception {
        /*
         * Local Declarations
         */
        try {
            /*
             * Loop and add disinct elements
             */
            if (string_Array_To_Add != null) {

                for (int i = 0; i < string_Array_To_Add.length; i++) {
                    /*
                     * Check 'distinct elements' and 'add'
                     */
                    if (!list_To_Add_String_Array_To.contains(string_Array_To_Add[i])) {
                        list_To_Add_String_Array_To.add(string_Array_To_Add[i]);
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("AntonymMatchDetector : addStringArrayToList : "
                    + e + " : " + e.getMessage());
        }
    }

    private boolean isTokenFoundInAntonymList(String[] antonymsArray, String tokenString2) throws Exception {
        /*
         * Local Declaration
         */
        Boolean isTokenFoundInAntonymList = false;

        try {
            /*
             * Loop through antonymsList and test
             */
            for (int i = 0; i < antonymsArray.length; i++) {
                /*
                 * Test for match
                 */
                if (tokenString2.equalsIgnoreCase(antonymsArray[i])) {
                    System.out.print("Antonym match found : \n\t");
                    return true;
                }
            }
        } catch (Exception e) {
            throw new Exception("AntonymMatchDetector : isTokenFoundInAntonymList :"
                    + e + " : " + e.getMessage());
        }

        return isTokenFoundInAntonymList;
    }

    /*
     * This class encapsulates the Boolean antonym match fields, mainly for
     * convenience purposes.
     */
    private final class AntonymMatchType {
        /*
         * Boolean Fields
         */

        private Boolean verbAntonymMatch,
                adjectiveAntonymMatch,
                nounAntonymMatch,
                adverbAntonymMatch;

        public AntonymMatchType() {
            /*
             * Initialize to False
             */
            verbAntonymMatch = Boolean.FALSE;
            adjectiveAntonymMatch = Boolean.FALSE;
            nounAntonymMatch = Boolean.FALSE;
            adverbAntonymMatch = Boolean.FALSE;
        }

        @Override
        public String toString() {
            return "Verb Antonym Match : " + Boolean.toString(verbAntonymMatch)
                    + "\nAdjective Antonym Match : " + Boolean.toString(adjectiveAntonymMatch)
                    + "\nAdverb Antonym Match : " + Boolean.toString(adverbAntonymMatch)
                    + "\nNoun Antonym Match : " + Boolean.toString(nounAntonymMatch) + "\n";
        }

        /*
         * Accessors
         */
        public Boolean getAdjectiveAntonymMatch() {
            return adjectiveAntonymMatch;
        }

        public void setAdjectiveAntonymMatch(Boolean adjectiveAntonymMatch) {
            this.adjectiveAntonymMatch = adjectiveAntonymMatch;
        }

        public Boolean getAdverbAntonymMatch() {
            return adverbAntonymMatch;
        }

        public void setAdverbAntonymMatch(Boolean adverbAntonymMatch) {
            this.adverbAntonymMatch = adverbAntonymMatch;
        }

        public Boolean getNounAntonymMatch() {
            return nounAntonymMatch;
        }

        public void setNounAntonymMatch(Boolean nounAntonymMatch) {
            this.nounAntonymMatch = nounAntonymMatch;
        }

        public Boolean getVerbAntonymMatch() {
            return verbAntonymMatch;
        }

        public void setVerbAntonymMatch(Boolean verbAntonymMatch) {
            this.verbAntonymMatch = verbAntonymMatch;
        }
    }
}
