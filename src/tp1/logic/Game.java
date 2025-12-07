package tp1.logic;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.GameModelException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.gameobjects.*;
import tp1.view.Messages;


public class Game implements GameWorld, GameModel, GameStatus{

	public static final int DIM_X = 30;
	public static final int DIM_Y = 15;
	
	private int remainingTime;
	private int points;
	private int lives;
	private final int defaultLives = 3;
	private int nLevel;
	
	private Mario mario;
	
	private GameObjectContainer gameObjects;
	private GameConfiguration lastConfig;
	
	
	private boolean finish = false;
	private boolean wins = false;
	private boolean looses = false;
	
	//Atributo nuevo, para gestionar el reset. Con la carga
	private String lastLoadedFile = null;
	
	public Game(int nLevel) {
		this.remainingTime = 100;
		this.points = 0;
		this.lives = 3;
		this.nLevel = nLevel;
		reset(nLevel);
	}
	
	
	public void parseGameObjectFactory(String objWords[]) throws OffBoardException, ObjectParseException {
		// intentamos crear Mario
		
		//Nuevo gameObject -> Mario
		GameObject nm = new Mario();
		
		//Parseamos el Mario
		nm = nm.parse(objWords, this);
		if(nm != null) {
			//Nuevo Mario añadido al jeugo y a la factoría
			gameObjects.addObjectFactory(nm);
			nm.addMarioGame();
			return;
		}
		
		
		//Resto de objetos que no son 
		GameObject gameobject = GameObjectFactory.parse(objWords, this);
		if(gameobject == null)
			throw new ObjectParseException(Messages.UNKNOWN_GAME_OBJECT.formatted(String.join(" ", objWords)));
			gameObjects.addObjectFactory(gameobject);
	}
	
	public void addMario(Mario m) {
		gameObjects.remove(this.mario);
		this.mario = m;
	}
	
	public void marioExited() {
		this.wins = true;
		//tick();
		this.points += this.remainingTime *10;
		this.remainingTime = 0;
		finish();
	}
	
	public boolean isFinished() {
		return finish;
	}
	
	public void finish() {
		// desaparecer mario
		this.finish = true;
	}
	
	public boolean reset(int nLevel) {
		this.nLevel = nLevel;
		switch (nLevel) {
		case 0 -> initLevel0();
		case 1 -> initLevel1();
		case 2 -> initLevel2();
		case -1 -> initLevelnegative1();
		default ->{
			return false;
		}
		}
		return true;
	}
	
	//Metodo modificado para soportar ficheros
	public void reset() {
		// Si habíamos cargado un fichero, intentamos recargarlo
		if (this.lastLoadedFile != null) {
			this.gameObjects = new GameObjectContainer();
			int oldLives = this.lives;
			//load(this.lastLoadedFile);
			applayConfig(lastConfig);
			this.lives = oldLives;
		} else {
			// Reset normal
			reset(this.nLevel);
		}
	}
	
	
	public void resetStats() {
		if (this.nLevel == -1) {
			this.lives = defaultLives;
			this.points = 0;
		}
	}
	
	public String positionToString(int col, int row) {
		
		return this.gameObjects.positionToString(new Position(col, row));
	}

	public void update() {
		tick();
		gameObjects.update();
		if(finish && playerLoses()) this.gameObjects.remove(mario);
	}
	
	public boolean isSolid(Position pos) {
		return gameObjects.isSolid(pos);
	}
	
	public void addAction(Action act) {
		if (act != null) mario.addAction(act); 
	}
	
	public boolean playerWins() {
		
		return (this.finish && this.wins);
	}

	public int remainingTime() {
		
		return this.remainingTime;
	}

	public int points() {
		
		return this.points;
	}
	
	public void addPoints(int points) {
		this.points += points;
	}

	public int numLives() {
		
		return this.lives;
	}

	@Override
	public String toString() {
		return Messages.GAME_NAME + " " + Messages.VERSION + "\n" + 
				Messages.REMAINING_TIME.formatted(remainingTime) + "\n" + 
				Messages.POINTS.formatted(points) + "\n" + 
				Messages.NUM_LIVES.formatted(lives) + "\n";
	}

	public boolean playerLoses() {
		return this.looses;
	}
	
	public void marioDead() {
		lives--;
		if (this.lives == 0) {
			//finish();
			//System.out.println(Messages.GAME_OVER);
			this.looses = true;
			finish();
		}else {
			reset();
		}
	}
	
	
	public void doInteractions(GameObject gobj) {
		gameObjects.doInteractions(gobj);
	}
	
	
	private void initLevel0() {
		//this.nLevel = 0;
		this.remainingTime = 100;
		
		// 1. Mapa
		gameObjects = new GameObjectContainer();
		for(int col = 0; col < 15; col++) {
			gameObjects.add(new Land(new Position(13,col)));
			gameObjects.add(new Land(new Position(14,col)));		
		}

		gameObjects.add(new Land(new Position(Game.DIM_Y-3,9)));
		gameObjects.add(new Land(new Position(Game.DIM_Y-3,12)));
		for(int col = 17; col < Game.DIM_X; col++) {
			gameObjects.add(new Land(new Position(Game.DIM_Y-2, col)));
			gameObjects.add(new Land(new Position(Game.DIM_Y-1, col)));		
		}

		gameObjects.add(new Land(new Position(9,2)));
		gameObjects.add(new Land(new Position(9,5)));
		gameObjects.add(new Land(new Position(9,6)));
		gameObjects.add(new Land(new Position(9,7)));
		gameObjects.add(new Land(new Position(5,6)));
		
		// Salto final
		int tamX = 8;
		//tamY= 8;
		int posIniX = Game.DIM_X-3-tamX, posIniY = Game.DIM_Y-3;
		
		for(int col = 0; col < tamX; col++) {
			for (int fila = 0; fila < col+1; fila++) {
				gameObjects.add(new Land(new Position(posIniY- fila, posIniX+ col)));
			}
		}

		gameObjects.add(new ExitDoor(new Position(Game.DIM_Y-3, Game.DIM_X-1)));

		// 3. Personajes
		this.mario = new Mario(this, new Position(Game.DIM_Y-3, 0));
		gameObjects.add(this.mario);

		gameObjects.add(new Goomba(this, new Position(0, 19)));
		//gameObjects.add(new Goomba(this, new Position(12, 4)));
	}
	
	private void initLevel1() {
		this.remainingTime = 100;
		
		// 1. Mapa
		gameObjects = new GameObjectContainer();
		for(int col = 0; col < 15; col++) {
			gameObjects.add(new Land(new Position(13,col)));
			gameObjects.add(new Land(new Position(14,col)));		
		}

		gameObjects.add(new Land(new Position(Game.DIM_Y-3,9)));
		gameObjects.add(new Land(new Position(Game.DIM_Y-3,12)));
		for(int col = 17; col < Game.DIM_X; col++) {
			gameObjects.add(new Land(new Position(Game.DIM_Y-2, col)));
			gameObjects.add(new Land(new Position(Game.DIM_Y-1, col)));		
		}

		gameObjects.add(new Land(new Position(9,2)));
		gameObjects.add(new Land(new Position(9,5)));
		gameObjects.add(new Land(new Position(9,6)));
		gameObjects.add(new Land(new Position(9,7)));
		gameObjects.add(new Land(new Position(5,6)));
		
		// Salto final
		int tamX = 8;
		//tamY= 8;
		int posIniX = Game.DIM_X-3-tamX, posIniY = Game.DIM_Y-3;
		
		for(int col = 0; col < tamX; col++) {
			for (int fila = 0; fila < col+1; fila++) {
				gameObjects.add(new Land(new Position(posIniY- fila, posIniX+ col)));
			}
		}

		gameObjects.add(new ExitDoor(new Position(Game.DIM_Y-3, Game.DIM_X-1)));

		// 3. Personajes
		this.mario = new Mario(this, new Position(Game.DIM_Y-3, 0));
		gameObjects.add(this.mario);

		gameObjects.add(new Goomba(this, new Position(0, 19)));
		gameObjects.add(new Goomba(this, new Position(4,6)));
		gameObjects.add(new Goomba(this, new Position(12,6)));
		gameObjects.add(new Goomba(this, new Position(12,8)));
		gameObjects.add(new Goomba(this, new Position(10,10)));
		gameObjects.add(new Goomba(this, new Position(12,11)));
		gameObjects.add(new Goomba(this, new Position(12,14)));
	}
	
	private void initLevel2() {
		this.remainingTime = 100;
		
		// 1. Mapa
		gameObjects = new GameObjectContainer();
		for(int col = 0; col < 15; col++) {
			gameObjects.add(new Land(new Position(13,col)));
			gameObjects.add(new Land(new Position(14,col)));		
		}

		gameObjects.add(new Land(new Position(Game.DIM_Y-3,9)));
		gameObjects.add(new Land(new Position(Game.DIM_Y-3,12)));
		for(int col = 17; col < Game.DIM_X; col++) {
			gameObjects.add(new Land(new Position(Game.DIM_Y-2, col)));
			gameObjects.add(new Land(new Position(Game.DIM_Y-1, col)));		
		}

		gameObjects.add(new Land(new Position(9,2)));
		gameObjects.add(new Land(new Position(9,5)));
		gameObjects.add(new Land(new Position(9,6)));
		gameObjects.add(new Land(new Position(9,7)));
		gameObjects.add(new Land(new Position(5,6)));
		
		// Salto final
		int tamX = 8;
		//tamY= 8;
		int posIniX = Game.DIM_X-3-tamX, posIniY = Game.DIM_Y-3;
		
		for(int col = 0; col < tamX; col++) {
			for (int fila = 0; fila < col+1; fila++) {
				gameObjects.add(new Land(new Position(posIniY- fila, posIniX+ col)));
			}
		}

		gameObjects.add(new ExitDoor(new Position(Game.DIM_Y-3, Game.DIM_X-1)));

		// 3. Personajes
		this.mario = new Mario(this, new Position(Game.DIM_Y-3, 0));
		gameObjects.add(this.mario);

		gameObjects.add(new Goomba(this, new Position(0, 19)));
		gameObjects.add(new Goomba(this, new Position(4,6)));
		gameObjects.add(new Goomba(this, new Position(12,6)));
		gameObjects.add(new Goomba(this, new Position(12,8)));
		gameObjects.add(new Goomba(this, new Position(10,10)));
		gameObjects.add(new Goomba(this, new Position(12,11)));
		gameObjects.add(new Goomba(this, new Position(12,14)));
		
		//practica 2_2
		// Para probar estas estensiones crear un nuevo nivel 2 que sea exactamente igual el nivel 1 
		// pero con una caja en la posición (9,4) y dos setas en las posiciones (12,8) y (2,20). 
		//Las posiciones indicadas siguen el formato: (fila,columna).
		gameObjects.add(new Box(this, new Position(9,4)));
		gameObjects.add(new MushRoom(this, new Position(12,8)));
		gameObjects.add(new MushRoom(this, new Position(2,20)));
	}
	
	private void initLevelnegative1(){
		this.remainingTime = 100;
		gameObjects = new GameObjectContainer();
	}

	
	public void tick() {
		remainingTime--;
	}
	
	public boolean isMarioWins() {
		return (this.finish && this.wins);
	}
	
	
	public void exit() {
		finish();
	}
	
	
	public void addGameObjectPending(GameObject obj) {
		gameObjects.addPending(obj);
	}

	@Override
	public boolean interactWith() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean offBoard(Position p) {
	    // Comprueba si se sale por los lados, por el techo o por el suelo (vacío)
	    return p.isLateral(p) || p.isRoof(p) || p.isVacio(p);
	}

	public void save(String fileName) throws GameModelException {
		// solo arroja excepciones sino se puede guardar
		// Utilizamos try-with-resources para instanciar el PrintWriter.
        // Esto abre el fichero y asegura su cierre automático al terminar el bloque[cite: 304].
        try (PrintWriter out = new PrintWriter(fileName)) {

        	// guardamos estado del juego
            out.println(remainingTime + " " + points + " " + lives);

            // guardamos todos los objetos del tablero
            out.print(gameObjects.toString()); 
            
            //view.showMessage(Messages.FILE_SAVED_CORRECTLY.formatted(fileName)+ "\n");

        } catch (FileNotFoundException e) {
            //	filenotfoundexception = tambie sirve para escritura
        	// si el fichero no ha podido ser abierto, se lanza esa excepción
            throw new GameModelException(Messages.ACCESS_DENIED.formatted(fileName), e);
        }
    }

	//load 
	public void load(String fileName) throws GameLoadException {
		// TODO Auto-generated method stub
		GameConfiguration config = new FileGameConfiguration(fileName, this);
				
		applayConfig(config);
		
		this.lastConfig = new FileGameConfiguration(fileName, this);
		this.lastLoadedFile = fileName;
	}
	
	//Aplicamos la configuracion dada por el fichero
	private void applayConfig(GameConfiguration config) {

		//Configuracion de las cosas
		 this.remainingTime = config.getRemainingTime();
		 this.points = config.points();
		 this.lives = config.numLives();
		 
		 //Reinicia el contenedor de objetos
		 this.gameObjects = new GameObjectContainer();
		 
		 //Añadimos bien al mario
		 GameObject newMario = config.getMario();
		 newMario.addMarioGame();
		 gameObjects.add(newMario);
		 
		 
		 //El resto
		  for (GameObject obj : config.getNPCObjects()) {
		        gameObjects.add(obj);
		  }
	}
	
}