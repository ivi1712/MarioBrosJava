package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameModelException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class LoadCommand extends AbstractCommand {

	private String fileName;
	private static final String NAME = Messages.LOAD_COMMAND_NAME;
    private static final String SHORTCUT = Messages.LOAD_COMMAND_SHORTCUT;
    private static final String DETAILS = Messages.LOAD_COMMAND_DETAILS;
    private static final String HELP = Messages.LOAD_COMMAND_HELP;

	public LoadCommand(String name, String shortcut, String details, String help) {
		super(NAME, SHORTCUT, DETAILS, HELP);
		// TODO Auto-generated constructor stub
	}

	public LoadCommand() {
		// TODO Auto-generated constructor stub
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException {
		try{
            game.load(fileName); 
            //Esto no se si esta bien
            view.showGame(); 
        }catch(GameModelException e){
            throw new CommandExecuteException(Messages.ERROR_COMMAND_EXECUTE, e);
        }
        

	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		// TODO Auto-generated method stub
		if(matchCommandName(commandWords[0])){
			if(commandWords.length != 2)
                throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
			LoadCommand cmd = new LoadCommand();
            cmd.fileName = commandWords[1];
            return cmd;
		}
		return null;
	}

}
