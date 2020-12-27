package fr.univnantes.state;

import java.util.List;

import fr.univnantes.cards.ACard;

class WaitingState implements State {
	@Override
	public void yourTurn(Game game) {
		game.setState(new PlayingState());
	}

	@Override
	public void draw(Game game, List<ACard> cards, boolean forced) {
		game.cards.addAll(cards);
	}

	@Override
	public void aboutToDrawFourCards(Game game) {
		game.setState(new WantToContestState());
	}

	@Override
	public void getContested(Game game) {
		game.setState(new WillGetContestedState());
	}

	@Override
	public void getSkipped(Game game) {
		game.setState(new CounterSkipState());
	}

	@Override
	public void getPlusTwoed(Game game, int quantity) {
		game.setState(new CounterPlusTwoState(quantity));
	}

	@Override
	public void cardPlayedBySomeoneElse(Game game, String otherClient, ACard card) {
		game.topCard = card;
	}
}