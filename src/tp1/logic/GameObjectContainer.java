package tp1.logic;

import tp1.exceptions.CommandParseException;
import tp1.exceptions.NoAvaibleCreateException;
import tp1.logic.gameobjects.*;
import tp1.view.Messages;

import java.util.ArrayList;
import java.util.List;

public class GameObjectContainer {
	
	private List<GameObject> gameObjects;
	private List<GameObject> gameObjectsPending;
	
	public GameObjectContainer(){
		gameObjects = new ArrayList<>();
		gameObjectsPending = new ArrayList<>();
	}
	
	public boolean remove(GameObject obj) {
		return gameObjects.remove(obj);
	}

	public void add(GameObject obj) {
		this.gameObjects.add(obj);
	}
	
	
	public void update() {
		
		for (GameObject obj : gameObjects) {
			obj.update();
			if (obj.isAlive()) doInteractions(obj);
		}
		
		// añadir los objetos nuevos añadidos en ejecuccion mientras se hace update/action
		// evitamos java.util.ConcurrentModificationException al añadir por ejemplo mushroom
		if (!gameObjectsPending.isEmpty()) {
	        gameObjects.addAll(gameObjectsPending);
	        gameObjectsPending.clear();
	    }
		
		//Limpiamos los elementos muertos
		clean();
		
	}
	
	private void clean() {		
		gameObjects.removeIf(obj -> !obj.isAlive());
	}

	
	public void addObjectFactory(GameObject obj){
		for (GameObject gameObject : gameObjects)
			if(obj.isInPosition(gameObject) && obj.isSolid()) return;
		this.gameObjects.add(obj);
		//return true;
	}
	
	// para la lista de pendientes
	public void addPending(GameObject obj) {
		this.gameObjectsPending.add(obj);
	}
	
	
	public String positionToString(Position pos) {
		String ptoString = "";
		for (GameObject obj : gameObjects) {
			if (obj.isInPosition(pos)) {
				ptoString += obj.getIcon();
			}
		}
		if (ptoString != "") return ptoString;
		
		return Messages.EMPTY;
	}
	
	
	public boolean isSolid(Position pos) {
		for (GameObject obj : gameObjects) {
			if (obj.isInPosition(pos) && obj.isSolid()) {return true;}
		}
		return false;
	}
	
	public void doInteractions(GameObject gobj) {
		for (GameObject obj : gameObjects) {
			gobj.interactWith(obj);			
			// comprobar que no este zombie
			if (!gobj.isAlive()) return;
			if (!obj.isAlive()) continue;
			obj.interactWith(gobj);
		}//6574
	}
	
}
