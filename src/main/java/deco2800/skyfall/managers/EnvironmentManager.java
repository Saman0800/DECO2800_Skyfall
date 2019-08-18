package deco2800.skyfall.managers;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.worlds.*;

import java.util.List;

public class EnvironmentManager {

   //Hours in a game day
   private long hours;

   //Day/Night tracker
   private boolean isDay;

   //Biome the player is in
   private String biome;

   /**
    * Constructor
    *
    */
   public EnvironmentManager(long i) {

      //Each day cycle goes for approx 24 minutes
      long time = (i / 60000);
      hours = time % 24;

      //Set Day/Night tracker
      if (hours > 12 && hours < 24) {
         isDay = false;
      } else {
         isDay = true;
      }

      //Set biome
      setBiome();
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
            if (currentTile != null) {
               biome = currentTile.getBiome().getBiomeName();
            }
         }
      }
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
    * @param time The time of day to be set
    */
   public void setTime(long time) {
      if (time > 24) {
         hours = 24;
      } else {
         hours = time;
      }
   }

   /**
    * Returns whether it is day or not
    * @return boolean True if it is day, False if night
    */
   public boolean isDay() {
      return isDay;
   }

   /**
    * Gets current biome player is in
    *
    * @return String Current biome of player, or null if player is moving between tiles
    */
   public String currentBiome() {
      return biome;
   }


   public void setBiomeMusic() {
      //get biome and play music accordingly
   }
}
