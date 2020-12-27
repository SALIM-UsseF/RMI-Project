package fr.univnantes.state;

import java.util.List;

import fr.univnantes.cards.ACard;

class WaitingForContestResultState implements State {
	private final boolean hasContested;

	WaitingForContestResultState(boolean hasContested) {
		this.hasContested = hasContested;
	}

	@Override
	public void winContest(Game game) throws StateException {
		game.setState(new WaitingState());
		game.client.iUserInterface.winContest(hasContested);
	}

	@Override
	public void loseContest(Game game, List<ACard> cards) throws StateException {
		game.setState(new WaitingState());
		game.cards.addAll(cards);
		game.client.iUserInterface.loseContest(cards, hasContested);
	}
}