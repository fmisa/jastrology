/*
 * $Id$
 */
package swisseph;

import junit.framework.TestCase;

/**
 * TestHouses
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class TestHouses extends TestCase {
    public void testHouses(){
        SwissEph sw = new SwissEph();
        SweDate sd = new SweDate();
        double latitude = 30.52;
        double longitude = 114.31;
        int hsy = 'P';
        double[] cusp = new double[13];
        double[] ascmc = new double[10];
        int result= sw.swe_houses(sd.getJulDay(), 0, latitude, longitude, hsy, cusp, ascmc);
        if(result == SweConst.ERR){
            throw new SwissephException(sd.getJulDay(),"Calculation was not possible due to nearness to the polar circle in Koch or Placidus house system or when requesting Gauquelin sectors. " +
                    "Calculation automatically switched to Porphyry house calculation method in this case");
        }
        for (int i = 0; i < cusp.length; i++) {
            double v = cusp[i];
            System.out.println("House "+i+" "+v);
        }
        for (int i = 0; i < ascmc.length; i++) {
            double v = ascmc[i];
            System.out.println("ascmc "+i+" "+v);
        }
    }

     
}
