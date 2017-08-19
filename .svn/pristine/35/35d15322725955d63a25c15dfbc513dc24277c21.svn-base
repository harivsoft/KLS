package com.vsoftcorp.kls.statemachine;

import com.vsoftcorp.kls.valuetypes.LoanApplicationAction;
import com.vsoftcorp.kls.valuetypes.LoanApplicationState;
import com.vsoftcorp.statemachine.impl.StateMachine;

/**
 * @author a1098
 * @Description This Class contains the different Transitions of Loan
 *              Application Application and the Transitions/Event possible
 *              available for the State change Initial state of the application
 *              NEW (Top State)
 */
public class LoanApplicationStateMachine extends
		StateMachine<LoanApplicationState, LoanApplicationAction> {

	public LoanApplicationStateMachine(LoanApplicationState theTopState) {
		super(theTopState);
		addTransition(theTopState, LoanApplicationAction.ENTER,
				LoanApplicationState.ENTERED);
		// at Entry Level
		addTransition(LoanApplicationState.ENTERED,
				LoanApplicationAction.RECOMMEND,
				LoanApplicationState.RECOMMENDED);

		// at Next level
		addTransition(LoanApplicationState.RECOMMENDED,
				LoanApplicationAction.INSPECT, LoanApplicationState.INSPECTED);

		// at Sanction Level
		addTransition(LoanApplicationState.INSPECTED,
				LoanApplicationAction.SANCTION, LoanApplicationState.SANCTIONED);

	}

	private static LoanApplicationStateMachine INSTANCE = new LoanApplicationStateMachine(
			LoanApplicationState.NEW);

	public static LoanApplicationStateMachine getInstance() {
		return INSTANCE;
	}

}
