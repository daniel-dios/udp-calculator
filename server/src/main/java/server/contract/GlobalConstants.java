package server.contract;

public class GlobalConstants {

    public static final String ADD = "+";
    public static final String SUB = "-";
    public static final String MUL = "x";
    public static final String DIV = ":";
    public static final String KO = "KO";

    public static boolean isKO(final String trim) {
        return KO.equals(trim);
    }
}
