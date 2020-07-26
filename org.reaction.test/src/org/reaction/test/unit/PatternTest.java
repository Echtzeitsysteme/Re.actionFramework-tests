package org.reaction.test.unit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.reaction.test.api.TestAPI;


import ReactionModel.ReactionContainer;

public class PatternTest {

	TestInstanceGenerator testGen;
	ReactionContainer container = null;
	
	@Before
	public void setup() {
		testGen = new TestInstanceGenerator();
	}
	
	@Test
	public void simpleBindingTest() {
		String testCaseName = "simpleBinding";
		container = testGen.createTestInstance("simpleBinding");
		TestcasesApiProvider validator = new TestcasesApiProvider(container, testCaseName);
		TestAPI api = validator.initAPI();
		
		assertTrue(api.simpleBinding().countMatches() == 400);
		while(api.simpleBinding().hasMatches()) {
			api.simpleBinding().apply();
		}
		assertTrue(api.simpleBinding().countMatches() == 0);
		
		assertTrue(api.simpleBindingBwd().countMatches() == 20);
		while(api.simpleBindingBwd().hasMatches()) {
			api.simpleBindingBwd().apply();
		}
		assertTrue(api.simpleBindingBwd().countMatches() == 0);
	}
	
	@Test
	public void stateChangeTest() {
		String testCaseName = "stateChange";
		container = testGen.createTestInstance(testCaseName);
		TestcasesApiProvider validator = new TestcasesApiProvider(container, testCaseName);
		TestAPI api = validator.initAPI();
		
		assertTrue(api.stateChange().countMatches() == 20);
		assertTrue(api.stateChangeBwd().countMatches() == 0);
		while(api.stateChange().hasMatches()) {
			api.stateChange().apply();
		}
		assertTrue(api.stateChange().countMatches() == 0);
		assertTrue(api.stateChangeBwd().countMatches() == 20);
		while(api.stateChangeBwd().hasMatches()) {
			api.stateChangeBwd().apply();
		}
		assertTrue(api.stateChange().countMatches() == 20);
		assertTrue(api.stateChangeBwd().countMatches() == 0);
	}

	@Test
	public void stateChangeAndBindTest() {
		String testCaseName = "stateChangeAndBind";
		container = testGen.createTestInstance(testCaseName);
		TestcasesApiProvider validator = new TestcasesApiProvider(container, testCaseName);
		// Hipe somehow has a problem with initializing the api?
		TestAPI api = validator.initAPI();

		assertTrue(api.stateChangeAndBind().countMatches() == 100);
		assertTrue(api.stateChangeAndBindBwd().countMatches() == 0);

		while(api.stateChangeAndBind().hasMatches()) {
			api.stateChangeAndBind().apply();
		}
		assertTrue(api.stateChangeAndBind().countMatches() == 0);
		assertTrue(api.stateChangeAndBindBwd().countMatches() == 20);
		while(api.stateChangeAndBindBwd().hasMatches()) {
			api.stateChangeAndBindBwd().apply();
		}
		assertTrue(api.stateChangeAndBind().countMatches() == 100);
		assertTrue(api.stateChangeAndBindBwd().countMatches() == 0);
	}
	
	@Test
	public void selfBindTest() {
		String testCaseName = "selfBind";
		container = testGen.createTestInstance(testCaseName);
		TestcasesApiProvider validator = new TestcasesApiProvider(container, testCaseName);
		// Hipe somehow has a problem with initializing the api?
		TestAPI api = validator.initAPI();

		assertTrue(api.selfBind().countMatches() == 20);

		assertTrue(api.selfBindBwd().countMatches() == 0);

		while(api.selfBind().hasMatches()) {
			api.selfBind().apply();
		}
		assertTrue(api.selfBind().countMatches() == 0);
		assertTrue(api.selfBindBwd().countMatches() == 20);
		while(api.selfBindBwd().hasMatches()) {
			api.selfBindBwd().apply();
		}
		assertTrue(api.selfBind().countMatches() == 20);
		assertTrue(api.selfBindBwd().countMatches() == 0);
	}
	
	@Test
	public void cyclicBindingsTest() {
		String testCaseName = "cyclicBindings";
		container = testGen.createTestInstance(testCaseName);
		TestcasesApiProvider validator = new TestcasesApiProvider(container, testCaseName);
		// Hipe somehow has a problem with initializing the api?
		TestAPI api = validator.initAPI();

		assertTrue(api.cyclicBindings().countMatches() == 400);
		assertTrue(api.obs_cyclicBindingsTest().countMatches() == 0);

		while(api.cyclicBindings().hasMatches()) {
			api.cyclicBindings().apply();
		}
		assertTrue(api.cyclicBindings().countMatches() == 0);
		assertTrue(api.obs_cyclicBindingsTest().countMatches() == 20);
	}
	
	@Test
	public void inRuleDegradationTest() {
		String testCaseName = "inRuleDegradation";
		container = testGen.createTestInstance(testCaseName);
		TestcasesApiProvider validator = new TestcasesApiProvider(container, testCaseName);
		// Hipe somehow has a problem with initializing the api?
		TestAPI api = validator.initAPI();

		

		assertTrue(api.inRuleDegradation().countMatches() == 20);
		assertTrue(api.inRuleDegradationBwd().countMatches() == 0);

		while(api.inRuleDegradation().hasMatches()) {
			api.inRuleDegradation().apply();
		}
		

		assertTrue(api.inRuleDegradation().countMatches() == 0);
		assertTrue(api.inRuleDegradationBwd().countMatches() == 20);
		
		while(api.inRuleDegradationBwd().hasMatches()) {
			api.inRuleDegradationBwd().apply();
		}
		assertTrue(api.inRuleDegradation().countMatches() == 20);
		assertTrue(api.inRuleDegradationBwd().countMatches() == 0);
	}
	
	@Test
	public void genericDegradationTest() {
		String testCaseName = "genericDegradation";
		container = testGen.createTestInstance(testCaseName);
		TestcasesApiProvider validator = new TestcasesApiProvider(container, testCaseName);
		// Hipe somehow has a problem with initializing the api?
		TestAPI api = validator.initAPI();

		assertTrue(api.genericDegradation().countMatches() == 20);
		assertTrue(api.obs_aWithFreeB().countMatches() == 0);

		while(api.genericDegradation().hasMatches()) {
			api.genericDegradation().apply();
		}
		assertTrue(api.genericDegradation().countMatches() == 0);
		assertTrue(api.obs_aWithFreeB().countMatches() == 20);
	}
	
	@Test
	public void wildcardSustainTest() {
		String testCaseName = "wildcardSustain";
		container = testGen.createTestInstance(testCaseName);
		TestcasesApiProvider validator = new TestcasesApiProvider(container, testCaseName);
		// Hipe somehow has a problem with initializing the api?
		TestAPI api = validator.initAPI();

		assertTrue(api.wildcardSustain().countMatches() == 100);
		assertTrue(api.wildcardSustainBwd().countMatches() == 0);

		while(api.wildcardSustain().hasMatches()) {
			api.wildcardSustain().apply();
		}
		assertTrue(api.wildcardSustain().countMatches() == 0);
		assertTrue(api.wildcardSustainBwd().countMatches() == 10);
		
		while(api.wildcardSustainBwd().hasMatches()) {
			api.wildcardSustainBwd().apply();
		}
		assertTrue(api.wildcardSustain().countMatches() == 100);
		assertTrue(api.wildcardSustainBwd().countMatches() == 0);
	}
	
	@Test
	public void freeGenericBindingTest() {
		String testCaseName = "freeGenericBinding";
		container = testGen.createTestInstance(testCaseName);
		TestcasesApiProvider validator = new TestcasesApiProvider(container, testCaseName);
		// Hipe somehow has a problem with initializing the api?
		TestAPI api = validator.initAPI();

		assertTrue(api.freeGenericBinding().countMatches() == 20);
		assertTrue(api.obs_aWithFreeB().countMatches() == 0);

		while(api.freeGenericBinding().hasMatches()) {
			api.freeGenericBinding().apply();
		}
		
		assertTrue(api.freeGenericBinding().countMatches() == 0);
		assertTrue(api.obs_aWithFreeB().countMatches() == 20);
	}
//	
//	@Test
//	public void synthesisFreeTest() {
//		String testCaseName = "synthesisFree";
//		container = testGen.createTestInstance(testCaseName);
//		TestcasesApiProvider validator = new TestcasesApiProvider(container, testCaseName);
//		// Hipe somehow has a problem with initializing the api?
//		TestAPI api = validator.initAPI();
//
//		assertTrue(api.synthesisFree().countMatches() == 1);
//		assertTrue(api.obs_synthesisFreeTest().countMatches() == 0);
//
//		api.synthesisFree().apply(20);
//		
//		assertTrue(api.synthesisFree().countMatches() == 1);
//		assertTrue(api.obs_synthesisFreeTest().countMatches() == 20);
//	}
//	
//	@Test
//	public void synthesisCustomStateTest() {
//		String testCaseName = "synthesisCustomState";
//		container = testGen.createTestInstance(testCaseName);
//		TestcasesApiProvider validator = new TestcasesApiProvider(container, testCaseName);
//		// Hipe somehow has a problem with initializing the api?
//		TestAPI api = validator.initAPI();
//
//		assertTrue(api.synthesisCustomState().countMatches() == 1);
//		assertTrue(api.obs_synthesisCustomStateTest().countMatches() == 0);
//
//		api.synthesisCustomState().apply(20);
//		
//		assertTrue(api.synthesisCustomState().countMatches() == 1);
//		assertTrue(api.obs_synthesisFreeTest().countMatches() == 20);
//	}
//
//	@Test
//	public void synthesisBoundTest() {
//		String testCaseName = "synthesisBound";
//		container = testGen.createTestInstance(testCaseName);
//		TestcasesApiProvider validator = new TestcasesApiProvider(container, testCaseName);
//		// Hipe somehow has a problem with initializing the api?
//		TestAPI api = validator.initAPI();
//
//		assertTrue(api.synthesisBound().countMatches() == 1);
//		assertTrue(api.obs_synthesisBoundTest().countMatches() == 0);
//
//		api.synthesisBound().apply(20);
//		
//		assertTrue(api.synthesisBound().countMatches() == 1);
//		assertTrue(api.obs_synthesisBoundTest()().countMatches() == 20);
//	}
}
