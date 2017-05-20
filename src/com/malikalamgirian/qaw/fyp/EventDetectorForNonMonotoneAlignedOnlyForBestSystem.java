/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malikalamgirian.qaw.fyp;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

/**
 *
 * @author wasif
 */
public class EventDetectorForNonMonotoneAlignedOnlyForBestSystem {

    public EventDetectorForNonMonotoneAlignedOnlyForBestSystem(Properties properties) throws Exception {
        /*
         * Set state of proper Properties instance field
         */
        this.properties = properties;
        /*
         * This also sets instance variable "event_Detector_File_URL"
         */
        this.setEvent_Detector_File_URL(properties);

        /*
         * call Process method
         */
        process();

    }

    /*
     * Declarations
     */
    private Properties properties;
    private String event_Detector_File_URL;

    /*
     * Detects event from 'Bag Of Common Words' and from 'Longest Common Subsequences'
     */
    private void process() throws Exception {
        /*
         * Local Declarations
         */
        Document doc;

        Element root,
                eventDetectionBase,
                lcsBasedEventDetectionBase,
                bagOfWordsBasedEventDetectionBase;

        Node pair;

        NodeList pairs,
                lcsOfCharacters,
                lcsOfLexemes,
                lcsBasedOnTokens,
                TBALSequenceForString1,
                TBALSequenceForString2,
                LBALSequenceForString1,
                LBALSequenceForString2;

        String lcsOfCharactersStr,
                lcsOfLexemesStr,
                lcsBasedOnTokensStr,
                TBALSequenceForString1Str,
                TBALSequenceForString2Str,
                LBALSequenceForString1Str,
                LBALSequenceForString2Str;

        try {
            System.out.println("EventDetector File URL is : " + getEvent_Detector_File_URL());
            System.out.println("properties.getNon_Monotone_Aligned_File_URL() is : " + properties.getNon_Monotone_Aligned_File_URL());

            /*
             * 1.Get XML Document, for <code>properties.getNon_Monotone_Aligned_File_URL()</code>
             */
            doc = XMLProcessor.getXMLDocumentForXMLFile(properties.getNon_Monotone_Aligned_File_URL());

            /*
             * 2. Get Document Element
             */
            root = doc.getDocumentElement();

            /*
             * 3. Select all "Pair" pairs
             *    Select LcsOfCharacters, LcsOfLexemes, LcsBasedOnTokens
             *    Select TBALSequenceForString1, TBALSequenceForString2, LBALSequenceForString1, LBALSequenceForString2
             */
            pairs = root.getElementsByTagName("Pair");

//            lcsOfCharacters = root.getElementsByTagName("LcsOfCharacters");
//            lcsOfLexemes = root.getElementsByTagName("LcsOfLexemes");
//            lcsBasedOnTokens = root.getElementsByTagName("LcsBasedOnTokens");

            TBALSequenceForString1 = root.getElementsByTagName("TBALSequenceForString1");
            TBALSequenceForString2 = root.getElementsByTagName("TBALSequenceForString2");

            LBALSequenceForString1 = root.getElementsByTagName("LBALSequenceForString1");
            LBALSequenceForString2 = root.getElementsByTagName("LBALSequenceForString2");

            /*
             * Creates following structure
             *
             * <EventDetection>
             *      <LcsBasedEventDetection>
             *          <LcsOfCharactersBasedEventDetection>
             *              true/false
             *          </LcsOfCharactersBasedEventDetection>
             *
             *          <LcsOfLexemesBasedEventDetection>
             *              true/false
             *          </LcsOfLexemesBasedEventDetection>
             *
             *          <LcsBasedOnTokensBasedEventDetection>
             *              true/false
             *          <LcsBasedOnTokensBasedEventDetection>
             *      </LcsBasedEventDetection>
             *
             *      <BOWBasedEventDetection>
             *          <TBALBasedEventDetection>
             *              <TBALSequenceForString1BasedEventDetection>
             *                  true/false
             *              </TBALSequenceForString1BasedEventDetection>
             *
             *              <TBALSequenceForString2BasedEventDetection>
             *                  true/false
             *              </TBALSequenceForString2BasedEventDetection>
             *
             *              <TBALBasedEventDetectionBasedOnString1AndString2Sequence>
             *                  true/false
             *              </TBALBasedEventDetectionBasedOnString1AndString2Sequence>
             *
             *              <TBALBasedEventDetectionBasedOnString1OrString2Sequence>
             *                  true/false
             *              </TBALBasedEventDetectionBasedOnString1OrString2Sequence>
             * 
             *          </TBALBasedEventDetection>
             *
             *          <LBALBasedEventDetection>
             *              true/false
             *          </LBALBasedEventDetection>
             *      </BOWBasedEventDetection>
             * </EventDetection>
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
                 * Create "EventDetection" base node
                 */
                eventDetectionBase = doc.createElement("EventDetection");

                /*
                 * Create lcsBasedEventDetectionBase, and bagOfWordsBasedEventDetectionBase nodes
                 */
//                lcsBasedEventDetectionBase = doc.createElement("LcsBasedEventDetection");
                bagOfWordsBasedEventDetectionBase = doc.createElement("BOWBasedEventDetection");

                /*
                 * Extract text-contents for 'LCS Based Event Detection' to pass to method
                 */
//                lcsOfCharactersStr = lcsOfCharacters.item(i).getTextContent();
//                lcsOfLexemesStr = lcsOfLexemes.item(i).getTextContent();
//                lcsBasedOnTokensStr = lcsBasedOnTokens.item(i).getTextContent();

                /*
                 * Add 'Lcs Based Event Detection'
                 */
//                addLcsBasedEventDetection(doc, lcsBasedEventDetectionBase,
//                        lcsOfCharactersStr, lcsOfLexemesStr, lcsBasedOnTokensStr);

                /*
                 * Extract text-contents for 'Bag Of Words Based Event Detection' to pass to method
                 */
                TBALSequenceForString1Str = TBALSequenceForString1.item(i).getTextContent();
                TBALSequenceForString2Str = TBALSequenceForString2.item(i).getTextContent();
                LBALSequenceForString1Str = LBALSequenceForString1.item(i).getTextContent();
                LBALSequenceForString2Str = LBALSequenceForString2.item(i).getTextContent();

                /*
                 * Add 'Bag Of Words Based Event Detection'
                 */
                addBagOfWordsBasedEventDetection(doc, bagOfWordsBasedEventDetectionBase,
                        TBALSequenceForString1Str, TBALSequenceForString2Str,
                        LBALSequenceForString1Str, LBALSequenceForString2Str);
                /*
                 * Append lcsBasedEventDetectionBase, and bagOfWordsBasedEventDetectionBase to
                 * 'eventDetectionBase' node
                 */
//                eventDetectionBase.appendChild(lcsBasedEventDetectionBase);
                eventDetectionBase.appendChild(bagOfWordsBasedEventDetectionBase);

                /*
                 * Append 'EventDetection' base node to 'pair'
                 */
                pair.appendChild(eventDetectionBase);

                System.out.println((i + 1) + " / " + pairs.getLength() + "\n");
            }

            /*
             * 5. Transform the tree, to "event_Detector_File_URL"
             */
            XMLProcessor.transformXML(doc, new StreamResult(new File(this.event_Detector_File_URL)));

        } catch (Exception e) {
            throw new Exception("EventDetector : Process :"
                    + e + " : " + e.getMessage());
        }
    }


    /*
     * Helper Methods
     */
    private String getEvent_Detector_File_URL() {
        return event_Detector_File_URL;
    }

    private void setEvent_Detector_File_URL(Properties properties) throws Exception {
        /*
         * Local Declarations
         */
        String suffixToAdd,
                newURL;
        try {
            /*
             * add Suffix, "_ed" for "Event Detection"
             */
            suffixToAdd = "_ed";

            /*
             * call FileSystemManager.addSuffixToFileURL
             */
            newURL = FileSystemManager.addSuffixToFileURL(properties.getNon_Monotone_Aligned_File_URL(), suffixToAdd, null);

            /*
             * set instance state
             */
            event_Detector_File_URL = newURL;

            /*
             * set "properties" instance field value
             */
            properties.setEvent_Detector_File_URL(newURL);

        } catch (Exception e) {
            throw new Exception("EventDetector : setEvent_Detector_File_URL : "
                    + e + " : " + e.getMessage());
        }
    }

    private void addLcsBasedEventDetection(Document doc,
            Element lcsBasedEventDetectionBase, String lcsOfCharactersStr,
            String lcsOfLexemesStr, String lcsBasedOnTokensStr)
            throws Exception {
        /*
         * Local Declarations
         */
        String interLexemeSeparatorCharacter,
                tokenizerChar,
                lcsLexemes[];

        /*    
         *      Creates following structure
         * 
         *      //<LcsBasedEventDetection>
         *          <LcsOfCharactersBasedEventDetection>
         *              true/false
         *          </LcsOfCharactersBasedEventDetection>
         *
         *          <LcsOfLexemesBasedEventDetection>
         *              true/false
         *          </LcsOfLexemesBasedEventDetection>
         *
         *          <LcsBasedOnTokensBasedEventDetection>
         *              true/false
         *          <LcsBasedOnTokensBasedEventDetection>
         *      //</LcsBasedEventDetection>
         */

        try {
            /*
             * set separator character
             */
            tokenizerChar = " ";

            /*
             * set interLexemeSeparatorCharactercharacter
             */
            interLexemeSeparatorCharacter = "/";

            /*
             * Tokenization for <LcsOfCharactersBasedEventDetection>
             */
            lcsLexemes = lcsOfCharactersStr.split(tokenizerChar);

            /*
             * Call method to add XML for <LcsOfCharactersBasedEventDetection>
             */
            addLcsOfCharactersBasedEventDetection(doc, lcsBasedEventDetectionBase, lcsLexemes, interLexemeSeparatorCharacter);

            /*
             * Tokenization for <LcsOfLexemesBasedEventDetection>
             */
            lcsLexemes = lcsOfLexemesStr.split(tokenizerChar);

            /*
             * Call method to add XML for <LcsOfLexemesBasedEventDetection>
             */
            addLcsOfLexemesBasedEventDetection(doc, lcsBasedEventDetectionBase, lcsLexemes, interLexemeSeparatorCharacter);


            /*
             * Tokenization for <LcsBasedOnTokensBasedEventDetection>
             */
            lcsLexemes = lcsBasedOnTokensStr.split(tokenizerChar);

            /*
             * Call method to add XML for <LcsBasedOnTokensBasedEventDetection>
             */
            addLcsBasedOnTokensBasedEventDetection(doc, lcsBasedEventDetectionBase, lcsLexemes, interLexemeSeparatorCharacter);


        } catch (Exception e) {
            throw new Exception("EventDetector : addLcsBasedEventDetection : "
                    + e + " : " + e.getMessage());
        }
    }

    private void addBagOfWordsBasedEventDetection(Document doc,
            Element bagOfWordsBasedEventDetectionBase,
            String TBALSequenceForString1Str, String TBALSequenceForString2Str,
            String LBALSequenceForString1Str, String LBALSequenceForString2Str)
            throws Exception {
        /*
         * Local Declarations
         */
        String interLexemeSeparatorCharacter,
                tokenizerChar,
                bowLexemesString1[],
                bowLexemesString2[];

        /*
         * Creates following structure
         *
         * //<BOWBasedEventDetection>
         *          <TBALBasedEventDetection>
         *              <TBALSequenceForString1BasedEventDetection>
         *                  true/false
         *              </TBALSequenceForString1BasedEventDetection>
         *
         *              <TBALSequenceForString2BasedEventDetection>
         *                  true/false
         *              </TBALSequenceForString2BasedEventDetection>
         *
         *              <TBALBasedEventDetectionBasedOnString1AndString2Sequence>
         *                  true/false
         *              </TBALBasedEventDetectionBasedOnString1AndString2Sequence>
         *
         *              <TBALBasedEventDetectionBasedOnString1OrString2Sequence>
         *                  true/false
         *              </TBALBasedEventDetectionBasedOnString1OrString2Sequence>
         *          </TBALBasedEventDetection>
         *
         *          <LBALBasedEventDetection>
         *              <LBALSequenceForString1BasedEventDetection>
         *                  true/false
         *              </LBALSequenceForString1BasedEventDetection>
         *
         *              <LBALSequenceForString2BasedEventDetection>
         *                  true/false
         *              </LBALSequenceForString2BasedEventDetection>
         *          </LBALBasedEventDetection>
         * //</BOWBasedEventDetection>
         */
        try {
            /*
             * set separator character
             */
            tokenizerChar = " ";

            /*
             * set interLexemeSeparatorCharactercharacter
             */
            interLexemeSeparatorCharacter = "/";

            /*
             * As, we know that <TBALBasedEventDetection> is based on TBALSequenceForString1 and
             * TBALSequenceForString2. As these are based on token level similarity (token_POS)
             * so an exhaustive search for 'Event' in both strings, as well as each string
             * separately.
             * 
             * Tokenization for <TBALBasedEventDetection>
             *
             * Here
             * bowLexemesString1, is defined as, tokens of 'TBALSequenceForString1Str'
             * bowLexemesString2, is defined as, tokens of 'TBALSequenceForString2Str'
             * 
             */
//            bowLexemesString1 = TBALSequenceForString1Str.split(tokenizerChar);
//            bowLexemesString2 = TBALSequenceForString2Str.split(tokenizerChar);

            /*
             * Call method to add XML for <TBALBasedEventDetection>
             */
//            addTBALBasedEventDetection(doc, bagOfWordsBasedEventDetectionBase, bowLexemesString1, bowLexemesString2, interLexemeSeparatorCharacter);

            /*
             * As, we know that <LBALBasedEventDetection> is based on LBALSequenceForString1 and
             * LBALSequenceForString2. As these are based on token plus first two characters of
             * POS level similarity (token_POS). So search for 'Event' in any one of two strings'
             * first two characters of POS can be worthwile.
             *
             * Tokenization for <LBALBasedEventDetection>
             *
             * Here
             * bowLexemesString1, is defined as, tokens of 'LBALSequenceForString1Str'
             * bowLexemesString2, is defined as, tokens of 'LBALSequenceForString2Str'
             *
             */
            bowLexemesString1 = LBALSequenceForString1Str.split(tokenizerChar);
            bowLexemesString2 = LBALSequenceForString2Str.split(tokenizerChar);

            /*
             * Call method to add XML for <LBALBasedEventDetection>
             */
            addLBALBasedEventDetection(doc, bagOfWordsBasedEventDetectionBase, bowLexemesString1, bowLexemesString2, interLexemeSeparatorCharacter);


        } catch (Exception e) {
            throw new Exception("EventDetector : addBagOfWordsBasedEventDetection : "
                    + e + " : " + e.getMessage());
        }
    }

    private void addLcsOfCharactersBasedEventDetection(Document doc,
            Element lcsBasedEventDetectionBase, String[] lcsLexemes,
            String intraLexemeSeparatorCharacter) throws Exception {
        /*
         * Local Declarations
         */
        Element lcsOfCharactersBasedEventDetectionElement;

        /*
         * Creates following structure
         * 
         *    //<LcsBasedEventDetection>
         *          <LcsOfCharactersBasedEventDetection>
         *              true/false
         *          </LcsOfCharactersBasedEventDetection>
         *    //<LcsBasedEventDetection>
         */

        try {
            /*
             * Create 'LcsOfCharactersBasedEventDetection' node
             */
            lcsOfCharactersBasedEventDetectionElement = doc.createElement("LcsOfCharactersBasedEventDetection");

            /*
             * If event is found, place TRUE, otherwise, FALSE             *
             */
            if (isEventFound(lcsLexemes, intraLexemeSeparatorCharacter)) {
                lcsOfCharactersBasedEventDetectionElement.setTextContent("true");
            } else {
                lcsOfCharactersBasedEventDetectionElement.setTextContent("false");
            }

            /*
             * Append 'lcsOfCharactersBasedEventDetectionElement' to "lcsBasedEventDetectionBase"
             */
            lcsBasedEventDetectionBase.appendChild(lcsOfCharactersBasedEventDetectionElement);

        } catch (Exception e) {
            throw new Exception("EventDetector : addLcsOfCharactersBasedEventDetection : "
                    + e + " : " + e.getMessage());
        }
    }

    private void addLcsOfLexemesBasedEventDetection(Document doc,
            Element lcsBasedEventDetectionBase, String[] lcsLexemes,
            String intraLexemeSeparatorCharacter) throws Exception {
        /*
         * Local Declarations
         */
        Element lcsOfLexemesBasedEventDetectionElement;

        /*
         * Creates following structure
         *
         *    //<LcsBasedEventDetection>
         *          <LcsOfLexemesBasedEventDetection>
         *              true/false
         *          </LcsOfLexemesBasedEventDetection>
         *    //<LcsBasedEventDetection>
         */

        try {
            /*
             * Create 'lcsOfLexemesBasedEventDetectionElement' node
             */
            lcsOfLexemesBasedEventDetectionElement = doc.createElement("LcsOfLexemesBasedEventDetection");

            /*
             * If event is found, place TRUE, otherwise, FALSE             *
             */
            if (isEventFound(lcsLexemes, intraLexemeSeparatorCharacter)) {
                lcsOfLexemesBasedEventDetectionElement.setTextContent("true");
            } else {
                lcsOfLexemesBasedEventDetectionElement.setTextContent("false");
            }

            /*
             * Append 'lcsOfLexemesBasedEventDetectionElement' to "lcsBasedEventDetectionBase"
             */
            lcsBasedEventDetectionBase.appendChild(lcsOfLexemesBasedEventDetectionElement);

        } catch (Exception e) {
            throw new Exception("EventDetector : addLcsOfLexemesBasedEventDetection : "
                    + e + " : " + e.getMessage());
        }
    }

    private void addLcsBasedOnTokensBasedEventDetection(Document doc,
            Element lcsBasedEventDetectionBase, String[] lcsLexemes,
            String interLexemeSeparatorCharacter) throws Exception {
        /*
         * Local Declarations
         */
        Element lcsBasedOnTokensBasedEventDetectionElement;

        /*
         * Creates following structure
         *
         *    //<LcsBasedEventDetection>
         *          <LcsBasedOnTokensBasedEventDetection>
         *              true/false
         *          <LcsBasedOnTokensBasedEventDetection>
         *    //<LcsBasedEventDetection>
         */

        try {
            /*
             * Create 'lcsBasedOnTokensBasedEventDetectionElement' node
             */
            lcsBasedOnTokensBasedEventDetectionElement = doc.createElement("LcsBasedOnTokensBasedEventDetection");

            /*
             * If event is found, place TRUE, otherwise, FALSE             *
             */
            if (isEventFound(lcsLexemes, interLexemeSeparatorCharacter)) {
                lcsBasedOnTokensBasedEventDetectionElement.setTextContent("true");
            } else {
                lcsBasedOnTokensBasedEventDetectionElement.setTextContent("false");
            }

            /*
             * Append 'lcsBasedOnTokensBasedEventDetectionElement' to "lcsBasedEventDetectionBase"
             */
            lcsBasedEventDetectionBase.appendChild(lcsBasedOnTokensBasedEventDetectionElement);

        } catch (Exception e) {
            throw new Exception("EventDetector : addLcsBasedOnTokensBasedEventDetection : "
                    + e + " : " + e.getMessage());
        }
    }

    private boolean isEventFound(String[] lcsLexemes,
            String interLexemeSeparatorCharacter) throws Exception {
        /*
         * Local Declarations
         */
        boolean isEventFound = false;

        String firstTwoCharactersOfPOS = "",
                verbCharacters = "VB";

        try {
            /*
             * Loop through lcsLexmes[], and try finding VERB
             */
            for (int i = 0; i < lcsLexemes.length; i++) {
                /*
                 * Extract firstTwoCharactersOfPOS
                 */
                try {
                    firstTwoCharactersOfPOS = lcsLexemes[i].substring(lcsLexemes[i].indexOf(interLexemeSeparatorCharacter) + 1, lcsLexemes[i].indexOf(interLexemeSeparatorCharacter) + 3);

                } catch (IndexOutOfBoundsException e) {
                    firstTwoCharactersOfPOS = "";
                }

                /*
                 * Comparison
                 */
                if (firstTwoCharactersOfPOS.equalsIgnoreCase(verbCharacters)) {
                    isEventFound = true;
                }
            }

        } catch (Exception e) {
            throw new Exception("EventDetector : isEventFound : "
                    + e + " : " + e.getMessage());
        }

        return isEventFound;
    }

    private void addTBALBasedEventDetection(Document doc,
            Element bagOfWordsBasedEventDetectionBase,
            String[] bowLexemesString1, String[] bowLexemesString2,
            String interLexemeSeparatorCharacter) throws Exception {
        /*
         * Local Declarations
         */
        Element TBALBasedEventDetectionBase,
                TBALSequenceForString1BasedEventDetectionElement,
                TBALSequenceForString2BasedEventDetectionElement,
                TBALBasedEventDetectionBasedOnString1AndString2SequenceElement,
                TBALBasedEventDetectionBasedOnString1OrString2SequenceElement;

        Boolean isEventFoundInBowLexemesString1,
                isEventFoundInBowLexemesString2;

        /*
         * Creates following structure
         * 
         * <TBALBasedEventDetection>
         *              <TBALSequenceForString1BasedEventDetection>
         *                  true/false
         *              </TBALSequenceForString1BasedEventDetection>
         *
         *              <TBALSequenceForString2BasedEventDetection>
         *                  true/false
         *              </TBALSequenceForString2BasedEventDetection>
         *
         *              <TBALBasedEventDetectionBasedOnString1AndString2Sequence>
         *                  true/false
         *              </TBALBasedEventDetectionBasedOnString1AndString2Sequence>
         *
         *              <TBALBasedEventDetectionBasedOnString1OrString2Sequence>
         *                  true/false
         *              </TBALBasedEventDetectionBasedOnString1OrString2Sequence>
         * </TBALBasedEventDetection>
         * 
         */

        try {
            /*
             * Create required nodes
             */
            TBALBasedEventDetectionBase = doc.createElement("TBALBasedEventDetection");
            TBALSequenceForString1BasedEventDetectionElement = doc.createElement("TBALSequenceForString1BasedEventDetection");
            TBALSequenceForString2BasedEventDetectionElement = doc.createElement("TBALSequenceForString2BasedEventDetection");
            TBALBasedEventDetectionBasedOnString1AndString2SequenceElement = doc.createElement("TBALBasedEventDetectionBasedOnString1AndString2Sequence");
            TBALBasedEventDetectionBasedOnString1OrString2SequenceElement = doc.createElement("TBALBasedEventDetectionBasedOnString1OrString2Sequence");

            /*
             * Search for Events
             */
            isEventFoundInBowLexemesString1 = isEventFound(bowLexemesString1, interLexemeSeparatorCharacter);
            isEventFoundInBowLexemesString2 = isEventFound(bowLexemesString2, interLexemeSeparatorCharacter);

            /*
             * Testing code
             */
            List<String> lst1 = new ArrayList<String>();
            List<String> lst2 = new ArrayList<String>();

            lst1.addAll(Arrays.asList(bowLexemesString1));
            lst2.addAll(Arrays.asList(bowLexemesString2));

            if (isEventFoundInBowLexemesString1 != isEventFoundInBowLexemesString2) {
                System.out.println("addTBALBasedEventDetection :  isEventFoundInBowLexemesString1 != isEventFoundInBowLexemesString2 : "
                        + lst1.toString() + " " + lst2.toString());
            }

            /*
             * Set proper text contents
             */
            TBALSequenceForString1BasedEventDetectionElement.setTextContent(Boolean.toString(isEventFoundInBowLexemesString1));
            TBALSequenceForString2BasedEventDetectionElement.setTextContent(Boolean.toString(isEventFoundInBowLexemesString2));

            TBALBasedEventDetectionBasedOnString1AndString2SequenceElement.setTextContent(Boolean.toString((isEventFoundInBowLexemesString1 && isEventFoundInBowLexemesString2)));
            TBALBasedEventDetectionBasedOnString1OrString2SequenceElement.setTextContent(Boolean.toString((isEventFoundInBowLexemesString1 || isEventFoundInBowLexemesString2)));

            /*
             * Append all calculations to TBALBasedEventDetectionBase
             */
            TBALBasedEventDetectionBase.appendChild(TBALSequenceForString1BasedEventDetectionElement);
            TBALBasedEventDetectionBase.appendChild(TBALSequenceForString2BasedEventDetectionElement);
            TBALBasedEventDetectionBase.appendChild(TBALBasedEventDetectionBasedOnString1AndString2SequenceElement);
            TBALBasedEventDetectionBase.appendChild(TBALBasedEventDetectionBasedOnString1OrString2SequenceElement);

            /*
             * Append TBALBasedEventDetectionBase to 'bagOfWordsBasedEventDetectionBase' (<BOWBasedEventDetection>)
             */
            bagOfWordsBasedEventDetectionBase.appendChild(TBALBasedEventDetectionBase);

        } catch (Exception e) {
            throw new Exception("EventDetector : addTBALBasedEventDetection : "
                    + e + " : " + e.getMessage());
        }
    }

    private void addLBALBasedEventDetection(Document doc,
            Element bagOfWordsBasedEventDetectionBase, String[] bowLexemesString1,
            String[] bowLexemesString2, String interLexemeSeparatorCharacter)
            throws Exception {
        /*
         * Local Declarations
         */
        Element LBALBasedEventDetectionBase,
                LBALSequenceForString1BasedEventDetectionElement,
                LBALSequenceForString2BasedEventDetectionElement,
                LBALEventDetectionElement;

        Boolean isEventFoundInBowLexemesString1,
                isEventFoundInBowLexemesString2;

        /*
         * Creates following structure
         *
         * <LBALBasedEventDetection>
         *              <LBALSequenceForString1BasedEventDetection>
         *                  true/false
         *              </LBALSequenceForString1BasedEventDetection>
         *
         *              <LBALSequenceForString2BasedEventDetection>
         *                  true/false
         *              </LBALSequenceForString2BasedEventDetection>
         * </LBALBasedEventDetection>
         */

        try {
            /*
             * Create required nodes
             */
            LBALBasedEventDetectionBase = doc.createElement("LBALBasedEventDetection");
//            LBALSequenceForString1BasedEventDetectionElement = doc.createElement("LBALSequenceForString1BasedEventDetection");
//            LBALSequenceForString2BasedEventDetectionElement = doc.createElement("LBALSequenceForString2BasedEventDetection");
            LBALEventDetectionElement = doc.createElement("LBALEventDetection");

            /*
             * Search for Events
             */
            isEventFoundInBowLexemesString1 = isEventFound(bowLexemesString1, interLexemeSeparatorCharacter);
            isEventFoundInBowLexemesString2 = isEventFound(bowLexemesString2, interLexemeSeparatorCharacter);

            /*
             * Testing code
             */
            if (isEventFoundInBowLexemesString1 != isEventFoundInBowLexemesString2) {
                System.out.println("addLBALBasedEventDetection :  isEventFoundInBowLexemesString1 != isEventFoundInBowLexemesString2 : "
                        + bowLexemesString1.toString() + " " + bowLexemesString2.toString());
            }

            /*
             * Set proper text contents
             */
//            LBALSequenceForString1BasedEventDetectionElement.setTextContent(Boolean.toString(isEventFoundInBowLexemesString1));
//            LBALSequenceForString2BasedEventDetectionElement.setTextContent(Boolean.toString(isEventFoundInBowLexemesString2));

            if (isEventFoundInBowLexemesString1 == true && isEventFoundInBowLexemesString2 == true) {
                LBALEventDetectionElement.setTextContent("true");
            } else {
                LBALEventDetectionElement.setTextContent("false");
            }

            /*
             * Append all calculations to LBALBasedEventDetectionBase
             */
//            LBALBasedEventDetectionBase.appendChild(LBALSequenceForString1BasedEventDetectionElement);
//            LBALBasedEventDetectionBase.appendChild(LBALSequenceForString2BasedEventDetectionElement);
            LBALBasedEventDetectionBase.appendChild(LBALEventDetectionElement);

            /*
             * Append LBALBasedEventDetectionBase to 'bagOfWordsBasedEventDetectionBase' (<BOWBasedEventDetection>)
             */
            bagOfWordsBasedEventDetectionBase.appendChild(LBALBasedEventDetectionBase);

        } catch (Exception e) {
            throw new Exception("EventDetector : addLBALBasedEventDetection : "
                    + e + " : " + e.getMessage());
        }
    }
}
