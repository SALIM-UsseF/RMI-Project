package fr.univnantes.cards;

public class EffectCard extends ACard {
	private static final long serialVersionUID = -1973843047004144289L;
	
	public final Effect effect;
	
	public EffectCard(Color color, Effect effect) {
		super(color);
		this.effect = effect;
	}
	
	@Override
	public boolean canBePlayedOn(ACard otherCard) {
		if(otherCard == null)
			return false;
		
		boolean playable = super.canBePlayedOn(otherCard);
		
		if(otherCard instanceof EffectCard)
			playable = playable || effect == ((EffectCard)otherCard).effect;

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

		text += "[ ";

		switch(effect) {
			case Skip:
			case Reverse:
			case Wild:
				text += effect.name();
				break;
			case PlusTwo:
				text += "+2";
				break;
			case PlusFour:
				text += "+4";
				break;
		}

		return text + " ]" + ANSIColor.RESET;
	}
}