package tp1.logic;

import tp1.exceptions.ActionParseException;
import tp1.view.Messages;

/**
 * Represents the allowed actions in the game
 *
 */
public enum Action {
	LEFT(-1,0), RIGHT(1,0), DOWN(0,1), UP(0,-1), STOP(0,0);
	
	private int x;
	private int y;
	
	private Action(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public static Action parseAction(String word) throws ActionParseException{
		// direccion si existe
		switch (word.toLowerCase()) {
		case "right", "r" -> {
			return RIGHT;
		}
		case "left", "l" -> {
			return LEFT;
		}
		case "stop", "s" -> {
			return STOP;
		}
		case "up", "u" -> {
			return UP;
		}
		case "down", "d" -> {
			return DOWN;
		}
		default -> {
			throw new ActionParseException(Messages.UNKNOWN_ACTION.formatted(word));
			}
		}
	}
	
	public static Action oposite(Action dir) {
		if(dir == Action.UP) return Action.DOWN;
		if(dir == Action.DOWN) return Action.UP;
		if(dir == Action.RIGHT) return Action.LEFT;
		if(dir == Action.LEFT) return Action.RIGHT;
		else return dir;
	}
	
}
