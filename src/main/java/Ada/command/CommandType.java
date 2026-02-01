package Ada.command;

public enum CommandType {
    TODO, DEADLINE, EVENT, //variable args: Desc, (/by, /from) (/to)
    MARK, UNMARK, DELETE,  //1 arg
    LIST, FIND,//0 args
    BYE
}
