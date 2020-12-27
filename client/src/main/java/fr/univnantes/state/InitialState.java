package fr.univnantes.state;

class InitialState implements State {
	@Override
	public void joinLobby(Game game, String name) {
		game.setState(new LobbyState());
	}
}