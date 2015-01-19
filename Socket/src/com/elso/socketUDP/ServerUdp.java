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
/**Construit un serveur UDP Daytime attaché au port 1300  
 * A la fin de la construction, le serveur n'est pas encore en service, 
 * mais sa méthode run() est prête à être exécutée
 * Pour Test
 */
public ServerUdp(int localPort) throws SocketException{
	socket= new DatagramSocket(localPort);
}
/**
 * Met le serveur Daytime en service.En permanance, ce dernier attend 
 * la réception d'un datagramme,y place l'heure courante et le renvoie à l'émetteur
 */
public void run(){
//Datagramme utilisé pour la réception et l'émission
	DatagramPacket packet;
	//Une zone de stockage même vide doit être allouée pour pouvoir créer le datagramme
	packet=new DatagramPacket(new byte[0],0);
	//Boucle d'attente de réception de datagramme et d'envoie de réponse
	logger.info("Serveur démarré sur:"+socket.getLocalSocketAddress());
	while(true){
		try{
			socket.receive(packet);
		}
		catch(IOException e){
			logger.log(Level.WARNING,"Problème de réception",e);
			continue;
		}
		/**
		 * Lorsque le datagramme est reçu, on convertit la date courante en 
		 * un tableau d'octet que l'on référe comme zone de stockage
		 */
	Date date=new Date();
	byte[] buffer=date.toString().getBytes();
	//Mise à jour des indices de la zone de stockage dans le datagramme
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
/*Méthode de test qui crée un serveur avec le port éventuellement
 * spécifié sur la ligne de commande et lance son exécution dans le processus léger
 * courant en appelant run()*/
public static void main (String arg[]){
	/*En l'absence d'argument sur la ligne de commande, le port local par défaut
	 * du serveur est affecté au port réservé à Daytime 
	 */
//int portDay;
//if(arg.length==0)
//	portDay=port;
//else
//	port=Integer.parseInt(arg[0]);
//Création et démarrage du serveur UDP Daytime attaché à ce port
try{
	ServerUdp serveur=new ServerUdp(port);
	serveur.run();
}
catch(SocketException soc){
	System.err.println("Création de serveur impossible");	
soc.printStackTrace(System.err);
}
}
}
