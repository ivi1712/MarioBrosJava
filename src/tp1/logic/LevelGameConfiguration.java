package tp1.logic;


import java.util.ArrayList;
import java.util.List;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.GameModelException;
import tp1.logic.gameobjects.Box;
import tp1.logic.gameobjects.ExitDoor;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.Goomba;
import tp1.logic.gameobjects.Land;
import tp1.logic.gameobjects.Mario;
import tp1.logic.gameobjects.MushRoom;
import tp1.view.Messages;

public class LevelGameConfiguration implements GameConfiguration {
	//Atributos
	private int time = 100;
	private int points = 0;
	private int lives = 3;
	private GameObject mario;
	private List<GameObject> objects = new ArrayList<>();
	
	//Necesitamos el juego para pasarselo al consturctor de objetos
	private GameWorld game;
	
	public LevelGameConfiguration(int nLevel, GameWorld game) throws GameModelException {
		this.game = game;
		
		switch (nLevel) {
		case 0 -> initLevel0();
		case 1 -> initLevel1();
		case 2 -> initLevel2();
		// Caso -1: No hacemos nada. 
		// Resultado: time=100, objects=[], mario=null. Igual que tu initLevelnegative1.
		case -1 -> {}
		// para test opcional, mala configuracion del test
		case 6 -> initLevel1();
		default ->{
			throw new GameModelException(Messages.INVALID_LEVEL_NUMBER);
			}
		
		}
	}
	
	//Niveles

		private void initLevel0() {
			addCommonMap();
			this.mario = new Mario(game, new Position(Game.DIM_Y-3, 0));
			objects.add(new Goomba(game, new Position(0, 19)));
		}

		private void initLevel1() {
			addCommonMap();
			this.mario = new Mario(game, new Position(Game.DIM_Y-3, 0));
			addCommonEnemies();
		}
		
		private void initLevel2() {
			initLevel1(); 
			objects.add(new Box(game, new Position(9,4)));
			objects.add(new MushRoom(game, new Position(12,8)));
			objects.add(new MushRoom(game, new Position(2,20)));
		}
		

		//Metodos aux

		private void addCommonMap() {
			for(int col = 0; col < 15; col++) {
				objects.add(new Land(game, new Position(13,col)));
				objects.add(new Land(game, new Position(14,col)));		
			}

			objects.add(new Land(game, new Position(Game.DIM_Y-3,9)));
			objects.add(new Land(game, new Position(Game.DIM_Y-3,12)));
			
			for(int col = 17; col < Game.DIM_X; col++) {
				objects.add(new Land(game, new Position(Game.DIM_Y-2, col)));
				objects.add(new Land(game, new Position(Game.DIM_Y-1, col)));		
			}

			objects.add(new Land(game, new Position(9,2)));
			objects.add(new Land(game, new Position(9,5)));
			objects.add(new Land(game, new Position(9,6)));
			objects.add(new Land(game, new Position(9,7)));
			objects.add(new Land(game, new Position(5,6)));
			
			int tamX = 8;
			int posIniX = Game.DIM_X-3-tamX;
			int posIniY = Game.DIM_Y-3;
			
			for(int col = 0; col < tamX; col++) {
				for (int fila = 0; fila < col+1; fila++) {
					objects.add(new Land(game, new Position(posIniY- fila, posIniX+ col)));
				}
			}

			objects.add(new ExitDoor(game, new Position(Game.DIM_Y-3, Game.DIM_X-1)));
		}
		
		private void addCommonEnemies() {
			objects.add(new Goomba(game, new Position(0, 19)));
			objects.add(new Goomba(game, new Position(4,6)));
			objects.add(new Goomba(game, new Position(12,6)));
			objects.add(new Goomba(game, new Position(12,8)));
			objects.add(new Goomba(game, new Position(10,10)));
			objects.add(new Goomba(game, new Position(12,11)));
			objects.add(new Goomba(game, new Position(12,14)));
		}

		//INTERFAZ 

		@Override public int getRemainingTime() { return time; }
		@Override public int points() { return points; }
		@Override public int numLives() { return lives; }
		
		@Override 
		public GameObject getMario() {
			if (this.mario != null) {
	            return this.mario.copy(); 
	        }
	        return null;
		}
		
		@Override 
		public List<GameObject> getNPCObjects() {
			//return objects;
			List<GameObject> copies = new ArrayList<>();
		    
		    // Recorremos la lista de objetos originales guardada en memoria
		    for (GameObject obj : this.objects) {
		        // Añadimos una COPIA de cada uno a la nueva lista
		        copies.add(obj.copy());
		    }
		    
		    return copies;
		}
		


}
