package fr.univnantes.state;

import java.rmi.RemoteException;

import fr.univnantes.cards.ACard;

class CounterSkipState implements State {
	@Override
	public void counterSkip(Game game, ACard card) {
		game.cards.remove(card);
		game.topCard = card;
		game.setState(new WaitingState());
		try {
			game.server.counterSkip(game.client.name, card);
		} catch(RemoteException e) {}
	}
}