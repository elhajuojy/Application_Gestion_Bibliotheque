package ma.yc.sas.enums;

public enum QueryStatementVerbs {
    SELECT ("SELECT"),
    INSERT("INSERT INTO") ,
    UPDATE ("UPDATE"),
    DELETE("DELETE") ;

    public final String verb;

    private QueryStatementVerbs(String verb) {
        this.verb = verb;
    }
}
