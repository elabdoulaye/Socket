package com.elso.socketUDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.logging.Logger;

public class ClientUdp {
	//R�cup�ration du journaliseur
		final static Logger logger=Logger.getLogger("Localhost") ;
		//Port pour le protocole Daytime
		static int portDay=1300;
		//Taille fixe de la zone de stockge pour la r�ception
		static int buffer_size=100;
		/**
		 * M�thode test qui envoie un datagramme vide � un serveur daytime
		 * re�oit le datagramme r�ponse et affiche l'heure re�ue
		 */
		public static void main(String[] args) throws Exception{
			//V�rification de la pr�sence de param�tre sur la ligne de commande
//	if(args.length != 1 && args.length!=2){
//		System.err.println("Usage: <machine> <port>");
//		System.exit(1);
//	}
	/**
	 * En l'absence du second argument sur la ligne de commande, le port du serveur est affect�
	 *r�serv� � daytime 
	 */
	int port=portDay;
	//if(args.length==2){
		//port=Integer.parseInt(args[1]);
		//R�cup�ration de l'adresse de socket du service Daytime � contacter
		//InetAddress adresse=InetAddress.getByName(args[0]);
	InetAddress adresse=InetAddress.getByName("127.0.0.1");
		SocketAddress socket_adr= new InetSocketAddress(adresse, port);
		//Allocation d'une zone de stockage pour la r�ception
		byte[] buffer=new byte[buffer_size];
		//Construction du datagramme � �mettre (aucun octet � envoyer)
		DatagramPacket packet=new DatagramPacket(buffer,0,0,adresse,port);
		//Construction de la socket et �mission du datagramme
		DatagramSocket socket=new DatagramSocket();
		logger.fine("Emission de:"+packet.getLength()+"octet vers"+packet.getSocketAddress());
		socket.send(packet);
		//Modification de l'objet datagramme pour la r�ception et l'attente
		packet.setLength(buffer_size);
		socket.receive(packet);
		//R�cup�ration et affichage du contenu du datagramme re�u
		logger.fine("R�ception de "+packet.getLength()+"octet "+packet.getSocketAddress());
		String date=new String(packet.getData(),packet.getOffset(),packet.getLength());
		System.out.println(date);
		//Fermture de la socket client
		socket.close();
	}
	}
	//}