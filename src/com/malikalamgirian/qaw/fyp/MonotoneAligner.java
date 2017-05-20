/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malikalamgirian.qaw.fyp;

import edu.stanford.nlp.trees.tregex.Token;
import java.io.File;
import java.util.regex.Pattern;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

/**
 * The <code>MonotoneAligner</code> class applies monotonic alignment 
 * techniques, such as <code>LCS</code> and related measures, to determine
 * similarity between strings under "monotonicity constraint". Where
 * instantiating this class requires <code>Properties</code> instance, with
 * <code>Properties.monotone_Aligned_File_URL</code> set properly beforehand.
 * 
 * @author wasif
 */
public class MonotoneAligner {

    public MonotoneAligner(Properties properties) throws Exception {
        /*
         * Set state of proper Properties instance field
         */
        this.properties = properties;
        /*
         * This also sets instance variable "monotone_Aligned_File_URL"
         */
        this.setMonotone_Aligned_File_URL(properties);

        /*
         * call Process method
         */
        process();

    }

    /*
     * Declarations
     */
    private Properties properties;
    private String monotone_Aligned_File_URL;


    /*
     * Applies 'Monotonic Alignment Techniques' to "PosFilteredString1" and "PosFilteredString2"
     */
    private void process() throws Exception {
        /*
         * Local Declarations
         */
        Document doc;

        Element root;

        Node pair;

        NodeList pairs,
                posFilteredString1,
                posFilteredString2;

        String posFilteredStr1,
                posFilteredStr2,
                lcsOfCharacters,
                lcsOfLexemes,
                lcsBasedOnTokens;

        try {
            System.out.println("MonotoneAligner File URL is : " + getMonotone_Aligned_File_URL());
            System.out.println("properties.getPos_Constraint_Applied_File_URL() is : " + properties.getPos_Constraint_Applied_File_URL());

            /*
             * 1.Get XML Document, for <code>properties.getPos_Constraint_Applied_File_URL()</code>
             */
            doc = XMLProcessor.getXMLDocumentForXMLFile(properties.getPos_Constraint_Applied_File_URL());

//            /*
//             * 2. Get Document Element
//             */
//            root = doc.getDocumentElement();
//
//            /*
//             * 3. Select all "PosFilteredString1", "PosFilteredString2", "Pair" pairs
//             */
//            posFilteredString1 = root.getElementsByTagName("PosFilteredString1");
//            posFilteredString2 = root.getElementsByTagName("PosFilteredString2");
//            pairs = root.getElementsByTagName("Pair");
//
//            /*
//             * 4. loop through all pairs
//             */
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
//                 * Calculate LCS (Characters Based) from 'Part-Of-Speech-Based-Filtered' Strings, 'posFilteredStr1' and 'posFilteredStr2'
//                 */
//                lcsOfCharacters = calculateLcsOfCharacters(posFilteredStr1, posFilteredStr2);
//
//                /*
//                 * Calculate LCS (Lexemes Based) from 'lcsOfCharacters', 'posFilteredStr1', 'posFilteredStr2'
//                 * where lexeme is defined to be 'token_POS'
//                 */
//                lcsOfLexemes = calculateLcsOfLexemes(lcsOfCharacters, posFilteredStr1, posFilteredStr2);
//
//                /*
//                 * Calculate LCS (Tokens Based) from 'lcsOfCharacters', 'posFilteredStr1', 'posFilteredStr2'
//                 * where lexeme is defined to be 'token_POS'
//                 */
//                lcsBasedOnTokens = calculateLcsBasedOnTokens(lcsOfCharacters, posFilteredStr1, posFilteredStr2);
//
//                /*
//                 * Test lcsOfLexemes and lcsBasedOnTokens symmetry
//                 */
//                if (!lcsOfLexemes.equalsIgnoreCase(lcsBasedOnTokens)) {
//                    System.out.println("\nFollowing LCS (lcsOfLexemes and lcsBasedOnTokens) are not equal : "
//                            + "\n\tPosFilteredString1 : " + posFilteredStr1
//                            + "\n\tPosFilteredString2 : " + posFilteredStr2
//                            + "\n\tLcs of Characters : " + lcsOfCharacters
//                            + "\n\tLcs of Lexemes : " + lcsOfLexemes
//                            + "\n\tLcs based on tokens : " + lcsBasedOnTokens);
//                }
//
//                /*
//                 * Add 'Longest Common Subsequence' calculated, 'lcsOfCharacters', 'lcsOfLexemes' and 'lcsOfTokens' as XML
//                 */
//                addLcsCalculations(doc, root, pair, lcsOfCharacters, lcsOfLexemes, lcsBasedOnTokens);
//
//                /*
//                 * TEST
//                 *
//                 * Final validation testing
//                 */
//                if (i == 12) {
//                    System.out.println("FINAL TEST : \tMonotoneAligner"
//                            + "\n\tposFilteredStr1 : " + posFilteredStr1
//                            + "\n\tposFilteredStr2 : " + posFilteredStr2
//                            + "\n\tLcs of Characters : " + lcsOfCharacters
//                            + "\n\tLcs of Lexemes : " + lcsOfLexemes
//                            + "\n\tLcs based on tokens : " + lcsBasedOnTokens);
//                }
//
//                System.out.println((i + 1) + " / " + pairs.getLength() + "\n");
//            }

            /*
             * 5. Transform the tree, to "monotone_Aligned_File_URL"
             */
            XMLProcessor.transformXML(doc, new StreamResult(new File(this.monotone_Aligned_File_URL)));



        } catch (Exception e) {
            throw new Exception("MonotoneAligner : Process :"
                    + e + " : " + e.getMessage());
        }

    }

    /*
     * Helper Methods
     */
    private String getMonotone_Aligned_File_URL() {
        return monotone_Aligned_File_URL;
    }

    private void setMonotone_Aligned_File_URL(Properties properties) throws Exception {
        /*
         * Local Declarations
         */
        String suffixToAdd,
                newURL;
        try {
            /*
             * add Suffix, "_ma" for "monotone aligned"
             */
            suffixToAdd = "_ma";

            /*
             * call FileSystemManager.addSuffixToFileURL
             */
            newURL = FileSystemManager.addSuffixToFileURL(properties.getPos_Constraint_Applied_File_URL(), suffixToAdd, null);

            /*
             * set instance state
             */
            monotone_Aligned_File_URL = newURL;

            /*
             * set "properties" instance field value
             */
            properties.setMonotone_Aligned_File_URL(newURL);

        } catch (Exception e) {
            throw new Exception("MonotoneAligner : setMonotone_Aligned_File_URL : "
                    + e + " : " + e.getMessage());
        }
    }

    private String calculateLcsOfCharacters(String posFilteredStr1, String posFilteredStr2) throws Exception {
        /*
         * Local Declarations
         */
        LCS lcsOfCharactersCalculator;

        String lcsOfCharacters;

        try {
            /*
             * Initialize lcsOfCharactersCalculator
             */
            lcsOfCharactersCalculator = new LCS();
            /*
             * Calculate lcsOfCharacters
             */
            lcsOfCharacters = lcsOfCharactersCalculator.applyLcsAlgorithm(posFilteredStr1, posFilteredStr2);
            /*
             * return lcsOfCharacters
             */
            return lcsOfCharacters;

        } catch (Exception e) {
            throw new Exception("MonotoneAligner : calculateLcsOfCharacters : "
                    + e + " : " + e.getMessage());
        }
    }

    /*
     * This Calculates LCS for the case where, 'noise' is removed on the basis of
     * complete 'lexeme', which means that lexemes of LCS are common to both
     * 'PosFilteredString1' and 'PosFilteredString2'
     */
    private String calculateLcsOfLexemes(String lcsOfCharacters, String posFilteredStr1, String posFilteredStr2) throws Exception {
        /*
         * Local Declarations
         */
        String lcsOfLexemes = "",
                tokens[],
                lexeme,
                tokenizerChar;

        try {
            /*
             * set separator character
             */
            tokenizerChar = " ";

            /*
             * Split lcsOfCharacters into tokens based on 'tokenizerChar' character
             */
            tokens = lcsOfCharacters.split(tokenizerChar);


            /*
             * Loop through 'tokens'
             * Dynamically get lexeme(s)
             */
            for (int i = 0; i < tokens.length; i++) {
                /*
                 * get 'lexeme'
                 */
                lexeme = tokens[i];

                if (isLexemeContainedInLexemeBasedString(lexeme, posFilteredStr1) || isLexemeContainedInLexemeBasedString(lexeme, posFilteredStr2)) {
                    /*
                     * If 'PosFilteredString1' or 'PosFilteredString2' contains 'lexeme'
                     * add it to lcsOfLexemes
                     */
                    if (lcsOfLexemes.equalsIgnoreCase("")) {
                        /*
                         * Case for first tokens[1]
                         */
                        lcsOfLexemes += tokens[i];
                    } else {
                        /*
                         * Case for other tokens[i]
                         */
                        lcsOfLexemes += tokenizerChar + tokens[i];
                    }
                }
            }

            return lcsOfLexemes;

        } catch (Exception e) {
            throw new Exception("MonotoneAligner : calculateLcsOfLexemes : "
                    + e + " : " + e.getMessage());
        }

    }

    /*
     * This Calculates LCS for the case where, 'noise' is removed on the basis of
     * complete 'token', which means that 'token' of 'lexeme' of LCS are common to both
     * 'PosFilteredString1' and 'PosFilteredString2'. This relaxes 'POS' of 'lexeme'
     * as in 'calculateLcsOfLexemes()'. The output icludes complete 'lexeme'(s).
     */
    private String calculateLcsBasedOnTokens(String lcsOfCharacters, String posFilteredStr1, String posFilteredStr2) throws Exception {
        /*
         * Local Declarations
         */
        String lcsBasedOnTokens = "",
                tokens[],
                lexeme,
                tokenizerChar,
                interLexemeSeparatorCharacter,
                token;

        try {
            /*
             * set separator character
             */
            tokenizerChar = " ";

            /*
             * set lexeme separator character
             */
            interLexemeSeparatorCharacter = "/";

            /*
             * Split lcsOfCharacters into tokens based on 'tokenizerChar' character
             */
            tokens = lcsOfCharacters.split(tokenizerChar);

            /*
             * Loop through 'tokens'
             * Dynamically get prefix 'token' from lexeme(s)
             */
            for (int i = 0; i < tokens.length; i++) {
                /*
                 * get 'lexeme'
                 */
                lexeme = tokens[i];

                /*
                 * get 'token' from lexeme
                 */
                try {
                    token = lexeme.substring(0, lexeme.indexOf(interLexemeSeparatorCharacter));
                } catch (IndexOutOfBoundsException e) {
                    /*
                     * Case where, lexeme does not contain POS, in token_POS
                     * that is, no '/' found.
                     */
                    token = lexeme;
                }

                if (isTokenContainedInLexemeBasedString(token, posFilteredStr1) || isTokenContainedInLexemeBasedString(token, posFilteredStr2)) {
                    /*
                     * If 'PosFilteredString1' or 'PosFilteredString2' contains 'token'
                     * add 'lexeme to lcsBasedOnTokens
                     */
                    if (lcsBasedOnTokens.equalsIgnoreCase("")) {
                        /*
                         * Case for first tokens[1]
                         */
                        lcsBasedOnTokens += tokens[i];
                    } else {
                        /*
                         * Case for other tokens[i]
                         */
                        lcsBasedOnTokens += tokenizerChar + tokens[i];
                    }
                }
            }

            return lcsBasedOnTokens;

        } catch (Exception e) {
            throw new Exception("MonotoneAligner : calculateLcsBasedOnTokens : "
                    + e + " : " + e.getMessage());
        }
    }

    private Boolean isLexemeContainedInLexemeBasedString(String lexeme_To_Find, String lexemeBasedString) throws Exception {
        /*
         * Local Declarations
         */
        String lexemes[],
                lexeme,
                tokenizerChar,
                token;

        Boolean isLexemeContainedInLexemeBasedString = false;

        try {
            /*
             * set separator character
             */
            tokenizerChar = " ";

            /*
             * Split lexemeBasedString into lexemes based on 'tokenizerChar' character
             */
            lexemes = lexemeBasedString.split(tokenizerChar);

            /*
             * Loop through 'lexemes'
             * Dynamically get prefix 'lexeme' from lexeme(s)
             */
            for (int i = 0; i < lexemes.length; i++) {
                /*
                 * get 'lexeme'
                 */
                lexeme = lexemes[i];

                /*
                 * Check if 'lexeme' extracted is equal to 'lexeme_To_Find'
                 */
                if (lexeme.equalsIgnoreCase(lexeme_To_Find)) {
                    /*
                     * Lexeme found
                     */
                    isLexemeContainedInLexemeBasedString = true;
                }
            }

        } catch (Exception e) {
            throw new Exception("MonotoneAligner : isLexemeContainedInLexemeBasedString : "
                    + e + " : " + e.getMessage());
        }

        return isLexemeContainedInLexemeBasedString;

    }

    private Boolean isTokenContainedInLexemeBasedString(String token_To_Find, String lexemeBasedString) throws Exception {
        /*
         * Local Declarations
         */
        String lexemes[],
                lexeme,
                tokenizerChar,
                interLexemeSeparatorCharacter,
                token;

        Boolean isTokenContainedInLexemeBasedString = false;

        try {
            /*
             * set separator character
             */
            tokenizerChar = " ";

            /*
             * set lexeme separator character
             */
            interLexemeSeparatorCharacter = "/";

            /*
             * Split lexemeBasedString into lexemes based on 'tokenizerChar' character
             */
            lexemes = lexemeBasedString.split(tokenizerChar);

            /*
             * Loop through 'lexemes'
             * Dynamically get prefix 'lexeme' from lexeme(s)
             */
            for (int i = 0; i < lexemes.length; i++) {
                /*
                 * get 'lexeme'
                 */
                lexeme = lexemes[i];

                /*
                 * get 'token' from lexeme
                 */
                try {

                    token = lexeme.substring(0, lexeme.indexOf(interLexemeSeparatorCharacter));

                } catch (IndexOutOfBoundsException e) {
                    /*
                     * Case where, lexeme does not contain POS, in token_POS
                     * that is, no '/' found.
                     */
                    token = lexeme;
                }

                /*
                 * Check if 'token' is equal to token_To_Find
                 */
                if (token.equalsIgnoreCase(token_To_Find)) {
                    /*
                     * "token' found
                     */
                    isTokenContainedInLexemeBasedString = true;
                }
            }

        } catch (Exception e) {
            throw new Exception("MonotoneAligner : isTokenContainedInLexemeBasedString : "
                    + e + " : " + e.getMessage());
        }

        return isTokenContainedInLexemeBasedString;

    }

    private Boolean addLcsCalculations(Document doc, Element root, Node pair, String lcsOfCharacters, String lcsOfLexemes, String lcsBasedOnToken) throws Exception {
        /*
         * Local Declarations
         */
        Element lcsBase,
                lcsOfCharactersElement,
                lcsOfLexemesElement,
                lcsBasedOnTokenElement;
        try {
            /*
             * Creates following structure
             *
             * <LCS>
             *      <LcsOfCharacters></LcsOfCharacters>
             *      <LcsOfLexemes></LcsOfLexemes>
             *      <LcsBasedOnTokens></LcsBasedOnTokens>
             * </LCS>
             */
            lcsBase = doc.createElement("LCS");

            lcsOfCharactersElement = doc.createElement("LcsOfCharacters");
            lcsOfLexemesElement = doc.createElement("LcsOfLexemes");
            lcsBasedOnTokenElement = doc.createElement("LcsBasedOnTokens");

            lcsOfCharactersElement.setTextContent(lcsOfCharacters);
            lcsOfLexemesElement.setTextContent(lcsOfLexemes);
            lcsBasedOnTokenElement.setTextContent(lcsBasedOnToken);

            lcsBase.appendChild(lcsOfCharactersElement);
            lcsBase.appendChild(lcsOfLexemesElement);
            lcsBase.appendChild(lcsBasedOnTokenElement);

            pair.appendChild(lcsBase);

            root.normalize();

            return true;

        } catch (Exception e) {
            throw new Exception("MonotoneAligner : addLcsCalculations : "
                    + e + " : " + e.getMessage());
        }
    }
}
