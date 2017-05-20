/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malikalamgirian.qaw.fyp;

import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 *
 * @author wasif
 */
public class CsvToArff {

    public CsvToArff(String input_CSV_File_Path_Name) throws Exception {
        /*
         * Set state variable
         */
        this.input_CSV_File_Path_Name = input_CSV_File_Path_Name;

        /*
         * Set output_ARFF_File_Path_Name
         */
        this.setOutput_ARFF_File_Path_Name(input_CSV_File_Path_Name);

        /*
         * Call Process
         */
        process();
    }

    /*
     * Declarations
     */
    private String input_CSV_File_Path_Name;
    private String output_ARFF_File_Path_Name;

    private void process() throws Exception {
        /*
         * Local Declarations
         */
        BufferedReader bReader;
        BufferedWriter bWriter;
        String line_Read = null,
                line_To_Write = null,
                attribute_Names[],
                attribute_Separator_Char;

        try {
            System.out.println("Input file : " + input_CSV_File_Path_Name);
            System.out.println("Output file :" + output_ARFF_File_Path_Name);

            /*
             * Set attribute_Separator_Char
             */
            attribute_Separator_Char = ",";

            /*
             * Get Reader
             */
            bReader = FileSystemManager.readFile(input_CSV_File_Path_Name);

            /*
             * Get Writer
             */
            bWriter = FileSystemManager.createFile(output_ARFF_File_Path_Name);

            /*
             * Perform Reading and Writing
             *
             * 1. Add @relation file_Name_Without_Extension
             * 2. Add @attribute att_Name att_Type
             * 3. Add @data
             *    Instances
             */

            /*
             * 1. Add @relation file_Name_Without_Extension
             */
            bWriter.write("@relation " + FileSystemManager.getFileNameWithoutExtension(this.input_CSV_File_Path_Name));

            /*
             * Insert NewLine
             */
            bWriter.newLine();
            bWriter.newLine();

            /*
             * 2. Add @attribute att_Name att_Type
             */
            line_Read = bReader.readLine();
            attribute_Names = line_Read.split(attribute_Separator_Char);

            for (int i = 0; i < attribute_Names.length; i++) {
                /*
                 * Set @attribute
                 */
                line_To_Write = "@attribute ";

                /*
                 * Set attribute Name
                 */
                line_To_Write += attribute_Names[i] + " ";

                /*
                 * Set attribute type
                 */
                line_To_Write += getAttributeTypeText(attribute_Names[i]);

                /*
                 * Write line
                 */
                bWriter.write(line_To_Write);

                /*
                 * Insert NewLine
                 */
                bWriter.newLine();

            }

            /*
             * Insert NewLine
             */
            bWriter.newLine();
            bWriter.newLine();

            /*
             * 3. Add @data
             */
            line_To_Write = "@data";
            bWriter.write(line_To_Write);

            /*
             * Insert NewLine
             */
            bWriter.newLine();
            bWriter.newLine();

            /*
             * add all instances
             */
            while ((line_Read = bReader.readLine()) != null) {
                /*
                 * Write Instance
                 */
                bWriter.write(line_Read);

                /*
                 * Insert NewLine
                 */
                bWriter.newLine();
            }

            /*
             * Insert NewLine
             */
            bWriter.newLine();

            /*
             * Flush Writer
             */
            bWriter.flush();

            /*
             * Close Reader and Writer
             */
            bReader.close();
            bWriter.close();

        } catch (Exception e) {
            throw new Exception("CsvToArff : process : "
                    + e + " : " + e.getMessage());
        }
    }


    /*
     * Helper Methods
     */
    public String getOutput_ARFF_File_Path_Name() {
        return output_ARFF_File_Path_Name;
    }

    private void setOutput_ARFF_File_Path_Name(String input_CSV_File_Path_Name) throws Exception {
        /*
         * Local Declarations
         */
        String suffixToAdd,
                newURL;
        try {
            /*
             * add Suffix, "" for "None"
             */
            suffixToAdd = "";

            /*
             * call FileSystemManager.addSuffixToFileURL
             */
            newURL = FileSystemManager.addSuffixToFileURL(this.input_CSV_File_Path_Name, suffixToAdd, "arff");

            /*
             * Set value
             */
            this.output_ARFF_File_Path_Name = newURL;

        } catch (Exception e) {
            throw new Exception("CsvToArff : setOutput_ARFF_File_Path_Name : "
                    + e + " : " + e.getMessage());
        }
    }

    private String getAttributeTypeText(String attribute_Name) throws Exception {
        /*
         * Local Declarations
         */
        String attribute_Type_Text_To_Return = null;

        try {
            if (attribute_Name.equalsIgnoreCase("String1Id")
                    || attribute_Name.equalsIgnoreCase("String2Id")                                     
                    || attribute_Name.equalsIgnoreCase("LcsOfCharactersBasedCosineSimilarity")
                    || attribute_Name.equalsIgnoreCase("LcsOfLexemesBasedCosineSimilarity")
                    || attribute_Name.equalsIgnoreCase("LcsBasedOnTokensBasedCosineSimilarity")                    
                    || attribute_Name.equalsIgnoreCase("LcsOfCharactersBasedOverlapCoefficient")
                    || attribute_Name.equalsIgnoreCase("LcsOfLexemesBasedOverlapCoefficient")
                    || attribute_Name.equalsIgnoreCase("LcsBasedOnTokensBasedOverlapCoefficient")
                    || attribute_Name.equalsIgnoreCase("LcsOfCharactersBasedDiceCoefficient")
                    || attribute_Name.equalsIgnoreCase("LcsOfLexemesBasedDiceCoefficient")
                    || attribute_Name.equalsIgnoreCase("LcsBasedOnTokensBasedDiceCoefficient")
                    || attribute_Name.equalsIgnoreCase("CharactersBasedLengthRatioOfContentVectors")
                    || attribute_Name.equalsIgnoreCase("TokenBasedLengthRatioOfContentVectors")
                    || attribute_Name.equalsIgnoreCase("CharactersBasedLengthDifferenceOfContentVectors")
                    || attribute_Name.equalsIgnoreCase("TokensBasedLengthDifferenceOfContentVectors")                    
                    || attribute_Name.equalsIgnoreCase("TBALBasedCosineSimilarity")
                    || attribute_Name.equalsIgnoreCase("LBALBasedCosineSimilarity")
                    || attribute_Name.equalsIgnoreCase("TBALBasedOverlapCoefficient")
                    || attribute_Name.equalsIgnoreCase("LBALBasedOverlapCoefficient")
                    || attribute_Name.equalsIgnoreCase("TBALBasedDiceCoefficient")
                    || attribute_Name.equalsIgnoreCase("LBALBasedDiceCoefficient")
                    || attribute_Name.equalsIgnoreCase("CosineSimilarity")
                    || attribute_Name.equalsIgnoreCase("OverlapCoefficient")
                    || attribute_Name.equalsIgnoreCase("DiceSimilarity")
                    || attribute_Name.equalsIgnoreCase("CosineSimilarityWithoutPOS")
                    || attribute_Name.equalsIgnoreCase("OverlapCoefficientWithoutPOS")
                    || attribute_Name.equalsIgnoreCase("DiceSimilarityWithoutPOS")
                    || attribute_Name.equalsIgnoreCase("CosineSimilarityRaw")
                    || attribute_Name.equalsIgnoreCase("CosineSimilarityTok")
                    || attribute_Name.equalsIgnoreCase("CosineSimilarityTokUnTagged")
                    || attribute_Name.equalsIgnoreCase("CosineSimilarityTokSwr")
                    || attribute_Name.equalsIgnoreCase("CosineSimilarityPrep")
                    ) {
                
                attribute_Type_Text_To_Return = "real";
                
            } else if (attribute_Name.equalsIgnoreCase("LcsOfCharactersBasedEventDetection")
                    || attribute_Name.equalsIgnoreCase("LcsOfLexemesBasedEventDetection")
                    || attribute_Name.equalsIgnoreCase("LcsBasedOnTokensBasedEventDetection")
                    || attribute_Name.equalsIgnoreCase("Polarity")
                    || attribute_Name.equalsIgnoreCase("VerbAntonymMatch")
                    || attribute_Name.equalsIgnoreCase("AdjectiveAntonymMatch")
                    || attribute_Name.equalsIgnoreCase("NounAntonymMatch")
                    || attribute_Name.equalsIgnoreCase("AdverbAntonymMatch")
                    || attribute_Name.equalsIgnoreCase("VerbOrAdjectiveAntonymMatch")
                    || attribute_Name.equalsIgnoreCase("VerbOrAdjectiveOrNounOrAdverbAntonymMatch")
                    || attribute_Name.equalsIgnoreCase("IntraString1OrIntraString2AntonymMatch")                                      
                    || attribute_Name.equalsIgnoreCase("LBALSequenceForString2BasedEventDetection")
                    || attribute_Name.equalsIgnoreCase("LBALSequenceForString1BasedEventDetection")
                    || attribute_Name.equalsIgnoreCase("LBALEventDetection")
                    || attribute_Name.equalsIgnoreCase("TBALBasedEventDetectionBasedOnString1OrString2Sequence")
                    || attribute_Name.equalsIgnoreCase("TBALBasedEventDetectionBasedOnString1AndString2Sequence")) {

                attribute_Type_Text_To_Return = "{true, false}";

            } else if (attribute_Name.equalsIgnoreCase("pair_Quality") ) {
                attribute_Type_Text_To_Return = "{0, 1}";

            } else {
                throw new Exception("Could not recognize the attribute type, attribute type text could not be found. ");
            }

        } catch (Exception e) {
            throw new Exception("CsvToArff : getAttributeTypeText : "
                    + e + " : " + e.getMessage());
        }

        return attribute_Type_Text_To_Return;
    }
}
