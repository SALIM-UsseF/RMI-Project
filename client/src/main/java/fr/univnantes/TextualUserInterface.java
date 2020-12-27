package fr.univnantes;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import fr.univnantes.cards.ACard;
import fr.univnantes.cards.ANSIColor;
import fr.univnantes.cards.Color;
import fr.univnantes.cards.Effect;
import fr.univnantes.cards.EffectCard;

public class TextualUserInterface implements IUserInterface {
	private static Predicate<? super ACard> isSkip() { return card -> card instanceof EffectCard && ((EffectCard)card).effect == Effect.Skip; }
	private static Predicate<? super ACard> isPlusTwo() { return card -> card instanceof EffectCard && ((EffectCard)card).effect == Effect.PlusTwo; }

	private static final Scanner scanner = new Scanner(System.in);
	private ILocalClient client;
	private String name;
	
	/**
	 * Begin Textual User
	 */
		TextualUserInterface() {
			
			System.out.println("*****************************");
			System.out.print("[*] Entrez votre nom : ");
			name = scanner.next();
			System.out.println("*****************************");
			
			try {
				client = new Client(name, this);
			} catch(RemoteException e) {
				e.printStackTrace();
			}
	
			System.out.println("*****************************");
			System.out.printf("[*] Bienvenue %s!%n", name);
			System.out.println("*****************************");
			
			boolean ready = false;
			do {
				System.out.println("**********************************[READY]*************************************");
				System.out.printf("[*] Hey %s!, tapez 'ready' pour rejoindre le game ;) %n", name);
				System.out.println("***************************************************************************");
				String input = scanner.nextLine();
				if("ready".equalsIgnoreCase(input)) {
					ready = true;
				} else {
					System.out.println("Oops!! essayer encore avec le mot 'ready' ;) ");
				}
			} while(!ready);
	
			client.setReady(ready);
		}
	/**
	 * End Textual User
	 */

	@Override
	public void startGame(List<String> players, List<ACard> initialCards, ACard pileCard) {
		System.out.println("**********************************[START GAME]*************************************");
		System.out.printf("[*] Allez-y!! on 'start' avec %d joueurs %n", players.size());
		System.out.println("[*] Liste des joueurs : ");
		System.out.println(players.stream().reduce((acc, pl) -> acc + ", " + pl).get());
		System.out.println("***********************************************************************************");
	}

	@Override
	public void yourTurn() {
		System.out.printf("**********************************[TOUR DE %s]*************************************%n", name);
		System.out.println("[*] Vos cartes :");
		System.out.println(ACard.asText(client.getCards(), false));
		System.out.println("[*] Top carte : " + client.getTopCard());
		
		List<ACard> pCards = playableCards(client.getCards(), client.getTopCard());
		if(pCards.size() == 0) {
			System.out.println(ANSIColor.CYAN.toString() + "[*] Vous n'avez aucune carte jouable" + ANSIColor.RESET.toString());
			return;
		}
		
		ACard cardToPlay = chooseACard(pCards);

		if(cardToPlay instanceof EffectCard) {
			switch(((EffectCard)cardToPlay).effect) {
				case PlusFour:
					System.out.println("[+4] Vous jouez un +4, le prochain joueur prends 4 cartes et cette carte devient " + cardToPlay.color.name().toLowerCase());
					break;
				case PlusTwo:
					System.out.println("[+2] Vous jouez un +2, le prochain joueur prends 2 cartes");
					break;
				case Reverse:
					System.out.println("[changement] Vous jouez un changement de sens, le prochain joueur devient le precedent");
					break;
				case Skip:
					System.out.println("[skip] Vous jouez une interdiction, le prochain joueur ne joue pas");
					break;
				case Wild:
					System.out.println("[choix] Vous jouez un choix de couleur, cette carte devient " + cardToPlay.color.name().toLowerCase());
					break;
			}
		}

		client.playCard(cardToPlay);
	}

	@Override
	public void draw(List<ACard> cards, boolean forced) {
		if(forced)
			System.out.print("[*] Vous ne pouvez jouer aucune carte, prenez une carte ");
		else
			System.out.print("[*] Vous prenez " + cards.size() + " carte" + (cards.size() == 1 ? "" : "s") + " : ");
		System.out.println(ACard.asText(cards, false));
	}

	@Override
	public void aboutToDrawFourCards() {
		int answer = 0;
		do {
			System.out.println("[*] Vous allez prenez 4 cartes, voulez-vous le contestez ? (o/n)");
			String input = scanner.nextLine();
			if("o".equalsIgnoreCase(input))
				answer = 1;
			else if("n".equalsIgnoreCase(input))
				answer = -1;
			else {
				System.out.println("Oops!! Reponse incorrecte");
				answer = 0;
			}
		} while(answer == 0);

		if(answer == 1) {
			System.out.println("[*] Vous contestez");
			client.contest();
		} else {
			System.out.println("[*] Vous ne contestez pas");
			client.doNotContest();
		}
	}

	@Override
	public void winContest(boolean hasContested) {
		if(hasContested)
			System.out.println("[*] Vous avez gagnez le conteste, le joueur precedent prendre 6 cartes");
		else
			System.out.println("[*] Le joueur suivant a tente de contester votre [+4], il perd et prends 4 cartes");
	}

	@Override
	public void loseContest(List<ACard> cards, boolean hasContested) {
		System.out.println("Vous perdez le conteste, vous piochez " + cards.size() + " cartes : " + ACard.asText(cards, false));
	}

	@Override
	public void getContested() {
		System.out.println("Un joueur conteste votre [+4]");
	}

	@Override
	public void getSkipped() {
		List<ACard> playableCards = client.getCards().stream().filter(isSkip()).collect(Collectors.toList());

		if(playableCards.size() == 0) {
			System.out.println("[*] Votre tour est passe");
		} else {
			System.out.println("[*] Jouer un [skip] pour contrer le precedent");
			ACard cardToPlay = chooseACard(playableCards);
			client.counterSkip(cardToPlay);
		}
	}

	@Override
	public void getPlusTwoed(int nbCardsStacked) {
		List<ACard> playableCards = client.getCards().stream().filter(isPlusTwo()).collect(Collectors.toList());

		if(playableCards.size() != 0) {
			System.out.println("[*] Jouer un [+2] pour faire prendre 2 cartes de plus au joueur suivant");
			ACard cardToPlay = chooseACard(playableCards);
			client.counterPlusTwo(cardToPlay);
		}
	}

	@Override
	public void cardPlayedBySomeoneElse(String otherPlayer, ACard card) {
		System.out.println(otherPlayer + " joue : " + card);
	}

	@Override
	public void endGame(String winner) {
		if(name.equals(winner)) {
			System.out.println("******************************************************************");
			System.out.println("[WIN] Wow, félicitations!! c'est toi le vainqueur ! Bravo !!");
			System.out.println("******************************************************************");
		}
		else
			System.out.println(winner + " a gagne. Bon chance dans les prochains tours ;) !!");

		System.exit(0);
	}

	private ACard chooseACard(List<ACard> cards) {
		System.out.println("[*] Cartes selectionnables : " + ACard.asText(cards, true));

		ACard choosenCard;
		do {
			System.out.print("[*] Entrez le numero de carte de votre choix : ");
			String input = scanner.nextLine();

			int cardNumber;
			try {
				cardNumber = Integer.parseInt(input);
				if(cardNumber < 1 || cardNumber > cards.size())
					throw new NumberFormatException();
				
				choosenCard = cards.get(cardNumber - 1);
			} catch(NumberFormatException e) {
				System.out.println("oops!! Nombre incorrect");
				choosenCard = null;
			}
		} while(choosenCard == null);

		if(choosenCard instanceof EffectCard && (((EffectCard)choosenCard).effect == Effect.Wild || ((EffectCard)choosenCard).effect == Effect.PlusFour)) {
			Color color;
			do {
				System.out.print("[*] Entrez la couleur de la carte >> couleurs disponibles: [rouge,vert,bleu, jaune] ");
				String input = scanner.nextLine();

				if("rouge".equalsIgnoreCase(input))
					color = Color.Red;
				else if("bleu".equalsIgnoreCase(input))
					color = Color.Blue;
				else if("vert".equalsIgnoreCase(input))
					color = Color.Green;
				else if("jaune".equalsIgnoreCase(input))
					color = Color.Yellow;
				else
					color = null;
				
				if(color == null)
					System.out.println("Oops!! Couleur incorrecte");
			} while(color == null);

			choosenCard.color = color;
		}

		System.out.println("[*] votre choix est " + choosenCard);

		return choosenCard;
	}

	private static List<ACard> playableCards(List<ACard> cards, ACard topCard) {
		return cards.stream().filter(card -> card.canBePlayedOn(topCard)).collect(Collectors.toList());
	}

	/**
	 * ********************************************* main ***************************************
	 * @param args
	 */
	public static void main(String[] args) {
		new TextualUserInterface();
	}
}