package tp1.logic;

import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.Mario;

public interface GameWorld {
	public boolean isSolid(Position pos);
	public void addPoints(int points);
	public void marioExited();
	public void marioDead();
	public void clean();
	public boolean isMarioWins();
	public boolean interactWith();
	
	public void doInteractions(GameObject gobj);
	public void addMario(Mario mario);
	public void addGameObject(GameObject obj);
<<<<<<< HEAD
	public boolean offBoard(Position p);
=======
>>>>>>> 3ff17d3396b5442e302dc3a3050db3e6f57e4d41
	
}
