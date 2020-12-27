package fr.univnantes;

import java.util.List;

import fr.univnantes.cards.ACard;

public interface IUserInterface {
	public void startGame(List<String> players, List<ACard> initialCards, ACard pileCard);

	public void yourTurn();
	public void draw(List<ACard> cards, boolean forced);

	public void aboutToDrawFourCards();
	public void winContest(boolean hasContested);
	public void loseContest(List<ACard> cards, boolean hasContested);
	public void getContested();

	public void getSkipped();
	public void getPlusTwoed(int nbCards);

	public void cardPlayedBySomeoneElse(String otherPlayer, ACard card);

	public void endGame(String winner);
}