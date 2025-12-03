package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
<<<<<<< HEAD
import tp1.exceptions.OffBoardException;
import tp1.exceptions.PositionParseException;
=======
>>>>>>> 3ff17d3396b5442e302dc3a3050db3e6f57e4d41
import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Goomba extends MovingObject{
	
	public Goomba(GameWorld game, Position position) {
		super(game, position);
		this.NAME = Messages.GOOMBA_NAME;
		this.SHORTCUT = Messages.GOOMBA_SHORTCUT;
	}
	
	public Goomba() {
		super();
		this.NAME = Messages.GOOMBA_NAME;
		this.SHORTCUT = Messages.GOOMBA_SHORTCUT;
	}
	
<<<<<<< HEAD
	public GameObject parse(String objWords[], GameWorld game) throws ObjectParseException, OffBoardException {
		
		// comprobacion goomba
		if (matchName(objWords[1])) {
			
			Position p;
			try {
				p = Position.parsePosition(objWords[0]);
			} catch (PositionParseException e) {
				throw new ObjectParseException(Messages.INVALID_GAME_OBJECT_POSITION.formatted(String.join(" ", objWords)), e);
=======
	public GameObject parse(String objWords[], GameWorld game) throws ObjectParseException {
		
		// comprobacion goomba
		if (objWords[1].toLowerCase().equals(this.NAME) || objWords[1].toLowerCase().equals(this.SHORTCUT)) {
			
			try {
				Position p = Position.parsePosition(objWords[0]);
				if (p == null) return null;
			} catch (NumberFormatException e) {
				throw new ObjectParseException(Messages.INVALID_POSITION_FORMAT.formatted(objWords[0]), e);
>>>>>>> 3ff17d3396b5442e302dc3a3050db3e6f57e4d41
			}
				
				Goomba g = new Goomba(game, p);
				
				// direccion goomba
				if (objWords.length > 2) {
					// direccion si existe
					switch (objWords[2].toLowerCase()) {
					case "right", "r" -> {
						//&g.avanza = false;
						g.avanza = Action.RIGHT;
					}
					case "left", "l" -> {
						//g.avanza = true;
						g.avanza = Action.LEFT;
					}
					default -> {
						throw new ObjectParseException(Messages.UNKNOWN_ACTION.formatted(objWords[2]));
					}
				}
				}
				return g;
				
		}
		return null;
		
	}
	
	@Override
	protected GameObject createInstance(GameWorld game, Position pos) {
		
		return null;
	}
	

	public boolean isInPosition (Position p) {
		return (this.pos.equals(p));
	}
	
	public String getIcon() {
		return Messages.GOOMBA;
	}
	
	
	public void update() {
		automaticMovement();
	}
	
	
	public boolean isNextToSolid(Action dir) {
		return game.isSolid(this.pos.moved(dir));
	}
	
	public boolean isNextToLateral(Action dir) {
		return this.pos.isLateral(this.pos.moved(dir));
	}
	
	
	public boolean isDead() {
		//return this.dead;
		return !isAlive();
	}
	
	public boolean receiveInteraction(Mario other) {
		if (isAlive()) {
			other.receiveInteraction(this);
		}
		return true;
	}
	
	
	@Override
	public boolean interactWith(GameItem item) {
		boolean canInteract = item.isInPosition(this.pos);
		if(canInteract) {
			item.receiveInteraction(this);
			return false;
		}
		return canInteract;
	}

}
