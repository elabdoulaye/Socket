package com.elso.socketUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerUdp {
final static Logger logger=Logger.getLogger("Localhost");
static int port=1300;
//Socket local du serveur
DatagramSocket socket;
/**Construit un serveur UDP Daytime attach� au port 1300  
 * A la fin de la construction, le serveur n'est pas encore en service, 
 * mais sa m�thode run() est pr�te � �tre ex�cut�e
 * Pour Test
 */
public ServerUdp(int localPort) throws SocketException{
	socket= new DatagramSocket(localPort);
}
/**
 * Met le serveur Daytime en service.En permanance, ce dernier attend 
 * la r�ception d'un datagramme,y place l'heure courante et le renvoie � l'�metteur
 */
public void run(){
//Datagramme utilis� pour la r�ception et l'�mission
	DatagramPacket packet;
	//Une zone de stockage m�me vide doit �tre allou�e pour pouvoir cr�er le datagramme
	packet=new DatagramPacket(new byte[0],0);
	//Boucle d'attente de r�ception de datagramme et d'envoie de r�ponse
	logger.info("Serveur d�marr� sur:"+socket.getLocalSocketAddress());
	while(true){
		try{
			socket.receive(packet);
		}
		catch(IOException e){
			logger.log(Level.WARNING,"Probl�me de r�ception",e);
			continue;
		}
		/**
		 * Lorsque le datagramme est re�u, on convertit la date courante en 
		 * un tableau d'octet que l'on r�f�re comme zone de stockage
		 */
	Date date=new Date();
	byte[] buffer=date.toString().getBytes();
	//Mise � jour des indices de la zone de stockage dans le datagramme
	packet.setData(buffer,0,buffer.length);
	//Envoi de datagramme qui contient le port et l'adresse du client
	try{
		socket.send(packet);
	}
	catch(IOException e){
		logger.log(Level.WARNING,"Emmission impossible",e);
		continue;
	}
	}
}
/*M�thode de test qui cr�e un serveur avec le port �ventuellement
 * sp�cifi� sur la ligne de commande et lance son ex�cution dans le processus l�ger
 * courant en appelant run()*/
public static void main (String arg[]){
	/*En l'absence d'argument sur la ligne de commande, le port local par d�faut
	 * du serveur est affect� au port r�serv� � Daytime 
	 */
//int portDay;
//if(arg.length==0)
//	portDay=port;
//else
//	port=Integer.parseInt(arg[0]);
//Cr�ation et d�marrage du serveur UDP Daytime attach� � ce port
try{
	ServerUdp serveur=new ServerUdp(port);
	serveur.run();
}
catch(SocketException soc){
	System.err.println("Cr�ation de serveur impossible");	
soc.printStackTrace(System.err);
}
}
}
