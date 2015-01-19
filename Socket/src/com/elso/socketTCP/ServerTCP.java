package com.elso.socketTCP;

import java.net.*;
import java.io.*;
public class ServerTCP  extends Thread{

	static int i = 0;
	ServerSocket serveur = null;
	Socket socketcli = null;
	BufferedReader buffer = null;

	public ServerTCP(Socket s) throws IOException{
		this.socketcli = s;
	}
	public void run(){
		try{
			
		    traitement();
		}
		catch(IOException e){
			System.out.println("TRAITEMENT IMPOSSIBLE!!!");
			}
	}
	public void traitement() throws IOException{
		try{
			PrintWriter mot = new PrintWriter(socketcli.getOutputStream(),true);
			if(i == 0){
        		i = socketcli.getPort();
        		mot.println("Bonjour client "+socketcli.getInetAddress()+" : "+socketcli.getPort());
        		mot.println("Si vous voulez quitter taper AU REVOIR  ");
        		 mot.flush();
        		 mot.close();
           	}
			buffer = new BufferedReader(new InputStreamReader(socketcli.getInputStream()));
			String mess = buffer.readLine();
			System.out.println("Chaine envoyée par le client : "+mess);
			if(mess.equalsIgnoreCase("AU REVOIR")){
				StringBuffer chaine = new StringBuffer("Déconnexion effectuée");
				mot.print(chaine);
				mot.print("\nA bientot client "+socketcli.getInetAddress()+" de port "+socketcli.getPort());
				mot.flush();
			    mot.close();
			    buffer.close();
				i = 0;
			}
			else{
				StringBuffer modifchaine = new StringBuffer(mess).replace(0,mess.length(), mess.toUpperCase());
			mot.print(modifchaine);
				System.out.println("Chaine réenvoyée au client : "+modifchaine);
			    mot.flush();
			    mot.close();
			}
		}
		catch(IOException e){System.out.println("Connexion");}
	}
	public static void main(String [] args) throws IOException{
		System.out.println(" Serveur démarré...\n...attente de connexion d'un client");
		
		try{
			@SuppressWarnings("resource")
			ServerSocket server = new ServerSocket(4001);
	        while(true){
	        	Socket socketcli = server.accept();
	        	new ServerTCP(socketcli).start();
	        }
	    }
        catch(IOException e){System.out.println("CONNEXION IMPOSSIBLE!!!");}
	}
}




