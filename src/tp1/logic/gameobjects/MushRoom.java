package tp1.logic.gameobjects;

import tp1.exceptions.ActionParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.PositionParseException;
import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class MushRoom extends MovingObject {

	public MushRoom(GameWorld game, Position pos) {
		super(game, pos);
		this.avanza = Action.RIGHT;
		this.NAME = Messages.MUSHROOM_NAME;
		this.SHORTCUT = Messages.MUSHROOM_SHORTCUT;
		
	}

	public MushRoom() {
		
		super();
		this.NAME = Messages.MUSHROOM_NAME;
		this.SHORTCUT = Messages.MUSHROOM_SHORTCUT;
	}
	
	@Override
	public void update() {
		
		automaticMovement();
	}
	
	public boolean interactWith(GameItem item) {
		boolean canInteract = item.isInPosition(this.pos);
		if(canInteract) {
			item.receiveInteraction(this);
		}
		return canInteract;
	}
	
	@Override
	public boolean receiveInteraction(Mario mario) {
		//System.out.println("bbsita");
		if (isAlive()) {
			dead();
			mario.receiveInteraction(this);
		}
		
		return true;
	}
	
	@Override
	public String getIcon() {
		
		return Messages.MUSHROOM;
	}
	
	@Override
	protected GameObject createInstance(GameWorld game, Position pos) {
		
		return new MushRoom(game, pos);
	}
	
	public GameObject parse(String objWords[], GameWorld game) throws ObjectParseException, OffBoardException {
		
		// comprobacion
		if (matchName(objWords[1])) {
			Position p;
			try {
				p = Position.parsePosition(objWords[0]);
			} catch (PositionParseException e) {
				throw new ObjectParseException(Messages.INVALID_GAME_OBJECT_POSITION.formatted(String.join(" ", objWords)), e);
			}
			MushRoom m = new MushRoom(game,p);
			
			// direccion
			if (objWords.length > 2) {
				// direccion si existe
				try {
					Action dir = Action.parseAction(objWords[2]);
					if (dir == Action.RIGHT  || dir == Action.LEFT) m.avanza = dir;
					else throw new ObjectParseException(Messages.INVALID_GAME_OBJECT_DIRECTION.formatted(String.join(" ", objWords)));
				}catch (ActionParseException e) {
					throw new ObjectParseException(Messages.UNKNOWN_GAME_OBJECT_DIRECTION.formatted(String.join(" ", objWords)), e);
				}
			}
			
			return m;
			
		}
		return null;
		
	}
	
	@Override
	public String toString() {
		// capitalize first letter of NAME
		String firstCapitalize = this.NAME.substring(0, 1).toUpperCase() + this.NAME.substring(1);
		return this.pos.toString() + " " + firstCapitalize + " " + this.avanza.toString(); // ej: (14,0) MushRoom RIGHT, primera del this.NAME en mayusculas
	}

}
