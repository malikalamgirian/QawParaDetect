/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malikalamgirian.qaw.fyp;

import java.io.File;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

/**
 * The <code>StopWordRemover</code> class implements common stop-words list on
 * tagged file, tagged by using <code>PosTagger</code> class on
 * <code>Properties.input_XML_File_URL</code>.
 *
 *
 * @author wasif
 */
public class StopWordRemover {

    public StopWordRemover(Properties properties) throws Exception {
        /*
         * Set state of proper Properties instance field
         */
        this.properties = properties;
        /*
         * This also sets instance variable "stop_Word_Removed_XML_File_URL"
         */
        this.setStop_Word_Removed_XML_File_URL(properties);

        /*
         * Instantiate Local variables
         */
        stop_Word_List = new String[]{
                    "a",
                    "an",
                    "and",
                    "as",
                    "at",
                    "by",
                    "for",
                    "from",
                    "in",
                    "is",
                    "it",
                    "its",
                    "of",
                    "on",
                    "that",
                    "the",
                    "to"
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
    private String stop_Word_Removed_XML_File_URL;
    private String[] stop_Word_List;

    /*
     * Removes Stop-words from "TaggedString1" and "TaggedString2"
     */
    private void process() throws Exception {
        /*
         * Local Declarations
         */
        Document doc;

        Element root;

        Node pair;

        NodeList pairs,
                taggedString1,
                taggedString2;

        String taggedStringStr1,
                taggedStringStr2,
                swrTaggedString1, // 'S'top 'W'ord 'R'emoved (swr) Strings
                swrTaggedString2;


        try {
            System.out.println("StopWordRemoved File URL is : " + getStop_Word_Removed_XML_File_URL());
            System.out.println("properties.getTagged_Strings_XML_File_URL is : " + properties.getTagged_Strings_XML_File_URL());

            /*
             * 1.Get XML Document, for <code>Properties.tagged_Strings_XML_File_URL</code>
             */
            doc = XMLProcessor.getXMLDocumentForXMLFile(properties.getTagged_Strings_XML_File_URL());

            /*
             * 2. Get Document Element
             */
            root = doc.getDocumentElement();

            /*
             * 3. Select all "TaggedString1", "TaggedString2", "Pair" pairs
             */
            taggedString1 = root.getElementsByTagName("TaggedString1");
            taggedString2 = root.getElementsByTagName("TaggedString2");
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
                 * Get text content of strings
                 */
                taggedStringStr1 = taggedString1.item(i).getTextContent();
                taggedStringStr2 = taggedString2.item(i).getTextContent();

//                /*
//                 * Remove stop-words from tagged strings
//                 */
//                swrTaggedString1 = removeStopWordsFromTaggedString(taggedStringStr1, stop_Word_List);
//                swrTaggedString2 = removeStopWordsFromTaggedString(taggedStringStr2, stop_Word_List);

                /*
                 * Remove stop-words from tagged strings
                 */
                swrTaggedString1 = taggedStringStr1;
                swrTaggedString2 = taggedStringStr2;

                /*
                 * Add stop-words-removed string pair as XML
                 */
                addStopWordsRemovedPair(doc, root, pair, swrTaggedString1, swrTaggedString2);


                /*
                 * TEST
                 *
                 * Final validation testing
                 */
                if (i == 12) {
                    System.out.println("FINAL TEST : \tStopWordRemover"
                            + "\n\ttaggedString1 : " + taggedStringStr1
                            + "\n\ttaggedString2 : " + taggedStringStr2
                            + "\n\tswrString1 : " + swrTaggedString1
                            + "\n\tswrString2 : " + swrTaggedString2);
                }

                System.out.println((i + 1) + " / " + pairs.getLength() + "\n");
            }

            /*
             * 5. Transform the tree, to "tagged_Strings_XML_File_URL"
             */
            XMLProcessor.transformXML(doc, new StreamResult(new File(this.stop_Word_Removed_XML_File_URL)));

        } catch (Exception e) {
            throw new Exception("StopWordRemover : Process :"
                    + e + " : " + e.getMessage());
        }
    }

    /*
     * Helper Methods
     */
    private String getStop_Word_Removed_XML_File_URL() {
        return stop_Word_Removed_XML_File_URL;
    }

    /*
     * sets stop_Word_Removed_XML_File_URL, in this class, as well "properties" object
     */
    private void setStop_Word_Removed_XML_File_URL(Properties properties) throws Exception {
        /*
         * Local Declarations
         */
        String suffixToAdd,
                newURL;
        try {
            /*
             * add Suffix
             */
            suffixToAdd = "_swr";

            /*
             * call FileSystemManager.addSuffixToFileURL
             */
            newURL = FileSystemManager.addSuffixToFileURL(properties.getTagged_Strings_XML_File_URL(), suffixToAdd, null);

            /*
             * set instance state
             */
            stop_Word_Removed_XML_File_URL = newURL;

            /*
             * set "properties" instance field value
             */
            properties.setStop_Word_Removed_XML_File_URL(newURL);


        } catch (Exception e) {
            throw new Exception("StopWordRemover : setStop_Word_Removed_XML_File_URL : "
                    + e + " : " + e.getMessage());
        }
    }

    /*
     * The tagged string must be separated by lexemes having underscore ("_")
     * between POS "tag" and "token", format is "token_POS".
     */
    private String removeStopWordsFromTaggedString(String tagged_String_To_Remove_Stop_Words_From, String[] stop_Word_List) throws Exception {
        /*
         * Local Declarations
         */
        String tokens[],
                token,
                separatorChar,
                stop_Words_Removed_String = "";

        try {
            /*
             * set separator character
             */
            separatorChar = "_";

            /*
             * Split string into tokens based on "space" character
             */
            tokens = tagged_String_To_Remove_Stop_Words_From.split(" ");

            /*
             * Loop through 'tokens'
             * Dynamically get prefix 'token' from lexeme
             */
            for (int i = 0; i < tokens.length; i++) {
                /*
                 * get 'token' from lexeme
                 */
                token = tokens[i].substring(0, tokens[i].lastIndexOf(separatorChar));

                /*
                 * Prepare 'stop_Words_Removed_String'
                 */
                if (!isTokenStopWord(token, stop_Word_List)) {
                    /*
                     * If 'token' is not stop-word, add it to 'stop_Words_Removed_String'
                     */
                    if (stop_Words_Removed_String.equalsIgnoreCase("")) {
                        /*
                         * Case for first tokens[i]
                         */
                        stop_Words_Removed_String += tokens[i];
                    } else {
                        /*
                         * Case for other tokens[i]
                         */
                        stop_Words_Removed_String += " " + tokens[i];
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("StopWordRemover : removeStopWordsFromTaggedString : "
                    + e + " : " + e.getMessage());
        }

        return stop_Words_Removed_String;
    }

    private Boolean isTokenStopWord(String token_To_Test_For_Stop_Word, String[] stop_Word_List) throws Exception {
        /*
         * Loop through stop_Word_List[] to check if 'token_To_Test_For_Stop_Word'
         * is stop word or not
         */
        /*
         * Local Declarations
         */
        Boolean is_Token_A_Stop_Word = false;

        for (int j = 0; j < stop_Word_List.length; j++) {
            /*
             * Test
             */
            if (token_To_Test_For_Stop_Word.equalsIgnoreCase(stop_Word_List[j])) {
                is_Token_A_Stop_Word = true;
            }
        }

        return is_Token_A_Stop_Word;
    }

    private boolean addStopWordsRemovedPair(Document doc, Element root, Node pair, String swrTaggedString1, String swrTaggedString2) throws Exception {
        /*
         * Local Declarations
         */
        Element stopWordRemovedPair,
                stopWordRemovedStr1,
                stopWordRemovedStr2;
        try {
            /*
             * Creates following structure
             *
             * <SwrPair>
             *      <SwrString1></SwrString1>
             *      <SwrString2></SwrString2>
             * </SwrPair>
             */
            stopWordRemovedPair = doc.createElement("SwrPair");

            stopWordRemovedStr1 = doc.createElement("SwrString1");
            stopWordRemovedStr2 = doc.createElement("SwrString2");

            stopWordRemovedStr1.setTextContent(swrTaggedString1);
            stopWordRemovedStr2.setTextContent(swrTaggedString2);

            stopWordRemovedPair.appendChild(stopWordRemovedStr1);
            stopWordRemovedPair.appendChild(stopWordRemovedStr2);

            pair.appendChild(stopWordRemovedPair);

            root.normalize();

            return true;

        } catch (Exception e) {
            throw new Exception("StopWordRemover : addStopWordsRemovedPair :"
                    + e + " : " + e.getMessage());
        }
    }
}
