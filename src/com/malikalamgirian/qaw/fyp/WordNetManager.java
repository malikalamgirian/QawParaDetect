/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malikalamgirian.qaw.fyp;

import edu.smu.tspell.wordnet.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wasif
 */
public class WordNetManager {

    public WordNetManager() throws Exception {
        /*
         * Call Connect to Wordnet
         */
        connect();
    }

    /*
     * Declarations
     */
    private WordNetDatabase wordNetDB;

    /*
     * Connects to WordNet Dictionary
     */
    private void connect() throws Exception {

        try {
            /*
             * Set System Property, 'wordnet.database.dir' to where the database files are i.e. dict
             * WN_DB_DIR="/media/My/Wasif/MY_MCS_Data/fourthSemester/finalProject/real_Material/word_Net/WordNet-3.0/dict"
             */
            System.setProperty("wordnet.database.dir", System.getenv("WN_DB_DIR"));

            /*
             * Get instance
             */
            wordNetDB = WordNetDatabase.getFileInstance();

        } catch (Exception e) {
            throw new Exception("WordNetManager :connect :"
                    + e + " : " + e.getMessage());
        }
    }

    /*
     * Gets Synset[] by calling getSynsets on 'WordNetDatabase' instance
     */
    public Synset[] getSynsets(String token, SynsetType pos, boolean morphology) throws Exception {
        try {
            /*
             * Get sysnets and return
             */
            return wordNetDB.getSynsets(token, pos, morphology);

        } catch (Exception e) {
            throw new Exception("WordNetManager : getSynsets :"
                    + e + " : " + e.getMessage());
        }
    }

    public String[] getSynonyms(String word_To_Get_Synonyms_Of,
            String first_Two_Characters_Of_POS, boolean morphology) throws Exception {
        /*
         * Local Declarations
         */
        Synset synsets_For_Token[],
                synset;

        WordSense[] synonym_Senses;

        String word_Forms_For_Synset[],
                synonyms_To_Return[];

        List<String> synonyms_List = null;

        try {
            /*
             * Initialize list
             */
            synonyms_List = new ArrayList<String>();

            /*
             * Get synsets for token by searching WordNet
             */
            synsets_For_Token = getSynsets(word_To_Get_Synonyms_Of,
                    getPosFromFirstCharacterOfPosTag(first_Two_Characters_Of_POS), morphology);

            /*
             * Get union of synonyms
             */
            for (int i = 0; i < synsets_For_Token.length; i++) {
                /*
                 * Get synset, to find antonyms of
                 */
                synset = synsets_For_Token[i];

                /*
                 * Get synonyms based on WordSense[] for all the wordForms
                 */
                word_Forms_For_Synset = synset.getWordForms();

                /*
                 * Check for non-duplicate, and add all word forms for synset, word_Forms_For_Synset
                 */
                for (int k = 0; k < word_Forms_For_Synset.length; k++) {
                    /*
                     * Check non-duplication
                     */
                    if (!synonyms_List.contains(word_Forms_For_Synset[k])) {
                        synonyms_List.add(word_Forms_For_Synset[k]);
                    }
                }

//                for (int j = 0; j < word_Forms_For_Synset.length; j++) {
//                    /*
//                     * Get derivationally related forms for "j"th wordForm
//                     */
//                    synonym_Senses = synset.getDerivationallyRelatedForms(word_Forms_For_Synset[j]);
//
//                    /*
//                     * Add non-duplicate 'wordForm' for all 'synonym_Senses' to List
//                     */
//                    for (int k = 0; k < synonym_Senses.length; k++) {
//                        /*
//                         * Check non-duplication
//                         */
//                        if (!synonyms_List.contains(synonym_Senses[k].getWordForm())) {
//                            synonyms_List.add(synonym_Senses[k].getWordForm());
//                        }
//                    }
//                }
            }

            /*
             * Convert to String array
             */
            synonyms_To_Return = new String[synonyms_List.size()];
            synonyms_List.toArray(synonyms_To_Return);

        } catch (Exception e) {
            throw new Exception("WordNetManager : getSynonyms :"
                    + e + " : " + e.getMessage());
        }

//        System.out.println("Synonyms of " + word_To_Get_Synonyms_Of + " : \n\t" + synonyms_List.toString());

        return synonyms_To_Return;
    }

    /*
     * IS this method working properly
     */
    public String[] getAntonyms(String word_To_Get_Antonyms_Of,
            String first_Two_Characters_Of_POS, boolean applyMorphology) throws Exception {
        /*
         * Local Declarations
         */
        Synset synsets_For_Token[],
                synset;

        WordSense[] antonym_Senses;

        String word_Forms_For_Synset[],
                antonyms_To_Return[];

        List<String> antonyms_List = null;

        try {
            /*
             * Initialize list
             */
            antonyms_List = new ArrayList<String>();

            /*
             * Get synsets for token by searching WordNet
             */
            synsets_For_Token = getSynsets(word_To_Get_Antonyms_Of,
                    getPosFromFirstCharacterOfPosTag(first_Two_Characters_Of_POS), applyMorphology);

            /*
             * Get union of antonyms of 'All Senses'
             *
             * Following has been dropped:
             * --- Get union of antonyms of 'first three senses' only
             * --- Because sometimes required senses were being missed out
             * --- for (int i = 0; i < synsets_For_Token.length && i < 3; i++)
             */
            for (int i = 0; i < synsets_For_Token.length; i++) {
                /*
                 * Get synset, to find antonyms of
                 */
                synset = synsets_For_Token[i];

                /*
                 * Get antonyms based on WordSense[] for all the wordForms
                 */
                word_Forms_For_Synset = synset.getWordForms();

                for (int j = 0; j < word_Forms_For_Synset.length; j++) {
                    /*
                     * Get antonyms for "j"th wordForm
                     */
                    antonym_Senses = synset.getAntonyms(word_Forms_For_Synset[j]);

                    /*
                     * Add non-duplicate 'wordForm' for all 'antonym_Senses' to List
                     */
                    for (int k = 0; k < antonym_Senses.length; k++) {
                        /*
                         * Check non-duplication
                         */
                        if (!antonyms_List.contains(antonym_Senses[k].getWordForm())) {
                            antonyms_List.add(antonym_Senses[k].getWordForm());
                        }
                    }
                }
            }

            /*
             * Convert to String array
             */
            antonyms_To_Return = new String[antonyms_List.size()];
            antonyms_List.toArray(antonyms_To_Return);

        } catch (Exception e) {
            throw new Exception("WordNetManager : getAntonyms :"
                    + e + " : " + e.getMessage());
        }

//        System.out.println("\nAntonyms of " + word_To_Get_Antonyms_Of + " : \n\t" + antonyms_List.toString());

        return antonyms_To_Return;
    }

    public SynsetType getPosFromFirstCharacterOfPosTag(String PosTag) throws Exception {
        /*
         * Local Declarations
         */
        SynsetType posToReturn = null;

        try {
            if (PosTag == null) {
                return posToReturn;
            } else {

                if (PosTag.startsWith("n") || PosTag.startsWith("N")) {
                    posToReturn = SynsetType.NOUN;
                } else if (PosTag.startsWith("v") || PosTag.startsWith("V")) {
                    posToReturn = SynsetType.VERB;
                } else if (PosTag.startsWith("j") || PosTag.startsWith("J")) {
                    posToReturn = SynsetType.ADJECTIVE;
                } else if (PosTag.startsWith("r") || PosTag.startsWith("R")) {
                    posToReturn = SynsetType.ADVERB;
                }
            }

            return posToReturn;

        } catch (Exception e) {
            throw new Exception("WordNetManager : getPosFromFirstCharacterOfPosTag :"
                    + e + " : " + e.getMessage());
        }
    }

    private String getLemma(String word_To_Get_Lemma_Of) throws Exception {
        /*
         * Local Declarations
         */

        try {

            return "";

        } catch (Exception e) {
            throw new Exception("WordNetManager : getLemma :"
                    + e + " : " + e.getMessage());
        }
    }

    /*
     * This method extends getAntonyms
     */
    public String[] getExtendedAntonyms(String word_To_Get_Antonyms_Of,
            String first_Two_Characters_Of_POS, boolean applyMorphology) throws Exception {
        /*
         * Local Declarations
         */
        Synset synsets_For_Token[],
                synset_For_Derivationally_Related_Word_Sense,
                synset;

        WordSense[] antonym_Senses,
                derivationally_Related_Senses;

        String word_Forms_For_Synset[],
                word_Forms_For_Derivationally_Related_Synset[],
                antonyms_To_Return[];

        List<String> antonyms_List = null;

        /*
         * Antonym Detection Process
         *
         * 1. getSynsets for "word_To_Get_Antonyms_Of"
         * 2. foreach "Synset"
         *    --- 2.1 WordSense[] getDerivationallyRelatedForms()
         *    ------- 2.1.1 foreach "WordSense"
         *    ------------- 2.1.1.1 Synset getSynset()
         *    --------------------- 2.1.1.1.1 foreach "Synset"
         *    ------------------------------- 2.1.1.1.1.1 String[] getWordForms()
         *    ------------------------------- 2.1.1.1.1.2 WordSense[] getAntonyms(String wordForm)
         *    ------------------------------------------- 2.1.1.1.1.2.1 foreach "WordForm"
         *    --------------------------------------------------------- 2.1.1.1.1.2.1.1 String getWordForm()
         *    --------------------------------------------------------- 2.1.1.1.1.2.1.2 Check and add "String" to antonyms_List
         *
         */

        try {
            /*
             * Initialize list
             */
            antonyms_List = new ArrayList<String>();

            /*
             * Get synsets for token by searching WordNet
             */
            synsets_For_Token = getSynsets(word_To_Get_Antonyms_Of,
                    getPosFromFirstCharacterOfPosTag(first_Two_Characters_Of_POS), applyMorphology);

            /*
             * Testing
             */
            //System.out.println("\nsynsets_For_Token.length : " + synsets_For_Token.length);

            /*
             * Get union of antonyms of all senses
             */
            for (int i = 0; i < synsets_For_Token.length; i++) {
                /*
                 * Get synset, to find antonyms of
                 */
                synset = synsets_For_Token[i];

                /*
                 * Get antonyms based on WordSense[] for all the wordForms
                 */
                word_Forms_For_Synset = synset.getWordForms();

                /*
                 * Test
                 */
                //System.out.println("\nsynsets_For_Token[ " + i + " ].toString() : " + synsets_For_Token[i].toString());

                /*
                 * foreach "String" word form, of "synset"
                 */
                for (int j = 0; j < word_Forms_For_Synset.length; j++) {

                    /*
                     * Get "Derivationally Related Senses" from "word form"
                     */
                    derivationally_Related_Senses = synset.getDerivationallyRelatedForms(word_Forms_For_Synset[j]);

                    /*
                     * Test
                     */
                    //System.out.println("\tderivationally_Related_Senses.length : " + derivationally_Related_Senses.length);

                    /*
                     * foreach "Derivationally Related Sense"
                     */
                    for (int k = 0; k < derivationally_Related_Senses.length; k++) {
                        /*
                         * Get "synset_For_Derivationally_Related_Word_Sense"
                         */
                        synset_For_Derivationally_Related_Word_Sense = derivationally_Related_Senses[k].getSynset();

                        /*
                         * Get "word_Forms_For_Derivationally_Related_Synset[]" from "synset_For_Derivationally_Related_Word_Sense"
                         * "l" here!
                         */
                        word_Forms_For_Derivationally_Related_Synset = synset_For_Derivationally_Related_Word_Sense.getWordForms();

                        /*
                         * Loop through "word_Forms_For_Derivationally_Related_Synset[]"  to get "antonym_Senses"
                         */
                        for (int l = 0; l < word_Forms_For_Derivationally_Related_Synset.length; l++) {
                            /*
                             * Get "antonym_Senses" for "synset_For_Derivationally_Related_Word_Sense" using "word_Forms_For_Derivationally_Related_Synset"
                             */
                            antonym_Senses = synset_For_Derivationally_Related_Word_Sense.getAntonyms(word_Forms_For_Derivationally_Related_Synset[l]);

                            /*
                             * Test
                             */
                            //System.out.println("\tword_Forms_For_Derivationally_Related_Synset[ " + l + " ] : "  + word_Forms_For_Derivationally_Related_Synset[l]);
                            //System.out.println("\tantonym_Senses.length : " + antonym_Senses.length );

                            /*
                             * Add non-duplicate 'wordForm' for all 'antonym_Senses' to List
                             */
                            for (int m = 0; m < antonym_Senses.length; m++) {
                                /*
                                 * Test
                                 */
                                //System.out.println("\tantonym_Senses[ " + m + " ].getWordForm() : " + antonym_Senses[m].getWordForm());

                                /*
                                 * Check non-duplication
                                 */
                                if (!antonyms_List.contains(antonym_Senses[m].getWordForm())) {
                                    antonyms_List.add(antonym_Senses[m].getWordForm());
                                }
                            }
                        }
                    }
                }
            }

            /*
             * Convert to String array
             */
            antonyms_To_Return = new String[antonyms_List.size()];
            antonyms_List.toArray(antonyms_To_Return);

        } catch (Exception e) {
            throw new Exception("WordNetManager : getExtendedAntonyms :"
                    + e + " : " + e.getMessage());
        }

//        System.out.println("\nExtended Antonyms of " + word_To_Get_Antonyms_Of + " : \n\t" + antonyms_List.toString());

        return antonyms_To_Return;
    }

    public Boolean areWordsSynonyms(String word1, String word2,
            String first_Two_Characters_Of_POS, boolean applyMorphology) throws Exception {
        /*
         * Local Declarations
         */
        boolean areWordsSynonyms = false;

        String[] synomyms_For_Word1,
                synomyms_For_Word2;

        try {
            /*
             * This will check for synonyms in bidirections,
             * so there are two rounds
             */
            /*
             * Get synonyms of word1
             */
            synomyms_For_Word1 = getSynonyms(word1, first_Two_Characters_Of_POS, applyMorphology);

            /*
             * Loop and Compare
             */
            for (int i = 0; i < synomyms_For_Word1.length; i++) {
                /*
                 * If match found return
                 */
                if (synomyms_For_Word1[i].equalsIgnoreCase(word2)) {
                    /*
                     * Synonym found : return true
                     */
                    System.out.println("\n\nSynonym Match Found : " + word1 + " :: " + word2 + "\n");

                    return true;
                }
            }

            /*
             * Get synonyms of word2
             */
            synomyms_For_Word2 = getSynonyms(word2, first_Two_Characters_Of_POS, applyMorphology);

            /*
             * Loop and Compare
             */
            for (int i = 0; i < synomyms_For_Word2.length; i++) {
                /*
                 * If match found return
                 */
                if (synomyms_For_Word2[i].equalsIgnoreCase(word1)) {
                    /*
                     * Synonym found : return true
                     */
                    System.out.println("\n\nSynonym Match Found : " + word1 + " :: " + word2 + "\n");

                    return true;
                }
            }

            /*
             * Check Match in, synomyms_For_Word1[], synomyms_For_Word2[]
             */
            for (int i = 0; i < synomyms_For_Word1.length; i++) {
                /*
                 * Inner Loop
                 */
                for (int j = 0; j < synomyms_For_Word2.length; j++) {
                    /*
                     * If match found return
                     */
                    if (synomyms_For_Word1[i].equalsIgnoreCase(synomyms_For_Word2[j])) {
                        /*
                         * Synonym found : return true
                         */
                        System.out.println("\n\nSynonym Match Found : Indirectly : "
                                + word1 + " :: " + word2 + " ::::>> " + synomyms_For_Word1[i] +  "\n");

                        return true;
                    }

                }


            }

        } catch (Exception e) {
            throw new Exception("WordNetManager : areWordsSynonyms :"
                    + e + " : " + e.getMessage());
        }

        return areWordsSynonyms;
    }
}
