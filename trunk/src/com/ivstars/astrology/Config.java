/*
 * Jastrology - Copyright 2006 Fengbo Xie, All Rights Reserved.
 *
 * http://www.ivstars.com/jastrology
 * http://sourceforge.net/projects/jastrology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ivstars.astrology;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.Velocity;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.awt.*;
import java.awt.image.BufferedImage;

import com.ivstars.astrology.util.CommonUtil;
import com.ivstars.astrology.util.AstrologyFonts;

/**
 * Config
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class Config {
    private  static Log log= LogFactory.getLog(Config.class);
    public static final String DEFAULT_CONFIG_FILE="/jastrology.properties";
    /**
        Variable for holding the configuration properties.
    */
    private  Properties config = null;

    private  String confiname;
    private  boolean isInitialised = false;
    private  Hashtable colorlist;
    private  Hashtable fontlist ;
    private  Hashtable imagelist ;
    private static boolean velocityInited = false;
    private static Hashtable configs = new Hashtable();
    public static Config getConfig(String configfile){
        if(CommonUtil.isEmpty(configfile)){
              configfile = DEFAULT_CONFIG_FILE;
        }
        Object config = configs.get(configfile);
        if(config!=null && config instanceof Config){
            return (Config)config;
        }
        config = new Config(configfile);
        configs.put(configfile,config);
        return (Config)config;
    }
    public Config() {
        init();
    }

    public Config(String configfile) {
        this.confiname = configfile;
        init();
        configs.put(confiname,this);
    }

    public Config(String configname,Properties props){
        this.confiname = configname;
        this.config = new Properties();
        this.config.putAll(props);
        clean();
    }
    /**
     * reload settings
     * @throws ConfigurationException
     */
    public synchronized void reload() throws ConfigurationException {
        isInitialised = false;
        init();
    }
    public void set(String key, String value){
        config.setProperty(key,value);
    }
    public void save(OutputStream out) throws IOException {
        config.store(out,"");
    }
    /**
        Loads the settings from the configuration file.
        @throws ConfigurationException This exception is thrown if the
            configuration settings cannot be loaded
    */
    protected synchronized void init()
        throws ConfigurationException
    {
        if (isInitialised) return;

        try
        {
            log.info("loading config file "+confiname);
            InputStream is = Config.class.getResourceAsStream(confiname);
            if (is == null)
            {
                throw new ConfigurationException("Can't find the properties file. Make sure '"+confiname +"' is in the CLASSPATH.");
            }
            config = new Properties();
            config.load(is);
            is.close();

        }
        catch (IOException ex)
        {
            config = null;
            throw new ConfigurationException("Error while loading properties from '"+confiname +"'. " + ex.getMessage());
        } catch (Exception e) {
            config = null;
            throw new ConfigurationException("Error while initial configuration",e);
        }
        clean();
        isInitialised = true;
        log.info("Config initialized!");
    }
    public static void initVelocity() throws Exception {
        if(velocityInited)
                return;
        //initial velocity
            Properties p = new Properties();
            p.load(Config.class.getResourceAsStream("/velocity.properties"));
            Velocity.init(p);
        velocityInited = true;
    }
    /**
     * clean color, font and image cache 
     */
    protected void clean(){
        log.info("initial color and font cache... ");
            colorlist=new Hashtable();
            fontlist = new Hashtable();
            imagelist = new Hashtable();
    }
    /**
        Get the configuration setting specified by <code>key</code> as a String value.
        @param key The key for the setting
        @return The value specified by <code>key</code> or null if it is not found
    */
    public String getString(String key)
    {
        //assert isInitialised : "Config has not been initialised";
        return config.getProperty(key);
    }

    /**
        Get the configuration setting specified by <code>key</code> as a String value.
        @param key The key for the setting
        @param default_value The value to return if the <code>key</code> is not found
        @return The value specified by <code>key</code> or <code>default_value</code>
            if it is not found
    */
    public String getString(String key, String default_value)
    {
        //assert isInitialised : "Config has not been initialised";
        return config.getProperty(key, default_value);
    }

    /**
        Get the configuration setting specified by <code>key</code> as an integer value.
        @param key The key for the setting
        @return The value specified by <code>key</code> or 0 if it is not found or
            is not a number
    */
    public int getInt(String key)
    {
        //assert isInitialised : "Config has not been initialised";
        return getInt(key, 0);
    }

    /**
        Get the configuration setting specified by <code>key</code> as an integer value.

        @param key The key for the setting
        @param default_value The value to return if the <code>key</code> is not found
        @return The value specified by <code>key</code> or <code>default_value</code>
            if it is not found
    */
    public int getInt(String key, int default_value)
    {
        //assert isInitialised : "Config has not been initialised";
        int result = default_value;
        String setting = config.getProperty(key);
        if (setting != null)
        {
            try
            {
                result = Integer.parseInt(setting);
            }
            catch (NumberFormatException ex)
            {}
        }
        return result;
    }

    public long getLong(String key, long default_value)
        {
            //assert isInitialised : "Config has not been initialised";
            long result = default_value;
            String setting = config.getProperty(key);
            if (setting != null)
            {
                try
                {
                    result = Long.parseLong(setting);
                }
                catch (NumberFormatException ex)
                {}
            }
            return result;
        }
    public float getFloat(String key, float default_value){
        float result = default_value;
        String setting = config.getProperty(key);
            if (setting != null)
            {
                try
                {
                    result = Float.parseFloat(setting);
                }
                catch (NumberFormatException ex)
                {}
            }
            return result;
    }
    /**
        Get the configuration setting specified by <code>key</code> as a boolean
        value. FALSE is returned if the setting doesn't exist, if it contains an
        empty string, or one of the values 'no', 'false' or '0'.
        In all other cases TRUE is returned.
        @param key The key for the setting
        @return The value specified by <code>key</code>
    */
    public boolean getBoolean(String key)
    {
        //assert isInitialised : "Config has not been initialised";
        String setting = config.getProperty(key);
        return (setting == null
            || setting.equals("")
            || setting.equals("0")
            || setting.equals("no")
            || setting.equals("false")
            ) ? false : true;
    }


    /**
     * get a color from the configuration file.
     * @param key the key of the color object, ControlFactory user the
     * name $key$. Color to find color configed in the properties.It should be a
     * 24-bit integer
     * @return Color the color defined in the file with the specified key or the
     * current LookAndFeel control's color if there's no color configed with the
     * very key name.
     */
    public Color getColor(String key){
        Object obj = colorlist.get(key + ".color");
        if (obj != null && (obj instanceof java.awt.Color))
            return (Color) obj;
        else {
            Color color = null;
            try {
                color =Color.decode(getString(key+".color"));
            } catch (Throwable fe) {
                color = UIManager.getColor("control");
            } finally {
                colorlist.put(key + ".color", color);
                return color;
            }
        }
    }
    static java.awt.Font defaultFont =
		new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12);
    /**
     * get font setting of this component
     * @param key component specification,such as Button, Label, Default and any other single word string.
     * @return a font modified by three paramaters: $key.Font.name,$key.Font.style,$key.Font.size,
     *         if there's no configuration with the specified key in the properties file, the defualt setting of
     *         font will be return.
     */
    public Font getFont(String key) {
        Object obj = fontlist.get(key + ".font");
        if (obj != null && (obj instanceof java.awt.Font))
            return (Font) obj;
        else {
            Font font = null;
            try {
                StringTokenizer st = new StringTokenizer(getString(key+".font"),",");
                String fname =st.nextToken();
                int style =Integer.parseInt(st.nextToken());
                float size = Float.parseFloat(st.nextToken());
                if(fname.endsWith(".ttf")){
                    font = AstrologyFonts.getFont(fname).deriveFont(style,size);
                }
                else font =
                    new java.awt.Font(fname,style,(int)size);
            } catch (Throwable fe) {
                font = UIManager.getFont("control");
                if(font == null) font = defaultFont;
            } finally {
                fontlist.put(key + ".font", font);
                return font;
            }
        }
    }

    public BufferedImage getImage(String key){
        Object obj = imagelist.get(key+".src");
        if(obj !=null && (obj instanceof BufferedImage))
            return (BufferedImage) obj;
        else{
            BufferedImage image = null;
            try {
                String path = getString(key+".src");
                if(CommonUtil.isEmpty(path)) return null;
                image = ImageIO.read(Config.class.getResource(path));
                imagelist.put(key+".src",image);
            } catch (Throwable e) {
                log.error("can not read image "+key,e);
            }
            return image;
        }
    }
    private static VersionInfo version;
    public static VersionInfo getVersionInfo(){
        if(version!=null)
            return version;
        try
        {
            log.debug("loading version info ");
            InputStream is = Config.class.getResourceAsStream("version.info");
            if (is == null)
            {
                throw new ConfigurationException("Can't find the version.info file. ");
            }
            Properties vp = new Properties();
            vp.load(is);
            is.close();
            version = new VersionInfo(vp.getProperty("codename"),vp.getProperty("version"),
                    vp.getProperty("release"),vp.getProperty("builddate"));
        }
        catch (IOException ex)
        {
            throw new ConfigurationException("Error while loading properties from  version.info. " + ex.getMessage());
        }
        return version;
    }
}

/**
	This exception is thrown by the {@link Config Config} class
	if there is a problem with finding or loading the configuration
	settings.
*/
 class ConfigurationException
	extends RuntimeException
{

	/**
		Creates a new <code>ConfigurationException</code>.
		@param message The message of the exception
	*/
	public ConfigurationException(String message)
	{
		super(message);
	}

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * <code>cause</code> is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.4
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new runtime exception with the specified cause and a
     * detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).  This constructor is useful for runtime exceptions
     * that are little more than wrappers for other throwables.
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A <tt>null</tt> value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     * @since 1.4
     */
    public ConfigurationException(Throwable cause) {
        super(cause);
    }
}

