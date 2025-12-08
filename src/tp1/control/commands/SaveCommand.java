package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameModelException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class SaveCommand extends AbstractCommand {
	
    private String fileName;
	private static final String NAME = Messages.COMMAND_SAVE_COMMAND_NAME;
    private static final String SHORTCUT = Messages.COMMAND_SAVE_COMMAND_SHORTCUT;
    private static final String DETAILS = Messages.COMMAND_SAVE_COMMAND_DETAILS;
    private static final String HELP = Messages.COMMAND_SAVE_COMMAND_HELP;

	public SaveCommand(String name, String shortcut, String details, String help) {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	public SaveCommand() {
		
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException{
        try{
            game.save(fileName); 
            view.showMessage(Messages.FILE_SAVED_CORRECTLY.formatted(fileName)+ "\n");
        }catch(GameModelException e){
            throw new CommandExecuteException(Messages.ERROR_COMMAND_EXECUTE, e);
        }
        
        
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		if(matchCommandName(commandWords[0])){
            if(commandWords.length != 2)
                throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);

            SaveCommand cmd = new SaveCommand();
            cmd.fileName = commandWords[1];
            return cmd;
        }
        return null;
	}

}
