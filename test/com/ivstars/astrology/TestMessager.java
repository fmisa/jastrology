package com.ivstars.astrology;

import junit.framework.TestCase;
import net.sf.anole.Messager;
import net.sf.anole.MessagerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2006-5-29
 * Time: 0:49:16
 * To change this template use File | Settings | File Templates.
 */
public class TestMessager extends TestCase {
    public void testMessager(){
        Messager messager = MessagerFactory.getMessager(Constants.BASE_PACKAGE);
        String sun=messager.getMessage("Sun");
        assertNotNull(sun);
        System.out.println("Sun:\t"+sun);
    }
}
