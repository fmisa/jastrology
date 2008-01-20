
/*
   The swisseph package used herein is a Java port of the Swiss Ephemeris
   of Astrodienst Zuerich, Switzerland. For copyright notices see the file
   LICENSE or - if not included - see at http://www.astro.com for license
   information.

   This small program is heavily based upon sweclips.c from the original
   Swiss Ephemeris package.

   Thomas Mack, mack@idb.cs.tu-bs.de, 25th of November 2001
*/

import swisseph.*;
import java.io.*;
import java.text.*;  // DateFormat etc.
import java.util.*;  // Locale etc.

/**
* Test program for planetary transits.
* See class swisseph.SwissEph.<P>
* Invoke with parameter -h to get the help text.
* @see swisseph.SwissEph
*/
public class Transits {

  static final String infocmd0 = "\n"+
  "  'Transits' computes planetary transits over specified longitudes,\n"+
  "  latitudes, distances, speeds in any of these directions or\n"+
  "  in any above variation over other planets.\n\n";

  static final String infocmd1 = "\n"+
  "  You can calculate several kinds of transits:\n\n"+
  "    - When does a planet transit a certain longitude,\n"+
  "      latitude or distance:\n"+
  "         -p. -b... -lon... [other options]\n"+
  "         -p. -b... -lat... [other options]\n"+
  "         -p. -b... -dist... [other options]\n"+
  "    - When does a planet transit a certain speed in\n"+
  "      longitude, latitude or distance:\n"+
  "         -p. -b... -s -lon... [other options]\n"+
  "         -p. -b... -s -lat... [other options]\n"+
  "         -p. -b... -s -dist... [other options]\n"+
  "    - When does a planet transit another planets\n"+
  "      longitude, latitude or distance:\n"+
  "         -p. -P. -b... -lon... [other options])\n"+
  "         -p. -P. -b... -lat... [other options])\n"+
  "         -p. -P. -b... -dist... [other options])\n"+
  "    - When does a planet transit another planets\n"+
  "      speed in longitude, latitude or distance:\n"+
  "         -p. -P. -b... -s -lon... [other options])\n"+
  "         -p. -P. -b... -s -lat... [other options])\n"+
  "         -p. -P. -b... -s -dist... [other options])\n"+
  "    - When does the SUM (==Yoga) of two planets positions\n"+
  "      (or speeds with -s option) reach a certain value:\n"+
  "         -p. -P. -b... +lon... [other options])\n"+
  "         -p. -P. -b... +lat... [other options])\n"+
  "         -p. -P. -b... +dist... [other options])\n"+
  "\n    Other options:\n"+
  "    - List all transits in a time range by giving a second time:\n"+
  "         [base options] -b... -b...\n"+
  "         [base options] -b... -B...\n"+
  "    - Give an exact starting and / or end time:\n"+
  "         [base options] -t...\n"+
  "         [base options] -ut...\n"+
  "         [base options] -T...\n"+
  "         [base options] -UT...\n"+
  "    - List a fixed number of consecutive transits:\n"+
  "         [base options] -n...\n"+
  "         [base options] -N...\n"+
  "    - Search backwards (\"reverse\"):\n"+
  "         [base options] -r\n"+
  "    - Calculate in the sidereal zodiac:\n"+
  "         [base options] -sid.\n"+
  "    - Calculate topocentric instead of geocentric:\n"+
  "         [base options] -topo...\n"+
  "    - Modify the input parsing:\n"+
  "         [base options] -loc... (input parsing AND output formatting)\n"+
  "         [base options] -iloc...\n"+
  "         [base options] -Dloc[...]\n"+
  "         [base options] -Nloc[...]\n"+
  "    - Modify the output formatting:\n"+
  "         [base options] -head, -q\n"+
  "         [base options] -f...\n"+
  "         [base options] -loc... (input parsing AND output formatting)\n"+
  "         [base options] -oloc...\n"+
  "         [base options] -dloc[...]\n"+
  "         [base options] -nloc[...]\n"+
  "    - Give the path to the ephemeris data files:\n"+
  "         [base options] -edir...\n"+
  "    - List all availables Locales for use with -loc etc. options:\n"+
  "         -locales\n"+
  "";

  static final String infodate = "\n"+
  "  Date entry:\n"+
  "  You can enter the start date entry (option '-b') and the end date\n"+
  "  entry (if required, option '-B') in one of the following formats:\n"+
  "\n"+
  "        27.2.1991       three integers separated by a nondigit character for\n"+
  "                        day month year. Dates are interpreted as Gregorian\n"+
  "                        after October 4, 1582 and as Julian Calender before.\n"+
  "                        Time is always set to midnight. Use -t / -ut to\n"+
  "                        set the time.\n"+
  "                        The sequence of year, month and day is determined\n"+
  "                        by the locale settings, see options -loc etc.. With\n"+
  "                        -locde 5-8-2000 would be interpreted as a date in\n"+
  "                        August, -locen would see a date in May 2000.\n"+
  "                        Use -t / -ut without any following time to force\n"+
  "                        times to be interpreted as ET (-t) or UT (-ut).\n"+
  "                        If the three letters jul are appended to the date,\n"+
  "                        the Julian calendar is used even after 1582.\n"+
  "                        If the four letters greg are appended to the date,\n"+
  "                        the Gregorian calendar is used even before 1582.\n"+
  "\n"+
  "        j2400123.67     the letter j followed by a real number, for\n"+
  "                        the absolute Julian daynumber of the start date.\n"+
  "                        Fraction .5 indicates midnight, fraction .0\n"+
  "                        indicates noon, other times of the day can be\n"+
  "                        chosen accordingly.\n"+
  "        today           this will use the current date.\n"+
  "  You can enter any time entry (options -t / -T / -ut / -UT) in the\n"+
  "  the following formats:\n"+
  "        hh:mm:ss        three integers representing hour, minutes and\n"+
  "                        seconds separated by non-digits\n"+
  "        now             (String) use current time.\n";

  String infocmd2 = null;
  String infoexamples = null;

  private void initHelpTexts() {
  infocmd2 = "\n"+
  "  Command line options:\n"+
  "    Main options:\n"+
  "        -bDATE  use this date; use format -b1992/3/24 or -bj2400000.5,\n"+
  "                to express the date as absolute Julian day number.\n"+
  "                Use option -hdate for more information.\n"+
  "                You can use two -b... options to give a starting and\n"+
  "                an end date.\n"+
  "                Note: the date format is month/day/year (US style)\n"+
  "                by default.\n"+
  "        -j....  Same as -bj....\n"+
  "        -BDATE  use this date as the end date for a time range, use\n"+
  "                -Bj..... for a julian day number\n"+
  "                Same as a second -b... option, but it searches for\n"+
  "                transits over ANY of the given transit points instead\n"+
  "                of looking for the NEXT transit point only, when giving\n"+
  "                more than one longitude etc.. See the -n / -N options\n"+
  "                for similar considerations.\n"+
  "        -J....  Same as -Bj....\n"+
  "        -uthh:mm:ss hour in UT for -b... date\n"+
  "                    You can use the String 'now' for the current UTC time.\n"+
  "        -UThh:mm:ss hour in UT for -B... date. If -UT is not given, it\n"+
  "                    defaults to the value of -ut\n"+
  "        -thh:mm:ss  hour in ET for -b... date, it defaults to 0.0\n"+
  "        -Thh:mm:ss  hour in ET for -B... date. Default: the value of -t\n"+
  "                    You can use the String 'now' for the current UTC time.\n"+
  "        -p.      transiting planet (like swetest.c)\n"+
  "                 Planets are:\n"+
  "                   0 Sun        7 Uranus             B osculating lunar\n"+
  "                   1 Moon       8 Neptune              apogee\n"+
  "                   2 Mercury    9 Pluto              D Chiron\n"+
  "                   3 Venus      m Mean node          E Pholus\n"+
  "                   4 Mars       t True node          F Ceres\n"+
  "                   5 Jupiter    A mean lunar apogee  G Pallas\n"+
  "                   6 Saturn       (Lilith)           H Juno\n"+
  "                                                     I Vesta\n"+
  "                 You can add a second planet for transits over a second\n"+
  "                 planet instead using -P. option (e.g.: -p01)\n"+
  "        -p..\n"+
  "        -P.      calculate transit over this (second) planet\n"+
  "        -lon...  longitude or longitudinal speed, over which the transit\n"+
  "                 has to occur. If two planets are given, this means the\n"+
  "                 position (or speed) of planet -px after planet -Px\n"+
  "        -lat...  latitude or latitudinal speed, over which the transit\n"+
  "                 has to occur. If two planets are given, this means the\n"+
  "                 position (or speed) of planet -px after planet -Px\n"+
  "        -dist... distance or speed in distance movement in AU, over which\n"+
  "                 the transit has to occur. If two planets are given, this\n"+
  "                 means the distance position (or speed) of planet -px\n"+
  "                 after planet -Px\n"+
  "        +lon...  same as -lon for transits of one planet over another\n"+
  "                 planet with the difference that the SUM (Yoga) of the\n"+
  "                 positions or speeds of both planets will be calculated\n"+
  "        +lat...  same as -lat for transits of one planet over another\n"+
  "                 planet with the difference that the SUM (Yoga) of the\n"+
  "                 positions or speeds of both planets will be calculated\n"+
  "        +dist... same as -dist for transits of one planet over another\n"+
  "                 planet with the difference that the SUM (Yoga) of the\n"+
  "                 positions or speeds of both planets will be calculated\n"+
  "        -lon, -lat, -dist, +lon, +lat, +dist can all take a  form that\n"+
  "                 increases the given value on each iteration by an offset.\n"+
  "                 The correct syntax is:\n"+
  "                 {\"-\"|\"+\"}{\"lon\"|\"lat\"|\"dist\"}STARTVAL[\"+\"|\"-\"OFFSET]\n"+
  "                 with STARTVAL and OFFSET being floating point numbers.\n"+
  "                 Use with option -n / -N or with -b -b / -b -B. Example:\n"+
  "                 -lon0+30.0 -n12\n"+
  "                 searches for 12 consecutive transits with the degree\n"+
  "                 changing from one step to the other by 30 degrees.\n"+
  "        -s       Calculate the transit over a given speed instead of\n"+
  "                 a given position\n"+
  "\n    Additional options:\n"+
  "        -r        search backward\n"+
  "        -topo[long;lat;elev]\n"+
  "                  Calculate related to a position on the surface of the\n"+
  "                  earth, default is geocentric calculation. Longitude,\n"+
  "                  latitude (degrees with decimal fraction) and elevation\n"+
  "                  (meters) are optional. Default is Z\u00fcrich: 8.55;47.38;400\n"+
  "        -sid.     a sidereal mode, if sidereal calculation is wanted.\n"+
  "                  Valid modes are:\n"+
  "                     0 Fagan/Bradley           10 Babylonian, Kugler2\n"+
  "                     1 Lahiri                  11 Babylonian, Kugler3\n"+
  "                     2 DeLuce                  12 Babylonian, Huber\n"+
  "                     3 Raman                   13 Babylonian, Mercier\n"+
  "                     4 Ushashashi              14 t0=Aldebaran, 15"+swed.ODEGREE_CHAR+" taurus\n"+
  "                     5 Krishnamurti            15 Hipparchos\n"+
  "                     6 Djwhal Khul             16 Sassanian\n"+
  "                     7 Sri Yukteshwar          17 Galactic center=0"+swed.ODEGREE_CHAR+" sagitt.\n"+
  "                     8 JN Bhasin               18 J2000\n"+
  "                     9 Babylonian, Kugler1     19 J1900\n"+
  "                                               20 B1950\n"+
  "        -n...     search for <n> transits instead of just one. If you want\n"+
  "                  all transits in a time range, use option -B... to give a\n"+
  "                  second date\n"+
  "        -N...     search for <N> transits instead of just one. Differently\n"+
  "                  to the -n option, this searches for the next OR(!) the\n"+
  "                  same OR(!) the previous transit position value, when you\n"+
  "                  give an increment value to the -lon etc. options. This\n"+
  "                  is useful ONLY, when a planet can move both direct and\n"+
  "                  retrograde, so you will not miss any transit point.\n"+
  "                  Notice the difference between the two commands:\n"+
  "                    java Transits -p5 -b2012/01/01 -lon60+10 -n6 -locen\n"+
  "                    java Transits -p5 -b2012/01/01 -lon60+10 -N6 -locen\n"+
  "        -f...     Output format options, default is -fdt or -fvdt, if we\n"+
  "                  calculate consecutive transits with changing degrees.\n"+
  "        -f+...    Same as -f, but add the following options to the\n"+
  "                  default options instead of replacing them all.\n"+
  "                  Available options:\n"+
  "          d, d..  Output transit date and time in US date style with a\n"+
  "                  given number of decimal places of the seconds part,\n"+
  "                  'd5' will give you a time output like 20:26:46.80099,\n"+
  "                  'd' (or 'd0') will result in 20:26:47\n"+
  "                  All date output is localized to the 'en_US' locale if\n"+
  "                  not specified by the different -loc etc. options.\n"+
  "          t       Postfix the dates with 'ET' (Ephemeris Time) or 'UT'\n"+
  "                  (Universal Time) as appropriate.\n"+
  "          j, j..  Output transit date and time as julian day numbers with\n"+
  "                  the given numbers of decimal places. Saying 'j' is\n"+
  "                  identical to saying 'j8': 8 decimal places.\n"+
  "          v, v..  Output the transit degree or distance or speed value\n"+
  "                  with the given number of decimal places. 'v' is equal\n"+
  "                  to 'v2'\n"+
  "          p, p..  Output the actual position (or speed) on the found date\n"+
  "                  with the given number of decimal places. 'p' is equal\n"+
  "                  to 'p2', which means to decimal places.\n"+
  "               Only when calculating relative transits:\n"+
  "          P, P..  (Capital P) Identical to 'p' except that there is an\n"+
  "                  additional output of the real difference of both\n"+
  "                  planets positions. 'P' means 'P2', which means, output\n"+
  "                  should give two decimal places.\n"+
  "  Localization (internationalization):\n"+
  "  ====================================\n"+
  "  Input parsing and output formatting is done using the 'en_US' locale,\n"+
  "  meaning in the american style by default."+
  "  Localization knows about two different fields: numbers and dates. You\n"+
  "  can give both localization information for input parsing and output\n"+
  "  formatting. The default is 'en_US', you can change it to your current\n"+
  "  system locale by giving the option -loc without any locale added.\n"+
  "  The -loc options will change all patterns at the same time, all other\n"+
  "  options will just care for partial aspects at a time.\n"+
  "    -loc  is for input parsing and output formatting of numbers and dates\n\n"+
  "    -iloc is for input parsing of numbers and dates only\n"+
  "    -oloc is for output formatting of numbers and dates only\n\n"+
  "    -Dloc is for input parsing of dates only\n"+
  "    -Nloc is for input parsing of numbers only\n"+
  "    -dloc is for output formatting of dates only\n"+
  "    -nloc is for output of numbers like degrees or speed or JD\n\n"+
  "    The locale parameter without any locale string added to it (-loc /\n"+
  "    -iloc / -oloc etc.pp.) will use the default system locale. Add the\n"+
  "    locale String to use a specific locale, e.g. -dlocro for romanian\n"+
  "    date output formatting or -olochi_IN to use the indian hindi style\n"+
  "    in output. Use -locswiss to revert to the default behaviour of\n"+
  "    Swetest.java and the original C versions of swiss ephemeris.\n"+
  "    You can append '24' to -loc etc., to use 24 hours date formats on\n"+
  "    output, even when the localization would use AM/PM formats. E.g.:\n"+
  "    -loc24hi_IN (24 hour time format in Hindi) or: -loc24 or -loc24en\n"+
  "    ATTENTION: input parsing of time is ALWAYS done in the 24 hour format\n"+
  "    hh:mm:ss only!\n"+
  "    -locales  List all available locales. Does not compute anything.\n"+
  "        -eswe     calculate with swiss ephemeris\n"+
  "        -emos     calculate with moshier ephemeris\n"+
  "        -edirPATH change the directory of the ephemeris files \n"+
  "        -q\n"+
  "        -head     don\'t print the header before the planet data\n\n"+
  "    Help options:\n"+
  "        -?, -h    display info\n"+
  "        -hdate    display date info\n"+
  "        -hex      display examples\n";


  String infoexamples = "\n"+
  "  Examples:\n\n"+
  "  Simple transits:\n"+
  "     Next transit of the moon over 123.4702 degrees:\n"+
  "       java Transits -p1 -lon123.4702 -btoday -utnow\n\n"+
  "     Last transit of the pluto over 0 degrees of latitudinal:\n"+
  "     position with output of actual position on that date:\n"+
  "       java Transits -p9 -lat0 -btoday -utnow -r -fdtjp\n\n"+
  "     When will mercury next time change it's longitudinal direction of:\n"+
  "     movement (\"change its direct motion to retrograde or vice versa\"):\n"+
  "       java Transits -p2 -btoday -utnow -lon0 -s\n\n"+
  "     When did Pluto cross a very far distance of 50.2 AU between year 0:\n"+
  "     and today with additional output of the julian day number:\n"+
  "       java Transits -p9 -b1/1/0 -Btoday -dist50.2 -f+j5\n\n"+
  "  Transits relative to other planets:\n"+
  "     Next conjunction of the jupiter with saturn:\n"+
  "       java Transits -p5 -P6 -lon0 -b11/28/2001 -fdtjp -ilocen\n\n"+
  "     Last full moon:\n"+
  "       java Transits -p0 -P1 -lon180 -btoday -utnow -r\n\n"+
  "     All full moons in year 2004:\n"+
  "       java Transits -p0 -P1 -lon180 -b1/1/2004 -B12/31/2004 -t24 -loc24en\n\n"+
  "     The first complete Nakshatras cycle in year 2004 starting with\n"+
  "     Ashvini (0"+swed.ODEGREE_CHAR+" in sidereal zodiac) related to a topocentric position\n"+
  "     somewhere in Germany:\n"+
  "       java Transits -b1/1/2004 -ut -topo11.0/52.22/160 -p1 -lon0+13.3333333333333 -n27 -sid1 -fv6dtj -ilocen\n\n"+
  "     The first 12 yogas starting from January 1, 2004:\n"+
  "       java Transits -p01 +lon0+13.33333333333333 -sid1 -b1/1/2004 -n12 -ut -ilocen -f+P\n\n"+
  "";
  }

  /**************************************************************/


  SwissEph  sw=new SwissEph();
  SwissLib  sl=new SwissLib();
  Extlib    el=new Extlib();
  SweDate   sde1=new SweDate();
  SweDate   sde2=new SweDate();
  SweDate   sdu1, sdu2;
  SwissData swed=new SwissData();
  CFmt f=new CFmt();

  String transitValS = null;
  double transitVal = 0.;
  String topoS = null;
  String dateFracSeparator = ".";
  String numIFracSeparator = ".";
  String numOFracSeparator = ".";
  // For formatting dates:
  SimpleDateFormat dif = null;
  SimpleDateFormat dof = null;
  // For formatting the decimal parts of the seconds in dates:
  // Fraction of a second not (yet?) supported on input.
  NumberFormat dnof = null;
  // For formatting other numbers:
  NumberFormat nnif = null;
  NumberFormat nnof = null;
  int secondsIdx = 0;

  Locale[] locs = Locale.getAvailableLocales();
  String locale = "en_US"; // Make en_US the default
  String Nlocale = null;   // Locale to localize numbers on input
  String Dlocale = null;   // Locale to interpret dates on input
  String nlocale = null;   // Locale to localize numbers on output
  String dlocale = null;   // Locale to interpret dates on output

  boolean force24hSystem = false;



  /**
  * See -h parameter for help on all parameters.
  */
  public static void main(String argv[]) {
    Transits sc=new Transits();
    System.exit(sc.startCalculations(argv));
  }

  /**
  * If you want to use this class in your own programs, you would
  * just call this method. All output will go to stdout only (so
  * far).
  * @param argv array of Strings containing all parameters like on
  * the command line.
  * @return nothing so far
  */
  public int startCalculations(String[] argv) {
    double top_long=8.55; // Zuerich
    double top_lat=47.38;
    double top_elev=400;
    int jmonut, jdayut, jyearut, jmonet, jdayet, jyearet;
    double jet = 0.0, jut = 0.0;
    int jmon2ut=0, jday2ut=0, jyear2ut=0, jmon2et=0, jday2et=0, jyear2et=0;
    double jet2 = 0.0, jut2 = 0.0;

    // Consecutive transit calculations need a minimum time difference
    final double MIN_TIME_DIFF = 1./24./3600./2.;

    // Default values for optional parameter:
    boolean withHeader = true;
    boolean backward = false;
    boolean isUt = false;
    boolean isUT = false;
    boolean calcSpeed = false;
    int sidmode=-1;                 // Means: tropical mode
    int whicheph = SweConst.SEFLG_SWIEPH;
    String ephepath = SweConst.SE_EPHE_PATH;
    double beginhour = 0;
    int pl2 = -2;                   // Means: not set
    boolean iterate = false;
    boolean countIsSet = false;
    boolean vcountIsSet = false;    // variable transit points
    boolean yogaTransit = true;
    boolean outputFormatIsSet = false;
    String outputFormat = "dt";
    double zm = 0;
    double zp = 0;

    // Required parameter:
    int pl1 = -2;
    boolean valIsSet = false;
    String begindate = null;
    String enddate = null;
    double endhour = 1./0.;         // Means: not set

    // Parameter values:
    int idxOffset = 0; // 0 to 5: lon / lat / dist / speed in lon / lat / dist
    double count = 1;
    String tvOffsetS = null;
    double tvOffset = 0;



    int iflag = 0;


    String sout;
    int i;
    String fname=SweConst.SE_FNAME_DE406;

    /*
     * parse command line
    */
    for (i = 0; i < argv.length; i++) {
      if (argv[i].equals("-hdate")) {
        System.out.println(infodate);
        sw.swe_close();
        return(0);
      } else if (argv[i].equals("-hex")) {
        if (infoexamples == null) {
          initHelpTexts();
        }
        System.out.println(infoexamples);
        sw.swe_close();
        return(0);
      } else if (argv[i].equals("-h") ||
                 argv[i].equals("-?")) {
        System.out.println(infocmd0);
        System.out.println(infocmd1);
        if (infoexamples == null) {
          initHelpTexts();
        }
        System.out.println(infocmd2);
        System.out.println(infoexamples);
        sw.swe_close();
        return(0);
      } else if (argv[i].equals("-head") ||
        argv[i].equals("-q")) {
        withHeader = false;
      } else if (argv[i].equals("-r")) {
        backward = true;
      } else if (argv[i].startsWith("-lon") ||
                 argv[i].startsWith("-lat") ||
                 argv[i].startsWith("-dist") ||
                 argv[i].startsWith("+lon") ||
                 argv[i].startsWith("+lat") ||
                 argv[i].startsWith("+dist")) {
          if (valIsSet) {
            System.err.println("Only one of -lon / -lat / -dist / +lon / +lat / +dist\n"+
                               "may be given. Use the -h option for more information.");
            return(1);
          }

          yogaTransit = (argv[i].charAt(0) == '+');
          argv[i] = "-" + argv[i].substring(1);

          String which=argv[i].substring(1,(argv[i].startsWith("-l")?4:5));
          String val=argv[i].substring((argv[i].startsWith("-l")?4:5));

          int idx = Math.max(val.lastIndexOf("-"), val.lastIndexOf("+"));
          if (idx > 0) {
              tvOffsetS = "-"+which+"@"+val.substring(idx);
              transitValS = "-"+which+"@"+val.substring(0,idx);
          } else {
            transitValS = "-"+which+"@"+val;
          }
          valIsSet=true;
          if (which.equals("lon")) {
            idxOffset = 0;
          } else if (which.equals("lat")) {
            idxOffset = 1;
          } else if (which.equals("dist")) {
            idxOffset = 2;
          }
      } else if (argv[i].startsWith("-topo")) {
        iflag |= SweConst.SEFLG_TOPOCTR;
        topoS=argv[i].substring(5);
      } else if (argv[i].startsWith("-sid")) {
        try {
          sidmode=Integer.parseInt(argv[i].substring(4));
          if (sidmode<0 || sidmode>20) {
            return invalidValue(argv[i],4);
          }
        } catch (NumberFormatException nf) {
          return invalidValue(argv[i],4);
        }
      } else if (argv[i].startsWith("-ejpl")) {
        whicheph = SweConst.SEFLG_JPLEPH;
        if (argv[i].length()>5) {
          fname=argv[i].substring(5);
        }
      } else if (argv[i].equals("-eswe")) {
        whicheph = SweConst.SEFLG_SWIEPH;
      } else if (argv[i].equals("-emos")) {
        whicheph = SweConst.SEFLG_MOSEPH;
      } else if (argv[i].startsWith("-edir")) {
        if (argv[i].length()>5) {
          ephepath=argv[i].substring(5);
        }
      } else if (argv[i].startsWith("-ut") ||
                 argv[i].startsWith("-t")) {
        int len=(argv[i].startsWith("-t")?2:3);
        isUt=(len==3);
        if (argv[i].length()>len) {
          beginhour = parseHour(argv[i].substring(len));
          iterate = true;
        }
      } else if (argv[i].startsWith("-UT") ||
                 argv[i].startsWith("-T")) {
        int len=(argv[i].startsWith("-T")?2:3);
        if (argv[i].length()<=len) {
          return invalidValue(argv[i],len);
        } else {
          isUT=(len==3);
          endhour = parseHour(argv[i].substring(len));
          iterate = true;
        }
      } else if (argv[i].equals("-s")) {
        calcSpeed = true;
      } else if (argv[i].startsWith("-b") ||
                 argv[i].startsWith("-j")) {
        if (argv[i].length()<=2) {
          return invalidValue(argv[i],2);
        } else {
          boolean wob = (argv[i].startsWith("-j"));
          if (begindate == null) {
            begindate = (wob?"j":"") + argv[i].substring(2);
          } else if (enddate == null) {
            enddate = (wob?"j":"") + argv[i].substring(2);
            iterate = true;
          } else {
            System.err.println("Invalid parameter combination:\n"+
                         "Excess -b option."+
                         "\nUse option '-h' for additional help.");
          }
        }
      } else if (argv[i].startsWith("-B") ||
                 argv[i].startsWith("-J")) {
        if (argv[i].length()<=2) {
          return invalidValue(argv[i],2);
        } else {
          boolean wob = (argv[i].startsWith("-J"));
          enddate = (wob?"j":"") + argv[i].substring(2);
          iterate = true;
          vcountIsSet = true;
        }
      } else if (argv[i].startsWith("-p")) {
        if (argv[i].length() == 3 ||
            argv[i].length() == 4) {
          pl1 = letter_to_ipl(argv[i].charAt(2));
          if (argv[i].length() == 4) {
            pl2 = letter_to_ipl(argv[i].charAt(3));
          }
        } else {
          return invalidValue(argv[i],2);
        }
      } else if (argv[i].startsWith("-P")) {
        if (argv[i].length() == 3) {
          pl2 = letter_to_ipl(argv[i].charAt(2));
        } else {
          return invalidValue(argv[i],2);
        }
      } else if (argv[i].startsWith("-f")) {
        if (argv[i].length() > 2) {
          outputFormat = argv[i].substring(2);
          outputFormatIsSet = true;
        } else {
          return invalidValue(argv[i],2);
        }
      } else if (argv[i].equals("-locales")) {
        String[] locs = el.getLocales();
        for (int n=0; n<locs.length; n++) {
          System.out.println(locs[n]);
        }
        return 0;
      } else if (argv[i].startsWith("-loc")) {
        locale = argv[i].substring(4);
        int idx24 = locale.indexOf("24");
        if (idx24 >= 0) {
          force24hSystem = true;
          locale = locale.substring(0,idx24) +
                   locale.substring(idx24+2);
        }
        if (locale.equals("swiss")) {
          locale = "de_DE";
          Nlocale = "en_US";
          nlocale = "en_US";
        }
      } else if (argv[i].startsWith("-iloc")) {
        Nlocale = argv[i].substring(5);
        int idx24 = Nlocale.indexOf("24");
        if (idx24 >= 0) {
          force24hSystem = true;
          Nlocale = Nlocale.substring(0,idx24) +
                    Nlocale.substring(idx24+2);
        }
        Dlocale = Nlocale;
      } else if (argv[i].startsWith("-Dloc")) {
        Dlocale = argv[i].substring(5);
        int idx24 = Dlocale.indexOf("24");
        if (idx24 >= 0) {
          force24hSystem = true;
          Dlocale = Dlocale.substring(0,idx24) +
                    Dlocale.substring(idx24+2);
        }
      } else if (argv[i].startsWith("-Nloc")) {
        Nlocale = argv[i].substring(5);
        int idx24 = Nlocale.indexOf("24");
        if (idx24 >= 0) {
          force24hSystem = true;
          Nlocale = Nlocale.substring(0,idx24) +
                    Nlocale.substring(idx24+2);
        }
      } else if (argv[i].startsWith("-oloc")) {
        dlocale = argv[i].substring(5);
        int idx24 = dlocale.indexOf("24");
        if (idx24 >= 0) {
          force24hSystem = true;
          dlocale = dlocale.substring(0,idx24) +
                    dlocale.substring(idx24+2);
        }
        nlocale = dlocale;
      } else if (argv[i].startsWith("-dloc")) {
        dlocale = argv[i].substring(5);
        int idx24 = dlocale.indexOf("24");
        if (idx24 >= 0) {
          force24hSystem = true;
          dlocale = dlocale.substring(0,idx24) +
                    dlocale.substring(idx24+2);
        }
      } else if (argv[i].startsWith("-nloc")) {
        nlocale = argv[i].substring(5);
        int idx24 = nlocale.indexOf("24");
        if (idx24 >= 0) {
          force24hSystem = true;
          nlocale = nlocale.substring(0,idx24) +
                    nlocale.substring(idx24+2);
        }
      } else if (argv[i].startsWith("-n")) {
        try {
          count = Integer.parseInt(argv[i].substring(2));
          iterate = true;
          countIsSet = true;
        } catch (NumberFormatException nf) {
          return invalidValue(argv[i],2);
        }
      } else if (argv[i].startsWith("-N")) {
        try {
          count = Integer.parseInt(argv[i].substring(2));
          iterate = true;
          vcountIsSet = true;
        } catch (NumberFormatException nf) {
          return invalidValue(argv[i],2);
        }
      } else {
        System.out.println("illegal option "+argv[i]);
        return(1);
      }
    }

    if (withHeader) {
      for (i = 0; i < argv.length; i++) { System.out.print(argv[i]+" "); }
      System.out.println();
    }

    // Internationalization:
    // Actually, we have five locales:
    // -loc          (var. locale) locale for ALL parsing and formatting
    //                      is overridden by the more specific locales
    // -Dloc (-iloc) (var. Dlocale) locale to parse dates
    // -Nloc (-iloc) (var. Nlocale) locale to parse numbers
    // -dloc (-oloc) (var. dlocale) locale to format dates
    // -nloc (-oloc) (var. nlocale) locale to format numbers
    locale = checkLocale(locale, "");
    Dlocale = checkLocale(Dlocale, locale);
    Nlocale = checkLocale(Nlocale, locale);
    dlocale = checkLocale(dlocale, locale);
    nlocale = checkLocale(nlocale, locale);

    dif = el.createLocDateTimeFormatter(Dlocale, true); // DateInputFormat
    dof = el.createLocDateTimeFormatter(dlocale, force24hSystem); // DateOutputFormat
    dnof = NumberFormat.getInstance(el.getLocale(nlocale)); // NumberOutputFormat
    dateFracSeparator = el.getDecimalSeparator(dnof);
    nnif = NumberFormat.getInstance(el.getLocale(Nlocale));
    nnif.setGroupingUsed(false);
    numIFracSeparator = el.getDecimalSeparator(nnif);
    nnof = NumberFormat.getInstance(el.getLocale(nlocale));
    nnof.setGroupingUsed(false);
    nnof.setMaximumFractionDigits(12);
    numOFracSeparator = el.getDecimalSeparator(nnof);
    secondsIdx = el.getPatternLastIdx(dof.toPattern(), "s", dof); // No input with parts of seconds?


    String par = null;
    tvOffset = readLocalizedDouble(tvOffsetS, nlocale);
    transitVal = readLocalizedDouble(transitValS, nlocale);


    // If -topo is given, read topographic values (lon / lat / height)
    if (topoS != null && topoS.length() > 0) {
      try {
        // Read number of fields and normalize fields to be separated by ';'
        int cnt=0;
        for(int k=0; k<topoS.length(); k++) {
          if (!Character.isDigit(topoS.charAt(k))) {
            char ch = topoS.charAt(k);
            if (ch != '-' && ch != '+') {
              if (!numIFracSeparator.equals(""+ch)) { // Well, can be a string, probably????
                topoS = topoS.substring(0,k) + ";" + topoS.substring(k+1);
                cnt++;
              }
            }
          }
        }
        // We need exactly three fields, meaning two field separators:
        if (cnt!=2) {
          return invalidValue("-topo"+topoS,5,Nlocale);
        }
        // Read field values:
        String ts = topoS;
        top_long=nnif.parse(ts.substring(0,ts.indexOf(';'))).doubleValue();
        ts=ts.substring(ts.indexOf(';')+1);
        top_lat=nnif.parse(ts.substring(0,ts.indexOf(';'))).doubleValue();
        ts=ts.substring(ts.indexOf(';')+1);
        top_elev=nnif.parse(ts).doubleValue();
      } catch (StringIndexOutOfBoundsException se) {
      } catch (Exception e) {
        return invalidValue("-topo"+topoS,5,Nlocale);
      }
    }


    // Interpret and check parameters and parameter combinations:
    // Set ephemeris data file paths:
    iflag = (iflag & ~SweConst.SEFLG_EPHMASK) | whicheph;
    String argv0=System.getProperties().getProperty("user.dir");
    if (ephepath.length() > 0) {
      sw.swe_set_ephe_path(ephepath);
    } else if (make_ephemeris_path(iflag, argv0) == SweConst.ERR) {
      iflag = (iflag & ~SweConst.SEFLG_EPHMASK) | SweConst.SEFLG_MOSEPH;
      whicheph = SweConst.SEFLG_MOSEPH;
    }
    if ((whicheph & SweConst.SEFLG_JPLEPH)!=0) {
      sw.swe_set_jpl_file(fname);
    }


    // Check for required parameters
    if (pl1 == -2) {
      System.err.println("Specify a planet with the -p option!\n"+
                         "Use the -h option for more information.");
      return(1);
    }
    if (begindate == null) {
      System.err.println("Specify a (starting) date with the -b option!\n"+
                         "Use the -h option for more information.");
      return(1);
    }
    if (!valIsSet) {
      System.err.println("Specify a longitude, latitude or distance value "+
                         "to be transited\nwith the -lon, -lat, -dist, +lon, "+
                         "+lat or +dist option!\nUse the -h option for "+
                         "more information.");
      return(1);
    }

    // A second time gets set equal to the first time, if it is not given
    // on the command line:
    if (Double.isInfinite(endhour)) {
      endhour = beginhour;
      isUT = isUt;
    }


    // Add the transit flags to the calculation flags:
    if (idxOffset==0) {
      iflag |= SweConst.SEFLG_TRANSIT_LONGITUDE;
    } else if (idxOffset==1) {
      iflag |= SweConst.SEFLG_TRANSIT_LATITUDE;
    } else if (idxOffset==2) {
      iflag |= SweConst.SEFLG_TRANSIT_DISTANCE;
    }
    if (calcSpeed) {
      iflag |= SweConst.SEFLG_TRANSIT_SPEED;
      idxOffset += 3;
    }

    // Initialize SweDate objects to the given times and dates:
    sde1 = makeDate(begindate, beginhour, isUt, Dlocale);
    sde2 = makeDate(enddate, endhour, isUT, Dlocale);

    // Swap dates if necessary:
    if (sde2 != null &&
        ((!backward && sde1.getJulDay() > sde2.getJulDay()) ||
        (backward && sde1.getJulDay() < sde2.getJulDay()))) {
      SweDate tmpdate = sde1;
      sde1 = sde2;
      sde2 = tmpdate;
    }

    double tjde=sde1.getJulDay();
    jyearet=sde1.getYear();
    jmonet=sde1.getMonth();
    jdayet=sde1.getDay();
    jet=sde1.getHour();
    sdu1=new SweDate(tjde + sde1.getDeltaT());
    jyearut=sdu1.getYear();
    jmonut=sdu1.getMonth();
    jdayut=sdu1.getDay();
    jut=sdu1.getHour();

    double tjde2=0.;
    if (sde2 != null) {
      tjde2=sde2.getJulDay();
      jyear2et=sde2.getYear();
      jmon2et=sde2.getMonth();
      jday2et=sde2.getDay();
      jet2=sde2.getHour();
      sdu2=new SweDate(tjde2 - sde2.getDeltaT());
      jyear2ut=sdu2.getYear();
      jmon2ut=sdu2.getMonth();
      jday2ut=sdu2.getDay();
      jut2=sdu2.getHour();
    }

    // vcountIsSet is only meaningful, when we have changing values:
    if (tvOffset == 0.) {
      vcountIsSet = false;
    }

    // -lon..+... etc. will additionally output the requested degree ('v')
    // before the date and time ('d'):
    if (outputFormat.charAt(0) == '+' && // Additional format options given AND multiple varying degrees
        (tvOffset != 0. && count > 1)) {
      outputFormat = "vdt" + outputFormat.substring(1);
    } else if (!outputFormatIsSet &&
        (tvOffset != 0. && count > 0)) { // Add 'v' for default format with mutiple varying degrees
      outputFormat = "vdt";
    } else if (outputFormat.charAt(0) == '+') { // Additional format options appended to default options
      // -f+... will add output formats to the default 'dt':
      outputFormat = "dt" + outputFormat.substring(1);
    }

    // Checks...
    // Parameter combinations:
    //   -p. -b... +-lon/lat/dist [other options]
    //   -p. -P. -b... +-lon/lat/dist [other options]
    //   -p. -b... -B... +-lon/lat/dist [other options]
    //   -p. -P. -b... -B... +-lon/lat/dist [other options]

    boolean invalidComb=false;
    if ((enddate != null && countIsSet)) { // 2 times => !countIsSet
      invalidComb=true;
    }
    if (invalidComb) {
      System.err.println("Invalid parameter combination.\n"+infocmd1+
                         "\nUse option '-h' for additional help.");
      return(1);
    }


    if (pl1 == -1 || pl2 == -1) {
      System.err.println("Unsupported planet! Check for valid "+
                         "planets with the '-h' option.");
      return(1);
    }

    if (yogaTransit && pl2 < 0) {
      System.err.println("Yoga Transits can only be computed on two planets." +
                         "\nUse '-h' option for valid parameter combinations.");
      return(1);
    }

    sw.swe_set_topo(top_long, top_lat, top_elev);

    if (sidmode>=0) {
      sw.swe_set_sid_mode(sidmode,0.,0.);
      iflag |= SweConst.SEFLG_SIDEREAL;
    }


    ///////////////////////////////////////////////////////////////////
    // Here the output and calculation starts with the output of the //
    // header if requested:                                          //
    ///////////////////////////////////////////////////////////////////
    if (withHeader) {
      if (sidmode>=0) {
        System.out.print("Ayanamsha");
        if (enddate == null) {
           System.out.print(":         "+
                                       doubleToDMS(sw.swe_get_ayanamsa(tjde)));
        } else {
           System.out.println("\n on begin date:    "+
                                       doubleToDMS(sw.swe_get_ayanamsa(tjde)));
           System.out.println(" on end date:      "+
                                      doubleToDMS(sw.swe_get_ayanamsa(tjde2)));
        }
      }
      System.out.println();
      if (isUt) {
        System.out.println("begin date:        "+jdToDate(sde1, true, 0)+" UT");
      } else {
        System.out.println("begin date:        "+jdToDate(sde1, false, 0)+" ET");
      }
      if (enddate!=null) {
        if (isUT) {
          System.out.println("end date:          "+jdToDate(sde2, true, 0)+" UT");
        } else {
          System.out.println("end date:          "+jdToDate(sde2, false, 0)+" ET");
        }
      }
      if (!yogaTransit) {
        System.out.println("Transiting planet: "+sw.swe_get_planet_name(pl1));
      }
      System.out.print("Reference point:   ");
      if (calcSpeed) {
        if (pl2 >= 0) {
          if (yogaTransit) {
            System.out.println("combined speed of "+nnof.format(Math.abs(transitVal))+swed.ODEGREE_CHAR+"/day "+
              "of planets " + sw.swe_get_planet_name(pl1)+" and " + sw.swe_get_planet_name(pl2));
          } else {
            System.out.println("speed of "+nnof.format(Math.abs(transitVal))+swed.ODEGREE_CHAR+"/day "+
              (transitVal<0?"lower":"higher")+" than speed of '"+
                  sw.swe_get_planet_name(pl2)+"'");
          }
          System.out.println("                   in "+
                  (idxOffset==3?"longitudinal":(idxOffset==4?"latitudinal":"distance"))+
                  " direction");
        } else {
          System.out.println(nnof.format(transitVal)+swed.ODEGREE_CHAR+"/day in "+
                    (idxOffset==3?"longitudinal":(idxOffset==4?"latitudinal":"distance"))+
                    " direction"+
                    (sidmode>=0?" in the sidereal zodiac":""));
        }
        System.out.println();
        if ((iflag&SweConst.SEFLG_TOPOCTR)!=0) {
          System.out.println("Topographic pos.:  "+
            doubleToDMS(Math.abs(top_long))+(top_long<0?"S":"N")+"/"+doubleToDMS(Math.abs(top_lat))+(top_lat<0?"W":"E")+"/"+nnof.format(top_elev)+"m");
        }
      } else { // Transit over a lon / lat / dist position:
        if (pl2 >= 0) {
          if (yogaTransit) {
            System.out.println("combined positions of '" +
                  sw.swe_get_planet_name(pl1) + "' and '" +
                  sw.swe_get_planet_name(pl2) + "' reach " +
                  nnof.format(Math.abs(transitVal)) + " " +
                  (idxOffset==2?"AU ":"degrees ")+
                  "\n                   in " +
                  (idxOffset==0?"longitudinal":(idxOffset==1?"latitudinal":"distance"))+
                  " direction"+(sidmode>=0?" in the sidereal zodiac":""));
          } else {
            System.out.println(nnof.format(Math.abs(transitVal))+" "+(idxOffset==2?"AU ":"degrees ")+
                (transitVal<0?"before ":"after ")+
                (idxOffset==0?"longitudinal":(idxOffset==1?"latitudinal":"distance"))+
                " position of '"+sw.swe_get_planet_name(pl2)+"'");
          }
        } else {
          System.out.println(nnof.format(transitVal)+(idxOffset==2?" AU ":""+swed.ODEGREE_CHAR+" ")+
              (idxOffset==0?"of longitude":(idxOffset==1?"of latitude":"distance"))+
                    (sidmode>=0?" in the sidereal zodiac":""));
        }
        if ((iflag&SweConst.SEFLG_TOPOCTR)!=0) {
          System.out.println("Topographic pos.:  "+
            doubleToDMS(Math.abs(top_long))+(top_long<0?"S":"N")+"/"+doubleToDMS(Math.abs(top_lat))+(top_lat<0?"W":"E")+"/"+nnof.format(top_elev)+"m");
        }
      }
      System.out.println();
    } // withHeader


    ///////////////////////////////////////////////////////////////////
    // Calculation and output                                        //
    ///////////////////////////////////////////////////////////////////

    if (yogaTransit) {
      iflag |= SweConst.SEFLG_YOGA_TRANSIT;
    }

    // Select the proper TransitCalculator:
    TransitCalculator tc = null;
    if (pl2 >= 0) {  // relative transit between two planets
      tc = new TCPlanetPlanet(sw, pl1, pl2, iflag, transitVal);
//    } else if (house1 >= 0) {  // planet - house transit
    } else { // transit of ONE planet over a position or speed
      tc = new TCPlanet(sw, pl1, iflag, transitVal);
    }

    boolean firstCalculation = true;
    boolean rollover = tc.getRollover();

    if (rollover && vcountIsSet) {
      // Negative offsets are mapped to positive ones, as we just look
      // for transitVal - tvOffset, transitVal and transitVal + tvOffset
      // in -b... -B... / or -N options.
      tvOffset = Math.abs(tvOffset)%360.;
    }


    double jdET2 = (backward?-Double.MAX_VALUE:Double.MAX_VALUE);
    if (sde2 != null) {
      jdET2 = sde2.getJulDay();
    }

    while(count > 0 ||
          (enddate != null && !backward && tjde < jdET2) ||
          (enddate != null && backward && tjde > jdET2)) {
      try {
        count--;

        tc.setOffset(transitVal);
        double z = sw.getTransitET(tc, tjde, backward, jdET2);
        // Calculate dates of neighbouring transit points if necessary
        if (vcountIsSet && !firstCalculation) {
          if (rollover) {
            tc.setOffset(transitVal - tvOffset);
            try {
              zm = sw.getTransitET(tc, tjde, backward, z);
            } catch (SwissephException swe) {
              if (swe.getType() != SwissephException.BEYOND_USER_TIME_LIMIT) {
                throw swe;
              }
              zm = (backward?-Double.MAX_VALUE:Double.MAX_VALUE);
            }
          }

          tc.setOffset(transitVal + tvOffset);
          try {
            zp = sw.getTransitET(tc, tjde, backward, z);
          } catch (SwissephException swe) {
            if (swe.getType() != SwissephException.BEYOND_USER_TIME_LIMIT) {
              throw swe;
            }
            zp = (backward?-Double.MAX_VALUE:Double.MAX_VALUE);
          }
        } // End of neighbouring transit point calculations

         // Selected closest transit point
        if (vcountIsSet && !firstCalculation) {
          if (backward) {
            if (rollover && zm > z && zp <= zm) { z = zm; transitVal -= tvOffset; }
            if (zp > z) { z = zp; transitVal += tvOffset; }
          } else {
            if (rollover && zm < z && zp >= zm) { z = zm; transitVal -= tvOffset; }
            if (zp < z) { z = zp; transitVal += tvOffset; }
          }
        }
        firstCalculation = false;

        // Stop calculation, when enddate had been reached
        if (sde2 != null &&
            ((!backward && z > sde2.getJulDay()) ||
            (backward && z < sde2.getJulDay()))) {
          break;
        }


        // Output calculated data
        sde1.setJulDay(z);
        if (rollover) {
          while (transitVal < 0) { transitVal += 360; }
          while (transitVal > 360) { transitVal -= 360; }
        }
        System.out.println(printFormatted(outputFormat, sde1, isUt, pl1, pl2, iflag, yogaTransit));

        // Initialize next calculations
        tjde = sde1.getJulDay() + (backward?-MIN_TIME_DIFF:MIN_TIME_DIFF);
        if (!vcountIsSet) {
          transitVal += tvOffset;
        }
      } catch (IllegalArgumentException il) {
        System.err.println("\n"+il.getMessage()+"\n\n");
      } catch (SwissephException swe) {
        if (swe.getType() == SwissephException.OUT_OF_TIME_RANGE) {
          System.err.println("\n"+swe.getMessage());
          break;
        } else if (swe.getType() == SwissephException.BEYOND_USER_TIME_LIMIT) {
          break;
        }
        System.err.println("\n"+swe.getMessage());
        tjde = swe.getJD();
      }
    } // while

    sw.swe_close();
    return 0;
  }


  // A number of e.g. 263.83 will be parsed as 263.83 in 'en' locale, but as
  // 263 only in 'de' locale. This method parses the double in the appropriate
  // locale and tries to check for validity.
  // The String 's' has to contain the parameter name + "@" + double value,
  // eg. "-lon@234.8903"
  double readLocalizedDouble(String s, String locale) {
    double val = 0.;
    char ch = '+';
    if (s != null) {
      String num = s.substring(s.indexOf("@")+1);
      String par = s.substring(0,s.indexOf("@"))+num;
      try {
        ch = num.charAt(0);
        if (!Character.isDigit(ch)) {
          num = num.substring(1);
        }
        val = nnif.parse(num).doubleValue();
      } catch (ParseException pe) {
        return invalidValue(par,par.length());
      }
      for(int k = num.length()-1; k >= 0; k--) {
        if (!Character.isDigit(num.charAt(k)) && k < num.length()-2) {
          if (val == (int)val) {
            return invalidValue(par,s.indexOf("@"),locale);
          }
        }
      }
      return (ch=='-'?-val:val);
    }
    return 0.;
  }

  // Print the date according to the format string given by the -f flag.
  // Planets and (calculation) flag are only needed for calculation of
  // actual positions or speeds when using -fp
  String printFormatted(String format, SweDate sd, boolean ut, int pl1, int pl2, int flags, boolean yogaTransit) {
    String s = "";
    boolean cntIsSet = false;
    int cnt = 0;
    for (int n = 0; n < format.length(); n++) {
      if (n > 0) {
        s += "   ";
      }
      switch ((int)format.charAt(n)) {
        // Print date and time:
        case (int)'d': // read optional decimal places:
                       cnt = 0;
                       for (int i = n+1; i < format.length(); i++) {
                         if (Character.isDigit(format.charAt(i))) {
                           cnt = cnt * 10 +
                                 Character.getNumericValue(format.charAt(i));
                           n++;
                         } else {
                           break;
                         }
                       }
                       s += jdToDate(sd, ut, cnt);
                       break;
        case (int)'t': // Append 'ET' or 'UT':
                       cnt = 0;
                       if (n != 0) {
                         s = s.substring(0,s.length() - 2);
                       }
                       s += (ut?"UT":"ET");
                       break;
        // Print date as julian day number:
        case (int)'j': // read optional decimal places:
                       cnt = 0;
                       cntIsSet = false;
                       for (int i = n+1; i < format.length(); i++) {
                         if (Character.isDigit(format.charAt(i))) {
                           cntIsSet = true;
                           cnt = cnt * 10 +
                                 Character.getNumericValue(format.charAt(i));
                           n++;
                         } else {
                           break;
                         }
                       }
                       if (!cntIsSet) { cnt = 8; }
                       s += printJD(sd.getJulDay(), ut, cnt);
                       break;
        // Output degree etc.:
        case (int)'v': // read optional decimal places:
                       cnt = 0;
                       cntIsSet = false;
                       for (int i = n+1; i < format.length(); i++) {
                         if (Character.isDigit(format.charAt(i))) {
                           cntIsSet = true;
                           cnt = cnt * 10 +
                                 Character.getNumericValue(format.charAt(i));
                           n++;
                         } else {
                           break;
                         }
                       }
                       if (!cntIsSet) { cnt = 2; }
                       s += printFloat(transitVal, 3, cnt) + swed.ODEGREE_CHAR+"";
                       if ((flags & SweConst.SEFLG_TRANSIT_SPEED) != 0) {
                         s += "/day";
                       }
                       break;
        // Output actual position or speed:
        // 'P' adds output of two planets position (speed) difference:
        case (int)'p':
        case (int)'P': boolean printSum = (format.charAt(n) == 'P');
                       // read optional decimal places:
                       cnt = 0;
                       cntIsSet = false;
                       for (int i = n+1; i < format.length(); i++) {
                         if (Character.isDigit(format.charAt(i))) {
                           cntIsSet = true;
                           cnt = cnt * 10 +
                                 Character.getNumericValue(format.charAt(i));
                           n++;
                         } else {
                           break;
                         }
                       }
                       if (!cntIsSet) { cnt = 8; }
                       StringBuffer serr = new StringBuffer();
                       double[] xx = new double[6];
                       int idx = 0;
                       if ((flags & SweConst.SEFLG_TRANSIT_LATITUDE) != 0) {
                         idx = 1;
                       } else if ((flags & SweConst.SEFLG_TRANSIT_DISTANCE) != 0) {
                         idx = 2;
                       }
                       int pflags = flags;
                       if ((flags & SweConst.SEFLG_TRANSIT_SPEED) != 0) {
                         idx += 3;
                         pflags |= SweConst.SEFLG_SPEED;
                       }
                       pflags &= ~(SweConst.SEFLG_TRANSIT_LONGITUDE |
                                   SweConst.SEFLG_TRANSIT_LATITUDE |
                                   SweConst.SEFLG_TRANSIT_DISTANCE |
                                   SweConst.SEFLG_TRANSIT_SPEED);

                       int ret = sw.swe_calc(sd.getJulDay(), pl1, pflags, xx, serr);
                       if (ret < 0) {
                         s += serr.toString().substring(0,10)+"...";
                       } else {
                         s += printFloat(xx[idx], 4, cnt);
                       }
// Puuuh: we need to calc it from tc!!!???!?!?!?
                       if (pl2 >= 0) {
                         double x1 = xx[idx];
                         s += "  ";
                         ret = sw.swe_calc(sd.getJulDay(), pl2, pflags, xx, serr);
                         if (ret < 0) {
                           s += serr.toString().substring(0,10)+"...";
                           break;
                         } else {
                           s += printFloat(xx[idx], 3, cnt);
                         }
                         if (printSum) {
                           x1 += (yogaTransit?xx[idx]:-xx[idx]);
                           if (idx >= 1) {
                             s += " / " + printFloat(x1, 3, cnt);
                           } else {
                             s += " / " + printFloat((x1+360.0)%360.0, 3, cnt);
                           }
                         }
                       }
                       break;
      }
    }
    return s;
  }

  double parseHour(String s) {
    if (s.equals("now")) {
      java.util.Calendar cal=java.util.Calendar.getInstance(java.util.TimeZone.getTimeZone("GMT"));
      return cal.get(java.util.Calendar.HOUR_OF_DAY)+
             cal.get(java.util.Calendar.MINUTE)/60.+
             cal.get(java.util.Calendar.SECOND)/3600.;
    }
    // 18:23:45 or shorter versions or other separators
    int[] h=new int[]{0,0,0};
    int field=0;
    for (int n=0; n<s.length();) {
      if (Character.isDigit(s.charAt(n))) {
        h[field]*=10;
        h[field]+=((int)s.charAt(n)-(int)'0');
        n++;
      } else {
        field++;
        if (field>h.length) { break; } // Without any comment?
        while (n<s.length()-1 && !Character.isDigit(s.charAt(++n)));
      }
    }
    return h[0]+h[1]/60.+h[2]/3600.;
  }


  int invalidValue(String arg, int idx) {
    return invalidValue(arg, idx, null);
  }
  int invalidValue(String arg, int idx, String locale) {
    if (arg.length()==idx) {
      System.err.println("\nMissing argument to parameter '"+arg+
                         "'.\nTry option -h for a syntax description.");
    } else {
      System.err.println("\nInvalid argument ("+arg.substring(idx)+
                         ") to parameter '"+arg.substring(0,idx)+
                         "'.\n"+(locale != null?"Maybe the locale "+
                         "requires a different format.\nAlso t":"\nT")+
                         "ry option -h for a syntax description.");
    }
    return(1);
  }


  String checkLocale(String locale, String defLocale) {
    if ("".equals(defLocale)) {
      defLocale = Locale.getDefault().toString();
    }
    
    if (locale == null) {
      return defLocale;
    }
    if ("".equals(locale)) {
      return Locale.getDefault().toString();
    }
    for(int n=0; n < locs.length; n++) {
      if (locs[n].toString().equals(locale)) {
        return locale;
      }
    }
System.err.println("Warning: Locale '" + locale + "' not found, using default locale '" + defLocale + "'");
System.err.println("Use option -locales to list all available Locales.");
    return defLocale;
  }


  /**
  * Returns date + time in localized form from a JDET in a SweDate
  * object
  */
  String jdToDate(SweDate sdET, boolean printUT, int decPlaces) {
    if (decPlaces > 18) { decPlaces = 18; }

    SweDate sx = new SweDate(
                        sdET.getJulDay()-
                        (printUT?sdET.getDeltaT():0)+
                        0.5/24./3600/Math.pow(10,decPlaces));

    // sx.getDate() will round a second time. We have to inhibit
    // this be cutting of the spare decimal places beyond the
    // the relevant numbers

    double hour=sx.getHour();
    int minutes=(int)((hour%1.)*60.);
    int seconds=(int)(((hour*60.)%1.)*60.);
    double mseconds=((hour*3600.)%1.);

    String s1 = "";
    String s2 = "";

    String s = null;
    String pat = dof.toPattern();
    // sx.getDate() will round by itself, but rounding is not allowed
    // to occur anymore. We only need an accuracy to a second for
    // getDate(), so let us cut off the rest here already!
    sx.setJulDay(sx.getJulDay() - mseconds/24./3600. + 0.5/24./3600.);

    if (sx.getYear() == 0) {
      // DateFormat.format() will not allow a year "0"...
      // Hack: We take the normal pattern and replace 0001 by 0000
      s = dof.format(sx.getDate(0));
      int idxy = el.getPatternLastIdx(pat, "yyyy", dof);

      s = s.substring(0,idxy+2) +
          s.substring(idxy,idxy+1) +
          s.substring(idxy+3);
    } else if (sx.getYear() < 0) {
      // We add one year, as the date formatter skips year 0
      sx.setYear(sx.getYear()+1);

      int idx = pat.indexOf("y");
      SimpleDateFormat dfm = (SimpleDateFormat)dof.clone();
      // If the separator between day, month and year is '-' we have to
      // (should?) distinguish it from the '-' of the year, so we add a
      // space before the year in this case. See locales da / da_DK /
      // es_BO / es_CL / es_HN / es_NI / es_PR / es_SV / nl / nl_NL /
      // pt / pt_PT.
      String sep = (idx>0 && pat.charAt(idx-1)=='-'?" ":"");
      pat = pat.substring(0,idx) + sep + "'-'" + pat.substring(idx);
      dfm.applyPattern(pat);
      s = dfm.format(sx.getDate(0));
    } else {
      s = dof.format(sx.getDate(0));
    }

    if (decPlaces > 0) {
      secondsIdx = el.getPatternLastIdx(pat, "ss", dof);
      s1 = s.substring(0,secondsIdx + 1/* +idxAdd */);
      s2 = s.substring(secondsIdx + 1/* +idxAdd */);
      s = dateFracSeparator;
      for (int i = decPlaces; i > 0; i--) {
        mseconds = (mseconds * 10) % 10;
        s += dnof.format((int)mseconds);
      }
    }

    return s1 + s + s2;
  }

  // Prints a floatingpoint number internationalized and
  // with correct rounding to a given number of decimal places
  String printFloat(double val, int width, int decPlaces) {
    if (decPlaces > 18) { decPlaces = 18; }
    // Well, could be another width, if necessary...
    if (width > 61) { width = 61; }
    String s = "                                                            ";
    if (val < 0) {
      val = -val;
      s += "-";
    }

    val += 0.5/Math.pow(10,decPlaces);

    int len = (String.valueOf((int)val)).length();
    s += nnof.format((int)val);
    s = s.substring(Math.max(len,s.length()-width));

    if (decPlaces > 0) {
      s += numOFracSeparator;
      double parts = val - (int)val;
      for (int i = decPlaces; i > 0; i--) {
        parts = (parts * 10) % 10;
        s += nnof.format((int)parts);
      }
    }

    return s;
  }


  String printJD(double jd, boolean printUT, int decPlaces) {
    if (decPlaces > 18) { decPlaces = 18; }

    SweDate sx = new SweDate(
                        jd-
                        (printUT?SweDate.getDeltaT(jd):0)+
                        0.5/Math.pow(10,decPlaces));
    jd = sx.getJulDay();

    String s = nnof.format((int)jd);

    if (decPlaces > 0) {
      s += numOFracSeparator;
      double parts = Math.abs(jd - (int)jd);
      for (int i = decPlaces; i > 0; i--) {
        parts = (parts * 10) % 10;
        s += nnof.format((int)parts);
      }
    }

    return s;
  }


  String doubleToDMS(double d) {
    int deg=(int)d;
    int min=(int)((d%1.)*60.);
    int sec=(int)(((d*60.)%1.)*60.);
    return ((deg<10?" ":"")+nnof.format(deg)+swed.ODEGREE_CHAR+""+
            (min<10?nnof.format(0):"")+nnof.format(min)+"'"+
            (sec<10?nnof.format(0):"")+nnof.format(sec)+"\"");
  }


  // Returns ET date from the (localized) String dt
  SweDate makeDate(String dt, double hour, boolean ut, String locString) {
    if (dt==null) { return null; }
    Locale loc = el.getLocale(locString);
    SweDate sd = new SweDate();
    boolean gregflag = SweDate.SE_GREG_CAL;

    if (dt.charAt(0) == 'j') {   /* parse a julian day number */
      double tjd = 0.;
      tjd = readLocalizedDouble("-b' or '-B@" + dt.substring(1), Nlocale);
      if (ut) {
        // The JD is meant to represent a UT date,
        // but this method is to return ET:
        tjd += sd.getDeltaT(tjd);
      }
      if (tjd < sd.getGregorianChange()) {
        gregflag = SweDate.SE_JUL_CAL;
      } else {
        gregflag = SweDate.SE_GREG_CAL;
      }
      if (dt.indexOf("jul") >= 0) {
        gregflag = SweDate.SE_JUL_CAL;
      } else if (dt.indexOf("greg") >= 0) {
        gregflag = SweDate.SE_GREG_CAL;
      }
      sd.setJulDay(tjd);
      sd.setCalendarType(gregflag,SweDate.SE_KEEP_JD); // Keep JulDay number!
    } else if (dt.equals("today")) {
      java.util.Calendar cal=java.util.Calendar.getInstance(java.util.TimeZone.getTimeZone("GMT"));
      dt=dif.format(cal.getTime());
      return makeDate(dt, hour, ut, locString);
    } else { // Parse a date string
      // We just parse the date as a sequence of three integers
      // How these three numbers will be interpreted (y-m-d or d-m-y
      // or whatever) will be determined from the locale "loc".
      int ints[] = new int[]{0, 0, 0};
      int jday, jmon, jyear;
      int i=0, n=0;
      boolean neg=false;
      try {
        for (; n<3; n++) {
          ints[n] = 0;
          neg=(dt.charAt(i)=='-');
          if (neg) { i++; }
          while (Character.isDigit(dt.charAt(i))) {
            ints[n]=ints[n]*10+Character.digit(dt.charAt(i++),10);
          }
          if (neg) { ints[n]=-ints[n]; }
          while (!Character.isDigit(dt.charAt(i)) && dt.charAt(i)!='-') { i++; }
        }
      } catch (StringIndexOutOfBoundsException siobe) {
        if (neg) { ints[n]=-ints[n]; }
      } catch (ArrayIndexOutOfBoundsException aob) {
      }
      // order the integers into date parts according to the locale:
      String pat = dif.toPattern().toLowerCase();
      int d = pat.indexOf("d");
      int m = pat.indexOf("m");
      int y = pat.indexOf("y");
      if (m < d && m < y) {
        jmon = ints[0];
        if (y < d) {  // m-y-d
          jyear = ints[1]; jday = ints[2];
        } else {      // m-d-y
          jday = ints[1]; jyear = ints[2];
        }
      } else if (y < d && y < m) {
        jyear = ints[0];
        if (m < d) {  // y-m-d
          jmon = ints[1]; jday = ints[2];
        } else {      // y-d-m
          jday = ints[1]; jmon = ints[2];
        }
      } else {
        jday = ints[0];
        if (m < y) {  // d-m-y
          jmon = ints[1]; jyear = ints[2];
        } else {      // d-y-m
          jyear = ints[1]; jmon = ints[2];
        }
      }

      sd.setDate(jyear,jmon,jday,hour,SweDate.SE_GREG_CAL);
      if (ut) { sd.setJulDay(sd.getJulDay() + sd.getDeltaT()); } // We need ET in this method...
      if (sd.getJulDay() < sd.getGregorianChange()) {
        gregflag = SweDate.SE_JUL_CAL;
      } else {
        gregflag = SweDate.SE_GREG_CAL;
      }
      if (dt.indexOf("jul") >= 0) {
        gregflag = SweDate.SE_JUL_CAL;
      } else if (dt.indexOf("greg") >= 0) {
        gregflag = SweDate.SE_GREG_CAL;
      }
      sd.setCalendarType(gregflag,SweDate.SE_KEEP_DATE); // Keep Date!
    }
    return sd;
  }

  /* make_ephemeris_path().
   * ephemeris path includes
   *   current working directory
   *   + program directory
   *   + default path from swephexp.h on current drive
   *   +                              on program drive
   *   +                              on drive C:
   */
  int make_ephemeris_path(int iflag, String argv0) {
    String path="", s="";
    int sp;
    String dirglue = swed.DIR_GLUE;
    int pathlen=0;
    /* moshier needs no ephemeris path */
    if ((iflag & SweConst.SEFLG_MOSEPH)!=0)
      return SweConst.OK;
    /* current working directory */
    path="."+swed.PATH_SEPARATOR;
    /* program directory */
    sp = argv0.lastIndexOf(dirglue);
    if (sp >= 0) {
      pathlen = sp;
      if (path.length() + pathlen < swed.AS_MAXCH-1) {
        s=argv0.substring(0,pathlen);
        path+=s+swed.PATH_SEPARATOR;
      }
    }
    if (path.length() + pathlen < swed.AS_MAXCH-1)
      path+=SweConst.SE_EPHE_PATH;
    return SweConst.OK;
  }

  int letter_to_ipl(char letter) {
    if (letter >= '0' && letter <= '9')
      return (int)letter - '0' + SweConst.SE_SUN;
    switch ((int)letter) {
      case (int)'m': return SweConst.SE_MEAN_NODE;
      case (int)'t': return SweConst.SE_TRUE_NODE;
      case (int)'A': return SweConst.SE_MEAN_APOG;
      case (int)'B': return SweConst.SE_OSCU_APOG;
      case (int)'D': return SweConst.SE_CHIRON;
      case (int)'E': return SweConst.SE_PHOLUS;
      case (int)'F': return SweConst.SE_CERES;
      case (int)'G': return SweConst.SE_PALLAS;
      case (int)'H': return SweConst.SE_JUNO;
      case (int)'I': return SweConst.SE_VESTA;
    }
    return -1;
  }
} // End of class Transits
