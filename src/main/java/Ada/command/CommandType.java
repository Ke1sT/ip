package Ada.command;

/**
 * Enumerates all supported command types.
 */
public enum CommandType {
    TODO, DEADLINE, EVENT, //variable args: Desc, (/by, /from) (/to)
    MARK, UNMARK, DELETE,  //1 arg
    LIST, //0 args
    BYE
}
