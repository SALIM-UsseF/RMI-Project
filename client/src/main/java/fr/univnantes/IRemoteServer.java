package fr.univnantes;

import java.rmi.Remote;
import java.rmi.RemoteException;

import fr.univnantes.cards.ACard;

public interface IRemoteServer extends Remote {
	public boolean join(IRemoteClient client) throws RemoteException;
	public void setReady(String client, boolean ready) throws RemoteException;

	public void contest(String contestingClient) throws RemoteException;
	public void doNotContest(String client) throws RemoteException;
	public void counterPlusTwo(String client, ACard card, int quantity) throws RemoteException;
	public void counterSkip(String client, ACard card) throws RemoteException;
	public void playCard(String client, ACard card) throws RemoteException;
}