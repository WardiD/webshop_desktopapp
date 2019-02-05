package connectors;

/**
 * Class responsible for closing all connections ( like database, SSH )
 */
public class Close {
    public static void closeProgram(){
        Database.close();
        SSH.close();
    }
}
