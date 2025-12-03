package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class UpdateCommand extends NoParamsCommand {
	private static final String NAME = Messages.COMMAND_UPDATE_NAME;
	private static final String SHORTCUT = Messages.COMMAND_UPDATE_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_UPDATE_DETAILS;
	private static final String HELP = Messages.COMMAND_UPDATE_HELP;
	//Constructor
	public UpdateCommand() {
        super(NAME,SHORTCUT,DETAILS,HELP);
    }

    @Override
    public void execute(GameModel game, GameView view) throws CommandExecuteException{
    	//Su error es mandado por AbstractCommand, lo mismo pasa en Help
			game.update();
        	view.showGame();
    }

    
    @Override
    public Command parse(String[] commandWords) throws CommandParseException{
    	//Su error es mandado por NoParamsCommand, lo mismo pasa en Help
		if (commandWords.length == 1 && (matchCommandName(commandWords[0])|| commandWords[0].equals(""))) {
			return this;
		}
		
		// 2. Caso normal: El usuario escribe "update" o "u".
				// Delegamos en la clase padre (NoParamsCommand).
				// Ella se encarga de:
				//    a) Ver si el nombre coincide.
				//    b) Si coincide pero hay argumentos extra -> Lanza CommandParseException (Lo que te falta ahora)
				//    c) Si coincide y no hay argumentos -> Devuelve this.
				//    d) Si no coincide -> Devuelve null.
		// Si no pongo esto hay un fallo en la linea 41
		return super.parse(commandWords);
	}
}
