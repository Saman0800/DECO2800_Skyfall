package deco2800.skyfall.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.observers.DayNightObserver;
import deco2800.skyfall.observers.TimeObserver;
import deco2800.skyfall.worlds.Tile;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.Arrays;

public class EnvironmentManager extends TickableManager {

    //Hours in a game day
    public int hours;

    // Seconds in a game day
    public int minutes;

    // Milliseconds since last time update
    private long currentMillis;

    // Seasons in game
    private String season;

    // Month in game
    private long month;

    // Month in game represented as an int
    public int monthInt;

    // Day/Night tracker
    private boolean isDay;

    // Biome player is currently in
    public String biome;

    // Time to display on screen
    private long displayHours;

    // Time of day: AM or PM
    public String TOD;

    // Music filename
    public String file;

    // Current music file being played
    public String currentFile;

    // List of entities in the game
    public List<AbstractEntity> entities;

    // Abstract entity within entities list. (Public for testing)
    public AbstractEntity player;

    //List of objects implementing TimeObserver
    private ArrayList<TimeObserver> timeListeners;

    //List of objects implementing DayNightObserver
    private ArrayList<DayNightObserver> dayNightListeners;

    // Correct biome name to display on screen
    private String biomeDisplay;

    // The previous biome that player was in
    private String previousBiome;

    // Current weather in the game
    public static String weather;

    // Weather event to happen after a certain amount of ticks
    public int weatherEvent = 0;

    // List of weather events
    private List<String> weatherList;

    // Background music manager
    private BGMManager bgmManager;


    /**
     * Constructor
     */
    public EnvironmentManager() {
        file = "resources/sounds/forest_day.wav";
        currentFile = "resources/sounds/forest_night.wav";
        timeListeners = new ArrayList<>();
        dayNightListeners = new ArrayList<>();
        currentMillis = System.currentTimeMillis();
        previousBiome = null;
        weatherList = Arrays.asList("Clear");
        weather = "Clear";
    }

    /**
     * Adds an observer to observe the time update
     *
     * @param observer the observer to add
     */
    public void addTimeListener(TimeObserver observer) {
        timeListeners.add(observer);
    }

    /**
     * Removes an observer from observing the time update
     *
     * @param observer the observer to remove
     */
    public void removeTimeListener(TimeObserver observer) {
        timeListeners.remove(observer);
    }

    /**
     * Get list of observers observing time update
     *
     * @return The list of observers currently observing the time update
     */
    public ArrayList<TimeObserver> getTimeListeners() {
        return timeListeners;
    }

    /**
     * Notifies all observers in timeListeners list of time change
     *
     * @param i The hour the game has updated to
     */
    public void updateTimeListeners(long i) {
        for (TimeObserver observer : timeListeners) {
            observer.notifyTimeUpdate(i);
        }
    }

    /**
     * Adds an observer to observe day/night change
     *
     * @param observer The observer to add
     */
    public void addDayNightListener(DayNightObserver observer) {
        dayNightListeners.add(observer);
    }

    /**
     * Removes and observer from observing day/night change
     *
     * @param observer The observer to remove
     */
    public void removeDayNightListener(DayNightObserver observer) {
        dayNightListeners.remove(observer);
    }

    /**
     * Gets list of observers observing day/night change
     *
     * @return The list of observers currently observing the day/night change
     */
    public ArrayList<DayNightObserver> getDayNightListeners() {
        return dayNightListeners;
    }

    /**
     * Notifies all observers in dayNightListeners list of day/night change
     *
     * @param isDay true if day, false if night
     */
    public void updateDayNightListeners(boolean isDay) {
        for (DayNightObserver observer : dayNightListeners) {
            observer.notifyDayNightUpdate(isDay);
        }
    }

    /**
     * Tracks the biome the player is currently in by retrieving the player's coordinates,
     * the corresponding tile, and the corresponding biome.
     */
    public void setBiome() {
        entities = GameManager.get().getWorld().getEntities();
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) instanceof MainCharacter) {
                player = entities.get(i);
                Tile currentTile = GameManager.get().getWorld().getTile(Math.round(player.getCol()),
                        Math.round(player.getRow()));
                if (currentTile != null) {
                    biome = currentTile.getBiome().getBiomeName();
                }
            }
        }
    }

    /**
     * Gets current biome player is in
     *
     * @return String Current biome of player, or null if player is moving between tiles
     */
    public String currentBiome() {
        return biome;
    }

    /**
     * Gets time of day in game
     *
     * @return long The time of day in hours
     */
    public long getTime() {
        return hours;
    }

    /**
     * @return Converts the game minutes and hours into a hour-decimal value. For
     * example the time 2:30am would yield a hour-decimal of 2.5
     */
    public float getHourDecimal() {
        return ((float) hours) + ((float) minutes / 60);
    }

    /**
     * Sets the time of day in game
     *
     * @param hour The hour of day to be set. Must be between 0-23 inclusive.
     * @param mins The minutes of the hour of day to be set. Must be between 0-59 inclusive.
     */

    public void setTime(int hour, int mins) {
        hours = hour;
        minutes = mins;

        //Check if observers need notifying, notifies if needed
        if (mins >= 60) {
            hours += 1;
            if (hours >= 24) {
                hours = 0;
            }
            minutes = 0;
            updateTimeListeners(hours);
        }

        //Update isDay boolean
        isDay();
    }
    
    /**
     * Returns whether it is day or not
     *
     * @return boolean True if it is day, False if night
     */
    public boolean isDay() {

        // Day is 6am - 6pm, Night 6pm - 6am
        if (hours < 6 || hours >= 18) {
            //check if observers need notifying
            if (isDay) {
                updateDayNightListeners(false);
            }
            isDay = false;
        } else {
            //check if observers need notifying
            if (!isDay) {
                updateDayNightListeners(true);
            }
            isDay = true;
        }
        return isDay;
    }

    /**
     * Returns whether it is day or not
     *
     * @return String am or pm depending on TOD
     */
    public String getTOD() {
        // Set hours to be displayed
        if (hours > 12 && hours < 24) {
            displayHours = hours - 12;
            TOD = "pm";
        } else if (hours == 24) {
            displayHours = hours - 12;
            TOD = "am";
        } else if (hours == 12) {
            displayHours = hours;
            TOD = "pm";
        } else {
            displayHours = hours;
            TOD = "am";
        }

        String prefix = minutes < 10 ? "0" : "";

        return Long.toString(displayHours) + ":" + prefix + Long.toString(minutes) + TOD;
    }

    /**
     * Sets the season in game, starting with summer.
     *
     * @param i the time in milliseconds
     */
    public void setMonth(long i) {
        //Each month goes for approx 30 days
        long timeMonth = (i / 60000) / 730;
        month = timeMonth % 12;
    }

    /**
     * Sets the month in game as an integer.
     *
     * @param month the month (from 1-12)
     */
    public void setMonthInt(int month) {
        // Set month as int
        monthInt = month;
    }

    /**
     * Gets the month in the game.
     *
     * @return month (int) (0 to 12)
     */
    public int getMonth() {
        monthInt = (int) month;
        return monthInt;
    }

    /**
     * Gets time of day in game
     *
     * @return String The month
     */
    public String getSeason() {
        String seasonString;
        if (monthInt == 1 || monthInt == 2 || monthInt == 12 || monthInt == 0) {
            seasonString = "Summer";
        } else if (monthInt == 3 || monthInt == 4 || monthInt == 5) {
            seasonString = "Autumn";
        } else if (monthInt == 6 || monthInt == 7 || monthInt == 8) {
            seasonString = "Winter";
        } else if (monthInt == 9 || monthInt == 10 || monthInt == 11) {
            seasonString = "Spring";
        } else {
            seasonString = "Invalid season";
        }
        return seasonString;
    }

    /**
     * Sets the filename in game.
     * Format for filenames: "biome_day/night" e.g. "forest_day"
     */
    public void setFilename() {
        isDay();
        currentBiome(); // Check current biome

        String filename = file.split("_", 4)[1];
        filename = "day.wav";
        filename = isDay() ? filename : "night.wav";

        // Until lake music created and ocean biome is restricted, play forest for now
        if (biome.equals("ocean") || biome.equals("lake") || biome.equals("river")) {
            file = "resources/sounds/forest_" + filename;
        } else {
            file = "resources/sounds/" + biome + "_" + filename;
        }
    }

    /**
     * Gets the filename in game.
     * @return the file being played
     */
    public String getFilename() {
        return file;
    }

    /**
     * Sets the music in game as per current time and biome the player resides in.
     */
    public void setTODMusic() {

        if (!(file.contains(currentFile))) {
            setFilename();

            // Stop current music
            try {
                bgmManager.stop();
            } catch (Exception e) {
                /* Exception caught, if any */
            }

            currentFile = file;

            // Play BGM
            try {
                bgmManager.initClip(currentFile);
                BGMManager.play();
                if (BGMManager.paused) {
                    BGMManager.mute();
                }
            } catch (Exception e) {
                /* Exception caught, if any */
            }
        }

        setFilename();

    }

    /**
     * Gets current biome player is in
     *
     * @return String Current biome of player, or null if player is moving between tiles
     */
    public String biomeDisplayName() {

        if (biome.equals("forest")) {
            biomeDisplay = "Forest";
        }
        if (biome.equals("volcanic_mountains")) {
            biomeDisplay = "Volcanic Mountains";
        }
        if (biome.equals("snowy_mountains")) {
            biomeDisplay = "Snowy Mountains";
        }
        if (biome.equals("mountain")) {
            biomeDisplay = "Mountain";
        }
        if (biome.equals("swamp")) {
            biomeDisplay = "Swamp";
        }
        if (biome.equals("lake")) {
            biomeDisplay = "Lake";
        }
        if (biome.equals("river")) {
            biomeDisplay = "River";
        }
        if (biome.equals("jungle")) {
            biomeDisplay = "Jungle";
        }
        if (biome.equals("desert")) {
            biomeDisplay = "Desert";
        }
        if (biome.equals("beach")) {
            biomeDisplay = "Beach";
        }
        if (biome.equals("ocean")) {
            biomeDisplay = "Ocean";
        }
        return biomeDisplay;
    }

    /**
     * Gets current biome player is in
     *
     * @return String Current biome of player, or null if player is moving between tiles
     */
    public static String currentWeather() {
        return weather;
    }

    /**
     * Generates a random weather event based on biome
     */
    public void randomWeatherEvent() {
        String randomElement;
        Random rand = new Random();

        if (biome != previousBiome) {
            switch (biome) {
                case "volcanic_mountains":
                    weatherList = Arrays.asList("Clear", "Storm", "Clear", "Earthquake", "Clear");
                    previousBiome = biome;
                    break;
                case "snowy_mountains":
                    weatherList = Arrays.asList("Clear", "Storm", "Clear", "Earthquake", "Snow", "Clear");
                    previousBiome = biome;
                    break;
                case "desert":
                    weatherList = Arrays.asList("Clear", "Storm", "Clear", "Earthquake", "Clear");
                    previousBiome = biome;
                    break;
                default:
                    weatherList = Arrays.asList("Clear", "Rain", "Storm", "Clear", "Earthquake", "Clear");
                    previousBiome = biome;
                    break;
            }
        }

        System.out.println(weatherList);
        randomElement = weatherList.get(rand.nextInt(weatherList.size()));
        weather = randomElement;
    }

    /**
     * Sets a certain weather event in game.
     *
     * @param event the weather event to occur
     */
    public void setRandomWeatherEvent(String event) {
        weather = event;
    }

    /**
     * On tick method for ticking managers with the TickableManager interface
     *
     * @param i
     */
    @Override
    public void onTick(long i) {
        long time = i;

        if (System.currentTimeMillis() - currentMillis >= 1000) {
            currentMillis = System.currentTimeMillis();
            minutes += 1;
        }

        // Set the TOD and month in game
        setTime(hours, minutes);
        setMonth(currentMillis);

        // Set Background music as per the specific biome and TOD
        setBiome();
        setTODMusic();
        currentWeather();

        // Key mapping to mute volume
        // M for mute and U to un-mute
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            BGMManager.mute();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
            BGMManager.unmute();
        }

        weatherEvent++;

        if (weatherEvent == 10000) {
            randomWeatherEvent();
            weatherEvent = 0;
        }
    }
}
