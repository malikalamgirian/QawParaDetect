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
public class NonMonotoneAlignerOld00 {

    public NonMonotoneAlignerOld00(Properties properties) throws Exception {
        /*
         * Set state of proper Properties instance field
         */
        this.properties = properties;
        /*
         * This also sets instance variable "monotone_Aligned_File_URL"
         */
        this.setNon_Monotone_Aligned_File_URL(properties);

        /*
         * call Process method
         */
        process();
    }

    /*
     * Declarations
     */
    private Properties properties;
    private String non_Monotone_Aligned_File_URL;


    /*
     * Applies 'Non-Monotonic Alignment Techniques' to "PosFilteredString1" and "PosFilteredString2"
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
                posFilteredStr2;

        try {
            System.out.println("NonMonotoneAligner File URL is : " + getNon_Monotone_Aligned_File_URL());
            System.out.println("properties.getMonotone_Aligned_File_URL() is : " + properties.getMonotone_Aligned_File_URL());

            /*
             * 1.Get XML Document, for <code>properties.getMonotone_Aligned_File_URL()</code>
             */
            doc = XMLProcessor.getXMLDocumentForXMLFile(properties.getMonotone_Aligned_File_URL());

            /*
             * 2. Get Document Element
             */
            root = doc.getDocumentElement();

            /*
             * 3. Select all "PosFilteredString1", "PosFilteredString2", "Pair" pairs
             */
            posFilteredString1 = root.getElementsByTagName("PosFilteredString1");
            posFilteredString2 = root.getElementsByTagName("PosFilteredString2");
            pairs = root.getElementsByTagName("Pair");

            /*
             * 4. loop through all pairs
             */
            for (int i = 0; i < pairs.getLength(); i++) {
                /*
                 * Get 'Pair'
                 */
                pair = pairs.item(i);

                /*
                 * Get Text content of strings
                 */
                posFilteredStr1 = posFilteredString1.item(i).getTextContent();
                posFilteredStr2 = posFilteredString2.item(i).getTextContent();

                /*
                 * Convert 'posFilteredStr1', 'posFilteredStr2' to lower-case letters
                 */
                posFilteredStr1 = posFilteredStr1.toLowerCase();
                posFilteredStr2 = posFilteredStr2.toLowerCase();

                /*
                 * Calculate, Create and Add "non-monotone-aligner" as XML
                 */
                calculateAndAddNonMonotoneAligner(doc, root, pair, posFilteredStr1, posFilteredStr2);

                System.out.println((i + 1) + " / " + pairs.getLength() + "\n");
            }
            /*
             * 5. Transform the tree, to "monotone_Aligned_File_URL"
             */
            XMLProcessor.transformXML(doc, new StreamResult(new File(this.non_Monotone_Aligned_File_URL)));

        } catch (Exception e) {
            throw new Exception("NonMonotoneAligner : Process :"
                    + e + " : " + e.getMessage());
        }
    }

    /*
     * Helper Methods
     */
    private String getNon_Monotone_Aligned_File_URL() {
        return non_Monotone_Aligned_File_URL;
    }

    private void setNon_Monotone_Aligned_File_URL(Properties properties) throws Exception {
        /*
         * Local Declarations
         */
        String suffixToAdd,
                newURL;
        try {
            /*
             * add Suffix, "_nma" for "non-monotone aligned"
             */
            suffixToAdd = "_nma";

            /*
             * call FileSystemManager.addSuffixToFileURL
             */
            newURL = FileSystemManager.addSuffixToFileURL(properties.getMonotone_Aligned_File_URL(), suffixToAdd, null);

            /*
             * set instance state
             */
            non_Monotone_Aligned_File_URL = newURL;

            /*
             * set "properties" instance field value
             */
            properties.setNon_Monotone_Aligned_File_URL(newURL);

        } catch (Exception e) {
            throw new Exception("NonMonotoneAligner : setNon_Monotone_Aligned_File_URL : "
                    + e + " : " + e.getMessage());
        }
    }

    private Boolean calculateAndAddNonMonotoneAligner(Document doc, Element root, Node pair, String posFilteredStr1, String posFilteredStr2) throws Exception {
        /*
         * Local Declarations
         */
        Element nonMonotoneAlignerBase,
                bagOfWordsBasedAlignmentBase,
                tokenBasedAlignedLexemesBase,
                lexemeBasedAlignedLexemesBase,
                lexemesString1Element,
                lexemesString2Element;

        String lexemesString1[],
                lexemesString2[],
                tokenizerChar;

        try {
            /*
             * set separator character
             */
            tokenizerChar = " ";

            /*
             * Split 'posFilteredStr1', 'posFilteredStr2' into lexemes
             */
            lexemesString1 = posFilteredStr1.split(tokenizerChar);
            lexemesString2 = posFilteredStr2.split(tokenizerChar);

            /*
             * Creates following structure
             *
             * <NonMonotoneAligner>
             *      <LexemesString1>
             *          <Lexeme Id="0">token_pos</Lexeme>
             *          <Lexeme Id="1">token_pos</Lexeme>
             *      </LexemesString1>
             *      <LexemesString2>
             *          <Lexeme Id="0">token_pos</Lexeme>
             *          <Lexeme Id="1">token_pos</Lexeme>
             *          <Lexeme Id="2">token_pos</Lexeme>
             *      </LexemesString2>
             * </NonMonotoneAligner>
             */
            nonMonotoneAlignerBase = doc.createElement("NonMonotoneAligner");

//            lexemesString1Element = doc.createElement("LexemesString1");
//            lexemesString2Element = doc.createElement("LexemesString2");

            /*
             * Add lexemes, to 'lexemesString1Element' and 'lexemesString2Element'
             * where N is 1,2
             */
//            addLexemesToLexemeStringNNode(doc, lexemesString1Element, lexemesString1);
//            addLexemesToLexemeStringNNode(doc, lexemesString2Element, lexemesString2);

            /*
             * Append 'lexemes' to 'nonMonotoneAlignerBase' <NonMonotoneAligner>
             */
//            nonMonotoneAlignerBase.appendChild(lexemesString1Element);
//            nonMonotoneAlignerBase.appendChild(lexemesString2Element);

            /*
             * Creates following structure
             *
             * //<NonMonotoneAligner>
             *      <BowBasedAlignment>
             *          <TokenBasedAlignedLexemes>
             *              <TBAL LexemeIdString1="0" LexemeIdString2="0" />
             *              <TBAL LexemeIdString1="1" LexemeIdString2="2" />
             *
             *              <TBALSequenceForString1>
             *                  token0_POS token1_POS
             *              </TBALSequenceForString1>
             *
             *              <TBALSequenceForString2>
             *                  token0_POS token2_POS
             *              </TBALSequenceForString2>
             * 
             *          </TokenBasedAlignedLexemes>
             *      </BowBasedAlignment>
             * //</NonMonotoneAligner>
             */
            bagOfWordsBasedAlignmentBase = doc.createElement("BowBasedAlignment");
            tokenBasedAlignedLexemesBase = doc.createElement("TokenBasedAlignedLexemes");

            /*
             * Add 'token based aligned lexemes'
             */
            addTokenBasedAlignedLexemes(doc, tokenBasedAlignedLexemesBase, lexemesString1, lexemesString2);

            /*
             * Append 'TokenBasedAlignedLexemes' to 'BowBasedAlignment'
             */
            bagOfWordsBasedAlignmentBase.appendChild(tokenBasedAlignedLexemesBase);

            /*
             * Creates following structure
             *
             * //<NonMonotoneAligner>
             *      //<BowBasedAlignment>
             *          <LexemeBasedAlignedLexemes>
             *              <LBAL LexemeIdString1="0" LexemeIdString2="0" />
             *              <LBAL LexemeIdString1="1" LexemeIdString2="2" />
             *
             *              <LBALSequenceForString1>
             *                  token0_POS token1_POS
             *              </LBALSequenceForString1>
             *
             *              <LBALSequenceForString2>
             *                  token0_POS token2_POS
             *              </LBALSequenceForString2>
             *
             *            \\<LBALBasedBOCW>
             *                  token0_POS token1_POS
             *            \\</LBALBasedBOCW>
             * 
             *          </LexemeBasedAlignedLexemes>
             *      //</BowBasedAlignment>
             * //</NonMonotoneAligner>
             */

            /*
             * Create node <LexemeBasedAlignedLexemes>
             */
            lexemeBasedAlignedLexemesBase = doc.createElement("LexemeBasedAlignedLexemes");

            /*
             * Add 'lexeme based aligned lexemes'
             */
            addLexemeBasedAlignedLexemes(doc, lexemeBasedAlignedLexemesBase, lexemesString1, lexemesString2);

            /*
             * Append <LexemeBasedAlignedLexemes> to 'BowBasedAlignment'
             */
            bagOfWordsBasedAlignmentBase.appendChild(lexemeBasedAlignedLexemesBase);

            /*
             * Append 'BowBasedAlignment' to <NonMonotoneAligner>
             */
            nonMonotoneAlignerBase.appendChild(bagOfWordsBasedAlignmentBase);

            /*
             * Add 'nonMonotoneAlignerBase' or <NonMonotoneAligner> to 'pair'
             */
            pair.appendChild(nonMonotoneAlignerBase);


            return true;

        } catch (Exception e) {
            throw new Exception("NonMonotoneAligner : calculateAndAddNonMonotoneAligner : "
                    + e + " : " + e.getMessage());
        }
    }

    private void addLexemesToLexemeStringNNode(Document doc, Element lexemesStringNElement, String[] lexemesString) throws Exception {
        /*
         * Local Declarations
         */
        Element lexeme;

        /*
         * Creates following structure
         *
         * //<LexemesString>
         *          <Lexeme Id="0">token_pos</Lexeme>
         *          <Lexeme Id="1">token_pos</Lexeme>
         * //</LexemesString>
         * 
         */

        try {
            /*
             * Loop through String[] lexemesString
             * and add 'lexemes'
             */
            for (int i = 0; i < lexemesString.length; i++) {
                /*
                 * Create lexeme
                 */
                lexeme = doc.createElement("Lexeme");

                /*
                 * Add 'Id' attribute to lexeme
                 */
                lexeme.setAttribute("Id", Integer.toString(i));

                /*
                 * Set text-content of lexeme
                 */
                lexeme.setTextContent(lexemesString[i]);

                /*
                 * Append lexeme to 'lexemesStringNElement'
                 */
                lexemesStringNElement.appendChild(lexeme);

            }

        } catch (Exception e) {
            throw new Exception("NonMonotoneAligner : addLexemesToLexemeStringNNode : "
                    + e + " : " + e.getMessage());
        }

    }

    private void addTokenBasedAlignedLexemes(Document doc,
            Element tokenBasedAlignedLexemesBase, String[] lexemesString1,
            String[] lexemesString2) throws Exception {
        /*
         * Local Declarations
         */
        Element TBAL,
                TBALSequenceForString1Element,
                TBALSequenceForString2Element;

        String tokenForString1Lexeme,
                tokenForString2Lexeme,
                interLexemeSeparatorCharacter,
                tokenizerChar,
                TBALSequenceForString1Str = "",
                TBALSequenceForString2Str = "";

        /*
         * Creates following structure
         *
         * //<TokenBasedAlignedLexemes>
         *              <TBAL String1LexemeId="0" String2LexemeId="0" />
         *              <TBAL String1LexemeId="1" String2LexemeId="2" />
         *
         *              <TBALSequenceForString1>
         *                  token0_POS token1_POS
         *              </TBALSequenceForString1>
         *
         *              <TBALSequenceForString2>
         *                  token0_POS token2_POS
         *              </TBALSequenceForString2>
         * 
         * //</TokenBasedAlignedLexemes>
         */

        try {
            /*
             * set separator character
             */
            tokenizerChar = " ";

            /*
             * Set interLexemeSeparatorCharacter
             */
            interLexemeSeparatorCharacter = "/";

            /*
             * Create nodes for <TBALSequenceForString1>, <TBALSequenceForString2>
             */
            TBALSequenceForString1Element = doc.createElement("TBALSequenceForString1");
            TBALSequenceForString2Element = doc.createElement("TBALSequenceForString2");

            /*
             * Loop through 'both strings'
             */
            for (int i = 0; i < lexemesString1.length; i++) {
                /*
                 * Get 'tokenForString1Lexeme' from lexemeString1[i]
                 */
                tokenForString1Lexeme = lexemesString1[i].substring(0, lexemesString1[i].indexOf(interLexemeSeparatorCharacter));

                /*
                 * Loop through string2
                 */
                for (int j = 0; j < lexemesString2.length; j++) {
                    /*
                     * Get 'tokenForString2Lexeme' from lexemeString2[j]
                     */
                    tokenForString2Lexeme = lexemesString2[j].substring(0, lexemesString2[j].indexOf(interLexemeSeparatorCharacter));

                    /*
                     * Check If tokenForString1Lexeme and tokenForString2Lexeme, are equal
                     */
                    if (tokenForString1Lexeme.equalsIgnoreCase(tokenForString2Lexeme)) {
                        /*
                         * Create and Add <TBAL> to <TokenBasedAlignedLexemes>
                         */
//                        TBAL = doc.createElement("TBAL");

                        /*
                         * Set proper attributes
                         */
//                        TBAL.setAttribute("String1LexemeId", Integer.toString(i));
//                        TBAL.setAttribute("String2LexemeId", Integer.toString(j));

                        /*
                         * Add to container node
                         */
//                        tokenBasedAlignedLexemesBase.appendChild(TBAL);

                        /*
                         * Create proper strings for <TBALSequenceForString1>
                         * and <TBALSequenceForString2>
                         */
                        if (TBALSequenceForString1Str.equals("") || TBALSequenceForString2Str.equals("")) {
                            /*
                             * Case for first lexeme, add proper lexemes
                             */
                            TBALSequenceForString1Str += lexemesString1[i];
                            TBALSequenceForString2Str += lexemesString2[j];

                        } else {
                            /*
                             * Case for rest of lexemes
                             */
                            TBALSequenceForString1Str += tokenizerChar + lexemesString1[i];
                            TBALSequenceForString2Str += tokenizerChar + lexemesString2[j];
                        }
                    }
                }
            }

            /*
             * Set text-contents for TBALSequenceForString1Element and TBALSequenceForString2Element
             */
            TBALSequenceForString1Element.setTextContent(TBALSequenceForString1Str);
            TBALSequenceForString2Element.setTextContent(TBALSequenceForString2Str);

            /*
             * Add TBALSequenceForString1Element and TBALSequenceForString2Element, to tokenBasedAlignedLexemesBase
             */
            tokenBasedAlignedLexemesBase.appendChild(TBALSequenceForString1Element);
            tokenBasedAlignedLexemesBase.appendChild(TBALSequenceForString2Element);


        } catch (Exception e) {
            throw new Exception("NonMonotoneAligner : addTokenBasedAlignedLexemes : "
                    + e + " : " + e.getMessage());
        }
    }

    /*
     * Here alignment or lexical coupling is based on token_POS,
     * where POS is considered upto '2' characters. Since we would consider
     * first two characters of POS, we would just consider the main POS, e.g.
     * Verb (VB), Noun (NN), Adjective (JJ), Adverb (RB). Not considering the
     * case of Adverb (WRB), contained in <code>PosBasedFilter.pos_To_Filter_Tag_Set</code>.
     */
    private void addLexemeBasedAlignedLexemes(Document doc, Element lexemeBasedAlignedLexemesBase, String[] lexemesString1,
            String[] lexemesString2) throws Exception {
        /*
         * Local Declarations
         */
        Element LBAL,
                LBALSequenceForString1Element,
                LBALSequenceForString2Element,
                LBAL_Based_BOCW_Element;

        String tokenForString1Lexeme,
                tokenForString2Lexeme,
                firstTwoCharactersOfPosOfString1Lexeme,
                firstTwoCharactersOfPosOfString2Lexeme,
                interLexemeSeparatorCharacter,
                tokenizerChar,
                LBALSequenceForString1Str = "",
                LBALSequenceForString2Str = "",
                LBAL_Based_BOCW_Str = "";

        /*
         * Creates following structure
         *
         * //<LexemeBasedAlignedLexemes>
         *              <LBAL String1LexemeId="0" String2LexemeId="0" />
         *              <LBAL String1LexemeId="1" String2LexemeId="2" />
         *
         *              <LBALSequenceForString1>
         *                  token0_POS token1_POS
         *              </LBALSequenceForString1>
         *
         *              <LBALSequenceForString2>
         *                  token0_POS token2_POS
         *              </LBALSequenceForString2>
         *
         *              \\<LBALBasedBOCW>
         *                  token0_POS token1_POS
         *              \\</LBALBasedBOCW>
         *
         * //</LexemeBasedAlignedLexemes>
         */

        try {
            /*
             * set separator character
             */
            tokenizerChar = " ";

            /*
             * Set interLexemeSeparatorCharacter
             */
            interLexemeSeparatorCharacter = "/";

            /*
             * Create nodes for <LBALSequenceForString1>, <LBALSequenceForString2>
             */
            LBALSequenceForString1Element = doc.createElement("LBALSequenceForString1");
            LBALSequenceForString2Element = doc.createElement("LBALSequenceForString2");

            /*
             * Create <LBALBasedBOCW> node
             */
            LBAL_Based_BOCW_Element = doc.createElement("LBALBasedBOCW");

            /*
             * Loop through 'both strings'
             */
            for (int i = 0; i < lexemesString1.length; i++) {
                /*
                 * Get 'tokenForString1Lexeme' from lexemeString1[i]
                 */
                tokenForString1Lexeme = lexemesString1[i].substring(0, lexemesString1[i].indexOf(interLexemeSeparatorCharacter));

                /*
                 * Get 'First Two Characters Of Pos Of String1 Lexeme'
                 */
                firstTwoCharactersOfPosOfString1Lexeme = lexemesString1[i].substring(lexemesString1[i].indexOf(interLexemeSeparatorCharacter) + 1, lexemesString1[i].indexOf(interLexemeSeparatorCharacter) + 3);

                /*
                 * Loop through string2
                 */
                for (int j = 0; j < lexemesString2.length; j++) {
                    /*
                     * Get 'tokenForString2Lexeme' from lexemeString2[j]
                     */
                    tokenForString2Lexeme = lexemesString2[j].substring(0, lexemesString2[j].indexOf(interLexemeSeparatorCharacter));

                    /*
                     * Get 'First Two Characters Of Pos Of String2 Lexeme'
                     */
                    firstTwoCharactersOfPosOfString2Lexeme = lexemesString2[j].substring(lexemesString2[j].indexOf(interLexemeSeparatorCharacter) + 1, lexemesString2[j].indexOf(interLexemeSeparatorCharacter) + 3);

                    /*
                     * Check If tokenForString1Lexeme and tokenForString2Lexeme, and
                     * firstTwoCharactersOfPosOfString1Lexeme firstTwoCharactersOfPosOfString2Lexeme, are equal
                     */
                    if (tokenForString1Lexeme.equalsIgnoreCase(tokenForString2Lexeme)
                            && firstTwoCharactersOfPosOfString1Lexeme.equalsIgnoreCase(firstTwoCharactersOfPosOfString2Lexeme)) {
                        /*
                         * Create and Add <LBAL> to <LexemeBasedAlignedLexemes>
                         */
//                        LBAL = doc.createElement("LBAL");

                        /*
                         * Set proper attributes
                         */
//                        LBAL.setAttribute("String1LexemeId", Integer.toString(i));
//                        LBAL.setAttribute("String2LexemeId", Integer.toString(j));

                        /*
                         * Add to container node
                         */
//                        lexemeBasedAlignedLexemesBase.appendChild(LBAL);

                        /*
                         * Create proper strings for <LBALSequenceForString1>
                         * and <LBALSequenceForString2>
                         */
                        if (LBALSequenceForString1Str.equals("") || LBALSequenceForString2Str.equals("")) {
                            /*
                             * Case for first lexeme, add proper lexemes
                             */
                            LBALSequenceForString1Str += lexemesString1[i];
                            LBALSequenceForString2Str += lexemesString2[j];

                        } else {
                            /*
                             * Case for rest of lexemes
                             */
                            LBALSequenceForString1Str += tokenizerChar + lexemesString1[i];
                            LBALSequenceForString2Str += tokenizerChar + lexemesString2[j];
                        }

                    } else if (tokenForString1Lexeme.equalsIgnoreCase(tokenForString2Lexeme)
                            && !firstTwoCharactersOfPosOfString1Lexeme.equalsIgnoreCase(firstTwoCharactersOfPosOfString2Lexeme)) {

                        System.out.println("Lexeme Based Aligned Lexemes : Lexical non-coupling on basis of POS : "
                                + "\n\tString1 token : " + tokenForString1Lexeme
                                + "\n\tString2 token : " + tokenForString2Lexeme
                                + "\n\tString1 two characters POS : " + firstTwoCharactersOfPosOfString1Lexeme
                                + "\n\tString2 two characters POS : " + firstTwoCharactersOfPosOfString2Lexeme);
                    }
                }
            }

            /*
             * Set text-contents for LBALSequenceForString1Element and LBALSequenceForString2Element
             */
            LBALSequenceForString1Element.setTextContent(LBALSequenceForString1Str);
            LBALSequenceForString2Element.setTextContent(LBALSequenceForString2Str);

            /*
             * Add LBALSequenceForString1Element and LBALSequenceForString2Element, to lexemeBasedAlignedLexemesBase
             */
            lexemeBasedAlignedLexemesBase.appendChild(LBALSequenceForString1Element);
            lexemeBasedAlignedLexemesBase.appendChild(LBALSequenceForString2Element);

        } catch (Exception e) {
            throw new Exception("NonMonotoneAligner : addLexemeBasedAlignedLexemes : "
                    + e + " : " + e.getMessage());
        }
    }
}
