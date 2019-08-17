package deco2800.skyfall.managers;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.PlayerPeon;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.*;

import java.util.List;

public class EnvironmentManager {
   //Game Time variable
   private long gameTime;

   //Hours in a game day
   private long hours;

   //Day/Night tracker
   private boolean isDay;

   //Biome the player is in
   private AbstractBiome biome;

   /**
    * Constructor
    *
    */
   public EnvironmentManager(long i) {
      gameTime = i;

      //Each day cycle goes for approx 24 minutes
      long time = (gameTime / 60000);
      hours = time % 24;

      //Set Day/Night tracker
      if (hours > 12 && hours < 24) {
         isDay = false;
      } else {
         isDay = true;
      }
   }

   public void test() {
      List<AbstractEntity> entities = GameManager.get().getWorld().getEntities();
      AbstractEntity player;
      for (int i = 0; i < entities.size(); i++) {
         if (entities.get(i).getObjectName() == "playerPeon") {
            player = entities.get(i);
            HexVector playerPosition = player.getPosition();

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
   public void setTime(int time) {
      hours = time;
   }


   public void setBiomeMusic() {
      //get biome and play music accordingly
   }
}
