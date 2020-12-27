package fr.univnantes.state;

import java.rmi.RemoteException;

import fr.univnantes.cards.ACard;
import fr.univnantes.cards.Effect;
import fr.univnantes.cards.EffectCard;

class PlayingState implements State {
	@Override
	public void playCard(Game game, ACard card) {
		game.cards.remove(card);
		game.topCard = card;
		if(card instanceof EffectCard && ((EffectCard)card).effect == Effect.PlusFour)
			game.setState(new WillGetContestedState());
		else
			game.setState(new WaitingState());
		try {
			game.server.playCard(game.client.name, card);
		} catch(RemoteException e) {}
	}
}