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
	
	//CONSTANTES Y ATRIBUTOS

	public static final int DIM_X = 30;
	public static final int DIM_Y = 15;
	
	//Valores por defecto
	private final int DEF_LIVES = 3;
	private final int DEF_TIME = 100;
	private final int DEF_POINTS = 0;
	
	
	//Estado dinamico 
	private int remainingTime;
	private int points;
	private int lives;
	private int nLevel;
	
	//Estados de finalizacion
	private boolean finish = false;
	private boolean wins = false;
	private boolean looses = false;
	
	
	//Contenedores y referencias
	private Mario mario;
	private GameObjectContainer gameObjects = new GameObjectContainer();
	
	//Memoria de Persistencia:
	//Guarda la configuracion del ultimo fichere cargado para poder resetearlo
	//Si es null -> estamos jugando un nivel numérico estandar
	private GameConfiguration lastConfig = null;
	

	
	//Constructor e inicializacion	
	
	public Game(int nLevel){
		this.remainingTime = DEF_TIME;
		this.points = DEF_POINTS;
		this.lives = DEF_LIVES;
		this.nLevel = nLevel;
		
		// CORRECCIÓN: Si el nivel inicial falla (ej: 6), cargamos el 1 por defecto
		// Al devolver boolean, no necesitamos try-catch aquí.
		//Esto se si esta bien
		if (!reset(nLevel)) {
			reset(1); 
			this.nLevel = 1;
		}
		       
	}
	
	
	//Metodos de la interfaz GameModel (Uso del controlador)
	

	public void update() {
		tick();
		gameObjects.update();
		//Si el jugador pierde, eliminamos a mario
		if(finish && playerLoses()) this.gameObjects.remove(mario);
	}
	
	public void addAction(Action act) {
		if (act != null) mario.addAction(act); 
	}
	
	
	//Resets (pertenencen al GameModel)
	
	public boolean reset(int nLevel){
		try {
			// Intentamos crear la config. Si falla, salta al catch.
			GameConfiguration config = new LevelGameConfiguration(nLevel, this);
			
			// Si llega aquí, es válido
			applyObjectConfigReset(config);
			
			this.nLevel = nLevel;
			this.lastConfig = null; // Limpiamos config de fichero si hubiera
			
			return true; // Éxito
			
		} catch (GameModelException e) {
			// Capturamos el error internamente y devolvemos false
			return false; 
		}
		
	}
	
	
	//Metodo modificado para soportar ficheros
	public void reset() {
		//Si hay una configuracion de fichero guardada la aplicamos
		if (this.lastConfig != null) {
			applyObjectConfigReset(lastConfig);
		} else {
			//Si no nivel standar:
			resetStats();
			reset(this.nLevel);
		}
	}
	
	
	
	public void resetStats() {
		if (this.nLevel == -1) {
			this.lives = DEF_LIVES;
			this.points = DEF_POINTS;
		}

	}
	
	
	public void exit() {
		finish();
	}
	
	
	//Save/Load
	

	public void save(String fileName) throws GameModelException {
		//try: asegura el cierre del fichero automaticamente
        try (PrintWriter out = new PrintWriter(fileName)) {

        	// 1. guardamos estado del juego
            out.println(remainingTime + " " + points + " " + lives);

            // 2. guardamos todos los objetos del tablero
            out.print(gameObjects.toString()); 
            

        } catch (FileNotFoundException e) {
            //filenotfoundexception = tambien sirve para escritura
        	// si el fichero no ha podido ser abierto, se lanza esa excepción
            throw new GameModelException(Messages.ACCESS_DENIED.formatted(fileName), e);
        }
    }

	//load 
	public void load(String fileName) throws GameLoadException {
		
		GameConfiguration config = new FileGameConfiguration(fileName, this);
				
		//Si la lectura acierta, actualizamos el juego
		applyConfig(config);
		
		//Guardamos para futuros resets
		this.lastConfig = config;
	}
	
	
	//METODOS DE APLICACION DE CONFIGURACION	
	
	//Aplicamos la configuracion dada por el fichero
	private void applyConfig(GameConfiguration config) {

		//Configuracion de puntos y numero de vidas
		 this.points = config.points();
		 this.lives = config.numLives();
		 this.remainingTime = config.getRemainingTime();
		 
		 // configuracion de objetos y tiempo restante
		 applyObjectConfig(config);
		 
	}
	
	private void applyObjectConfig(GameConfiguration config){
		//Reinicia el contenedor de objetos
		 this.gameObjects = new GameObjectContainer();
		 
		 GameObject newMario = config.getMario();
		 //Añadimos bien al mario
		 if(newMario != null) {
			 newMario.addMarioGame();
			 gameObjects.add(newMario);
		 }else {
			 this.mario = null; // No hay Mario en este nivel
		 }
		
		 
		 
		 //El resto
		  for (GameObject obj : config.getNPCObjects()) {
		        gameObjects.add(obj);
		  }
	}
	
	// para el metodo reset, donde además de resetear los objetos, necesitamos resetear el tiempo
	private void applyObjectConfigReset(GameConfiguration config){
		this.remainingTime = config.getRemainingTime();
		applyObjectConfig(config);
	}
	
	
	//FACTORIA Y PARSEO DE OBJETOS
	
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
	
	
	//METODOS DE LA INTERFAZ GAMEWORLD
	
	
	
	public void addGameObjectPending(GameObject obj) {
		gameObjects.addPending(obj);
	}
		
	
	public boolean isSolid(Position pos) {
		return gameObjects.isSolid(pos);
	}
	
	public boolean offBoard(Position p) {
	    // Comprueba si se sale por los lados, por el techo o por el suelo (vacío)
	    return p.isLateral(p) || p.isRoof(p) || p.isVacio(p);
	}

	public void doInteractions(GameObject gobj) {
		gameObjects.doInteractions(gobj);
	}
	
	//LOGICA DE JUEGO Y ESTADO (vidas, puntos , fin) GAMEWORLD
	
	
	public void tick() {
		remainingTime--;
	}
	
	public void marioDead() {
		lives--;
		if (this.lives == 0) {
			this.looses = true;
			finish();
		}else {
			reset();
		}
	}
	
	public void finish() {
		// desaparecer mario
		this.finish = true;
	}
	
	
	//Mario -> GameWorld
	
	public boolean isMarioWins() {
		return (this.finish && this.wins);
	}
	
	
	public void marioExited() {
		this.wins = true;
		//tick();
		this.points += this.remainingTime *10;
		this.remainingTime = 0;
		finish();
	}
	
	public void addMario(Mario m) {
		gameObjects.remove(this.mario);
		this.mario = m;
	}
	
	
	//METODOS DE GAME STATUS (Vista)
	public String positionToString(int col, int row) {
		
		return this.gameObjects.positionToString(new Position(col, row));
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
	

	
	public boolean isFinished() {
		return finish;
	}
	

	@Override
	public boolean interactWith() {
		
		return false;
	}
	
}