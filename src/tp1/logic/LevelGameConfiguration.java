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
	private int time;
	private int points;
	private int lives;
	private GameObject mario;
	private List<GameObject> objects = new ArrayList<>();
	
	public LevelGameConfiguration(int nLevel) throws GameModelException {
		switch (nLevel) {
		case 0 -> initLevel0();
		case 1 -> initLevel1();
		case 2 -> initLevel2();
		case -1 -> initLevelnegative1();
		// para test opcional, mala configuracion del test
		case 6 -> initLevel1();
		default ->{
			throw new GameModelException(Messages.INVALID_LEVEL_NUMBER);
		}
		}
	}
	private void initLevel0() {
		//this.nLevel = 0;
		this.time = 100;
		
		// 1. Mapa
		//objects = new GameObjectContainer();
		for(int col = 0; col < 15; col++) {
			objects.add(new Land(new Position(13,col)));
			objects.add(new Land(new Position(14,col)));		
		}

		objects.add(new Land(new Position(Game.DIM_Y-3,9)));
		objects.add(new Land(new Position(Game.DIM_Y-3,12)));
		for(int col = 17; col < Game.DIM_X; col++) {
			objects.add(new Land(new Position(Game.DIM_Y-2, col)));
			objects.add(new Land(new Position(Game.DIM_Y-1, col)));		
		}

		objects.add(new Land(new Position(9,2)));
		objects.add(new Land(new Position(9,5)));
		objects.add(new Land(new Position(9,6)));
		objects.add(new Land(new Position(9,7)));
		objects.add(new Land(new Position(5,6)));
		
		// Salto final
		int tamX = 8;
		//tamY= 8;
		int posIniX = Game.DIM_X-3-tamX, posIniY = Game.DIM_Y-3;
		
		for(int col = 0; col < tamX; col++) {
			for (int fila = 0; fila < col+1; fila++) {
				objects.add(new Land(new Position(posIniY- fila, posIniX+ col)));
			}
		}

		objects.add(new ExitDoor(new Position(Game.DIM_Y-3, Game.DIM_X-1)));

		// 3. Personajes
		this.mario = new Mario(this, new Position(Game.DIM_Y-3, 0));
		objects.add(this.mario);

		objects.add(new Goomba(this, new Position(0, 19)));
		//objects.add(new Goomba(this, new Position(12, 4)));
	}
	
	private void initLevel1() {
		this.time = 100;
		
		// 1. Mapa
		objects = new GameObjectContainer();
		for(int col = 0; col < 15; col++) {
			objects.add(new Land(new Position(13,col)));
			objects.add(new Land(new Position(14,col)));		
		}

		objects.add(new Land(new Position(Game.DIM_Y-3,9)));
		objects.add(new Land(new Position(Game.DIM_Y-3,12)));
		for(int col = 17; col < Game.DIM_X; col++) {
			objects.add(new Land(new Position(Game.DIM_Y-2, col)));
			objects.add(new Land(new Position(Game.DIM_Y-1, col)));		
		}

		objects.add(new Land(new Position(9,2)));
		objects.add(new Land(new Position(9,5)));
		objects.add(new Land(new Position(9,6)));
		objects.add(new Land(new Position(9,7)));
		objects.add(new Land(new Position(5,6)));
		
		// Salto final
		int tamX = 8;
		//tamY= 8;
		int posIniX = Game.DIM_X-3-tamX, posIniY = Game.DIM_Y-3;
		
		for(int col = 0; col < tamX; col++) {
			for (int fila = 0; fila < col+1; fila++) {
				objects.add(new Land(new Position(posIniY- fila, posIniX+ col)));
			}
		}

		objects.add(new ExitDoor(new Position(Game.DIM_Y-3, Game.DIM_X-1)));

		// 3. Personajes
		this.mario = new Mario(this, new Position(Game.DIM_Y-3, 0));
		objects.add(this.mario);

		objects.add(new Goomba(this, new Position(0, 19)));
		objects.add(new Goomba(this, new Position(4,6)));
		objects.add(new Goomba(this, new Position(12,6)));
		objects.add(new Goomba(this, new Position(12,8)));
		objects.add(new Goomba(this, new Position(10,10)));
		objects.add(new Goomba(this, new Position(12,11)));
		objects.add(new Goomba(this, new Position(12,14)));
	}
	
	private void initLevel2() {
		this.time = 100;
		
		// 1. Mapa
		objects = new GameObjectContainer();
		for(int col = 0; col < 15; col++) {
			objects.add(new Land(new Position(13,col)));
			objects.add(new Land(new Position(14,col)));		
		}

		objects.add(new Land(new Position(Game.DIM_Y-3,9)));
		objects.add(new Land(new Position(Game.DIM_Y-3,12)));
		for(int col = 17; col < Game.DIM_X; col++) {
			objects.add(new Land(new Position(Game.DIM_Y-2, col)));
			objects.add(new Land(new Position(Game.DIM_Y-1, col)));		
		}

		objects.add(new Land(new Position(9,2)));
		objects.add(new Land(new Position(9,5)));
		objects.add(new Land(new Position(9,6)));
		objects.add(new Land(new Position(9,7)));
		objects.add(new Land(new Position(5,6)));
		
		// Salto final
		int tamX = 8;
		//tamY= 8;
		int posIniX = Game.DIM_X-3-tamX, posIniY = Game.DIM_Y-3;
		
		for(int col = 0; col < tamX; col++) {
			for (int fila = 0; fila < col+1; fila++) {
				objects.add(new Land(new Position(posIniY- fila, posIniX+ col)));
			}
		}

		objects.add(new ExitDoor(new Position(Game.DIM_Y-3, Game.DIM_X-1)));

		// 3. Personajes
		this.mario = new Mario(this, new Position(Game.DIM_Y-3, 0));
		objects.add(this.mario);

		objects.add(new Goomba(this, new Position(0, 19)));
		objects.add(new Goomba(this, new Position(4,6)));
		objects.add(new Goomba(this, new Position(12,6)));
		objects.add(new Goomba(this, new Position(12,8)));
		objects.add(new Goomba(this, new Position(10,10)));
		objects.add(new Goomba(this, new Position(12,11)));
		objects.add(new Goomba(this, new Position(12,14)));
		
		//practica 2_2
		// Para probar estas estensiones crear un nuevo nivel 2 que sea exactamente igual el nivel 1 
		// pero con una caja en la posición (9,4) y dos setas en las posiciones (12,8) y (2,20). 
		//Las posiciones indicadas siguen el formato: (fila,columna).
		objects.add(new Box(this, new Position(9,4)));
		objects.add(new MushRoom(this, new Position(12,8)));
		objects.add(new MushRoom(this, new Position(2,20)));
	}
	
	private void initLevelnegative1(){
		this.time = 100;
		objects = new GameObjectContainer();
	}
	@Override
	public int getRemainingTime() {
		
		return 0;
	}

	@Override
	public int points() {
		
		return 0;
	}

	@Override
	public int numLives() {
		
		return 0;
	}

	@Override
	public GameObject getMario() {
		
		return null;
	}

	@Override
	public List<GameObject> getNPCObjects() {
		
		return null;
	}
	
	

}
