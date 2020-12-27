package fr.univnantes.cards;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class ACard implements Serializable {
	private static final long serialVersionUID = -6320387025355652230L;
	
	private static int nextId = 0;

	public final int id = nextId++;
	public Color color;

	public ACard(Color color) {
		this.color = color;
	}

	public boolean canBePlayedOn(ACard otherCard) {
		return color == Color.Wild || otherCard.color == Color.Wild || color == otherCard.color;
	}

	public static String asText(List<ACard> cards) {
		Optional<String> content = cards.stream().map(card -> card.toString()).reduce((acc, card) -> acc + " - " + card);
		return "{ " + (content.isPresent() ? content.get() : "") + " }";
	}
}