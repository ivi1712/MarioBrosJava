package tp1.logic.gameobjects;

import tp1.exceptions.ActionParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.PositionParseException;
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
	
	public GameObject parse(String objWords[], GameWorld game) throws ObjectParseException, OffBoardException {
		
		// comprobacion goomba
		if (matchName(objWords[1])) {
			Position p;
			try {
				p = Position.parsePosition(objWords[0]);
			} catch (PositionParseException e) {
				throw new ObjectParseException(Messages.INVALID_GAME_OBJECT_POSITION.formatted(String.join(" ", objWords)), e);
			}
			
			if (game.offBoard(p)) {
	            throw new OffBoardException(Messages.INVALID_GAME_OBJECT_POSITION_OFFBOARD.formatted(String.join(" ", objWords)));
	        }
			
			if (objWords.length > 3) { // demasiados argumentos
	            throw new ObjectParseException(Messages.INVALID_GAME_OBJECT_EXTRA_ARGS.formatted(String.join(" ", objWords)));
			}
				
				Goomba g = new Goomba(game, p);
				
				// direccion goomba
				if (objWords.length > 2) {
					// direccion si existe
					try {
						Action dir = Action.parseAction(objWords[2]);
						if (dir == Action.RIGHT  || dir == Action.LEFT) g.avanza = dir;
						else throw new ObjectParseException(Messages.INVALID_GAME_OBJECT_DIRECTION.formatted(String.join(" ", objWords)));
					}catch (ActionParseException e) {
						throw new ObjectParseException(Messages.UNKNOWN_GAME_OBJECT_DIRECTION.formatted(String.join(" ", objWords)), e);
					}
				}
				//System.out.println("retorna goomba");
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

	@Override
	public String toString() {
		
		String firstCapitalize = this.NAME.substring(0, 1).toUpperCase() + this.NAME.substring(1);
		return this.pos.toString() + " " + firstCapitalize + " " + this.avanza.toString(); // ej: (14,0) Goomba
	}	
}
