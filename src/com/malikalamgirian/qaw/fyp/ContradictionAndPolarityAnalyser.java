/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malikalamgirian.qaw.fyp;

import java.io.File;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

/**
 *
 * @author wasif
 */
public class ContradictionAndPolarityAnalyser {

    public ContradictionAndPolarityAnalyser(Properties properties) throws Exception {
        /*
         * Set state of proper Properties instance field
         */
        this.properties = properties;
        /*
         * This also sets instance variable "setContradiction_And_Polarity_Analyser_File_URL"
         */
        this.setContradiction_And_Polarity_Analyser_File_URL(properties);
        /*
         * Set this.negation_Modifiers_List
         *
         * NOT, NO, N'T, NEVER, NONE, 'NOR', 'NEITHER'
         */
        negation_Modifiers_List = new String[]{
                    "NOT",
                    "NO",
                    "N'T",
                    "NEVER",
                    "NONE"
                };

        /*
         * call Process method
         */
        process();
    }

    /*
     * Declarations
     */
    private Properties properties;
    private String contradiction_And_Polarity_Analyser_File_URL;
    private String negation_Modifiers_List[];

    /*
     * Contradiction and Polarity analysis is performed on the basis on negation
     * modifier. Tagged strings are used, because they help in form of being
     * tokenized beforehand, e.g. ain't as ai_POS, n't_POS.
     */
    private void process() throws Exception {
        /*
         * Local Declarations
         */
        Document doc;

        Element root,
                contradictionAndPolarityBase,
                negationUsageInString1Element,
                negationUsageInString2Element,
                polarityElement;

        Node pair;

        NodeList pairs,
                taggedString1,
                taggedString2;

        String intraLexemeSeparatorCharacter,
                tokenizerChar,
                taggedString1Str,
                taggedString2Str;

        Boolean isNegationModifierFoundInString1,
                isNegationModifierFoundInString2;

        try {
            System.out.println("ContradictionAndPolarityAnalyser File URL is : " + getContradiction_And_Polarity_Analyser_File_URL());
            System.out.println("properties.getEvent_Detector_File_URL() is : " + properties.getEvent_Detector_File_URL());

            /*
             * set separator character
             */
            tokenizerChar = " ";

            /*
             * set intraLexemeSeparatorCharacter character, for 'Tagged String'
             */
            intraLexemeSeparatorCharacter = "_";

            /*
             * 1.Get XML Document, for <code>properties.getEvent_Detector_File_URL()</code>
             */
            doc = XMLProcessor.getXMLDocumentForXMLFile(properties.getEvent_Detector_File_URL());

            /*
             * 2. Get Document Element
             */
            root = doc.getDocumentElement();

            /*
             * 3. Select all "Pair", 'TaggedString1' and 'TaggedString2'
             */
            pairs = root.getElementsByTagName("Pair");
            taggedString1 = root.getElementsByTagName("TaggedString1");
            taggedString2 = root.getElementsByTagName("TaggedString2");

            /*
             * Creates following structure
             *
             * <ContradictionAndPolarity>
             *      <NegationUsageInString1>
             *          true/false
             *      </NegationUsageInString1>
             *
             *       <NegationUsageInString2>
             *          true/false
             *      </NegationUsageInString2>
             *
             *      <Polarity>
             *          true/false
             *      </Polarity>
             * </ContradictionAndPolarity>
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
                 * Extract text-contents from 'TaggedString1' and 'TaggedString2'
                 */
                taggedString1Str = taggedString1.item(i).getTextContent();
                taggedString2Str = taggedString2.item(i).getTextContent();

                /*
                 * Create required nodes
                 */
                contradictionAndPolarityBase = doc.createElement("ContradictionAndPolarity");
//                negationUsageInString1Element = doc.createElement("NegationUsageInString1");
//                negationUsageInString2Element = doc.createElement("NegationUsageInString2");
                polarityElement = doc.createElement("Polarity");

                /*
                 * Prepare text-contents
                 */
                isNegationModifierFoundInString1 = isNegationFoundInString(taggedString1Str, negation_Modifiers_List, tokenizerChar, intraLexemeSeparatorCharacter);
                isNegationModifierFoundInString2 = isNegationFoundInString(taggedString2Str, negation_Modifiers_List, tokenizerChar, intraLexemeSeparatorCharacter);

                /*
                 * Set proper text-contents
                 */
//                negationUsageInString1Element.setTextContent(Boolean.toString(isNegationModifierFoundInString1));
//                negationUsageInString2Element.setTextContent(Boolean.toString(isNegationModifierFoundInString2));

                /*
                 * Set <Polarity> node's text content
                 */
                if (isNegationModifierFoundInString1 == isNegationModifierFoundInString2) {
                    polarityElement.setTextContent("true");
                } else {
                    polarityElement.setTextContent("false");

                    /*
                     * For testing purposes
                     */
                    System.out.println(
                            "\nPolarity is FALSE : "
                            + "\n\tTaggedString1 : " + taggedString1Str
                            + "\n\tTaggedString2 : " + taggedString2Str
                            + "\n\tisNegationModifierFoundInString1 : " + Boolean.toString(isNegationModifierFoundInString1)
                            + "\n\tisNegationModifierFoundInString2 : " + Boolean.toString(isNegationModifierFoundInString2));
                }

                /*
                 * Append Nodes to contradictionAndPolarityBase
                 */
//                contradictionAndPolarityBase.appendChild(negationUsageInString1Element);
//                contradictionAndPolarityBase.appendChild(negationUsageInString2Element);
                contradictionAndPolarityBase.appendChild(polarityElement);

                /*
                 * Append contradictionAndPolarityBase, to pair
                 */
                pair.appendChild(contradictionAndPolarityBase);

                System.out.println((i + 1) + " / " + pairs.getLength() + "\n");
            }

            /*
             * 5. Transform the tree, to "contradiction_And_Polarity_Analyser_File_URL"
             */
            XMLProcessor.transformXML(doc, new StreamResult(new File(this.contradiction_And_Polarity_Analyser_File_URL)));


        } catch (Exception e) {
            throw new Exception("ContradictionAndPolarityAnalyser : Process :"
                    + e + " : " + e.getMessage());
        }
    }


    /*
     * Helper Methods
     */
    private String getContradiction_And_Polarity_Analyser_File_URL() {
        return contradiction_And_Polarity_Analyser_File_URL;
    }

    private void setContradiction_And_Polarity_Analyser_File_URL(Properties properties) throws Exception {
        /*
         * Local Declarations
         */
        String suffixToAdd,
                newURL;
        try {
            /*
             * add Suffix, "_cnp" for "Contradiction and Polarity"
             */
            suffixToAdd = "_cnp";

            /*
             * call FileSystemManager.addSuffixToFileURL
             */
            newURL = FileSystemManager.addSuffixToFileURL(properties.getEvent_Detector_File_URL(), suffixToAdd, null);

            /*
             * set instance state
             */
            contradiction_And_Polarity_Analyser_File_URL = newURL;

            /*
             * set "properties" instance field value
             */
            properties.setContradiction_And_Polarity_Analyser_File_URL(newURL);

        } catch (Exception e) {
            throw new Exception("ContradictionAndPolarityAnalyser : setContradiction_And_Polarity_Analyser_File_URL : "
                    + e + " : " + e.getMessage());
        }
    }

    private Boolean isNegationFoundInString(String taggedString, String[] negation_Modifiers_List, String tokenizerChar, String interLexemeSeparatorCharacter) throws Exception {
        /*
         * Local Declarations
         */
        Boolean isNegationFoundInString = false;

        String lexemes[],
                token;

        try {
            /*
             * Tokenize string
             */
            lexemes = taggedString.split(tokenizerChar);

            /*
             * loop through all lexemes
             */
            for (int i = 0; i < lexemes.length; i++) {
                /*
                 * Extract token, from token_POS, to test for potential
                 * negation modifier
                 */
                try {
                    token = lexemes[i].substring(0, lexemes[i].indexOf(interLexemeSeparatorCharacter));
                } catch (IndexOutOfBoundsException e) {
                    token = lexemes[i];
                }

                /*
                 * Test if token, is a negation modifier or not
                 */
                if (isTokenNegationModifier(token, negation_Modifiers_List)) {
                    isNegationFoundInString = true;
                }

            }

        } catch (Exception e) {
            throw new Exception("ContradictionAndPolarityAnalyser : isNegationFoundInString :"
                    + e + " : " + e.getMessage());
        }

        return isNegationFoundInString;
    }

    private boolean isTokenNegationModifier(String token, String[] negation_Modifiers_List) throws Exception {
        /*
         * Local Declarations
         */
        Boolean isTokenNegationModifier = false;

        try {
            /*
             * loop through negation_Modifiers_List
             */
            for (int i = 0; i < negation_Modifiers_List.length; i++) {
                /*
                 * Test if token is negation modifier
                 */
                if (token.equalsIgnoreCase(negation_Modifiers_List[i])) {
                    return true;
                }

            }

        } catch (Exception e) {
            throw new Exception("ContradictionAndPolarityAnalyser : isTokenNegationModifier :"
                    + e + " : " + e.getMessage());
        }

        return isTokenNegationModifier;
    }
}
