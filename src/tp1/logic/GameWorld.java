package tp1.logic;

import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.Mario;

public interface GameWorld {
	public boolean isSolid(Position pos);
	public void addPoints(int points);
	public void marioExited();
	public void marioDead();
	public boolean isMarioWins();
	public boolean interactWith();
	
	public void doInteractions(GameObject gobj);
	public void addMario(Mario mario);
	public void addGameObjectPending(GameObject obj);
	public boolean offBoard(Position p);
	
}
