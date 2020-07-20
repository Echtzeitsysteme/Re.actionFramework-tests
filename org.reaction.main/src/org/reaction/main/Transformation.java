package org.reaction.main;

import java.io.File;

import org.eclipse.emf.ecore.EPackage;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet;
import org.reaction.dsl.reactionLanguage.ReactionModel;
import org.reaction.export.BNGLFactory;
import org.reaction.ibex.patternCreation.GTCreator;
import org.reaction.ibex.patternCreation.IBeXCreator;
import org.reaction.ibex.patternCreation.SimDefCreator;
import org.reaction.intermTrafo.transformation.IntermTransformation;
import org.reaction.intermTrafo.util.EMFResourceHelper;
import org.reaction.reactionmodel.generator.ContainerEMF;
import org.reaction.reactionmodel.generator.ContainerGenerator;

import IntermediateModel.IntermediateModelContainer;

public class Transformation {

	String dslModelLocation;
	String modelName;
	String trgProjectLocation;
	BNGLFactory bnglFactory;

	public Transformation(String dslModelLocation, String modelName, String trgProjectLocation) {
		this.dslModelLocation = dslModelLocation;
		this.modelName = modelName;
		this.trgProjectLocation = trgProjectLocation;
	}

	public void start() {
		IntermediateModelContainer intermModel;

//		// Clear directories
//		System.out.println("Clearing directories...");
//		
//		//Clear tempModel Folder
//		File tempModelFolder = new File(tempModels);
//		deleteFolder(tempModelFolder);
//		
//		//Clear trgProjectLocations
//		File trgProjectModelFolder = new File(trgProjectLocation+"/model/");
//		File trgProjectInstanceFolder = new File(trgProjectLocation+"/instances/");
//		deleteFolder(trgProjectModelFolder);
//		deleteFolder(trgProjectInstanceFolder);
		final String userDir = System.getProperty("user.dir");
		final String tempModels = userDir + "\\intermModels\\";
		final String dslModelFile = dslModelLocation + modelName + ".xmi";
		ReactionModel dslModel = null;
		// Reaction model to intermediate model
		try {
			System.out.println("Initiating Reaction to Intermediate Transformation...");
			dslModel = EMFResourceHelper.loadReactionModel(dslModelFile);
			IntermTransformation dslToInterm = new IntermTransformation(dslModel);
			intermModel = dslToInterm.generateIntermediateModel();
			intermModel.setName(modelName);

//			String intermModelSaveLocation = tempModels + modelName + "Intermediate.xmi";
//			EMFResourceHelper.saveResource(intermModel, intermModelSaveLocation);
		} catch (Exception e) {
			System.err.println("\nTransforming to Intermediate Model failed with:");
			e.printStackTrace();
			return;
		}

		// Intermediate to SimSG model
		System.out.println("Initiating Intermediate to SimSG Transformation...");

		ContainerGenerator containerGen = new ContainerEMF(intermModel);

		String metamodelPath = trgProjectLocation + "/model/" + modelName + "Model.ecore";
		String modelPath = trgProjectLocation + "/instances/simulation_instances/" + modelName + "Model.xmi";

		try {
			containerGen.doGenerate(modelPath, metamodelPath);
		} catch (Exception e) {
			System.err.println("Creating Ecore-Model for SimSG failed with:");
			e.printStackTrace();
			return;
		}

		System.out.println("Ecore Creation successful.");

		System.out.print("Loading Ecore Model... ");
		EPackage metamodelPackage;
		try {
			metamodelPackage = EMFResourceHelper.loadEPackage(metamodelPath);
		} catch (Exception e) {
			System.err.println("\nLoading Ecore-Model of SimSG failed with:");
			e.printStackTrace();
			return;
		}
		System.out.print(" Successful.\n");

		// Creating IBeX items
		System.out.println("Initiating generation of IBeX items...");

		try {
			IBeXCreator ibexCreator = new IBeXCreator(intermModel, metamodelPackage);
			IBeXPatternSet ibexPatternSet = ibexCreator.getIBeXPatternSet();
			String ibexSaveLocation = trgProjectLocation + "/model/ibex-patterns.xmi";
			ibexCreator.savePatternSet(ibexSaveLocation);

			GTCreator gtCreator = new GTCreator(ibexPatternSet);
//		GTRuleSet gtRuleSet = gtCreator.getGTRuleSet();
			String gtSaveLocation = trgProjectLocation + "/model/gtRules.xmi";
			gtCreator.saveRuleSet(gtSaveLocation);

			SimDefCreator simDefCreator = new SimDefCreator(intermModel, trgProjectLocation);
			String simDefSaveLocation = trgProjectLocation + "/instances/simulation_definitions/" + modelName
					+ "Model.xmi";
			simDefCreator.saveDefinition(simDefSaveLocation);
		} catch (Exception e) {
			System.err.println("Creating IBeX items failed with:");
			e.printStackTrace();
			return;
		}
		System.out.println("IBeX items created successfully.");

		System.out.println("Transformation complete.");
		bnglFactory = new BNGLFactory(dslModel, intermModel, containerGen.getUsedAgentsInModel(),
				containerGen.getUsedStates());
		bnglFactory.generateBNGL();
	}

	public void exportAsBNGL(String exportLocation) {
		System.out.println("Starting BNGL export.");

		try {
			bnglFactory.saveFile(exportLocation + modelName + ".bngl");
			System.out.println("Finished BNGL export. Saved to " + exportLocation + modelName + ".bngl");
		} catch (Exception e) {
			System.err.println("Saving .bngl-File failed with " + e.getMessage());
		}
	}

	private static void deleteFolder(File folder) {
		for (File f : folder.listFiles()) {
			if (f.isDirectory()) {
				deleteFolder(f);
			} else {
				f.delete();
			}
		}
	}

}
