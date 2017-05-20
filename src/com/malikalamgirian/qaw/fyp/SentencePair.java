/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malikalamgirian.qaw.fyp;

/**
 *
 * @author wasif
 */
public class SentencePair {

    private static long String1Id;
    private static long String2Id;
    private static String String1;
    private static String String2;
    private static int pairQuality;

    public SentencePair() {
    }

    public SentencePair(long String1Id, long String2Id, String String1,
            String String2, int pairQuality) {
        /*
         * Set String1Id
         */
        SentencePair.String1Id = String1Id;

        /*
         * Set String2Id
         */
        SentencePair.String2Id = String2Id;

        /*
         * Set String1
         */
        SentencePair.String1 = String1;

        /*
         * Set String2
         */
        SentencePair.String2 = String2;

        /*
         * Set PairQuality
         */
        SentencePair.pairQuality = pairQuality;
    }

    public long getString1Id() {
        return String1Id;
    }

    public void setString1Id(long String1Id) {
        this.String1Id = String1Id;
    }

    public long getString2Id() {
        return String2Id;
    }

    public void setString2Id(long String2Id) {
        this.String2Id = String2Id;
    }

    public String getString1() {
        return String1;
    }

    public void setString1(String String1) {
        this.String1 = String1;
    }

    public String getString2() {
        return String2;
    }

    public void setString2(String String2) {
        this.String2 = String2;
    }

    public int getPairQuality() {
        return pairQuality;
    }

    public void setPairQuality(int pairQuality) throws Exception {
        /*
         * constraint for pair quality
         */
        if (pairQuality == 0 || pairQuality == 1) {
            this.pairQuality = pairQuality;
        } else {
            throw new Exception("Pair Quality out of range, it must be 1 or 0.");
        }
    }

    @Override
    public String toString() {
        return pairQuality + "\t" + String1Id + "\t" + String2Id + "\t" + String1 + "\t" + String2;
    }
}
