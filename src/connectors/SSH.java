package connectors;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SSH{
	public static Session session;
	private static JSch jsch;


	public static void connectSSH(){
        final String rhost = "pascal.fis.agh.edu.pl";
        final String host= "pascal.fis.agh.edu.pl";
        final int lport = 5432; // local port
        final int rport = 5432; //remote port
        final String sshUser = "5wardega";
        final String sshPassword = "entumblendsd";

        try{
            //Set StrictHostKeyChecking property to no to avoid UnknownHostKey issue
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            jsch = new JSch();
            SSH.session=jsch.getSession(sshUser, host, 22);
            SSH.session.setPassword(sshPassword);
            SSH.session.setConfig(config);
            SSH.session.connect();
            System.out.println("SSH Connected");
            int assinged_port=SSH.session.setPortForwardingL(lport, rhost, rport);
            System.out.println("localhost:"+assinged_port+" -> "+rhost+":"+rport);
            System.out.println("Port Forwarded");

        } catch (JSchException ex){
            ex.printStackTrace();
        }

	}

	public static void close(){
		if(SSH.session !=null && SSH.session.isConnected()){
                System.out.println("Closing SSH Connection");
                SSH.session.disconnect();
            }
	}
	
}