/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malikalamgirian.qaw.fyp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;
import javax.xml.transform.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

/**
 *
 * @author wasif
 */
public class TxtToXmlConvertor {

    public TxtToXmlConvertor(String txt_Input_File_URL) throws Exception {
        
        try {
            /*
             * Set input file URL
             */
            this.txt_Input_File_URL = txt_Input_File_URL;

            /*
             * Set output file URL
             */
            set_XML_Output_File_URL(txt_Input_File_URL);

            /*
             * Set input file Name
             */
            set_TXT_Input_File_Name(txt_Input_File_URL);

            /*
             * Convert the file
             */
            Convert(this.txt_Input_File_URL, this.xml_Output_File_URL);

        } catch (Exception e) {
            throw new Exception("TxtToXmlConvertor :"
                    + e + " : " + e.getMessage());
        }
        
    }
    
    /*
     * To convert Standard format txt file to Standard XML format
     *
     * Standard TXT format is :
     *
     * ﻿Quality	#1 ID	#2 ID	#1 String	#2 String
     *  1	702876	702977  This is str1.   This is str2.
     * 
     *
     * Standard XML format is :
     * 
     * <txt_filename_without_extension>
     *   <Pair Quality="">
     *      <String1 Id=""></String1>
     *      <String2 Id=""></String2>
     *   </Pair>
     * </txt_filename_without_extension>
     *
     */

    /*
     * Declarations
     */
    private String txt_Input_File_URL,
            txt_Input_File_Name,
            xml_Output_File_URL;

    private boolean Convert(String txt_Input_File_URL, String xml_Output_File_URL) throws Exception {

        try {

            System.out.println("Convert File : " + txt_Input_File_URL + "\nOn : " + new Date().toString());
            /* Here we
             * 1. Open input file
             * 2. Get Blank XML Document
             * 3. Generate Document Element
             * 4. Read Lines & Using a method Populate them in XML file
             * 5. Apply transformation to XML
             * 6. close the open file
             */

            String lineRead;

            /* 1.1 Open input file */
            FileInputStream fInStream = new FileInputStream(txt_Input_File_URL);

            /* 1.2 Get object of DataInputStream */
            DataInputStream dInStream = new DataInputStream(fInStream);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(dInStream));

            /* 2.Get Blank XML Document */
            Document doc = com.malikalamgirian.qaw.fyp.XMLProcessor.getBlankXMLDocument();

            /* 3. Generate Document Element */
            Element root = doc.createElement(this.txt_Input_File_Name);

            /* 3.1 add it to xml tree */
            doc.appendChild(root);

            /* 4. Read Lines & Using a method Populate them in XML file */

            /* 4.1 Waste away the titles line */
            lineRead = bReader.readLine();

            /* 4.2 Populate all other lines */
            while ((lineRead = bReader.readLine()) != null) {
                try {
                    PopulateLine(lineRead, doc, root);
                } catch (Exception ex) {
                    System.out.println("PopulateLine: for Conversion from txt to XML : " + ex);
                    System.out.println(ex.getMessage());
                    System.out.println("Line Read: " + lineRead);
                }
            }

            /* 5. Transform */
            root.normalize();

            TransformerFactory tranFactory = TransformerFactory.newInstance();
            Transformer aTransformer = tranFactory.newTransformer();

            aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

            /* 5.1 Describes the domain of the indent-amount parameter (apache xst specific, non JAXP generic). */
            aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            /* 5.2 Set source and destination */
            Source src = new DOMSource(doc);

            Result dest = new StreamResult(new File(xml_Output_File_URL));

            aTransformer.transform(src, dest);

            /* 6 */
            bReader.close();

        } catch (TransformerFactoryConfigurationError e) {
            throw new Exception("Convert has gotten some problem : TransformerFactoryConfigurationError :"
                    + e + " : " + e.getMessage());
        } catch (TransformerException e) {
            throw new Exception("Convert has gotten some problem : TransformerException :"
                    + e + " : " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Convert has gotten some problem :"
                    + e + " : " + e.getMessage());
        }

        return true;
    }

    private boolean PopulateLine(String lineToPopulate, Document doc, Element root) throws Exception {

        try {

            /* Here we
             * 1. Preprocess the line into proper storage units
             * 2. Create proper element hierarchy and add them to root node
             */
            String String1,
                    String2,
                    StringID1,
                    StringID2,
                    Quality;

            String[] arrayForRegex;

            /* 1. Preprocess the line into proper storage units
             * 1.1 Here we divide the string into Strings and Numbers
             * 1.2 Extract numbers
             * 1.3 Extract strings
             */

            /*
             * New testing, worked well, Success
             * this regex is the proper one, which uses
             * Tab "\t" for split operation,
             * because this change was lately made so some comments might be improper
             * the explicit commenting Has been done for implementing this new Regex
             */
            arrayForRegex = lineToPopulate.split("\t");

            for (int i = 0; i < arrayForRegex.length; i++) {
                System.out.println(arrayForRegex[i]);
            }
            System.out.println("\n\n");

            /*
             * 1
             */
            Quality = arrayForRegex[0];
            StringID1 = arrayForRegex[1];
            StringID2 = arrayForRegex[2];
            String1 = arrayForRegex[3];
            String2 = arrayForRegex[4];

            /* 2 Create proper element hierarchy and add them to root node */
            Element pair = doc.createElement("Pair");

            /* Add the atribute to the child */
            pair.setAttribute("Quality", Quality);

            Element string1 = doc.createElement("String1");
            Element string2 = doc.createElement("String2");

            string1.setAttribute("Id", StringID1);
            string2.setAttribute("Id", StringID2);

            string1.setTextContent(String1);
            string2.setTextContent(String2);

            pair.appendChild(string1);
            pair.appendChild(string2);

            root.appendChild(pair);

            root.normalize();

        } catch (DOMException e) {
            throw new Exception("PopulateLine has gotten some problem : DOMException :"
                    + e + " : " + e.getMessage());
        }
        return true;
    }


    /*
     * Setters
     */
    private void set_XML_Output_File_URL(String txt_Input_File_URL) {

        this.xml_Output_File_URL = txt_Input_File_URL.substring(0, txt_Input_File_URL.length() - 4);
        this.xml_Output_File_URL += "_xml";
        this.xml_Output_File_URL += ".xml";
    }

    public String getXml_Output_File_URL() {
        return xml_Output_File_URL;
    }  

    private void set_TXT_Input_File_Name(String txt_Input_File_URL) {
        /*
         * You may need to convert "/" with "\\" in windows.
         * "/"  : Ubuntu
         * "\\" : MSRPC
         */
        this.txt_Input_File_Name = txt_Input_File_URL.substring(txt_Input_File_URL.lastIndexOf("/") + 1, txt_Input_File_URL.lastIndexOf(".")) + "_xml";
    }
}
