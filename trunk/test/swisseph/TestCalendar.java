/*
 * $Id$
 */
package swisseph;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.Date;

/**
 * TestCalculator
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class TestCalendar extends TestCase {
    public void testTimezone(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        print(calendar);
        calendar.set(Calendar.YEAR,2005);
        calendar.set(Calendar.MONTH,1);
        calendar.set(Calendar.DAY_OF_MONTH,3);
        calendar.set(Calendar.HOUR_OF_DAY,12);
        calendar.set(Calendar.MINUTE,13);
        print(calendar);
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        //calendar.setTime(new Date());
        print(calendar);
    }

    void print(Calendar cal){
        System.out.println(cal.getTimeInMillis());
        System.out.println(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"
            +cal.get(Calendar.DATE)+" "+cal.get(Calendar.HOUR_OF_DAY)+":"
            +cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND)+" "+cal.getTimeZone());


    }
}
