package deco2800.skyfall.managers;

import deco2800.skyfall.worlds.*;

public class EnvironmentManager {
   //Game Time variable
   private long gameTime;

   //Hours in a game day
   private long hours;

   //Night hours in a game day
   private long pmHours;

   //Biome the player is in
   private AbstractBiome biome;

   /**
    * Constructor
    *
    */
   public EnvironmentManager(long i) {
      gameTime = i;

      //Each day cycle goes for approx 24 minutes
      long time = (gameTime/ 60000);
      hours = time % 24;

      pmHours = hours;

      //Refactor hours to standard time
      if (hours > 12 && hours < 24) {
         pmHours = hours - 12;
      }
   }

   /**
    * Gets time of day in game
    *
    * @return long The time of day
    */
   public long getTime() {
      if (hours > 11 && hours < 24) {
         return pmHours;
         //System.out.println(pmHours + "pm");
      }
      //System.out.println(hours + "am");
      return hours;
   }

   /**
    * Sets the time of day in game
    *
    * @param time The time of day to be set
    */
   public void setTime(int time) {
      hours = time;
   }


   public void setBiomeMusic() {
      //get biome and play music accordingly
   }
}
