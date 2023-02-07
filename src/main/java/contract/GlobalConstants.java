package contract;

public class GlobalConstants {

    public static final String SUM = "+";
    public static final String SUBS = "-";
    public static final String MUL = "x";
    public static final String DIV = ":";
    public static final String KO = "KO";

    public static boolean isKO(final String trim) {
        return KO.equals(trim);
    }
}
