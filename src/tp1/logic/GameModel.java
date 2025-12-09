package tp1.logic;

import tp1.exceptions.GameModelException;

//Para el control del juego

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;

public interface GameModel {
	public boolean isFinished();
	public void update();
	public void reset();
	public void addAction(Action act);
	public boolean reset(int Level);
	public void exit();
	public void parseGameObjectFactory(String objWords[]) throws ObjectParseException, OffBoardException;
	public void resetStats();
	public void save(String fileName) throws GameModelException;
	public void load(String fileName) throws GameModelException;
}
