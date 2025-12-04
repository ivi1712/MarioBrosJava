package tp1.control.commands;

import java.util.Arrays;
import java.util.List;

import tp1.exceptions.CommandParseException;
import tp1.view.Messages;

//Se crean los comandos
public class CommandGenerator {

	private static final List<Command> AVAILABLE_COMMANDS = Arrays.asList(
			new LoadCommand(),
			new SaveCommand(),
			new AddObjectCommand(),
			new ActionCommand(),
			new UpdateCommand(),
			new ResetCommand(),
			new HelpCommand(),
			new ExitCommand()
	);

	public static Command parse(String[] commandWords) throws CommandParseException {		
		
		Command parsed = null;
		for (Command c: AVAILABLE_COMMANDS) {
			// Si c.parse lanza la excepciones, se manda auto al controller
			// No hcae falta hacer un try cacth con la causa de la accion porque
			// Ya nos la da el comando
				parsed = c.parse(commandWords);
				if(parsed != null) return parsed;
		}
		//No ha coincidido con ningún comando de lso que se ha mandado
		throw new CommandParseException(Messages.UNKNOWN_COMMAND.formatted(commandWords[0]));
	}
		
	public static String commandHelp() {
		StringBuilder commands = new StringBuilder();
		
		commands.append(Messages.HELP_AVAILABLE_COMMANDS).append(Messages.LINE_SEPARATOR);
		
		for (Command c: AVAILABLE_COMMANDS) {
			
			commands.append(c.helpText());
		}
		
		return commands.toString();
	}

}
