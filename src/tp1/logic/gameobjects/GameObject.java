package tp1.logic.gameobjects;

import java.text.ParseException;

import tp1.exceptions.GameParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.PositionParseException;
import tp1.logic.Action;
import tp1.logic.FileGameConfiguration;
import tp1.logic.GameObjectContainer;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public abstract class GameObject implements GameItem { 

	protected Position pos; // If you can, make it private.
	//protected boolean alive;
	protected GameWorld game; 
	
	protected String NAME;
	protected String SHORTCUT;
	
	public GameObject(GameWorld game, Position pos) {
		//this.alive = true;
		this.pos = pos;
		this.game = game;
	}
	
	
	public GameObject(Position pos) {
		this.pos = pos;
	}
	
	public GameObject() {
		
	}
	
	public boolean isInPosition(Position p) {
		return (this.pos.equals(p));
	}
	
	public boolean isInPosition(GameObject g) {
		return (this.pos.equals(g.pos));
	}
	
	public boolean isAlive() {return true;};
	
	// abstractas
	public abstract void dead();
	public abstract void update();
	public abstract String getIcon();

	// Not mandatory but recommended
	protected void move(Action dir) {
		
		this.pos = this.pos.moved(dir); 
	}
	public GameObject parse(String objWords[], GameWorld game) throws ObjectParseException, OffBoardException{
		// addGameObject objectos sencillos esto es para land, exit door, goomba y los nurvos objrtos. todo menos mario
		// x = 0, y= 1, n or sh = 2
		// comprobar que es el 
		if(matchName(objWords[1])) {
			Position p;
			try {
				p = Position.parsePosition(objWords[0]);
				
				if(p.isLateral(p)|| p.isVacio(p) || p.isRoof(p))
					throw new OffBoardException(Messages.INVALID_GAME_OBJECT_POSITION_OFFBOARD.formatted(String.join(" ", objWords)));
				
			} catch (PositionParseException e) {
				throw new ObjectParseException(Messages.INVALID_GAME_OBJECT_POSITION.formatted(String.join(" ", objWords)), e);
			}
			
			if(objWords.length > 2) {
				throw new ObjectParseException(Messages.INVALID_GAME_OBJECT_EXTRA_ARGS.formatted(String.join(" ", objWords)));
			}
			
			return createInstance(game, p);
			
		}
		return null;
	}
	
	// se sobrescribe en cada clase hija para poder hacer funcionar metodo parse
	protected abstract GameObject createInstance(GameWorld game, Position pos);
	
	
	@Override
		public boolean interactWith(GameItem item) {
			
			return false;
		}
	@Override
		public boolean isSolid() {
			
			return false;
		}
	@Override
		public boolean receiveInteraction(ExitDoor obj) {
			
			return false;
		}
	@Override
		public boolean receiveInteraction(Goomba obj) {
			
			return false;
		}
	@Override
		public boolean receiveInteraction(Land obj) {
			
			return false;
		}
	@Override
		public boolean receiveInteraction(Mario obj) {
			
			return false;
		}
	@Override
	public boolean receiveInteraction(MushRoom obj) {
		
		return false;
	}
	
	@Override
	public boolean receiveInteraction(Box obj) {
		
		return false;
	}
	
	public void addMarioGame() {
		// solo se sobreescribe en Mario
	}

	public boolean matchName(String s) {
		return s.toLowerCase().equals(this.NAME)|| s.toLowerCase().equals(this.SHORTCUT);
	}
	
	public String toString() {
		String firstCapitalize = this.NAME.substring(0, 1).toUpperCase() + this.NAME.substring(1);
		return this.pos.toString() + " " + firstCapitalize; // ej: (14,0) Land
	}
	
}
