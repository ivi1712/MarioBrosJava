package tp1.logic;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.GameModelException;
import tp1.exceptions.ObjectParseException;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.GameObjectFactory;
import tp1.logic.gameobjects.Mario;
import tp1.view.Messages;

public class FileGameConfiguration implements GameConfiguration {
	private int time;
	private int points;
	private int lives;
	private GameObject mario;
	private List<GameObject> objects = new ArrayList<>();
	
	// Guardamos el nombre para mensajes de error si fuera necesario
	private String fileName;

	public FileGameConfiguration(String fileName, Game game) throws  GameLoadException {
		
		
		this.fileName = fileName;
		
		
		try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))){
			
			//Leemos la intro del jeugo
			readStatusGame(scanner);
			
			//Leemos los objetos
			readGameObjects(scanner, game);
			
			
		}catch(FileNotFoundException e) {
			throw new GameLoadException(Messages.FILE_NOT_FOUND.formatted(fileName), e);
		}

	}
	

	
	private void readStatusGame(Scanner scanner) throws GameLoadException {
		String line = "";
		try {
			if (scanner.hasNextLine()) {
				line = scanner.nextLine().trim();
				// Procesamos la línea con un scanner secundario para asegurar formato
				try (Scanner lineScanner = new Scanner(line)) {
					this.time = lineScanner.nextInt();
					this.points = lineScanner.nextInt();
					this.lives = lineScanner.nextInt();
				}
			}
		} catch (Exception e) {
			throw new GameLoadException(Messages.INVALID_GAME_STATUS.formatted(line));
		}
		
	}

	private void readGameObjects(Scanner scanner, GameWorld game) throws GameLoadException {
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine().trim();
			if (line.isEmpty()) continue; // Ignorar líneas vacías

			String[] words = line.split("\\s+");
			try {
				
				// Intentamso crear Mario, si mario != null enotnces es mario añadimos a game y a objetos
				GameObject nm = new Mario();
				nm = nm.parse(words, game);
				if(nm != null) {
					//Nuevo Mario añadido al jeugo y a la factoría
					this.mario = nm;
					continue;
				}
				
				// Delegamos en la factoría (que ya lanza ObjectParseException si falla)
				GameObject obj = GameObjectFactory.parse(words, game);
				objects.add(obj);
				if (obj == null) {
					// Si la factoría devuelve null es que el objeto es desconocido
					throw new ObjectParseException(Messages.UNKNOWN_GAME_OBJECT.formatted(line));
				}
			} catch (GameModelException e) {
				// Envolvemos cualquier error de parseo de objetos
				throw new GameLoadException(Messages.INVALID_FILE_CONFIGURATION.formatted(fileName) , e);
			}
		}
	}
	

	
	//Getters
	@Override
	public int getRemainingTime() {return time;}

	@Override
	public int points() {return points;}

	@Override
	public int numLives() {return lives;}

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
