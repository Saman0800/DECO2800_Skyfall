package deco2800.skyfall.managers;

import deco2800.skyfall.worlds.*;

public class EnvironmentManager {

   /**
    * Constructor
    * Returns time of day
    */
   public EnvironmentManager(long i) {
      long gameTime = i;

      //Each day cycle goes for approx 24 minutes
      long hours = (gameTime/ 60000);
      hours %= 24;

      long pmHours = hours;

      //Refactor hours to standard time
      if (hours > 12 && hours < 24) {
         pmHours = hours - 12;
      }

      //Print time
      if (hours > 11 && hours < 24) {
         System.out.println(pmHours + "pm");
      } else {
         System.out.println(hours + "am");
      }
   }

   public void setBiomeMusic() {
      //get biome and play music accordingly
   }
}
