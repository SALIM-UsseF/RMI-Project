package fr.univnantes;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import fr.univnantes.cards.ACard;
import fr.univnantes.state.Game;
import fr.univnantes.state.StateException;

public class Client extends UnicastRemoteObject implements ILocalClient, IRemoteClient {
	private static final long serialVersionUID = 2501464824644144715L;

	public final String name;
	public final IUserInterface iUserInterface;
	final Game game;
	final IRemoteServer server;
	private List<String> players;

	public Client(String name, IUserInterface ui) throws RemoteException {
		super();

		this.name = name;
		this.iUserInterface = ui;

		IRemoteServer s = null;

		try {
			s = (IRemoteServer)Naming.lookup("rmi://localhost:1099/gameCarte");
			s.join((IRemoteClient)this);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		server = s;
		this.game = new Game(this, server);
	}

	@Override
	public void startGame(List<String> players, List<ACard> initialCards, ACard pileCard) throws RemoteException {
		this.players = players;
		try {
			game.startGame(players, initialCards, pileCard);
		} catch(StateException e) {
			e.printStackTrace();
		}
		iUserInterface.startGame(players, initialCards, pileCard);
	}

	@Override
	public void yourTurn() throws RemoteException {
		try {
			game.yourTurn();
		} catch(StateException e) {
			e.printStackTrace();
		}
		iUserInterface.yourTurn();
	}

	@Override
	public void draw(List<ACard> cards, boolean forced) throws RemoteException {
		try {
			game.draw(cards, forced);
		} catch(StateException e) {
			e.printStackTrace();
		}
		iUserInterface.draw(cards, forced);
	}

	@Override
	public void aboutToDrawFourCards() throws RemoteException {
		try {
			game.aboutToDrawFourCards();
		} catch(StateException e) {
			e.printStackTrace();
		}
		iUserInterface.aboutToDrawFourCards();
	}

	@Override
	public void winContest() throws RemoteException {
		try {
			game.winContest();
		} catch(StateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loseContest(List<ACard> cards) throws RemoteException {
		try {
			game.loseContest(cards);
		} catch(StateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getContested() throws RemoteException {
		try {
			game.getContested();
		} catch(StateException e) {
			e.printStackTrace();
		}
		iUserInterface.getContested();
	}

	@Override
	public void getSkipped() throws RemoteException {
		try {
			game.getSkipped();
		} catch(StateException e) {
			e.printStackTrace();
		}
		iUserInterface.getSkipped();
	}

	@Override
	public void getPlusTwoed(int quantity) throws RemoteException {
		try {
			game.getPlusTwoed(quantity);
		} catch(StateException e) {
			e.printStackTrace();
		}
		iUserInterface.getPlusTwoed(quantity);
	}

	@Override
	public void cardPlayedBySomeoneElse(String otherClient, ACard card) throws RemoteException {
		try {
			game.cardPlayedBySomeoneElse(otherClient, card);
		} catch(StateException e) {
			e.printStackTrace();
		}
		iUserInterface.cardPlayedBySomeoneElse(otherClient, card);
	}

	@Override
	public void endGame(String winner) throws RemoteException {
		game.endGame(winner);
		iUserInterface.endGame(winner);
	}

	@Override
	public List<String> getPlayers() {
		return players;
	}

	@Override
	public List<ACard> getCards() {
		return game.cards;
	}

	@Override
	public ACard getTopCard() {
		return game.getTopCard();
	}

	// ----------

	@Override
	public void setReady(boolean ready) {
		try {
			game.setReady(ready);
		} catch(StateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contest() {
		try {
			game.contest();
		} catch(StateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doNotContest() {
		try {
			game.doNotContest();
		} catch(StateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void counterPlusTwo(ACard card) {
		try {
			game.counterPlusTwo(card);
		} catch(StateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void counterSkip(ACard card) {
		try {
			game.counterSkip(card);
		} catch(StateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void playCard(ACard card) {
		try {
			game.playCard(card);
		} catch(StateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getName() throws RemoteException {
		return name;
	}
}