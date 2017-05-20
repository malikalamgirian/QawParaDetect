/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malikalamgirian.qaw.fyp;

import java.io.BufferedWriter;

/**
 *
 * @author wasif
 *
 * This class converts SentencePair objects to Text Dataset.
 */
public class SentencePairToTextDataset {

    private static SentencePair sentencePair[] = null;
    private static String text_File_Path_To_Save = null;

    /*
     * For text file writing
     */
    private BufferedWriter bWriter = null;

    public SentencePairToTextDataset() {
    }

    public SentencePairToTextDataset(SentencePair sentencePair[], String text_File_Path_To_Save) {
        /*
         * Sentence Pair
         */
        SentencePairToTextDataset.sentencePair = sentencePair;

        /*
         * Path to save file at
         */
        SentencePairToTextDataset.text_File_Path_To_Save = text_File_Path_To_Save;
    }

    public SentencePair[] getSentencePair() {
        return sentencePair;
    }

    public void setSentencePair(SentencePair[] sentencePair) {
        SentencePairToTextDataset.sentencePair = sentencePair;
    }

    public String getText_File_Path_To_Save() {
        return text_File_Path_To_Save;
    }

    public void setText_File_Path_To_Save(String text_File_Path_To_Save) {
        SentencePairToTextDataset.text_File_Path_To_Save = text_File_Path_To_Save;
    }

    public String convert() throws Exception {
        /*
         * this method converts Sentence Pairs to TXT file
         * and returns the address of file created
         */
        try {
            /*
             * Test main variables
             */
            if (sentencePair == null) {
                throw new Exception("sentencePair[] provided is null.");
            } else if (text_File_Path_To_Save == null) {
                throw new Exception("Path to save text file is null.");
            }

            /*
             * Create file writer
             */
            bWriter = FileSystemManager.createFile(text_File_Path_To_Save);

            /*
             * Write heading line
             */
            bWriter.write("﻿Quality	#1 ID	#2 ID	#1 String	#2 String");
            bWriter.newLine();

            /*
             * Write all Sentence Pairs
             */
            for (int i = 0; i < sentencePair.length; i++) {
                /*
                 * Write and enter a newline
                 */
                bWriter.write(sentencePair[i].toString());
                bWriter.newLine();
            }

            /*
             * Flush and Close writer
             */
            bWriter.flush();
            bWriter.close();


        } catch (Exception e) {
            throw new Exception("Could not convert Sentence Pairs to Txt file.");
        }

        return getText_File_Path_To_Save();
    }
}
