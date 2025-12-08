package tp1.logic;

import java.util.List;

import tp1.logic.gameobjects.GameObject;

public interface GameConfiguration {
	   // game status
	   public int getRemainingTime();
	   public int points();
	   public int numLives();
	   // game objects
	   public GameObject getMario();
	   public List<GameObject> getNPCObjects(); //Devuelve todos los objetos menos mario
}
