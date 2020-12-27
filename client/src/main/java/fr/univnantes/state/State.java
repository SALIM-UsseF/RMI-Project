package fr.univnantes.state;

import java.util.List;

import fr.univnantes.cards.ACard;

interface State {
	default void setReady(Game game, boolean ready) throws StateException {
		throw new StateException(this, "setReady", LobbyState.class);
	}
	default void joinLobby(Game game, String name) throws StateException {
		throw new StateException();
	}
	default void leaveLobby(Game game) throws StateException {
		throw new StateException();
	}
	default void startGame(Game game, List<String> clients, List<ACard> initialCards, ACard pileCard) throws StateException {
		throw new StateException(this, "startGame", LobbyState.class);
	}

	default void yourTurn(Game game) throws StateException {
		throw new StateException(this, "yourTurn", WaitingState.class);
	}
	default void playCard(Game game, ACard card) throws StateException { // Wild and wild+4
		throw new StateException(this, "playWildCard", PlayingState.class);
	}
	default void draw(Game game, List<ACard> cards, boolean forced) throws StateException { // Stays in the same state
		throw new StateException(this, "draw", WaitingState.class);
	}

	default void aboutToDrawFourCards(Game game) throws StateException { // You're about to draw 4 cards and you now have the choice to contest
		throw new StateException(this, "aboutToDrawFourCards", WaitingState.class);
	}
	default void contest(Game game) throws StateException { // You think that the +4 was wrongfully played so you contest the move
		throw new StateException(this, "contest", WantToContestState.class);
	}
	default void doNotContest(Game game) throws StateException { // The +4 may have been right so you do nothing
		throw new StateException(this, "doNotContest", WantToContestState.class);
	}
	default void winContest(Game game) throws StateException { // When the mofo did had a card of the right color so he draws 4 cards
		throw new StateException(this, "winContest", WaitingForContestResultState.class, WillGetContestedState.class);
	}
	default void loseContest(Game game, List<ACard> cards) throws StateException { // Cards to draw after loosing the contest (6)
		throw new StateException(this, "loseContest", WaitingForContestResultState.class, WillGetContestedState.class);
	}
	
	default void getContested(Game game) throws StateException { // When you wrongfully put a +4 and the next player contested it so you draw 4 cards
		throw new StateException(this, "getContested", WaitingState.class);
	}

	default void getSkipped(Game game) throws StateException {
		throw new StateException(this, "getSkipped", WaitingState.class);
	}
	default void counterSkip(Game game, ACard card) throws StateException {
		throw new StateException(this, "counterSkip", CounterSkipState.class);
	}
	default void getPlusTwoed(Game game, int cardsStacked) throws StateException {
		throw new StateException(this, "getPlusTwoed", WaitingState.class);
	}
	default void counterPlusTwo(Game game, ACard card) throws StateException {
		throw new StateException(this, "counterPlusTwo", CounterPlusTwoState.class);
	}

	default void cardPlayedBySomeoneElse(Game game, String otherPlayer, ACard card) throws StateException {
		throw new StateException(this, "cardPlayedBySomeoneElse", WaitingState.class);
	}

	default void endGame(Game game, String winner) {
		game.setState(new EndGameState());
	}

	default void replay(Game game) throws StateException {
		throw new StateException();
	}
	default void quit(Game game) throws StateException {
		throw new StateException();
	}
}