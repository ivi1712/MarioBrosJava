package tp1.logic;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.GameModelException;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.GameObjectFactory;
import tp1.logic.gameobjects.Mario;

public class FileGameConfiguration implements GameConfiguration {
	private int time;
	private int points;
	private int lives; 

	public FileGameConfiguration(String fileName, Game game) throws FileNotFoundException, GameLoadException {
		// TODO Auto-generated constructor stub
		Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)));
		readStatusGame(scanner);
		//readGameObjects(scanner, game);
	}
	
	public void readStatusGame(Scanner scanner) throws GameLoadException{
		try {
            this.time = scanner.nextInt();
            this.points = scanner.nextInt();
            this.lives = scanner.nextInt();
            scanner.nextLine(); // posible error por salto de linea para readGameObjects()
            
        } catch (Exception e) {
            // Si nextInt falla o no hay suficientes datos
            throw new GameLoadException("Incorrect game status", e);
        }
	}
	

	@Override
	public int getRemainingTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int points() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int numLives() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Mario getMario() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GameObject> getNPCObjects() {
		// TODO Auto-generated method stub
		return null;
	}

}
