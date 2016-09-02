package se.treehou.ng.ohcommunicator.util;

/**
 * Holds commands that can be sent to openhab server
 */
public class Commands {

    private Commands() {}

    public static final String COMMAND_ON       = "ON";
    public static final String COMMAND_OFF      = "OFF";
    public static final String COMMAND_UNINITIALIZED = "Uninitialized";

    public static final String COMMAND_OPEN     = "OPEN";
    public static final String COMMAND_CLOSE    = "CLOSE";

    public static final String COMMAND_UP       = "UP";
    public static final String COMMAND_DOWN     = "DOWN";
    public static final String COMMAND_STOP     = "STOP";
    public static final String COMMAND_MOVE     = "MOVE";

    public static final String COMMAND_INCREMENT = "INCREASE";
    public static final String COMMAND_DECREMENT = "DECREASE";

    public static final String COMMAND_COLOR    = "%d,%d,%d";
}
