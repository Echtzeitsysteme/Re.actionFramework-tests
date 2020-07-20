package org.reaction.main;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EPackage;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet;
import org.reaction.dsl.reactionLanguage.ReactionLanguagePackage;
import org.reaction.dsl.reactionLanguage.ReactionModel;
import org.reaction.export.BNGLFactory;
import org.reaction.ibex.patternCreation.GTCreator;
import org.reaction.ibex.patternCreation.IBeXCreator;
import org.reaction.ibex.patternCreation.SimDefCreator;
import org.reaction.reactionmodel.generator.ContainerEMF;
import org.reaction.reactionmodel.generator.ContainerGenerator;
import org.reaction.intermTrafo.transformation.IntermTransformation;
import org.reaction.intermTrafo.util.EMFResourceHelper;

import IntermediateModel.IntermediateModelContainer;
import ReactionModel.ReactionModelPackage;


public class Main {

	public static void main(String[] args) {

		ReactionLanguagePackage.eINSTANCE.eClass();
		ReactionModelPackage.eINSTANCE.eClass();

		// Load Model ---- Adjust paths in the following two lines to load and place files correctly
		final String dslModelLocation =   "..\\example\\GKL.xmi";
		final String trgProjectLocation = "..\\org.reaction.gklExample";
		
		final String userDir = System.getProperty("user.dir");
		final String tempModels = userDir + "/models/";

		IntermediateModelContainer intermModel;

		// Clear directories
		System.out.println("Clearing directories...");
		
//		//Clear tempModel Folder
//		File tempModelFolder = new File(tempModels);
//		deleteFolder(tempModelFolder);
//		
//		//Clear trgProjectLocations
//		File trgProjectModelFolder = new File(trgProjectLocation+"/model/");
//		File trgProjectInstanceFolderDefs = new File(trgProjectLocation+"/instances/simulation_definitions/");
//		File trgProjectInstanceFolderInis = new File(trgProjectLocation+"/instances/simulation_instances/");
//		deleteFolder(trgProjectModelFolder);
//		deleteFolder(trgProjectInstanceFolderDefs);
//		deleteFolder(trgProjectInstanceFolderInis);

		ReactionModel dslModel = null;
		// Reaction model to intermediate model
		try {
			System.out.println("Initiating Reaction to Intermediate Transformation...");
			dslModel = EMFResourceHelper.loadReactionModel(dslModelLocation);
			IntermTransformation dslToInterm = new IntermTransformation(dslModel);
			intermModel = dslToInterm.generateIntermediateModel();

			// Get dsl Model name --> TODO: let it be entered within language
			String dslModelFilePatternString = "(.+\\\\)(?<fileName>.+)(\\.xmi)";
			Pattern dslModelFilePattern = Pattern.compile(dslModelFilePatternString);
			Matcher m = dslModelFilePattern.matcher(dslModelLocation);
			String intermModelSaveLocation;

			if (m.find()) {
				String dslModelName = m.group("fileName");
				intermModel.setName(dslModelName);
				intermModelSaveLocation = tempModels + dslModelName + "Intermediate.xmi";
			} else {
				System.err.println("Could not find dsl model name. Exiting");
				return;
			}

			EMFResourceHelper.saveResource(intermModel, intermModelSaveLocation);
		} catch (Exception e) {
			System.err.println("\nTransforming to Intermediate Model failed with:");
			e.printStackTrace();
			return;
		}

		// Intermediate to SimSG model
		System.out.println("Initiating Intermediate to SimSG Transformation...");

		ContainerGenerator containerGen = new ContainerEMF(intermModel);

		String metamodelPath = trgProjectLocation + "/model/" + intermModel.getName() + "Model.ecore";
		String modelPath = trgProjectLocation + "/instances/simulation_instances/" + intermModel.getName()
				+ "Model.xmi";

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
		String modelName = null;
		try {
			IBeXCreator ibexCreator = new IBeXCreator(intermModel, metamodelPackage);
			IBeXPatternSet ibexPatternSet = ibexCreator.getIBeXPatternSet();
			String ibexSaveLocation = trgProjectLocation + "/model/ibex-patterns.xmi";
			ibexCreator.savePatternSet(ibexSaveLocation);

			GTCreator gtCreator = new GTCreator(ibexPatternSet);
			String gtSaveLocation = trgProjectLocation + "/model/gtRules.xmi";
			gtCreator.saveRuleSet(gtSaveLocation);

			SimDefCreator simDefCreator = new SimDefCreator(intermModel, trgProjectLocation);
			modelName = simDefCreator.getDefinition().getName();
			String simDefSaveLocation = trgProjectLocation + "/instances/simulation_definitions/" + modelName + ".xmi";
			simDefCreator.saveDefinition(simDefSaveLocation);
		} catch (Exception e) {
			System.err.println("Creating IBeX items failed with:");
			e.printStackTrace();
			return;
		}
		System.out.println("IBeX items created successfully.");

		System.out.println("Transformation complete.");

		System.out.println("Starting BNGL export.");
		String exportLocation = userDir + "/export/";
		BNGLFactory bnglFactory = new BNGLFactory(dslModel, intermModel, containerGen.getUsedAgentsInModel(),
				containerGen.getUsedStates());
		bnglFactory.generateBNGL();
		bnglFactory.saveFile(exportLocation + modelName + ".bngl");
		System.out.println("Finished BNGL export. Saved to " + exportLocation + "test.bngl");
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
