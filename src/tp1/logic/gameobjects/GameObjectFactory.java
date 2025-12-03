package tp1.logic.gameobjects;

import java.util.Arrays;
import java.util.List;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameWorld;
import tp1.view.Messages;

public class GameObjectFactory {

	public GameObjectFactory() {
	}
	private static final List<GameObject> availableObjects  = Arrays.asList(
			new Land(),
			new ExitDoor(),
			new Goomba(),
			new Mario(),
			new Box(),
			new MushRoom()
	);
	public static GameObject parse (String objWords[], GameWorld game) throws ObjectParseException, OffBoardException{
<<<<<<< HEAD
		GameObject parsed = null;
		for (GameObject c: availableObjects) {
			parsed = c.parse(objWords, game);
			if(parsed != null) return parsed;
		}
		if (parsed == null) throw new ObjectParseException(Messages.COMMAND_ADDOBJECT_DETAILS);
=======
		for (GameObject c: availableObjects) {
			GameObject parsed = c.parse(objWords, game);
			if(parsed != null) return parsed;
		}
>>>>>>> 3ff17d3396b5442e302dc3a3050db3e6f57e4d41
		
		return null;
		
	}
}
