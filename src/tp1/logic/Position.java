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
		return p.col >= Game.DIM_X || p.col < 0;
	}
	
	public boolean isRoof(Position p) {
		return p.row < 0;
	}
	
	@Override
	public String toString() {
		return "(" + this.row + "," + this.col + ")";
	}
	
	public static Position parsePosition(String parse) throws PositionParseException{
		if (parse == null) {
			throw new PositionParseException(Messages.INVALID_POSITION_FORMAT.formatted(parse));
		}

		String[] parseList = parse.split(",", -1);
		if (parseList.length != 2) {
			throw new PositionParseException(Messages.INVALID_POSITION_FORMAT.formatted(parse));
		}

		String rowText = parseList[0].replaceAll("\\(", "").trim();
		String colText = parseList[1].replaceAll("\\)", "").trim();
		try {
			return new Position(Integer.parseInt(rowText), Integer.parseInt(colText));
		} catch(NumberFormatException e) {
			throw new PositionParseException(Messages.INVALID_POSITION_FORMAT.formatted(parse), e);
		}
	}

}
