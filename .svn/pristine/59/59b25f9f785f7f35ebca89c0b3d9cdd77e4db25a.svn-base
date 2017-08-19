package com.vsoftcorp.kls.statemachine;

import java.util.Iterator;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import com.vsoftcorp.kls.valuetypes.LoanApplicationAction;
import com.vsoftcorp.kls.valuetypes.LoanApplicationState;
import com.vsoftcorp.statemachine.InvalidStateException;
import com.vsoftcorp.statemachine.NoTransitionFoundForTrigger;

/**
 * @author sponnam
 *
 */
public class LoanApplicationStateMachineTest {

	@Test
	public void testGetSourceStates_given_Action() throws InvalidStateException {
		LoanApplicationStateMachine stateMachine = LoanApplicationStateMachine
				.getInstance();
		LoanApplicationState sourceState = null;
		Set<LoanApplicationState> sourceStates = stateMachine
				.getSourceStates(LoanApplicationAction.INSPECT);
		Iterator<LoanApplicationState> iterator = sourceStates.iterator();
		if (iterator.hasNext()) {
			sourceState = iterator.next();
		}
		Assert.assertEquals(LoanApplicationState.RECOMMENDED, sourceState);
	}

	@Test
	public void testTargetState_givenTopState_and_action()
			throws NoTransitionFoundForTrigger, InvalidStateException {
		LoanApplicationState theTopState = LoanApplicationStateMachine
				.getInstance().getTopState();

		LoanApplicationState targetState = LoanApplicationStateMachine
				.getInstance().getTargetState(theTopState,
						LoanApplicationAction.ENTER);
		Assert.assertEquals(LoanApplicationState.ENTERED, targetState);

	}

	@Test
	public void testGetTargetState_given_Action() throws InvalidStateException,
			NoTransitionFoundForTrigger {
		LoanApplicationStateMachine stateMachine = LoanApplicationStateMachine
				.getInstance();
		LoanApplicationState sourceState = null;
		Set<LoanApplicationState> sourceStates = stateMachine
				.getSourceStates(LoanApplicationAction.INSPECT);
		Iterator<LoanApplicationState> iterator = sourceStates.iterator();
		if (iterator.hasNext()) {
			sourceState = iterator.next();
		}
		LoanApplicationState targetState = stateMachine.getTargetState(
				sourceState, LoanApplicationAction.INSPECT);
		Assert.assertEquals(LoanApplicationState.INSPECTED, targetState);
	}
}
