package connectors;

public class Close {
    public static void closeProgram(){
        Database.close();
        SSH.close();
    }
}
