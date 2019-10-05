package deco2800.skyfall.util;

import java.util.HashMap;
import java.util.Map;
import java.io.*;

/**
 * Abstracts saving settings to a file
 * Reads on constructor, saves all files on close()
 * Designed for no exceptions or error checking, all exceptional cases are dealt with defaults
 * Typical usage is create->get->set->close
 */
public class SettingsFile {
    //location of settings file
    String path;

    //internally, all values are stored as string to string
    Map<String, String> values;

    /**
     * Sets the given file as location, if a file is not found, will create, errors internally handled
     * @param path location of settings file (internal convention should be: settings/FEATURE_NAME/SETTING_NAME.ini)
     * The file at path should look like:
     * ----------------------
     * Key0 Value0
     * Key1 Value1
     * Key2 Value2 ValueContinued2 Strings with spaces supported
     * Key3 Value3
     * ----------------------
     * But invalid file and even non-existent files will be handled
     */
    public SettingsFile(String path) {
        this.path = path;
        values = new HashMap<String, String>();
        try ( BufferedReader file = new BufferedReader( new FileReader(path) ) ) {
            //File found, attempt to read it
            String line;
            //read line and set values
            while ((line = file.readLine()) != null) {
                String[] vec = line.split(" ");
                if (vec.length < 2) {
                    //invalid format for this line
                    continue;
                }
                values.put( vec[0], line.substring(vec[0].length()+1) );
            }
        }
        catch (FileNotFoundException ex) {
            //File not found
            //This is fine, the class will create a file on exit
        }
        catch (IOException ex) {
            //IO related error
            //Also fine
        }
    }

    /**
     * Closes SettingsFile and saves to path
     */
    public void close() {
        //create directory for file, if needed
        File child = new File(path);
        File parent = child.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdir();
        }

        //Set up writer
        try {
            PrintWriter file = new PrintWriter(path);
            for (Map.Entry<String,String> entry : values.entrySet()) {
                file.print(entry.getKey() + " " + entry.getValue() + "\n");
            }
            file.close();
        }
        catch (IOException ex) {
            //Could have a lot of causes outside of our control
            //SettingFile will default on next execution
        }
    }

    /**
     * Gets float from key, reverts to backup on fail
     * @param key String key, case sensitive
     * @param backup Backup double value, will revert to this on failure
     * @return Returned value, backup on failure
     */
    public double get(String key, double backup) {
        String value = values.get(key);
        if (value == null) {
            values.put( key, Double.toString(backup) );
            value = values.get(key);
        }
        try {
            return Float.parseFloat(value);
        }
        catch (NumberFormatException ex) {
            //set default and return
            values.put( key, Double.toString(backup) );
            return backup;
        }
    }

    /**
     * Gets int from key, reverts to backup on fail
     * @param key String key, case sensitive
     * @param backup Backup int value, will revert to this on failure
     * @return Returned value, backup on failure
     */
    public int get(String key, int backup) {
        String value = values.get(key);
        if (value == null) {
            values.put( key, Integer.toString(backup) );
            value = values.get(key);
        }
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException ex) {
            //set default and return
            values.put( key, Integer.toString(backup) );
            return backup;
        }
    }

    /**
     * Gets string from key, reverts to backup on failgit sa=
     * @param key String key, case sensitive
     * @param backup Backup string value, will revert to this on failure
     * @return Returned value, backup on failure
     */
    public String get(String key, String backup) {
        String value = values.get(key);
        if (value == null) {
            values.put( key, backup );
            value = values.get(key);
        }
        return value;
    }

    /**
     * Sets value, not saved until close() invoked
     * @param key Key to be used
     * @param value Value to save to
     */
    public void set(String key, double value) {
        values.put( key, Double.toString(value) );
    }

    /**
     * Sets value, not saved until close() invoked
     * @param key Key to be used
     * @param value Value to save to
     */
    public void set(String key, int value) {
        values.put( key, Integer.toString(value) );
    }

    /**
     * Sets value, not saved until close() invoked
     * @param key Key to be used
     * @param value Value to save to
     */
    public void set(String key, String value) {
        values.put( key, value );
    }

}
