package tp1.logic;


import java.util.List;


import tp1.logic.gameobjects.GameObject;

public class LevelGameConfiguration implements GameConfiguration {
	
	
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
