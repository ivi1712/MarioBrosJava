package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class ResetCommand extends AbstractCommand {
	
	private Integer level; //Entero, sin decimales
	private static final String NAME = Messages.COMMAND_RESET_NAME;
	private static final String SHORTCUT = Messages.COMMAND_RESET_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_RESET_DETAILS;
	private static final String HELP = Messages.COMMAND_RESET_HELP;
	
	//Constructor
	public ResetCommand() {
        //super("reset", "r", "[r]eset [numLevel]", "reset the game to initial configuration if not numLevel else load the numLevel map");
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

    @Override
    public void execute(GameModel game, GameView view) throws CommandExecuteException {
    	if(level == null) {
    		game.reset();
    		view.showGame();
    	}else {
    		game.resetStats();
    		//Si reset falla el nivel no existe
    		if(!game.reset(level)) {
    			//En vez de enviar esto: view.showError(Messages.INVALID_LEVEL_NUMBER);
    			//Ahora enviamos esto
    			throw new CommandExecuteException(Messages.INVALID_LEVEL_NUMBER);   			
    		}else {
    			view.showGame();
    		}
    	}
    	
    }
    
    @Override
    public Command parse(String[] words) throws CommandParseException {
    	
    	//Comprobamos si coinciden
    	if(matchCommandName(words[0]))    {
    		if(words.length > 2) {
    			throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
    		}
        		
    		//Creamos el comando
    		// Como ya hemos comprobado arriba que la palabra no puede ser mayor que 2
    		// No hace falta lo que teniamos antes de words.length >= 1
        		ResetCommand cmd = new ResetCommand();
        		
        		//Hay argumentos:
        		//  - Si: Cambiamos de nivel
        		if(words.length == 2) {
        		   // 3. Si llegamos aquí, es seguro convertirlo
                    try {
                    	 cmd.level = Integer.parseInt(words[1]);
                    } catch(NumberFormatException nfe){
                     	throw new CommandParseException(Messages.LEVEL_NOT_A_NUMBER_ERROR.formatted(words[1]), nfe);
                    }
        		}
        		return cmd;
        	
    	}
    	
    	//Nombre no coincide
    	return null;
    }
}
