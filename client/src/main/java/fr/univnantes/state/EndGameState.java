package fr.univnantes.state;

class EndGameState implements State {
	@Override
	public void replay(Game game) {
		game.setState(new LobbyState());
	}

	@Override
	public void quit(Game game) {
		System.exit(0);
	}
}