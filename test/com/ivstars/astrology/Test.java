/*
 * $Id$
 */
package com.ivstars.astrology;



/**
 * Test
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class Test {
   
    public static void main(String[] args){
        if(args.length==0)
         for(int i=0;i<256;i++){
             System.out.println(i+"\t"+(char)i);
         }
        else
        junit.textui.TestRunner.main(args);
    }
    public static void usage(){
        System.out.println("java "+Test.class.getName());
    }
}
