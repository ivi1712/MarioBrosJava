package tp1.logic.gameobjects;

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
		//this.avanza = false;
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
		
		// comprobacion goomba
		if (objWords[1].toLowerCase().equals(this.NAME) || objWords[1].toLowerCase().equals(this.SHORTCUT)) {
			Position p;
			try {
				p = Position.parsePosition(objWords[0]);
			} catch (PositionParseException e) {
				throw new ObjectParseException(Messages.INVALID_GAME_OBJECT_POSITION.formatted(String.join(" ", objWords)), e);
			}
			MushRoom m = new MushRoom(game,p);
			
			// direccion goomba
			if (objWords.length > 2) {
				// direccion si existe
				switch (objWords[2].toLowerCase()) {
				case "right", "r" -> {
					//m.avanza = false;
					m.avanza = Action.RIGHT;
				}
				case "left", "l" -> {
					m.avanza = Action.LEFT;
				}
				default -> {
					throw new ObjectParseException(Messages.UNKNOWN_ACTION.formatted(objWords[2]));
				}
				}	
			}
			return m;
			
		}
		return null;
		
	}
	

}
