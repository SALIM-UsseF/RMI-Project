package fr.univnantes.state;

import java.rmi.RemoteException;

import fr.univnantes.cards.*;

class CounterPlusTwoState implements State {
	private final int nbCardsStacked;

	CounterPlusTwoState(int cardsStacked) {
		this.nbCardsStacked = cardsStacked;
	}

	@Override
	public void counterPlusTwo(Game game, ACard card) {
		game.cards.remove(card);
		game.topCard = card;
		game.setState(new WaitingState());
		try {
			game.server.counterPlusTwo(game.client.name, card, nbCardsStacked + 1);
		} catch(RemoteException e) {}
	}
}