package tp1.logic;

import tp1.exceptions.PositionParseException;
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
	
	public static Position parsePosition(String parse) throws PositionParseException{
		if (parse == null) return null;
		String[] parseList = parse.split(",");
		parseList[0] = parseList[0].replaceAll("\\(", "");
		parseList[1] = parseList[1].replaceAll("\\)", "");
		Position p;
		try {
			p = new Position(Integer.parseInt(parseList[0]), Integer.parseInt(parseList[1]));
		} catch(NumberFormatException e) {
			throw new PositionParseException(Messages.INVALID_POSITION_FORMAT.formatted(parse), e);
		}
		return p;
	}

}
