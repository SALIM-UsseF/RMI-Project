package fr.univnantes;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import fr.univnantes.cards.ACard;

public interface IRemoteClient extends Remote, Serializable {
	public void startGame(List<String> clients, List<ACard> initialCards, ACard pileCard) throws RemoteException;
	public void yourTurn() throws RemoteException;
	public void draw(List<ACard> cards, boolean forced) throws RemoteException;

	public void aboutToDrawFourCards() throws RemoteException;
	public void getContested() throws RemoteException;
	public void winContest() throws RemoteException;
	public void loseContest(List<ACard> cards) throws RemoteException;

	public void getSkipped() throws RemoteException;
	public void getPlusTwoed(int quantity) throws RemoteException;

	public void cardPlayedBySomeoneElse(String otherClient, ACard card) throws RemoteException;

	public void endGame(String winner) throws RemoteException;

	public String getName() throws RemoteException;
}