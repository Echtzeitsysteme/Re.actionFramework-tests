package org.reaction.gklExample.benchmark;

import org.reaction.gklExample.api.GklExampleSimSGApi;
import org.simsg.core.simulation.Simulation;
import org.simsg.core.simulation.SimulationConfigurator;
import org.simsg.core.utils.Runtimer;

import ReactionModel.ReactionModelPackage;

public class GKLBenchmark {

	public static void main(String[] args) {
		ReactionModelPackage.eINSTANCE.eClass();
		GklExampleSimSGApi api = new GklExampleSimSGApi();

		// Choose Pattern Matching Engine to use
//		api.configureForDemocles();
		api.configureForHiPE();

		api.configureStochasticSimulation();
		SimulationConfigurator config = api.getSimulationConfigurator();

		// Set Model to use
		config.setModel("GKLModel"); // The name of your .react-file + "Model"
		config.addObservableStatistic();

		Simulation sim = config.createSimulation();
		sim.initializeClocked();
		sim.runClocked();
		sim.printCurrentMatches();
		sim.displayResults();
		sim.saveModelGraph();
		sim.finish();

		System.out.println(Runtimer.getInstance().toString());
	}
}
