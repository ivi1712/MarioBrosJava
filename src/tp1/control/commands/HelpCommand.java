package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class HelpCommand extends NoParamsCommand {

    private static final String NAME = Messages.COMMAND_HELP_NAME;
    private static final String SHORTCUT = Messages.COMMAND_HELP_SHORTCUT;
    private static final String DETAILS = Messages.COMMAND_HELP_DETAILS;
    private static final String HELP = Messages.COMMAND_HELP_HELP;

    public HelpCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException{
<<<<<<< HEAD
		//Su error es mandado por AbstractCommand, lo mismo pasa en Help
			view.showMessage(CommandGenerator.commandHelp());
=======
		try {
			view.showMessage(CommandGenerator.commandHelp());
		} catch (Exception e) {
			throw new CommandExecuteException("Excepcion en HELP no identificada", e); 
		}
>>>>>>> 3ff17d3396b5442e302dc3a3050db3e6f57e4d41
	}
	
}
