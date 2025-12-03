package tp1.control.commands;

import tp1.exceptions.CommandParseException;
import tp1.view.Messages;

public abstract class NoParamsCommand extends AbstractCommand {

	public NoParamsCommand(String name, String shortcut, String details, String help) {
		//Este es el constructor del padre (AbstractCommand)
		//Cada vez que creamos un comando, le pasmos estos datos
		super(name, shortcut, details, help);
		
		//Entonces el constructor padre los guarda en variables(this.name,...)
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException{
<<<<<<< HEAD
		//Cambio: estetica & entendimiento mas legible
		if(matchCommandName(commandWords[0])) {
			if (commandWords.length > 1) 
				throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
			
=======
		
		// argumentos extra con nombre que coincide
		if (commandWords.length > 1 && matchCommandName(commandWords[0])) 
			throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
		
		// coincide y no tiene argumentos
		if (commandWords.length == 1 && matchCommandName(commandWords[0])) {
>>>>>>> 3ff17d3396b5442e302dc3a3050db3e6f57e4d41
			return this;
		}
		return null;
	}
}
