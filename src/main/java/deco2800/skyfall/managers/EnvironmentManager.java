package deco2800.skyfall.managers;

public class EnvironmentManager {

   //Hours in a game day
   private long hours;

   //Day/Night tracker
   private boolean isDay;

   //Biome player is currently in
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
            //If player coords don't match tile coords, currentTile returns null
            //eg if player isn't exactly in the middle of a tile (walking between tiles), coords don't match
            //So below if statement is needed
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
      setDay();
   }

   /**
    * Sets day/night tracker after updating time
    */
   private void setDay() {
      if (hours > 12 && hours < 24) {
         isDay = false;
      } else {
         isDay = true;
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
    * TODO for sprint 2
    */
   public void setBiomeMusic() {
      //get biome and play music accordingly
   }
   
}
