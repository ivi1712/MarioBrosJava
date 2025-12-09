package tp1.view;

import tp1.util.MyStringUtils;

public class Messages {
	
	//  Cabecera inicial del juego. Se usa en Main o Controller al iniciar.
	public static final String VERSION = "3.0";
	public static final String GAME_NAME = "MarioBross";
	public static final String USAGE = "Usage: %s [<level>]".formatted(GAME_NAME);
	public static final String WELCOME = String.format("%s %s%n", GAME_NAME, VERSION);

	//  Validación de argumentos en el ResetCommand o en el Main (al leer args).
	public static final String LEVEL_NOT_A_NUMBER = "The level must be a number";
	public static final String INVALID_LEVEL_NUMBER = "Not valid level number"; // Usado en ResetCommand.execute()
	public static final String LEVEL_NOT_A_NUMBER_ERROR = String.format("%s: %%s", LEVEL_NOT_A_NUMBER); // Usado en ResetCommand.parse()

	//  Interfaz de usuario básica en GameView / Controller.
	public static final String PROMPT = "Command > ";
	public static final String DEBUG = "[DEBUG] Executing: %s%n";
	public static final String ERROR = "[ERROR] Error: %s"; // Usado por Controller para formatear cualquier excepción capturada.

	// GAME STATUS
	//  En GameView (ConsoleView) para pintar la cabecera del tablero.
	public static final String NUMBER_OF_CYCLES = "Number of cycles: %s";
	public static final String REMAINING_TIME = "Time: %s";
	public static final String POINTS = "Points: %s";
	public static final String NUM_LIVES = "Lives: %s";

	// GAME END MESSAGE
	//  En GameView.showEndMessage() para indicar el resultado final.
	public static final String GAME_OVER = "Game over";
	public static final String PLAYER_QUITS = "Player leaves the game";
	public static final String MARIO_WINS = "Thanks, Mario! Your mission is complete.";
	
	// Position format
	//  Formato genérico para imprimir posiciones (x,y).
	public static final String POSITION = "(%s,%s)";

	// Other
	//  Utilidades de formateo para la vista (MyStringUtils, ConsoleView).
	public static final String SPACE = " ";
	public static final String TAB = "   ";
	public static final String LINE_SEPARATOR = System.lineSeparator();
	public static final String LINE = "%s" + LINE_SEPARATOR;
	public static final String LINE_TAB = TAB + LINE;
	public static final String LINE_2TABS = TAB + LINE_TAB;

//Commands
	// ERRORES DE FACTORÍAS Y PARSEO DE OBJETOS
	
	//  En CommandGenerator.parse() cuando ningún comando coincide.
	public static final String UNKNOWN_COMMAND = "Unknown command: %s";
	
	//  En Game.parseGameObjectFactory() si la factoría devuelve null.
	public static final String INVALID_GAME_OBJECT = "Invalid game object: \"%s\"";
	
	//  En FileGameConfiguration o GameObjectFactory si se lee un nombre desconocido.
	public static final String UNKNOWN_GAME_OBJECT = "Unknown game object: \"%s\"";
	
	//  En GameObject.parse() cuando Position.parsePosition() falla.
	public static final String INVALID_GAME_OBJECT_POSITION = "Invalid object position: \"%s\"";
	
	//  En GameObjectContainer.addObjectFactory() si la posición está ocupada.
	public static final String NOAVAIBLE_GAME_OBJECT = "No avaible to create a game object in this position";
	
	//  En Position.parsePosition() cuando el formato numérico falla.
	public static final String INVALID_POSITION_FORMAT = "Invalid position: \"%s\"";
	
	//  En GameObject.parse() (validación límites) o OffBoardException.
	public static final String INVALID_GAME_OBJECT_POSITION_OFFBOARD = "Object position is off board: \"%s\"";
	
	//  En GameObject.parse() si sobran argumentos.
	public static final String INVALID_GAME_OBJECT_EXTRA_ARGS = "Object parse error, too much args: \"%s\"";
	
	//  En clases MovingObject (Goomba/Mario) al parsear dirección desconocida.
	public static final String UNKNOWN_GAME_OBJECT_DIRECTION = "Unknown moving object direction: \"%s\"";
	
	//  En clases MovingObject cuando la dirección es válida (UP) pero no aplicable al objeto.
	public static final String INVALID_GAME_OBJECT_DIRECTION = "Invalid moving object direction: \"%s\"";
	
	//  En Mario.parse() para tamaño incorrecto.
	public static final String INVALID_MARIO_SIZE = "Invalid Mario size: \"%s\"";
	
	//  En Box.parse() para estado incorrecto.
	public static final String INVALID_BOX_STATUS = " Invalid Box status: \"%s\"";

	//  ERRORES DE COMANDOS GENÉRICOS 
	
	//  Validaciones generales de parseo.
	public static final String COMMAND_PARAMETERS_MISSING = "Missing parameters";
	
	//  En cualquier Command.parse() si el número de argumentos no encaja.
	public static final String COMMAND_INCORRECT_PARAMETER_NUMBER = "Incorrect parameter number";
	
	//  En Action.parseAction() (llamado por ActionCommand).
	public static final String UNKNOWN_ACTION = "Unknown action: \"%s\"";
	public static final String ILLEGAL_ACTION = "Illegal action: \"%s\""; // (Menos usado, para acciones prohibidas por lógica)
	
	//  Errores genéricos de comando.
	public static final String INVALID_COMMAND = "Invalid command: %s";
	public static final String INVALID_COMMAND_PARAMETERS = "Invalid command parameters";
	
	//  En Command.execute() (ej: AddObjectCommand) para envolver GameModelException.
	public static final String ERROR_COMMAND_EXECUTE = "Command execute problem";
	
	//  En ActionCommand.parse() si la lista final de acciones queda vacía.
	public static final String ACTION_COMMAND_EMPTY = "Incorrect 'action command', because the action list is empty (all actions are unknown).";
	
	//  Error genérico de parseo.
	public static final String COMMAND_PARSE_PROBLEM = "Command parse problem";
	
	//  En operaciones de ficheros (Save/Load) si hay SecurityException.
	public static final String ACCESS_DENIED = "Acceso denegado al archivo: \"%s\" ";

	//  AYUDA (HELP) 
	
	//  Cabecera del comando Help.
	public static final String HELP_AVAILABLE_COMMANDS = "Available commands:";
	@Deprecated
	/* @formatter:off */
	public static final String[] HELP_LINES = new String[] { HELP_AVAILABLE_COMMANDS,
		"[a]ction [[R]IGHT | [L]EFT | [U]P | [D]OWN | [S]TOP]+: user performs actions",
		"[u]pdate | \"\": user does not perform any action",
		"[r]eset [numLevel]: reset the game to initial configuration if not numLevel else load the numLevel map",
		"[h]elp: print this help message",
		"[e]xit: exits the game"
	};
	/* @formatter:on */
	@Deprecated
	public static final String HELP = String.join(LINE_SEPARATOR+"   ", HELP_LINES) + LINE_SEPARATOR;
	
	//  En AbstractCommand.helpText() para formatear la ayuda de cada comando.
	public static final String COMMAND_HELP_TEXT = "%s: %s";
	
	//  DEFINICIÓN DE COMANDOS (Nombre, Atajo, Detalles, Ayuda) 
	// Se usan en los constructores de cada clase Command (super(...)).
	
	// UPDATE
	public static final String COMMAND_UPDATE_NAME = "update";
	public static final String COMMAND_UPDATE_SHORTCUT = "u";
	public static final String COMMAND_UPDATE_DETAILS = "[u]pdate | \"\"";
	public static final String COMMAND_UPDATE_HELP = "user does not perform any action";
		
	// EXIT
	public static final String COMMAND_EXIT_NAME = "exit";
	public static final String COMMAND_EXIT_SHORTCUT = "e";
	public static final String COMMAND_EXIT_DETAILS = "[e]xit";
	public static final String COMMAND_EXIT_HELP = "exits the game";
	
	// HELP
	public static final String COMMAND_HELP_NAME = "help";
	public static final String COMMAND_HELP_SHORTCUT = "h";
	public static final String COMMAND_HELP_DETAILS = "[h]elp";
	public static final String COMMAND_HELP_HELP = "print this help message";
	
	// ACTION
	public static final String COMMAND_ACTION_NAME = "action";
	public static final String COMMAND_ACTION_SHORTCUT = "a";
	public static final String COMMAND_ACTION_DETAILS = "[a]ction [[R]IGHT | [L]EFT | [U]P | [D]OWN | [S]TOP]+";
	public static final String COMMAND_ACTION_HELP = "user performs actions";
	
	// ADDOBJECT
	public static final String COMMAND_ADDOBJECT_NAME = "addobject";
	public static final String COMMAND_ADDOBJECT_SHORTCUT = "ao";
	public static final String COMMAND_ADDOBJECT_DETAILS = "[a]dd[O]bject <object_description>";
	public static final String COMMAND_ADDOBJECT_HELP = "adds to the board the object given by object_description.\n      "
			+ "<object_description> = (col,row) objName [dir [BIG|SMALL]]. Ej. (12,3) Mario LEFT SMALL";
	public static final String COMMAND_ADDOBJECT_HELP2 = "adds to the board the object given by object_description.";

	
	// RESET
	public static final String COMMAND_RESET_NAME = "reset";
	public static final String COMMAND_RESET_SHORTCUT = "r";
	public static final String COMMAND_RESET_DETAILS = "[r]eset [numLevel]";
	public static final String COMMAND_RESET_HELP = "reset the game to initial configuration if not numLevel else load the numLevel map";

	//LOAD (Persistencia)
	public static final String LOAD_COMMAND_NAME = "load";
	public static final String LOAD_COMMAND_SHORTCUT = "l";
	public static final String LOAD_COMMAND_DETAILS = "[l]oad <fileName>";
	public static final String LOAD_COMMAND_HELP = "load the game configuration from text file <fileName>";
	
	//  Mensaje genérico de error de carga (puede usarse en excepciones encadenadas).
	public static final String ERROR_LOAD = "Error loading game: ";
	
	//  En FileGameConfiguration o Game.load() cuando hay error de formato general.
	public static final String INVALID_FILE_CONFIGURATION = "Invalid file \"%s\" configuration";
	
	//  En LoadCommand.execute() para envolver la excepción del modelo.
	public static final String UNABLE_TO_LOAD = "Unable to load game configuration from file \"%s\"";
	
	//  En FileGameConfiguration al leer la cabecera (100 0 3).
	public static final String INVALID_GAME_STATUS = "Incorrect game status \"%s\"";

	//SAVE (Persistencia)
	public static final String COMMAND_SAVE_COMMAND_NAME = "save";
	public static final String COMMAND_SAVE_COMMAND_SHORTCUT = "s";
	public static final String COMMAND_SAVE_COMMAND_DETAILS = "[s]ave <fileName>";
	public static final String COMMAND_SAVE_COMMAND_HELP = "save the actual configuration in text file <fileName>";
	
	//  En FileGameConfiguration (constructor) cuando no encuentra el archivo.
	public static final String FILE_NOT_FOUND = "File not found: \"%s\"";
	
	//  En SaveCommand.execute() al terminar con éxito.
	public static final String FILE_SAVED_CORRECTLY = "   File \"%s\" correctly saved";

	
// GameObjectFactory (Identificadores para parseo)
//  Usados en el método parse() de cada clase concreta y en la Factoría.
	
	// LAND
	public static final String LAND_NAME = "land";
	public static final String LAND_SHORTCUT = "l";
	
	// MARIO
	public static final String MARIO_NAME = "mario";
	public static final String MARIO_SHORTCUT = "m";
	
	// GOOMBA
	public static final String GOOMBA_NAME = "goomba";
	public static final String GOOMBA_SHORTCUT = "g";
	
	//EXITDOOR
	public static final String EXITDOOR_NAME = "exitdoor";
	public static final String EXITDOOR_SHORTCUT = "ed";
	
	// MUSHROOM
	public static final String MUSHROOM_NAME = "mushroom";
	public static final String MUSHROOM_SHORTCUT = "mu";
	
	// BOX
	public static final String BOX_NAME = "box";
	public static final String BOX_SHORTCUT = "b";

//Symbols
//  En el método getIcon() de cada GameObject y en la vista para pintar celdas vacías.
	public static final String EMPTY = "";
	public static final String LAND = MyStringUtils.repeat("▓",ConsoleView.CELL_SIZE);
	public static final String EXIT_DOOR = "🚪";
	public static final String MARIO_STOP = "🧑";
	public static final String MARIO_RIGHT = "🧍";//"🧍➡️";
	public static final String MARIO_LEFT = "🚶";//"⬅️🚶";
	public static final String GOOMBA = "🐻";
	
	public static final String MUSHROOM = "🍄";
	public static final String BOX = MyStringUtils.repeat("?",ConsoleView.CELL_SIZE);
	public static final String EMPTY_BOX = MyStringUtils.repeat("0",ConsoleView.CELL_SIZE);
}