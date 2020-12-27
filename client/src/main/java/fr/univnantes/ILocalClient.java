package fr.univnantes;

import java.util.List;

import fr.univnantes.cards.ACard;

public interface ILocalClient {
	public void setReady(boolean ready);
	public void contest();
	public void doNotContest();
	public void counterPlusTwo(ACard card);
	public void counterSkip(ACard card);
	public void playCard(ACard card);

	public List<String> getPlayers();
	public List<ACard> getCards();
	public ACard getTopCard();
}