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
public class NonMonotoneAligner {

    public NonMonotoneAligner(Properties properties) throws Exception {
        /*
         * Set state of proper Properties instance field
         */
        this.properties = properties;
        /*
         * This also sets instance variable "monotone_Aligned_File_URL"
         */
        this.setNon_Monotone_Aligned_File_URL(properties);
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
    private String non_Monotone_Aligned_File_URL;
    private WordNetManager wordNet;


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

    private Boolean calculateAndAddNonMonotoneAligner(Document doc, Element root,
            Node pair, String posFilteredStr1, String posFilteredStr2) throws Exception {
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
            addTokenBasedAlignedLexemes(doc, tokenBasedAlignedLexemesBase, lexemesString1,
                    lexemesString2);

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
            addLexemeBasedAlignedLexemes(doc, lexemeBasedAlignedLexemesBase, lexemesString1,
                    lexemesString2);

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

    private void addLexemesToLexemeStringNNode(Document doc, Element lexemesStringNElement,
            String[] lexemesString) throws Exception {
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
                TBALSequenceForString2Element,
                TBUnalignedLexemesSequenceForString1Element,
                TBUnalignedLexemesSequenceForString2Element;

        String tokenForString1Lexeme,
                tokenForString2Lexeme,
                interLexemeSeparatorCharacter,
                tokenizerChar,
                TBALSequenceForString1Str = "",
                TBALSequenceForString2Str = "",
                TBUnalignedLexemesSequenceForString1Str = "",
                TBUnalignedLexemesSequenceForString2Str = "";

        int String1_To_String2_BOW_Alignment_Table[][],
                String2_To_String1_BOW_Alignment_Table[][];

        /*
         * This alignment uses the guidelines of 20/10/2010 discussion, that has
         * a printed version as well.
         *
         * The procedure is as
         *
         * Separate
         *
         * String1_To_String2_BOW_Alignment_Table[][], String2_To_String1_BOW_Alignment_Table[][]
         * 
         * TBALSequenceForString1Str, TBALSequenceForString2Str
         *
         * TBUnalignedLexemesSequenceForString1Str, TBUnalignedLexemesSequenceForString2Str
         *
         * ---------------------
         *
         * Separate Processing steps for lexemesString1 and lexemesString2
         *
         * 1. Simple String Matching
         *
         * 2. Synonym Matching
         *
         * 3. Standard Semantic Similarity Matching
         *
         * --------------------
         * 
         * Creates following structure
         *
         * //<TokenBasedAlignedLexemes>
         *              //<TBAL String1LexemeId="0" String2LexemeId="0" AlignmentType="SimpleMatch/SynonymMatch/SemanticSimilarityMatch" />
         *              //<TBAL String1LexemeId="1" String2LexemeId="2" AlignmentType="SimpleMatch/SynonymMatch/SemanticSimilarityMatch" />
         *
         *              <TBALSequenceForString1>
         *                  token0_POS token1_POS
         *              </TBALSequenceForString1>
         *
         *              <TBALSequenceForString2>
         *                  token0_POS token2_POS
         *              </TBALSequenceForString2>
         *
         *              <TBUnalignedLexemesSequenceForString1>
         *                  token0_POS token2_POS
         *              </TBUnalignedLexemesSequenceForString1>
         *
         *              <TBUnalignedLexemesSequenceForString2>
         *                  token0_POS token2_POS
         *              </TBUnalignedLexemesSequenceForString2>
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
             * Create nodes for 
             * 
             * <TBALSequenceForString1>, <TBALSequenceForString2>
             *
             * <TBUnalignedLexemesSequenceForString1>, <TBUnalignedLexemesSequenceForString2>
             *
             */
            TBALSequenceForString1Element = doc.createElement("TBALSequenceForString1");
            TBALSequenceForString2Element = doc.createElement("TBALSequenceForString2");
            TBUnalignedLexemesSequenceForString1Element = doc.createElement("TBUnalignedLexemesSequenceForString1");
            TBUnalignedLexemesSequenceForString2Element = doc.createElement("TBUnalignedLexemesSequenceForString2");

            /*
             * Initialize String1_To_String2_BOW_Alignment_Table, String2_To_String1_BOW_Alignment_Table
             */
            String1_To_String2_BOW_Alignment_Table = new int[lexemesString1.length][lexemesString2.length];
            String2_To_String1_BOW_Alignment_Table = new int[lexemesString2.length][lexemesString1.length];

            /*
             * ****************************************************************
             * ******* Loops for String1_To_String2_BOW_Alignment_Table *******
             * *** which in fact produces String2 Alignment and Unalignment ***
             * ****************************************************************
             */

            try {

                /*
                 * Loop
                 *
                 * Simple String Similarity Match FOR
                 *
                 * String1_To_String2_BOW_Alignment_Table
                 *
                 */
                for (int i = 0; i < lexemesString1.length; i++) {
                    /*
                     * Get 'tokenForString1Lexeme' from lexemeString1[i]
                     */
                    tokenForString1Lexeme = lexemesString1[i].substring(0,
                            lexemesString1[i].indexOf(interLexemeSeparatorCharacter));


                    /*
                     * Loop through string2
                     */
                    for (int j = 0; j < lexemesString2.length; j++) {
                        /*
                         * Get 'tokenForString2Lexeme' from lexemeString2[j]
                         */
                        tokenForString2Lexeme = lexemesString2[j].substring(0,
                                lexemesString2[j].indexOf(interLexemeSeparatorCharacter));

                        /*
                         * Check If
                         *
                         * tokenForString1Lexeme and tokenForString2Lexeme, are equal
                         *
                         * and String1_To_String2_BOW_Alignment_Table is zero... Means an alignment has not been done.
                         *
                         * Set the jth column to 1, for all i's, means that string2 lexeme has been aligned it to...
                         *
                         */
                        if (String1_To_String2_BOW_Alignment_Table[i][j] == 0
                                && tokenForString1Lexeme.equalsIgnoreCase(tokenForString2Lexeme)) {
                            /*
                             * Set the jth column to 1, for all i's (lexemeString1), here used as k.
                             */
                            for (int k = 0; k < lexemesString1.length; k++) {
                                String1_To_String2_BOW_Alignment_Table[k][j] = 1;
                            }

                            /*
                             * Break inner loop, because alignment has been done.
                             */
                            break;
                        } /*
                         * Check If
                         *
                         * tokenForString1Lexeme and tokenForString2Lexeme, are Synonyms
                         *
                         * and String1_To_String2_BOW_Alignment_Table is zero... Means an alignment has not been done.
                         *
                         * Set the jth column to 1, for all i's, means that string2 lexeme has been aligned it to...
                         *
                         */ else if (String1_To_String2_BOW_Alignment_Table[i][j] == 0
                                && wordNet.areWordsSynonyms(tokenForString1Lexeme, tokenForString2Lexeme, null, true)) {
                            /*
                             * Set the jth column to 1, for all i's (lexemeString1), here used as k.
                             */
                            for (int k = 0; k < lexemesString1.length; k++) {
                                String1_To_String2_BOW_Alignment_Table[k][j] = 1;
                            }

                            /*
                             * Break inner loop, because alignment has been done.
                             */
                            break;
                        }
                    }
                }


                /*
                 * ****************************************************************
                 * ******* Loops for String2_To_String1_BOW_Alignment_Table *******
                 * *** which in fact produces String1 Alignment and Unalignment ***
                 * ****************************************************************
                 */
                /*
                 * Loop
                 *
                 * Simple String Similarity Match FOR
                 *
                 * String2_To_String1_BOW_Alignment_Table
                 *
                 */
                for (int i = 0; i < lexemesString2.length; i++) {
                    /*
                     * Get 'tokenForString2Lexeme' from lexemeString2[i]
                     */
                    tokenForString2Lexeme = lexemesString2[i].substring(0,
                            lexemesString2[i].indexOf(interLexemeSeparatorCharacter));

                    /*
                     * Loop through string1
                     */
                    for (int j = 0; j < lexemesString1.length; j++) {
                        /*
                         * Get 'tokenForString1Lexeme' from lexemeString1[j]
                         */
                        tokenForString1Lexeme = lexemesString1[j].substring(0,
                                lexemesString1[j].indexOf(interLexemeSeparatorCharacter));

                        /*
                         * Check If
                         *
                         * tokenForString1Lexeme and tokenForString2Lexeme, are equal
                         *
                         * and String2_To_String1_BOW_Alignment_Table is zero... Means an alignment has not been done.
                         *
                         * Set the jth column to 1, for all i's, means that string1 lexeme has been aligned it to...
                         *
                         */
                        if (String2_To_String1_BOW_Alignment_Table[i][j] == 0
                                && tokenForString1Lexeme.equalsIgnoreCase(tokenForString2Lexeme)) {
                            /*
                             * Set the jth column to 1, for all i's (lexemeString2), here used as k.
                             */
                            for (int k = 0; k < lexemesString2.length; k++) {
                                String2_To_String1_BOW_Alignment_Table[k][j] = 1;
                            }

                            /*
                             * Break inner loop, because alignment has been done.
                             */
                            break;
                        } /*
                         * Check If
                         *
                         * tokenForString1Lexeme and tokenForString2Lexeme, are Synonyms
                         *
                         * and String2_To_String1_BOW_Alignment_Table is zero... Means an alignment has not been done.
                         *
                         * Set the jth column to 1, for all i's, means that string1 lexeme has been aligned it to...
                         *
                         */ else if (String2_To_String1_BOW_Alignment_Table[i][j] == 0
                                && wordNet.areWordsSynonyms(tokenForString1Lexeme, tokenForString2Lexeme, null, true)) {
                            /*
                             * Set the jth column to 1, for all i's (lexemeString2), here used as k.
                             */
                            for (int k = 0; k < lexemesString2.length; k++) {
                                String2_To_String1_BOW_Alignment_Table[k][j] = 1;
                            }

                            /*
                             * Break inner loop, because alignment has been done.
                             */
                            break;
                        }
                    }
                }

            } catch (IndexOutOfBoundsException e) {
                // do nothing
            }



            /*
             * ****************************************************************
             * *************** Prepare Text Contents For String1 **************
             * ******* Loops for String2_To_String1_BOW_Alignment_Table *******
             * ****************************************************************
             * **************** TBALSequenceForString1Str *********************
             * ************* TBUnalignedLexemesSequenceForString1Str **********
             * ****************************************************************
             */
            for (int i = 0; i < lexemesString1.length; i++) {
                /*
                 * Test values of first row, for every column
                 */
                if (String2_To_String1_BOW_Alignment_Table[0][i] == 1) {
                    /*
                     * Create TBALSequenceForString1Str
                     */
                    if (TBALSequenceForString1Str.equals("")) {
                        /*
                         * Case for first lexeme, add proper lexeme
                         */
                        TBALSequenceForString1Str += lexemesString1[i];

                    } else {
                        /*
                         * Case for rest of lexemes
                         */
                        TBALSequenceForString1Str += tokenizerChar + lexemesString1[i];
                    }

                } else if (String2_To_String1_BOW_Alignment_Table[0][i] == 0) {
                    /*
                     * Create TBUnalignedLexemesSequenceForString1Str
                     */
                    if (TBUnalignedLexemesSequenceForString1Str.equals("")) {
                        /*
                         * Case for first lexeme, add proper lexeme
                         */
                        TBUnalignedLexemesSequenceForString1Str += lexemesString1[i];

                    } else {
                        /*
                         * Case for rest of lexemes
                         */
                        TBUnalignedLexemesSequenceForString1Str += tokenizerChar + lexemesString1[i];
                    }
                }
            }

            /*
             * ****************************************************************
             * *************** Prepare Text Contents For String2 **************
             * ******* Loops for String1_To_String2_BOW_Alignment_Table *******
             * ****************************************************************
             * **************** TBALSequenceForString2Str *********************
             * ************* TBUnalignedLexemesSequenceForString2Str **********
             * ****************************************************************
             */
            for (int i = 0; i < lexemesString2.length; i++) {
                /*
                 * Test values of first row, for every column
                 */
                if (String1_To_String2_BOW_Alignment_Table[0][i] == 1) {
                    /*
                     * Create TBALSequenceForString2Str
                     */
                    if (TBALSequenceForString2Str.equals("")) {
                        /*
                         * Case for first lexeme, add proper lexeme
                         */
                        TBALSequenceForString2Str += lexemesString2[i];

                    } else {
                        /*
                         * Case for rest of lexemes
                         */
                        TBALSequenceForString2Str += tokenizerChar + lexemesString2[i];
                    }

                } else if (String1_To_String2_BOW_Alignment_Table[0][i] == 0) {
                    /*
                     * Create TBUnalignedLexemesSequenceForString2Str
                     */
                    if (TBUnalignedLexemesSequenceForString2Str.equals("")) {
                        /*
                         * Case for first lexeme, add proper lexeme
                         */
                        TBUnalignedLexemesSequenceForString2Str += lexemesString2[i];

                    } else {
                        /*
                         * Case for rest of lexemes
                         */
                        TBUnalignedLexemesSequenceForString2Str += tokenizerChar + lexemesString2[i];
                    }
                }
            }


            /*
             * Set text-contents for 
             * 
             * TBALSequenceForString1Element and TBALSequenceForString2Element
             *
             * TBUnalignedLexemesSequenceForString1Str, TBUnalignedLexemesSequenceForString2Str
             */
            TBALSequenceForString1Element.setTextContent(TBALSequenceForString1Str);
            TBALSequenceForString2Element.setTextContent(TBALSequenceForString2Str);
            TBUnalignedLexemesSequenceForString1Element.setTextContent(TBUnalignedLexemesSequenceForString1Str);
            TBUnalignedLexemesSequenceForString2Element.setTextContent(TBUnalignedLexemesSequenceForString2Str);

            /*
             * Add
             *
             * TBALSequenceForString1Element and TBALSequenceForString2Element,
             *
             * TBUnalignedLexemesSequenceForString1Element, TBUnalignedLexemesSequenceForString2Element
             * 
             * to tokenBasedAlignedLexemesBase
             */
            tokenBasedAlignedLexemesBase.appendChild(TBALSequenceForString1Element);
            tokenBasedAlignedLexemesBase.appendChild(TBALSequenceForString2Element);
            tokenBasedAlignedLexemesBase.appendChild(TBUnalignedLexemesSequenceForString1Element);
            tokenBasedAlignedLexemesBase.appendChild(TBUnalignedLexemesSequenceForString2Element);


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
    private void addLexemeBasedAlignedLexemes(Document doc, Element lexemeBasedAlignedLexemesBase,
            String[] lexemesString1,
            String[] lexemesString2) throws Exception {
        /*
         * Local Declarations
         */
        Element LBAL,
                LBALSequenceForString1Element,
                LBALSequenceForString2Element,
                LBAL_Based_BOCW_Element,
                LBUnalignedLexemesSequenceForString1Element,
                LBUnalignedLexemesSequenceForString2Element;

        String tokenForString1Lexeme,
                tokenForString2Lexeme,
                firstTwoCharactersOfPosOfString1Lexeme,
                firstTwoCharactersOfPosOfString2Lexeme,
                interLexemeSeparatorCharacter,
                tokenizerChar,
                LBALSequenceForString1Str = "",
                LBALSequenceForString2Str = "",
                LBAL_Based_BOCW_Str = "",
                LBUnalignedLexemesSequenceForString1Str = "",
                LBUnalignedLexemesSequenceForString2Str = "";

        int BOW_Alignment_Table[][],
                String1_To_String2_BOW_Alignment_Table[][],
                String2_To_String1_BOW_Alignment_Table[][];

        /*
         * This alignment uses the guidelines of 20/10/2010 discussion, that has
         * a printed version as well.
         *
         * The procedure is as
         *
         * Separate
         *
         * String1_To_String2_BOW_Alignment_Table[][], String2_To_String1_BOW_Alignment_Table[][]
         *
         * LBALSequenceForString1Str, LBALSequenceForString2Str
         *
         * LBUnalignedLexemesSequenceForString1Str, LBUnalignedLexemesSequenceForString2Str
         *
         * ---------------------
         *
         * Separate Processing steps for lexemesString1 and lexemesString2
         *
         * 1. Simple String Matching
         *
         * 2. Synonym Matching
         *
         * 3. Standard Semantic Similarity Matching
         *
         * --------------------
         * 
         * 
         * Creates following structure
         *
         * //<LexemeBasedAlignedLexemes>
         *              <LBAL String1LexemeId="0" String2LexemeId="0" AlignmentType="SimpleMatch/SynonymMatch/SemanticSimilarityMatch" />
         *              <LBAL String1LexemeId="1" String2LexemeId="2" AlignmentType="SimpleMatch/SynonymMatch/SemanticSimilarityMatch" />
         *
         *              <LBALSequenceForString1>
         *                  token0_POS token1_POS
         *              </LBALSequenceForString1>
         *
         *              <LBALSequenceForString2>
         *                  token0_POS token2_POS
         *              </LBALSequenceForString2>          
         *
         *              <LBUnalignedLexemesSequenceForString1>
         *                  token0_POS token2_POS
         *              </LBUnalignedLexemesSequenceForString1>
         *
         *              <LBUnalignedLexemesSequenceForString2>
         *                  token0_POS token2_POS
         *              </LBUnalignedLexemesSequenceForString2>
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
             * Create nodes for
             *
             * <LBALSequenceForString1>, <LBALSequenceForString2>
             *
             * <LBUnalignedLexemesSequenceForString1>, <LBUnalignedLexemesSequenceForString2>
             */
            LBALSequenceForString1Element = doc.createElement("LBALSequenceForString1");
            LBALSequenceForString2Element = doc.createElement("LBALSequenceForString2");
            LBUnalignedLexemesSequenceForString1Element = doc.createElement("LBUnalignedLexemesSequenceForString1");
            LBUnalignedLexemesSequenceForString2Element = doc.createElement("LBUnalignedLexemesSequenceForString2");

            /*
             * Create <LBALBasedBOCW> node
             */
            LBAL_Based_BOCW_Element = doc.createElement("LBALBasedBOCW");

            /*
             * Initialize String1_To_String2_BOW_Alignment_Table, String2_To_String1_BOW_Alignment_Table
             */
            String1_To_String2_BOW_Alignment_Table = new int[lexemesString1.length][lexemesString2.length];
            String2_To_String1_BOW_Alignment_Table = new int[lexemesString2.length][lexemesString1.length];

            /*
             * ****************************************************************
             * ******* Loops for String1_To_String2_BOW_Alignment_Table *******
             * *** which in fact produces String2 Alignment and Unalignment ***
             * ****************************************************************
             */

            try {

                /*
                 * Loop
                 *
                 * Simple String Similarity Match FOR
                 *
                 * String1_To_String2_BOW_Alignment_Table
                 *
                 */
                for (int i = 0; i < lexemesString1.length; i++) {
                    /*
                     * Get 'tokenForString1Lexeme' from lexemeString1[i]
                     */
                    tokenForString1Lexeme = lexemesString1[i].substring(0,
                            lexemesString1[i].indexOf(interLexemeSeparatorCharacter));

               try
               {
                    /*
                     * Get 'First Two Characters Of Pos Of String1 Lexeme'
                     */
                    firstTwoCharactersOfPosOfString1Lexeme = lexemesString1[i].substring(lexemesString1[i].indexOf(interLexemeSeparatorCharacter) + 1, lexemesString1[i].indexOf(interLexemeSeparatorCharacter) + 3);
                }
               catch(IndexOutOfBoundsException e){
                    firstTwoCharactersOfPosOfString1Lexeme = lexemesString1[i].substring(lexemesString1[i].indexOf(interLexemeSeparatorCharacter) + 1, lexemesString1[i].indexOf(interLexemeSeparatorCharacter) + 2);
                }

                    /*
                     * Loop through string2
                     */
                    for (int j = 0; j < lexemesString2.length; j++) {
                        /*
                         * Get 'tokenForString2Lexeme' from lexemeString2[j]
                         */
                        tokenForString2Lexeme = lexemesString2[j].substring(0,
                                lexemesString2[j].indexOf(interLexemeSeparatorCharacter));
                        try {
                        /*
                         * Get 'First Two Characters Of Pos Of String2 Lexeme'
                         */
                        firstTwoCharactersOfPosOfString2Lexeme = lexemesString2[j].substring(lexemesString2[j].indexOf(interLexemeSeparatorCharacter) + 1, lexemesString2[j].indexOf(interLexemeSeparatorCharacter) + 3);
                         }
                        catch(IndexOutOfBoundsException e) {
                        firstTwoCharactersOfPosOfString2Lexeme = lexemesString2[j].substring(lexemesString2[j].indexOf(interLexemeSeparatorCharacter) + 1, lexemesString2[j].indexOf(interLexemeSeparatorCharacter) + 2);
                        }

                        /*
                         * Check If
                         *
                         * tokenForString1Lexeme and tokenForString2Lexeme, are equal
                         *
                         * and firstTwoCharactersOfPosOfString1Lexeme  equals firstTwoCharactersOfPosOfString2Lexeme
                         *
                         * and String1_To_String2_BOW_Alignment_Table is zero... Means an alignment has not been done.
                         *
                         * Set the jth column to 1, for all i's, means that string2 lexeme has been aligned it to...
                         *
                         */
                        if (firstTwoCharactersOfPosOfString1Lexeme.equalsIgnoreCase(firstTwoCharactersOfPosOfString2Lexeme)
                                && String1_To_String2_BOW_Alignment_Table[i][j] == 0
                                && tokenForString1Lexeme.equalsIgnoreCase(tokenForString2Lexeme)) {
                            /*
                             * Set the jth column to 1, for all i's (lexemeString1), here used as k.
                             */
                            for (int k = 0; k < lexemesString1.length; k++) {
                                String1_To_String2_BOW_Alignment_Table[k][j] = 1;
                            }

                            /*
                             * Break inner loop, because alignment has been done.
                             */
                            break;
                        } /*
                         * Check If
                         *
                         * tokenForString1Lexeme and tokenForString2Lexeme, are Synonyms
                         *
                         * and firstTwoCharactersOfPosOfString1Lexeme  equals firstTwoCharactersOfPosOfString2Lexeme
                         *
                         * and String1_To_String2_BOW_Alignment_Table is zero... Means an alignment has not been done.
                         *
                         * Set the jth column to 1, for all i's, means that string2 lexeme has been aligned it to...
                         *
                         */ else if (firstTwoCharactersOfPosOfString1Lexeme.equalsIgnoreCase(firstTwoCharactersOfPosOfString2Lexeme)
                                && String1_To_String2_BOW_Alignment_Table[i][j] == 0
                                && wordNet.areWordsSynonyms(tokenForString1Lexeme, tokenForString2Lexeme, null, true)) {
                            /*
                             * Set the jth column to 1, for all i's (lexemeString1), here used as k.
                             */
                            for (int k = 0; k < lexemesString1.length; k++) {
                                String1_To_String2_BOW_Alignment_Table[k][j] = 1;
                            }

                            /*
                             * Break inner loop, because alignment has been done.
                             */
                            break;

                        } else if (String1_To_String2_BOW_Alignment_Table[i][j] == 0
                                && tokenForString1Lexeme.equalsIgnoreCase(tokenForString2Lexeme)) {

                            System.out.println("Lexeme Based Aligned Lexemes : Lexical non-coupling on basis of POS : "
                                    + "\n\tString1 token : " + tokenForString1Lexeme
                                    + "\n\tString2 token : " + tokenForString2Lexeme
                                    + "\n\tString1 two characters POS : " + firstTwoCharactersOfPosOfString1Lexeme
                                    + "\n\tString2 two characters POS : " + firstTwoCharactersOfPosOfString2Lexeme);
                        } else if (String1_To_String2_BOW_Alignment_Table[i][j] == 0
                                && wordNet.areWordsSynonyms(tokenForString1Lexeme, tokenForString2Lexeme, null, true)) {

                            System.out.println("Lexeme Based Aligned Lexemes : Lexical non-coupling on basis of POS : "
                                    + "\n\tString1 token : " + tokenForString1Lexeme
                                    + "\n\tString2 token : " + tokenForString2Lexeme
                                    + "\n\tString1 two characters POS : " + firstTwoCharactersOfPosOfString1Lexeme
                                    + "\n\tString2 two characters POS : " + firstTwoCharactersOfPosOfString2Lexeme);
                        }

                    }
                }


                /*
                 * ****************************************************************
                 * ******* Loops for String2_To_String1_BOW_Alignment_Table *******
                 * *** which in fact produces String1 Alignment and Unalignment ***
                 * ****************************************************************
                 */
                /*
                 * Loop
                 *
                 * Simple String Similarity Match FOR
                 *
                 * String2_To_String1_BOW_Alignment_Table
                 *
                 */
                for (int i = 0; i < lexemesString2.length; i++) {
                    /*
                     * Get 'tokenForString2Lexeme' from lexemeString2[i]
                     */
                    tokenForString2Lexeme = lexemesString2[i].substring(0,
                            lexemesString2[i].indexOf(interLexemeSeparatorCharacter));

                    try {
                    /*
                     * Get 'First Two Characters Of Pos Of String2 Lexeme'
                     */
                    firstTwoCharactersOfPosOfString2Lexeme = lexemesString2[i].substring(lexemesString2[i].indexOf(interLexemeSeparatorCharacter) + 1, lexemesString2[i].indexOf(interLexemeSeparatorCharacter) + 3);
                   }
                         catch(IndexOutOfBoundsException e){
                                 firstTwoCharactersOfPosOfString2Lexeme = lexemesString2[i].substring(lexemesString2[i].indexOf(interLexemeSeparatorCharacter) + 1, lexemesString2[i].indexOf(interLexemeSeparatorCharacter) + 2);
                  }


                    /*
                     * Loop through string1
                     */
                    for (int j = 0; j < lexemesString1.length; j++) {
                        /*
                         * Get 'tokenForString1Lexeme' from lexemeString1[j]
                         */
                        tokenForString1Lexeme = lexemesString1[j].substring(0,
                                lexemesString1[j].indexOf(interLexemeSeparatorCharacter));

                        try {
                        /*
                         * Get 'First Two Characters Of Pos Of String1 Lexeme'
                         */
                        firstTwoCharactersOfPosOfString1Lexeme = lexemesString1[j].substring(lexemesString1[j].indexOf(interLexemeSeparatorCharacter) + 1, lexemesString1[j].indexOf(interLexemeSeparatorCharacter) + 3);
                         }
                         catch(IndexOutOfBoundsException e){
                                firstTwoCharactersOfPosOfString1Lexeme = lexemesString1[j].substring(lexemesString1[j].indexOf(interLexemeSeparatorCharacter) + 1, lexemesString1[j].indexOf(interLexemeSeparatorCharacter) + 2);
                        }

                        /*
                         * Check If
                         *
                         * tokenForString1Lexeme and tokenForString2Lexeme, are equal
                         *
                         * and firstTwoCharactersOfPosOfString1Lexeme  equals firstTwoCharactersOfPosOfString2Lexeme
                         *
                         * and String2_To_String1_BOW_Alignment_Table is zero... Means an alignment has not been done.
                         *
                         * Set the jth column to 1, for all i's, means that string1 lexeme has been aligned it to...
                         *
                         */
                        if (firstTwoCharactersOfPosOfString1Lexeme.equalsIgnoreCase(firstTwoCharactersOfPosOfString2Lexeme)
                                && String2_To_String1_BOW_Alignment_Table[i][j] == 0
                                && tokenForString1Lexeme.equalsIgnoreCase(tokenForString2Lexeme)) {
                            /*
                             * Set the jth column to 1, for all i's (lexemeString2), here used as k.
                             */
                            for (int k = 0; k < lexemesString2.length; k++) {
                                String2_To_String1_BOW_Alignment_Table[k][j] = 1;
                            }

                            /*
                             * Break inner loop, because alignment has been done.
                             */
                            break;
                        }

                        /*
                         * Check If
                         *
                         * tokenForString1Lexeme and tokenForString2Lexeme, are Synonyms
                         *
                         * and firstTwoCharactersOfPosOfString1Lexeme  equals firstTwoCharactersOfPosOfString2Lexeme
                         *
                         * and String2_To_String1_BOW_Alignment_Table is zero... Means an alignment has not been done.
                         *
                         * Set the jth column to 1, for all i's, means that string1 lexeme has been aligned it to...
                         *
                         */
                        if (firstTwoCharactersOfPosOfString1Lexeme.equalsIgnoreCase(firstTwoCharactersOfPosOfString2Lexeme)
                                && String2_To_String1_BOW_Alignment_Table[i][j] == 0
                                && wordNet.areWordsSynonyms(tokenForString1Lexeme, tokenForString2Lexeme, null, true)) {
                            /*
                             * Set the jth column to 1, for all i's (lexemeString2), here used as k.
                             */
                            for (int k = 0; k < lexemesString2.length; k++) {
                                String2_To_String1_BOW_Alignment_Table[k][j] = 1;
                            }

                            /*
                             * Break inner loop, because alignment has been done.
                             */
                            break;
                        } else if (String2_To_String1_BOW_Alignment_Table[i][j] == 0
                                && tokenForString1Lexeme.equalsIgnoreCase(tokenForString2Lexeme)) {

                            System.out.println("Lexeme Based Aligned Lexemes : Lexical non-coupling on basis of POS : "
                                    + "\n\tString1 token : " + tokenForString1Lexeme
                                    + "\n\tString2 token : " + tokenForString2Lexeme
                                    + "\n\tString1 two characters POS : " + firstTwoCharactersOfPosOfString1Lexeme
                                    + "\n\tString2 two characters POS : " + firstTwoCharactersOfPosOfString2Lexeme);
                        } else if (String2_To_String1_BOW_Alignment_Table[i][j] == 0
                                && wordNet.areWordsSynonyms(tokenForString1Lexeme, tokenForString2Lexeme, null, true)) {

                            System.out.println("Lexeme Based Aligned Lexemes : Lexical non-coupling on basis of POS : "
                                    + "\n\tString1 token : " + tokenForString1Lexeme
                                    + "\n\tString2 token : " + tokenForString2Lexeme
                                    + "\n\tString1 two characters POS : " + firstTwoCharactersOfPosOfString1Lexeme
                                    + "\n\tString2 two characters POS : " + firstTwoCharactersOfPosOfString2Lexeme);
                        }


                    }
                }

            } catch (IndexOutOfBoundsException e) {
                // do nothing
            }


            /*
             * ****************************************************************
             * *************** Prepare Text Contents For String1 **************
             * ******* Loops for String2_To_String1_BOW_Alignment_Table *******
             * ****************************************************************
             * **************** LBALSequenceForString1Str *********************
             * ************* LBUnalignedLexemesSequenceForString1Str **********
             * ****************************************************************
             */
            for (int i = 0; i < lexemesString1.length; i++) {
                /*
                 * Test values of first row, for every column
                 */
                if (String2_To_String1_BOW_Alignment_Table[0][i] == 1) {
                    /*
                     * Create LBALSequenceForString1Str
                     */
                    if (LBALSequenceForString1Str.equals("")) {
                        /*
                         * Case for first lexeme, add proper lexeme
                         */
                        LBALSequenceForString1Str += lexemesString1[i];

                    } else {
                        /*
                         * Case for rest of lexemes
                         */
                        LBALSequenceForString1Str += tokenizerChar + lexemesString1[i];
                    }

                } else if (String2_To_String1_BOW_Alignment_Table[0][i] == 0) {
                    /*
                     * Create LBUnalignedLexemesSequenceForString1Str
                     */
                    if (LBUnalignedLexemesSequenceForString1Str.equals("")) {
                        /*
                         * Case for first lexeme, add proper lexeme
                         */
                        LBUnalignedLexemesSequenceForString1Str += lexemesString1[i];

                    } else {
                        /*
                         * Case for rest of lexemes
                         */
                        LBUnalignedLexemesSequenceForString1Str += tokenizerChar + lexemesString1[i];
                    }
                }
            }

            /*
             * ****************************************************************
             * *************** Prepare Text Contents For String2 **************
             * ******* Loops for String1_To_String2_BOW_Alignment_Table *******
             * ****************************************************************
             * **************** LBALSequenceForString2Str *********************
             * ************* LBUnalignedLexemesSequenceForString2Str **********
             * ****************************************************************
             */
            for (int i = 0; i < lexemesString2.length; i++) {
                /*
                 * Test values of first row, for every column
                 */
                if (String1_To_String2_BOW_Alignment_Table[0][i] == 1) {
                    /*
                     * Create LBALSequenceForString2Str
                     */
                    if (LBALSequenceForString2Str.equals("")) {
                        /*
                         * Case for first lexeme, add proper lexeme
                         */
                        LBALSequenceForString2Str += lexemesString2[i];

                    } else {
                        /*
                         * Case for rest of lexemes
                         */
                        LBALSequenceForString2Str += tokenizerChar + lexemesString2[i];
                    }

                } else if (String1_To_String2_BOW_Alignment_Table[0][i] == 0) {
                    /*
                     * Create LBUnalignedLexemesSequenceForString2Str
                     */
                    if (LBUnalignedLexemesSequenceForString2Str.equals("")) {
                        /*
                         * Case for first lexeme, add proper lexeme
                         */
                        LBUnalignedLexemesSequenceForString2Str += lexemesString2[i];

                    } else {
                        /*
                         * Case for rest of lexemes
                         */
                        LBUnalignedLexemesSequenceForString2Str += tokenizerChar + lexemesString2[i];
                    }
                }
            }


            /*
             * Set text-contents for LBALSequenceForString1Element and LBALSequenceForString2Element,
             *
             * LBUnalignedLexemesSequenceForString1Element,  and, LBUnalignedLexemesSequenceForString2Element
             */
            LBALSequenceForString1Element.setTextContent(LBALSequenceForString1Str);
            LBALSequenceForString2Element.setTextContent(LBALSequenceForString2Str);
            LBUnalignedLexemesSequenceForString1Element.setTextContent(LBUnalignedLexemesSequenceForString1Str);
            LBUnalignedLexemesSequenceForString2Element.setTextContent(LBUnalignedLexemesSequenceForString2Str);

            /*
             * Add LBALSequenceForString1Element , LBALSequenceForString2Element, and
             *
             * LBUnalignedLexemesSequenceForString1Element,  and, LBUnalignedLexemesSequenceForString2Element
             *
             * to lexemeBasedAlignedLexemesBase
             */
            lexemeBasedAlignedLexemesBase.appendChild(LBALSequenceForString1Element);
            lexemeBasedAlignedLexemesBase.appendChild(LBALSequenceForString2Element);
            lexemeBasedAlignedLexemesBase.appendChild(LBUnalignedLexemesSequenceForString1Element);
            lexemeBasedAlignedLexemesBase.appendChild(LBUnalignedLexemesSequenceForString2Element);

        } catch (Exception e) {
            throw new Exception("NonMonotoneAligner : addLexemeBasedAlignedLexemes : "
                    + e + " : " + e.getMessage());
        }
    }
}
