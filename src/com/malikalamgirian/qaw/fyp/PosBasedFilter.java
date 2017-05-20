/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malikalamgirian.qaw.fyp;

import java.io.File;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

/**
 * The <code>PosBasedFilter</code> class applies POS constraint on stop-word-removed
 * file. Where it requires <code>Properties</code> instance, with
 * <code>Properties.stop_Word_Removed_XML_File_URL</code> set properly beforehand.
 *
 * @author wasif
 */
public class PosBasedFilter {

    public PosBasedFilter(Properties properties) throws Exception {
        /*
         * Set state of proper Properties instance field
         */
        this.properties = properties;
        /*
         * This also sets instance variable "pos_Constraint_Applied_File_URL"
         */
        this.setPos_Constraint_Applied_File_URL(properties);

        /*
         * Instantiate Local variables
         */
        pos_To_Filter_Tag_Set = new String[]{
                    /* Verb sub-classes */
                    "VB",
                    "VBD",
                    "VBG",
                    "VBN",
                    "VBP",
                    "VBZ",
                    /* Noun sub-classes */
                    "NN",
                    "NNP",
                    "NNPS",
                    "NNS",
                    /* Adjective sub-classes */
                    "JJ",
                    "JJR",
                    "JJS",
                    /* Adverb sub-classes */
                    "RB",
                    "RBR",
                    "RBS",
                    "WRB"
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
    private String pos_Constraint_Applied_File_URL;
    private String[] pos_To_Filter_Tag_Set;

    /*
     * Applies PosBasedFilter to "SwrString1" and "SwrString2"
     */
    private void process() throws Exception {
        /*
         * Local Declarations
         */
        Document doc;

        Element root;

        Node pair;

        NodeList pairs,
                swrString1,
                swrString2;

        String swrStringStr1,
                swrStringStr2,
                posFilteredString1,
                posFilteredString2;

        try {
            System.out.println("PosFilter File URL is : " + getPos_Constraint_Applied_File_URL());
            System.out.println("properties.getStop_Word_Removed_XML_File_URL is : " + properties.getStop_Word_Removed_XML_File_URL());

            /*
             * 1.Get XML Document, for <code>properties.getStop_Word_Removed_XML_File_URL()</code>
             */
            doc = XMLProcessor.getXMLDocumentForXMLFile(properties.getStop_Word_Removed_XML_File_URL());

            /*
             * 2. Get Document Element
             */
            root = doc.getDocumentElement();

            /*
             * 3. Select all "SwrString1", "SwrString2", "Pair" pairs
             */
            swrString1 = root.getElementsByTagName("SwrString1");
            swrString2 = root.getElementsByTagName("SwrString2");
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
                swrStringStr1 = swrString1.item(i).getTextContent();
                swrStringStr2 = swrString2.item(i).getTextContent();

//                /*
//                 * Apply POS Constraint on Strings, 'swrStringStr1' and 'swrStringStr2'
//                 */
//                posFilteredString1 = applyPosConstraintOnSwrString(swrStringStr1, pos_To_Filter_Tag_Set);
//                posFilteredString2 = applyPosConstraintOnSwrString(swrStringStr2, pos_To_Filter_Tag_Set);

                /*
                 * Apply POS Constraint on Strings, 'swrStringStr1' and 'swrStringStr2'
                 */
                posFilteredString1 = swrStringStr1;
                posFilteredString2 = swrStringStr2;


                /*
                 * Add POS Constraint applied string pair as XML
                 */
                addPosConstraintAppliedPair(doc, root, pair, posFilteredString1, posFilteredString2);

                /*
                 * TEST
                 *
                 * Final validation testing
                 */
                if (i == 12) {
                    System.out.println("FINAL TEST : \tPosBasedFilter"
                            + "\n\tswrString1 : " + swrStringStr1
                            + "\n\tswrString2 : " + swrStringStr2
                            + "\n\tposFilteredString1 : " + posFilteredString1
                            + "\n\tposFilteredString2 : " + posFilteredString2);
                }

                System.out.println((i + 1) + " / " + pairs.getLength() + "\n");
            }

            /*
             * 5. Transform the tree, to "pos_Constraint_Applied_File_URL"
             */
            XMLProcessor.transformXML(doc, new StreamResult(new File(this.pos_Constraint_Applied_File_URL)));


        } catch (Exception e) {
            throw new Exception("PosBasedFilter : Process :"
                    + e + " : " + e.getMessage());
        }
    }

    /*
     * Helper Methods
     */
    private String getPos_Constraint_Applied_File_URL() {
        return pos_Constraint_Applied_File_URL;
    }

    private void setPos_Constraint_Applied_File_URL(Properties properties) throws Exception {
        /*
         * Local Declarations
         */
        String suffixToAdd,
                newURL;
        try {
            /*
             * add Suffix
             */
            suffixToAdd = "_filtered";

            /*
             * call FileSystemManager.addSuffixToFileURL
             */
            newURL = FileSystemManager.addSuffixToFileURL(properties.getStop_Word_Removed_XML_File_URL(), suffixToAdd, null);

            /*
             * set instance state
             */
            pos_Constraint_Applied_File_URL = newURL;

            /*
             * set "properties" instance field value
             */
            properties.setPos_Constraint_Applied_File_URL(newURL);

        } catch (Exception e) {
            throw new Exception("PosBasedFilter : setPos_Constraint_Applied_File_URL : "
                    + e + " : " + e.getMessage());
        }
    }

    /*
     * The 'stop-word-removed' string must be separated by lexemes having underscore ("_")
     * between POS "tag" and "token", format is "token_POS".
     */
    private String applyPosConstraintOnSwrString(String stop_Words_Removed_String_To_Apply_POS_Constraint_On, String[] pos_To_Filter_Tag_Set) throws Exception {
        /*
         * Local Declarations
         */
        String tokens[],
                POS,
                separatorChar,
                pos_Constraint_Applied_String = "";

        try {
            /*
             * set separator character
             */
            separatorChar = "_";

            /*
             * Split string into tokens based on "space" character
             */
            tokens = stop_Words_Removed_String_To_Apply_POS_Constraint_On.split(" ");

            /*
             * Loop through 'tokens'
             * Dynamically get suffix 'POS' from lexeme
             */
            for (int i = 0; i < tokens.length; i++) {
                /*
                 * get 'POS' from lexeme
                 */
                POS = tokens[i].substring(tokens[i].lastIndexOf(separatorChar) + 1, tokens[i].length());

                /*
                 * Prepare 'pos_Constraint_Applied_String'                 
                 *
                 * !tokens[i].startsWith("-LRB") to fix '('
                 */
                if ((isPosToBeConstrained(POS, pos_To_Filter_Tag_Set)) && !tokens[i].startsWith("-LRB")) {
                    /*
                     * If 'POS' is to be constrained, means POS is also is in pos_To_Filter_Tag_Set
                     * Add tokens[i] to 'pos_Constraint_Applied_String'
                     */
                    if (pos_Constraint_Applied_String.equalsIgnoreCase("")) {
                        /*
                         * Case for first tokens[1]
                         */
                        pos_Constraint_Applied_String += tokens[i];
                    } else {
                        /*
                         * Case for other tokens[i]
                         */
                        pos_Constraint_Applied_String += " " + tokens[i];
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("PosBasedFilter : applyPosConstraintOnSwrString : "
                    + e + " : " + e.getMessage());
        }

        return pos_Constraint_Applied_String;
    }

    private boolean isPosToBeConstrained(String pos_To_Test_For_Pos_Constraint, String[] pos_To_Filter_Tag_Set) {
        /*
         * Loop through pos_To_Filter_Tag_Set[] to check if 'pos_To_Test_For_Pos_Constraint'
         * is to be constrained or not
         */
        /*
         * Local Declarations
         */
        Boolean is_Token_To_Be_Constrained = false;

        for (int j = 0; j < pos_To_Filter_Tag_Set.length; j++) {
            /*
             * Test
             */
            if (pos_To_Test_For_Pos_Constraint.equalsIgnoreCase(pos_To_Filter_Tag_Set[j])) {
                is_Token_To_Be_Constrained = true;

                /*
                 * break
                 */
                break;
            }
        }

        return is_Token_To_Be_Constrained;
    }

    private boolean addPosConstraintAppliedPair(Document doc, Element root, Node pair, String posFilteredString1, String posFilteredString2) throws Exception {
        /*
         * Local Declarations
         */
        Element posFilteredPair,
                posFilteredStr1,
                posFilteredStr2;
        try {
            /*
             * Creates following structure
             *
             * <PosFilteredPair>
             *      <PosFilteredString1></PosFilteredString1>
             *      <PosFilteredString2></PosFilteredString2>
             * </PosFilteredPair>
             */
            posFilteredPair = doc.createElement("PosFilteredPair");

            posFilteredStr1 = doc.createElement("PosFilteredString1");
            posFilteredStr2 = doc.createElement("PosFilteredString2");

            /*
             * Create 'Content Vectors' for simple strings
             * Replace all Spaces (" ") [with comma (",")]
             */
            posFilteredString1 = posFilteredString1.replaceAll(" ", " ").replaceAll("_", "/");
            posFilteredString2 = posFilteredString2.replaceAll(" ", " ").replaceAll("_", "/");

            posFilteredStr1.setTextContent(posFilteredString1);
            posFilteredStr2.setTextContent(posFilteredString2);

            posFilteredPair.appendChild(posFilteredStr1);
            posFilteredPair.appendChild(posFilteredStr2);

            pair.appendChild(posFilteredPair);

            root.normalize();

            return true;

        } catch (Exception e) {
            throw new Exception("PosBasedFilter : addPosConstraintAppliedPair :"
                    + e + " : " + e.getMessage());
        }
    }
}
