/*
 * $Id$
 */
package swisseph;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * TestSweDate
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class TestSweDate extends TestCase {
    public void testToJulianDay(){
        //SweDate sdate = new SweDate(2006,1,2,14);

        SweDate sdate = new SweDate();
        long offset = 0;//1000*60*60*8;

        System.out.println(sdate.getDate(offset));
        System.out.println("Julian Day:"+sdate.getJulDay());
        System.out.println("Delta T:"+sdate.getDeltaT()*60*60*24);

        SwissLib lib = new SwissLib();
        double sidtime=lib.swe_sidtime(sdate.getJulDay());
        System.out.println("Sidereal time:"+toHour(sidtime));


    }
    public void testNagetiveHours(){
        SweDate sdate = new SweDate(2006,1,2,-2);
        System.out.println(sdate.getDate(0));
        System.out.println(sdate.getJulDay());
    }
    public void testSiderealTime(){
        double sidtime = 20.10037;
        // Formatting a double with a TimeFormatter:
           SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
           TimeZone utc = TimeZone.getTimeZone("GMT+0");
           df.setTimeZone(utc);
           System.out.println(df.format(new Date((long)(sidtime*3600*1000))));


           // Manual output formatting:
           sidtime += 1/60/60/2; // Round the number to the seconds part!
           System.out.println(sidtime);
           int hours = (int)sidtime;
           sidtime = (sidtime - hours)*60;
           int minutes =  (int)sidtime;
           int seconds = (int)((sidtime - minutes)*60);

           // This will have the numbers formatted without leading zeros:
           System.out.println(hours + ":" + minutes + ":" + seconds);

           // This will format the number with leading zeros:
           System.out.println((hours<10?"0":"") + hours + ":" +
                              (minutes<10?"0":"") + minutes + ":" +
                              (seconds<10?"0":"") + seconds);

    }
    public void testHouse(){
        SweDate sdate = new SweDate();
        SwissEph se = new SwissEph();
        double[] cups = new double[16];
        double[] ascmc = new double[10];
        int result=se.swe_houses(sdate.getJulDay(),0,39.92,116.46,'P',cups,ascmc);
        assertEquals(result,SweConst.OK);
        for (int i = 0; i < ascmc.length; i++) {
            double v = ascmc[i];
            System.out.println("ascmc["+i+"]="+v);
        }
        for (int i = 0; i < cups.length; i++) {
            double cup = cups[i];
            System.out.println("cups["+i+"]="+cup);
        }
    }
    public void testCal(){
         SweDate sdate = new SweDate();

    }
    public void testTimeZone(){
        Calendar cal = Calendar.getInstance();
        System.out.println(cal.getTimeZone());
        System.out.println(format(cal));
        Calendar gmt0 = Calendar.getInstance(TimeZone.getTimeZone("GMT+0"));
        //gmt0.setTime(cal.getTime());
        System.out.println("gmt 0:"+format(gmt0));
        Calendar gmt8 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        //gmt8.setTime(cal.getTime());
        System.out.println("gmt 8:"+format(gmt8));
    }

    private String format(Calendar cal){
        StringBuffer sb = new StringBuffer();
        sb.append(cal.get(Calendar.YEAR));
        sb.append("-");
        sb.append(cal.get(Calendar.MONTH)+1);
        sb.append("-");
        sb.append(cal.get(Calendar.DAY_OF_MONTH));
        sb.append(" ");
        sb.append(cal.get(Calendar.HOUR_OF_DAY));
        sb.append(":");
        sb.append(cal.get(Calendar.MINUTE));
        sb.append(":");
        sb.append(cal.get(Calendar.SECOND));
        return sb.toString();
    }
    private String toHour(double sidtime){
        // Manual output formatting:
           sidtime += 1/60/60/2; // Round the number to the seconds part!
           System.out.println(sidtime);
           int hours = (int)sidtime;
           sidtime = (sidtime - hours)*60;
           int minutes =  (int)sidtime;
           int seconds = (int)((sidtime - minutes)*60);
        // This will format the number with leading zeros:
           return ((hours<10?"0":"") + hours + ":" +
                              (minutes<10?"0":"") + minutes + ":" +
                              (seconds<10?"0":"") + seconds);
    }
}
