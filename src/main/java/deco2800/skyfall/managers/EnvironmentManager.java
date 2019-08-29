package deco2800.skyfall.managers;

import deco2800.skyfall.Tickable;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.worlds.Tile;
import org.lwjgl.Sys;

import java.util.List;

public class EnvironmentManager extends TickableManager {

   //Hours in a game day
   private long hours;

   //Day/Night tracker
   private boolean isDay;

   //Biome player is currently in
   private String biome;

   //Time to display on screen
   long displayHours;

   // Time of day: AM or PM
   private String TOD;

   /**
    * Constructor
    *
    */
   public EnvironmentManager() {
      // Constructor details here
   }

   /**
    * Private helper function for constructor to set biome
    */
   private void setBiome() {
      List<AbstractEntity> entities = GameManager.get().getWorld().getEntities();
      AbstractEntity player;
      for (int i = 0; i < entities.size(); i++) {
         if (entities.get(i).getObjectName().equals("playerPeon")) {
            player = entities.get(i);
            Tile currentTile = GameManager.get().getWorld().getTile(player.getCol(), player.getRow());
            // If player coords don't match tile coords, currentTile returns null
            // eg if player isn't exactly in the middle of a tile (walking between tiles), coords don't match
            // So below if statement is needed
            if (currentTile != null) {
               biome = currentTile.getBiome().getBiomeName();
            } else {
               // do nothing
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
      System.out.println(biome);
      return biome;
   }

   /**
    * Gets time of day in game
    *
    * @return long The time of day
    */
   public long getTime() {
      return hours;
   }

   /**
    * Sets the time of day in game
    *
    * @param i The time of day to be set
    */
   public void setTime(long i) {
      //Each day cycle goes for approx 24 minutes
      long time = (i / 60000);
      hours = time % 24;
   }

   /**
    * Returns whether it is day or not
    * @return boolean True if it is day, False if night
    */
   public boolean isDay() {
      if (hours > 12 && hours < 24) {
         isDay = false;
      } else {
         isDay = true;
      }

      return isDay;
   }

   /**
    * Returns whether it is day or not
    * @return am or pm depending on TOD
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

      return Long.toString(displayHours) + TOD;
   }

   /**
    * On tick method for ticking managers with the TickableManager interface
    *
    * @param i
    */
   @Override
   public void onTick(long i) {
      long time = i;
      if (System.currentTimeMillis() - time > 20) {
         time = System.currentTimeMillis();
      }
      setTime(time);
      setBiome();
      currentBiome();
   }
   
}
