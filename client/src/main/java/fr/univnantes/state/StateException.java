package fr.univnantes.state;

import java.util.stream.Stream;

public class StateException extends Exception {
	private static final long serialVersionUID = 5963988078799833080L;

	StateException() {
		super();
	}

	@SafeVarargs
	StateException(State currentState, String methodName, Class<? extends State>... expectedStates) {
		super(methodName + " must be called from " + Stream.of(expectedStates).map(state -> state.getName()).reduce((acc, stateName) -> acc + stateName).get() + ", not from " + currentState.getClass().getName());
	}
}