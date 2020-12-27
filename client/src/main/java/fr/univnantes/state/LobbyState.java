package fr.univnantes.state;

import java.rmi.RemoteException;
import java.util.List;

import fr.univnantes.cards.ACard;

class LobbyState implements State {
	@Override
	public void leaveLobby(Game game) {
		game.setState(new InitialState());
	}

	@Override
	public void setReady(Game game, boolean ready) {
		try {
			game.server.setReady(game.client.name, ready);
		} catch(RemoteException e) {}
	}

	@Override
	public void startGame(Game game, List<String> players, List<ACard> initialCards, ACard pileCard) {
		game.nbPlayers = players.size();
		game.cards.addAll(initialCards);
		game.topCard = pileCard;
		
		game.setState(new WaitingState());
	}
}