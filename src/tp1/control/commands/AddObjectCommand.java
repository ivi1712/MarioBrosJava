package tp1.control.commands;

import java.util.Arrays;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class AddObjectCommand extends AbstractCommand{

	//private List<GameObject> actions; // lista de acciones 
	private static final String NAME = Messages.COMMAND_ADDOBJECT_NAME;
	private static final String SHORTCUT = Messages.COMMAND_ADDOBJECT_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_ADDOBJECT_DETAILS;
	private static final String HELP = Messages.COMMAND_ADDOBJECT_HELP;
	
	// prompt como lista de strings con el objeto con sus propiedades
	private String[] objWords;
	
	//Constructor
	public AddObjectCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
		//this.objWords = new ArrayList<String>();
	}

	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException{
		
		try {
			game.parseGameObjectFactory(objWords);
			view.showGame();
		}catch (ObjectParseException | OffBoardException  e) {
			throw new CommandExecuteException(Messages.ERROR_COMMAND_EXECUTE, e);
		}
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException{
		
		//ejeccucion del comando deseado
		if (matchCommandName(commandWords[0])) {
			//Comprobamos aqui la longitud, porque si esta falla tiene que lanzar una excepcion
			// preparar objWords para eliminar el comando
			// 0 = (x,y), 1 = objeto , 2 = atributo (opc), 3 = atributo (opc)
			if(commandWords.length >= 3){
				this.objWords = Arrays.copyOfRange(commandWords, 1, commandWords.length);
				return this;
			}else{
				throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
			}
			
		}
				
		return null;
	}
	

}
