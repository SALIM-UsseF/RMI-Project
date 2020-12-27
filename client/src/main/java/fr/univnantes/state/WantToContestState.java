package fr.univnantes.state;

import java.rmi.RemoteException;

class WantToContestState implements State {
	@Override
	public void contest(Game game) {
		game.setState(new WaitingForContestResultState(true));
		try {
			game.server.contest(game.client.name);
		} catch(RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doNotContest(Game game) {
		game.setState(new WaitingForContestResultState(false));
		try {
			game.server.doNotContest(game.client.name);
		} catch(RemoteException e) {
			e.printStackTrace();
		}
	}
}