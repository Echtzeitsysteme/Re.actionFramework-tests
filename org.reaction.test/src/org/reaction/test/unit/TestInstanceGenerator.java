package org.reaction.test.unit;

import ReactionModel.ReactionContainer;
import ReactionModel.ReactionModelFactory;
import ReactionModel.ReactionModelPackage;
import TestcasesModel.A;
import TestcasesModel.P_s;
import TestcasesModel.T;
import TestcasesModel.TestcasesModelFactory;
import TestcasesModel.U_s;
import TestcasesModel.X;

public class TestInstanceGenerator {

	private TestcasesModelFactory factory;
	
	public TestInstanceGenerator() {
		ReactionModelPackage.eINSTANCE.eClass();
		factory = TestcasesModelFactory.eINSTANCE;
	}
	
	public ReactionContainer createTestInstance(String testcaseName) {
		
		ReactionContainer container = ReactionModelFactory.eINSTANCE.createReactionContainer();
		U_s us = factory.createU_s();
		P_s ps = factory.createP_s();
		container.getStates().add(us);
		container.getStates().add(ps);
		
		switch(testcaseName) {
		case "simpleBinding": {
			for(int i = 0; i < 20; i++) {
				A a = factory.createA();
				T t = factory.createT();
				a.setA_c_u(us);
				container.getAgents().add(a);
				container.getAgents().add(t);
			}
			break;
		}
		case "stateChange": {
			for(int i = 0; i < 20; i++) {
				X x = factory.createX();
				x.setX_c_u(us);
				container.getAgents().add(x);
			}
			break;
		}
		case "stateChangeAndBind":{
			for(int i=0; i < 10; i++) {
				X x1 = factory.createX();
				X x2 = factory.createX();
				x1.setX_c_u(us);
				x2.setX_c_p(ps);
				container.getAgents().add(x1);
				container.getAgents().add(x2);
			}
			break;
		}
		case "selfBind":{
			for (int i=0; i < 20; i++) {
				A a = factory.createA();
				a.setA_c_u(us);
				container.getAgents().add(a);
			}
			break;
		}
		case "cyclicBindings":{
			for (int i=0; i < 20; i++) {
				A a = factory.createA();
				X x = factory.createX();
				a.setA_c_u(us);
				x.setX_c_u(us);
				container.getAgents().add(a);
				container.getAgents().add(x);
			}
			break;
		}
		case "inRuleDegradation":{
			for (int i=0; i < 20; i++) {
				A a = factory.createA();
				X x = factory.createX();
				a.setA_c_u(us);
				x.setX_c_u(us);
				a.setA_b_X_c(x);
				x.setX_c_A_b(a);
				container.getAgents().add(a);
				container.getAgents().add(x);
			}
			break;
		}
		case "genericDegradation":{
			for (int i=0; i < 20; i++) {
				A a = factory.createA();
				T t = factory.createT();
				a.setA_c_u(us);
				a.setA_b_T_k(t);
				t.setT_k_A_b(a);
				container.getAgents().add(a);
				container.getAgents().add(t);
			}
			break;
		}
		case "wildcardSustain":{
			for (int i=0; i < 5; i++) {
				A a = factory.createA();
				T t = factory.createT();
				X x = factory.createX();
				a.setA_c_u(us);
				x.setX_c_u(us);
				a.setA_b_X_b(x);
				x.setX_b_A_b(a);
				container.getAgents().add(a);
				container.getAgents().add(t);
				container.getAgents().add(x);
			}
			for (int i=0; i < 5; i++) {
				A a = factory.createA();
				T t = factory.createT();
				X x = factory.createX();
				a.setA_c_u(us);
				x.setX_c_u(us);
				a.setA_b_X_c(x);
				x.setX_c_A_b(a);
				container.getAgents().add(a);
				container.getAgents().add(t);
				container.getAgents().add(x);
			}
			break;
		}
		case "freeGenericBinding":{
			for (int i=0; i < 20; i++) {
				A a = factory.createA();
				T t = factory.createT();
				a.setA_c_u(us);
				a.setA_b_T_k(t);
				t.setT_k_A_b(a);
				container.getAgents().add(a);
				container.getAgents().add(t);
			}
			break;
		}
		case "synthesisFree":{
			break;
		}
		case "synthesisCustomState":{
			break;
		}
		case "synthesisBound":{
			break;
		}
		case "degradation":{
			for (int i=0; i < 20; i++) {
				A a = factory.createA();
				a.setA_c_u(us);
				container.getAgents().add(a);
			}
			break;
		}
		case "undefinedDegradation":{
			for (int i=0; i < 5; i++) {
				A a = factory.createA();
				a.setA_c_u(us);
				container.getAgents().add(a);
			}
			for (int i=0; i < 5; i++) {
				A a = factory.createA();
				T t = factory.createT();
				a.setA_c_u(us);
				a.setA_b_T_k(t);
				t.setT_k_A_b(a);
				container.getAgents().add(a);
				container.getAgents().add(t);
			}
			for (int i=0; i < 5; i++) {
				A a = factory.createA();
				T t = factory.createT();
				a.setA_c_u(us);
				a.setA_c_T_k(t);
				t.setT_k_A_c(a);
				container.getAgents().add(a);
				container.getAgents().add(t);
			}
			break;
		}

		}
		
		return container;
	}

	
}
