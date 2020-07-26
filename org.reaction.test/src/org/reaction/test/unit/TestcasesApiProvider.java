package org.reaction.test.unit;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.reaction.test.api.TestDemoclesApp;
import org.reaction.test.api.TestHiPEApp;

import ReactionModel.ReactionContainer;
import ReactionModel.ReactionModelPackage;

public class TestcasesApiProvider extends TestDemoclesApp{

  public TestcasesApiProvider(ReactionContainer container, String testCaseName) {
	  ReactionModelPackage.eINSTANCE.eClass();
	  
    // Determine the path to the workspace root for loading models
    //File root = new File();
    workspacePath = "D:/source/";	//TODO: Use relative path

    File root = new File(TestcasesApiProvider.class.getResource(".").getFile());
    workspacePath = root.getParentFile().getParentFile().getParentFile().getParentFile().getParentFile()
                        .getParent() + File.separatorChar;
    
    // Add the container as a model to be monitored by the pattern matcher            
    createModel(URI.createURI(testCaseName + ".xmi"));
    resourceSet.getResources().get(0).getContents().add(container);
    System.out.println("");
  }
}
