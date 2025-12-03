package tp1.logic.gameobjects;


<<<<<<< HEAD
import tp1.exceptions.ActionParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
=======
import tp1.exceptions.CommandParseException;
>>>>>>> 3ff17d3396b5442e302dc3a3050db3e6f57e4d41
import tp1.exceptions.PositionParseException;
import tp1.logic.Action;
import tp1.logic.ActionList;
import tp1.logic.GameWorld;
import tp1.view.Messages;
import tp1.logic.Position;

public class Mario extends MovingObject{
	
	private boolean big = true;
<<<<<<< HEAD
=======
	//private boolean right = true;
	//private boolean left = false;
	//private boolean stop = false;
>>>>>>> 3ff17d3396b5442e302dc3a3050db3e6f57e4d41
	private boolean cayendo = false;
	private boolean downstop = false;
	private ActionList actlist;	
	
	public Mario(GameWorld game, Position position) {
		super(game, position);
		this.actlist = new ActionList();
		//this.avanza = false;
		this.avanza = Action.RIGHT;
	}
	
	public Mario() {
		this.actlist = new ActionList();
		this.NAME = Messages.MARIO_NAME;
		this.SHORTCUT = Messages.MARIO_SHORTCUT;
		this.avanza = Action.RIGHT;
	}
	
<<<<<<< HEAD
	public GameObject parse(String objWords[], GameWorld game) throws ObjectParseException, OffBoardException{
		// comprobacion de mario 
		if (matchName(objWords[1])) {
			// pase Position and check not null
			Position p;
			try {
				p = Position.parsePosition(objWords[0]);
			} catch (PositionParseException e) {
				throw new ObjectParseException(Messages.INVALID_GAME_OBJECT_POSITION.formatted(String.join(" ", objWords)), e);
			}
			
			if (game.offBoard(p)) {
	            throw new OffBoardException(Messages.INVALID_GAME_OBJECT_POSITION_OFFBOARD.formatted(String.join(" ", objWords)));
	        }
			
			if (objWords.length > 4) {
	            throw new ObjectParseException(Messages.INVALID_GAME_OBJECT_EXTRA_ARGS.formatted(String.join(" ", objWords)));
	        }

			Mario m = new Mario(game, p);
			if (objWords.length >2) {
				try {
					Action act = Action.parseAction(objWords[2]);
					m.lookDirection(act, false);
				} catch (ActionParseException e) {
					// TODO: handle exception
					throw new ObjectParseException(Messages.UNKNOWN_ACTION.formatted(objWords[2]));
=======
	public GameObject parse(String objWords[], GameWorld game) throws PositionParseException{
		// comprobacion de mario 
		if (objWords[1].toLowerCase().equals(this.NAME)|| objWords[1].toLowerCase().equals(this.SHORTCUT)) {
			// pase Position and check not null
			try {
				Position p = Position.parsePosition(objWords[0]);
			} catch (NumberFormatException e) {
				throw new PositionParseException(e.getMessage(),e);
			}
			
			//if (p == null) return null;

			Mario m = new Mario(game, p);
			if (objWords.length >2) {
				Action act = Action.parseAction(objWords[2]);
				if(act != null) {
					m.lookDirection(act, false);
				}else {
					return null;
>>>>>>> 3ff17d3396b5442e302dc3a3050db3e6f57e4d41
				}
			}
			
			// parse tamaño
			if(objWords.length > 3) {
				// small or big si existe
<<<<<<< HEAD
				//m.big = stringtoBig(objWords[3].toLowerCase());
				switch (objWords[3].toLowerCase()) {
                case "big", "b" ->  m.big = true;
                case "left", "l"-> m.big = false;
                default -> {throw new ObjectParseException(Messages.INVALID_MARIO_SIZE.formatted(String.join(" ", objWords)));} 
				}
=======
				m.big = stringtoBig(objWords[3].toLowerCase());
>>>>>>> 3ff17d3396b5442e302dc3a3050db3e6f57e4d41
			}
			
			return m;
		}
		return null;
	}
<<<<<<< HEAD

=======
	
	private boolean stringtoBig(String s) {
		switch (s.toLowerCase()) {
		case "big", "b" -> {
			return true;
		}
		case "small", "s" -> {
			return false;
		}
		default -> {
			return false; // a la espera de las excepciones
		}
		}
	}
	
>>>>>>> 3ff17d3396b5442e302dc3a3050db3e6f57e4d41
	@Override
	protected GameObject createInstance(GameWorld game, Position pos) {
		
		return null;
	}
	

	public void update() {
		this.cayendo = false;
			if (actlist.anyActions()) {
				while(actlist.anyActions()) {
					actionMovement(actlist.nextAction());
					game.doInteractions(this);
				}	
				//this.avanza = false;
				this.avanza = Action.RIGHT;
				return;
			}
			//automatico big
			if(!automaticMovement()) {
				game.doInteractions(this);
				return;
			}		
	}
	
	

	private void actionMovement(Action dir) {
		if(isNextToLateral(dir) || isNextToSolid(dir) || isNextToRoof(dir)) {
			if(dir == Action.DOWN) {
				//this.stop = true;
				//this.left = false;
				//this.right = false;
				this.downstop = true;
				this.avanza = Action.STOP;
				return;
			}
			lookDirection(dir, true);
		}else {
			if (lookDirection(dir, false)) return;
			marioMove(dir);
		}
	}
	
	public void marioMove(Action dir) {
		this.pos = this.pos.moved(dir);
	}
	
	public boolean lookDirection(Action dir, boolean choque) {
		
		if(choque) {
			if(dir == Action.LEFT) dir = Action.RIGHT;
			if(dir == Action.RIGHT) dir = Action.LEFT;
		}
		
		if(dir == Action.DOWN) {
			Position suelo = this.pos.moved(Action.DOWN);
			caida(suelo);
			return true;
		}else if (dir == Action.STOP) {
			//this.stop = true;
			this.downstop = true;
<<<<<<< HEAD
			this.avanza = dir;
=======
			this.avanza = Action.STOP;
>>>>>>> 3ff17d3396b5442e302dc3a3050db3e6f57e4d41
		} else {
			this.avanza = dir;
			this.downstop = false;
		}
		return choque;
	}
	
	
	public boolean automaticMovement() {
		
		if (caidaUnitaria(this.pos.moved(Action.DOWN))) {
			return false;
		}
		//Action dir = avanza ? Action.LEFT : Action.RIGHT;
		if(isNextToLateral(this.avanza) || isNextToSolid(this.avanza) || isNextToRoof(this.avanza)) {
			//avanza = !avanza;
			leftToRight(this.avanza);
		} else {
			movimientoUnitario();
		}
		return true;
		
	}
	
	
	//redirect movimientoUnitario
	protected void movimientoUnitario(Action avanza, Action dir) {
		
		movimientoUnitario(avanza);
	}
	
	public void movimientoUnitario() {
		if(!this.downstop) {
			this.pos = this.pos.moved(this.avanza);
			//leftToRight(avanza);
			//this.stop = false;
		}
	}
	
	public void leftToRight(Action dir) {
		this.avanza = Action.oposite(dir);
	}
	
	public boolean isNextToRoof(Action dir) {
		Position nextPos = this.pos.moved(dir);
		return nextPos.isRoof(nextPos);
	}
	
	public boolean isNextToLateral(Action dir) {
		Position lateral = this.pos.moved(dir);
		if(big) {
			Position lateral_arriba = this.pos.moved(Action.UP).moved(dir);
			return (lateral.isLateral(lateral) || lateral.isLateral(lateral_arriba));
		}
		
		return lateral.isLateral(lateral);
	}
	
	public boolean isNextToSolid(Action dir) {
		boolean solido =game.isSolid(this.pos.moved(dir));
		if(big) {
			solido = game.isSolid(this.pos.moved(dir).moved(Action.UP)) || solido;
		}
		if(solido && dir == Action.UP) {
			checkBox(dir);
		}
		return solido;
	}
	
	public void checkBox(Action dir) {
		marioMove(dir);
		if(big) {
			move(dir);
		}
		game.doInteractions(this);
		move(Action.oposite(dir));
		if (big) {
			move(Action.oposite(dir));
		}
	}
	
	public String getIcon() {
		// devuelve el icono, segun su direccion
		if (avanza == Action.STOP) {
			return Messages.MARIO_STOP; 
		} else if (avanza == Action.RIGHT) {
			return Messages.MARIO_RIGHT;
		} else if (avanza == Action.LEFT) {
			return Messages.MARIO_LEFT;
		} else if (avanza == Action.DOWN) {
			return Messages.MARIO_STOP;
		}
		return Messages.MARIO_STOP;
	}
	
	public String toString() {
		return "Mario [pos=" + pos + ", big=" + big + 
				", avanza=" + avanza + ", cayendo=" 
				+ cayendo + "]";
	}
	
	public boolean isInPosition (Position p) {
		if (this.big) {
			Position pb = this.pos;
			pb = pb.moved(Action.UP);
			return (this.pos.equals(p)|| pb.equals(p));
		}
		return (this.pos.equals(p));
	}
	
	
	public boolean isInPosition (Goomba g) {
		if(this.big) {
			Position pb = this.pos.moved(Action.UP);
			return (g.isInPosition(pb)||g.isInPosition(this.pos));
		}
		return g.isInPosition(this.pos);
	}
	
	public void addAction(Action act) {
		this.actlist.add(act);
	}
	
	public boolean caida(Position suelo) {
		//caida infinita
		 while (caidaUnitaria(suelo)) {
			 if (this.pos.isVacio(this.pos)) {
		            return true;
		        }
			 suelo = this.pos.moved(Action.DOWN);
		 }
		return false;
		
	}
	
	public boolean caidaUnitaria(Position suelo) {
		if (game.isSolid(suelo)) return false; 
		if(!game.isSolid(suelo)) {
			if (suelo.isVacio(suelo)) {
				this.pos = suelo;
				game.marioDead();
				return true;
			}
			this.pos = suelo;
			this.cayendo = true;
			game.doInteractions(this);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean receiveInteraction(ExitDoor other) {
		game.marioExited();
		return true;
	}
	
	@Override
	public boolean receiveInteraction(Goomba goomba) {
		
		goomba.dead();
		game.addPoints(100);
		
		if (isAlive()) {
			if (!cayendo) {
				if (this.big) {
					this.big = false;
				} else {
					game.marioDead();
					dead();
				}	
			}
		}
		return true;
	}
	
	public boolean isWin() {
		return game.isMarioWins();
	}
	
	public boolean isBig() {
		return this.big;
	}
	
	
	@Override
	public boolean interactWith(GameItem item) {
		boolean canInteract = item.isInPosition(this.pos);
		if(canInteract) {
			return item.receiveInteraction(this);
		}
		return canInteract;
	}
	
	@Override
	public boolean receiveInteraction(MushRoom mushRoom) {
		mushRoom.receiveInteraction(this);
		if (!big) {
			this.big = true;
		}
		return true;
	}
	
	@Override
	public boolean receiveInteraction(Box box) {
		box.receiveInteraction(this);
		return false;
	}
	
	
	public void addMarioGame() {
		game.addMario(this);
	}
	
}
