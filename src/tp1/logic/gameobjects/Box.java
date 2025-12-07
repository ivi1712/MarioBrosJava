package tp1.logic.gameobjects;

import tp1.exceptions.GameParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.PositionParseException;
import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Box extends GameObject {
	
	private boolean full = true;
	private final static int pointsAdd = 50;

	public Box(GameWorld game, Position pos) {
		super(game, pos);
		this.NAME = Messages.BOX_NAME;
		this.SHORTCUT = Messages.BOX_SHORTCUT;
	}

	public Box() {
		super();
		this.NAME = Messages.BOX_NAME;
		this.SHORTCUT = Messages.BOX_SHORTCUT;
	}
	
	public boolean isSolid() {return true;}
	
	public boolean isFull() {
		return this.full;
	}
	
	public GameObject parse(String objWords[], GameWorld game) throws ObjectParseException, OffBoardException {

		// comprobacion de mario 
		if (matchName(objWords[1])) {
			
			Position p;
			try {
				p = Position.parsePosition(objWords[0]);
			} catch (PositionParseException e) {
				throw new ObjectParseException(Messages.INVALID_GAME_OBJECT_POSITION.formatted(String.join(" ", objWords)), e);
			}
				
			// crea instancia de box 
			Box b = new Box(game, p);
			
			if (objWords.length > 3) {
				throw new ObjectParseException(Messages.INVALID_GAME_OBJECT_EXTRA_ARGS.formatted(String.join(" ", objWords)));
			}
			
			// diferencia entre full y empty
			if (objWords.length > 2) {
				switch (objWords[2].toLowerCase()) {
				case "full", "f" -> {
					b.full = true;
				}
				case "empty", "e" -> {
					b.full = false;
				}
				default -> {
					throw new ObjectParseException(Messages.INVALID_BOX_STATUS.formatted(String.join(" ", objWords)));
				}
				}
			}
			
			
			return b;
		}
		return null;
	}

	@Override
	public boolean receiveInteraction(MushRoom mushRoom) {
		
		return false;
	}
	
	@Override
	public boolean receiveInteraction(Mario m) {
		if(isFull()) {
			game.addPoints(pointsAdd);
			//game.addMushroom(this.pos);
			game.addGameObjectPending(new MushRoom(game, this.pos.moved(Action.UP)));
			this.full = false;
			m.receiveInteraction(this);		
		}
		return true;
	}

	@Override
	public String getIcon() {
		
		return this.full ? Messages.BOX : Messages.EMPTY_BOX;
	}
	
	@Override
	protected GameObject createInstance(GameWorld game, Position pos) {
		
		return new Box(game, pos);
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
	public void dead() {
		return;
	}

	@Override
	public void update() {
		return;
	}
	//toString
	public String toString() {
		String status = this.full ? "Full" : "Empty";
		// capitalize first letter of NAME
		String firstCapitalize = this.NAME.substring(0, 1).toUpperCase() + this.NAME.substring(1);
		return this.pos.toString() + " " + firstCapitalize + " " + status; // ej: (14,0) Box Full, primera del this.NAME en mayusculas
	}
	@Override
	public GameObject copy() {
	    Box newBox = new Box(this.game, this.pos);
	    newBox.full = this.full;
	    return newBox;
	}
	
}
