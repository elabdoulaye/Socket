package com.elso.socketTCP;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientTCP extends Thread {
	Socket socket = null;
    BufferedReader buffer = null;
    PrintWriter print = null; 
    String ad=null;
    int port;
    public ClientTCP(){
    	
	}
    public void run(){
    	Socket socket=null;
		try {
			socket = new Socket("127.0.0.1",4000);
			buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String mess = buffer.readLine();
			System.out.println("Message:\n "+mess);
			
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    	
	    envoyer();
	}
    
	public void envoyer(){
		Scanner s = new Scanner(System.in);
		try{
			
	        while(true){
	        	try{
	    			socket = new Socket("127.0.0.1",4001);
	    			print = new PrintWriter(socket.getOutputStream(),true);
	    			buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    			System.out.print("Ecrire la chaine à transformer : ");
		        	String mess = s.nextLine();
		        	print.println(mess);
			        print.flush();
			       System.out.println();
			        if(!mess.equalsIgnoreCase("AU REVOIR")){
			        	
				        reception(); 
		        	}
			        else{
		    			
			        	String rec = buffer.readLine();
						System.out.println("Message du serveur:\n"+rec);
						System.out.println("Merci de votre visite");
		    			buffer.close();
		    			print.close();
			        	socket.close();
			        	System.exit(0);
			        
			        }
	    		}
	    	    catch(UnknownHostException e){System.out.print("machine introuvable");}
	    	    catch(IOException e){System.out.print("I/O impossible!");}
		    }
	    }
	    catch(Exception e){System.out.println("Problème lors de l'envoi de données!!!"+e);
	        System.exit(1);
	    }
	}
	public void reception(){
		try{
			String rec = buffer.readLine();                                                                                                                                                                                                              
			System.out.println("Résultat envoyé par le serveur: "+rec);
		}
		catch(IOException e){}
	}
    public static void main(String args[]) throws IOException{
    	ClientTCP cli = new ClientTCP();
		cli.start();
    }
}

