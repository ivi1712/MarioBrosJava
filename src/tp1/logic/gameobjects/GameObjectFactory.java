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
		GameObject parsed = null;
		for (GameObject c: availableObjects) {
			parsed = c.parse(objWords, game);
			if(parsed != null) return parsed;
		}
		throw new ObjectParseException(Messages.UNKNOWN_GAME_OBJECT.formatted(String.join(" ", objWords)));
		
	}
}
