package tp1.control.commands;

import java.util.ArrayList;
import java.util.List;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.logic.Action;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

/*
 * Command
 * 	  |
 * AbstractCommand
 *    |							|
 * ActionCommand				| (Proviene de AbstractCommand)
 * (Gurda y lleva la 			
 * lista de acciones)
 * */
public class ActionCommand extends AbstractCommand{
	private List<Action> actions; // lista de acciones 
	private static final String NAME = Messages.COMMAND_ACTION_NAME;
	private static final String SHORTCUT = Messages.COMMAND_ACTION_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_ACTION_DETAILS;
	private static final String HELP = Messages.COMMAND_ACTION_HELP;
	
	//Constructor
	public ActionCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
		this.actions = new ArrayList<>();
	}
	
	//Metodo parse : Encargada de convertir el texto en acciones
	
	@Override
	public Command parse(String[] words) throws CommandParseException{
		if(words.length >= 2 && 
				(words[0].toLowerCase().equalsIgnoreCase(ActionCommand.SHORTCUT) || words[0].toLowerCase().equalsIgnoreCase(ActionCommand.SHORTCUT))) {
			
			ActionCommand cmd = new ActionCommand();
			
			//Procesamos cada accion
			for(int i = 1; i<words.length; i++) {
				Action dir = Action.parseAction(words[i].toLowerCase());
				if(dir == null) return null;
				else cmd.actions.add(dir);
				
			}//for
			return cmd; //Devuelve el comando con las acciones cargadas
		}
		return null;
	}
	
	//Metodo encargado de agragar esas acciones a la lista de acciones de Mario 
	//y Actualiza el juego
	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException{
		//Añadir todas la sacciones a la lista de acciones de Mario
		//Copiar private actions a mario actionlist 
		while(!actions.isEmpty()) {
			game.addAction(this.actions.remove(0)); //Agragamos la accion al juego
		}
		game.update(); // Actualiza el estado del jeugo con las acciones
		view.showGame();
	}
	
}
