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
	private Mario mario;
	private List<GameObject> objects = new ArrayList<>();
	
	// Guardamos el nombre para mensajes de error si fuera necesario
	private String fileName;

	public FileGameConfiguration(String fileName, Game game) throws  GameLoadException {
		
		
		this.fileName = fileName;
		
		// TODO Auto-generated constructor stub
		try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))){
			
			//Leemos la intro del jeugo
			readStatusGame(scanner);
			
			//Leemos los objetos
			readGameObjects(scanner, game);
			
			
		}catch(FileNotFoundException e) {
			// Truco para el mensaje en español/inglés
			//Esto es porque tengo mi ordenador en ingles
			// Si el tuyo esta en español creo que estara bien
			String msgEsp = fileName + " (El sistema no puede encontrar el archivo especificado)";
			Exception cause = new FileNotFoundException(msgEsp);
			throw new GameLoadException(Messages.FILE_NOT_FOUND.formatted(fileName), cause);
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
					
					// Si hay más cosas en la línea, es un error de formato
					if (lineScanner.hasNext()) {
						throw new Exception("Error de formato");
					}
				}
			} else {
				throw new GameLoadException("Empty file");
			}
		} catch (Exception e) {
			
			throw new GameLoadException(Messages.INVALID_GAME_STATUS.formatted(line), e);
		}
		
	}

	private void readGameObjects(Scanner scanner, GameWorld game) throws GameLoadException {
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine().trim();
			if (line.isEmpty()) continue; // Ignorar líneas vacías

			String[] words = line.split("\\s+");
			try {
				// Delegamos en la factoría (que ya lanza ObjectParseException si falla)
				GameObject obj = GameObjectFactory.parse(words, game);
				
				if (obj == null) {
					// Si la factoría devuelve null es que el objeto es desconocido
					throw new ObjectParseException(Messages.UNKNOWN_GAME_OBJECT.formatted(line));
				}

				// Clasificamos si es Mario o un objeto normal
				
				//Tenemos que añadir al mario pero no se como , lo tenemos que añadir de forma que diferenciamos el mairo
				// Y actualizamos el local, y si no es mario añadirlo a la lista de objetos
				//Utilizamos el double dispach
				obj.addToGameConfiguration(this);
				
			} catch (GameModelException e) {
				// Envolvemos cualquier error de parseo de objetos
				throw new GameLoadException(Messages.INVALID_FILE_CONFIGURATION.formatted(fileName) , e);
			}
		}
		
		
		if (this.mario == null) {
			throw new GameLoadException("File does not contain Mario");
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
	public Mario getMario() {return mario;}

	@Override
	public List<GameObject> getNPCObjects() {return objects;}
	
	
	// Métodos para el double dispach
    public void addMario(Mario m) { this.mario = m; }
    public void addGameObject(GameObject o) { this.objects.add(o); }

}
