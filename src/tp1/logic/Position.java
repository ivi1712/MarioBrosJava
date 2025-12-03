package tp1.logic;

<<<<<<< HEAD
import tp1.exceptions.PositionParseException;
=======
import tp1.exceptions.OffBoardException;
>>>>>>> 3ff17d3396b5442e302dc3a3050db3e6f57e4d41
import tp1.view.Messages;

public final class Position {

	private final int col;
	private final int row;
	
	public Position(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public boolean equals(Position p) {
		return this.col == p.col && this.row == p.row;
	}
	
	
	public Position moved(Action a) {
	    return new Position(this.row + a.getY(), this.col + a.getX());
	}
	
	public boolean isVacio(Position p) {
		return p.row >= Game.DIM_Y;
	}
	
	public boolean isLateral(Position p) {
		return p.col > Game.DIM_X || p.col < 0;
	}
	
	public boolean isRoof(Position p) {
		return p.row < 0;
	}
	
	@Override
	public String toString() {
		
		return "(" + this.col + "," + this.row + ")";
	}
	
<<<<<<< HEAD
	public static Position parsePosition(String parse) throws PositionParseException{
=======
	public static Position parsePosition(String parse) throws NumberFormatException{
>>>>>>> 3ff17d3396b5442e302dc3a3050db3e6f57e4d41
		if (parse == null) return null;
		String[] parseList = parse.split(",");
		parseList[0] = parseList[0].replaceAll("\\(", "");
		parseList[1] = parseList[1].replaceAll("\\)", "");
<<<<<<< HEAD
		Position p;
		try {
			p = new Position(Integer.parseInt(parseList[0]), Integer.parseInt(parseList[1]));
		} catch(NumberFormatException e) {
			throw new PositionParseException(Messages.INVALID_POSITION_FORMAT.formatted(parse), e);
=======
		String valor = null;
		Position p;
		try {
			valor = parseList[0];
			int x = Integer.parseInt(valor);
			valor = parseList[1];
			int y = Integer.parseInt(valor);
			p = new Position(x, y);
		} catch(NumberFormatException e) {
			throw new NumberFormatException(String.format("For input string: \"%s\"  ", valor));
>>>>>>> 3ff17d3396b5442e302dc3a3050db3e6f57e4d41
		}
		return p;
	}

}
