package fr.univnantes.cards;

public class NumberCard extends ACard {
	private static final long serialVersionUID = 1920263878730653339L;
	
	public final int value;
	
	public NumberCard(Color color, int value) {
		super(color);
		this.value = value;
	}
	
	@Override
	public boolean canBePlayedOn(ACard otherCard) {
		if(otherCard == null)
			return false;
		
		boolean playable = super.canBePlayedOn(otherCard);
		
		if(otherCard instanceof NumberCard)
			playable |= value == ((NumberCard) otherCard).value;
		
		return playable;
	}
	
	@Override
	public String toString() {
		String text = "";
		switch(color) {
			case Red:
				text += ANSIColor.RED.toString();
				break;
			case Blue:
				text += ANSIColor.BLUE.toString();
				break;
			case Green:
				text += ANSIColor.GREEN.toString();
				break;
			case Yellow:
				text += ANSIColor.YELLOW.toString();
				break;
			case Wild:
				text += ANSIColor.WHITE.toString();
		}

		return text + "[ " + value + " ]" + ANSIColor.RESET;
	}
}