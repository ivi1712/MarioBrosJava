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
		try {
			game.update();
        	view.showGame();
		} catch (Exception e) { 
			throw new CommandExecuteException("Excepcion en UPDATE  no identificada", e); // que mensaje poner?
		}
    }

    
    @Override
    public Command parse(String[] commandWords) throws CommandParseException{
		
		if (commandWords.length == 1 && (matchCommandName(commandWords[0])|| commandWords[0].equals(""))) {
			if (commandWords.length > 1) {
                throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
            }
			return this;
		}else{
			System.out.println(commandWords[0]);
		}
		
		return null;
	}
}
