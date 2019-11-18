package ProgrammingToolkit.java.lib.utils;

import java.io.File;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPlatformRunnable;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.JavaRuntime;

import changeassistant.multipleexample.util.PropertyLoader;

public class EclipseUtil implements IPlatformRunnable {

	public static IWorkspaceRoot createWorkspace(String workspaceDir) {
//		PropertyLoader.load(new File(Constants.projectDir + "datasetConfig/properties"));
//		PropertyLoader.props.setProperty("Project_Home_Path", workspaceDir);
		IWorkspaceRoot workSpace = ResourcesPlugin.getWorkspace().getRoot();
		return workSpace;
	}

	public static IProject createNewPrject(IWorkspaceRoot root, String projectName, String fileName, String srcStr) {
		/**
		 * code from
		 */
		IProject project = null;
		try {

			// create a project with name
			project = root.getProject(projectName);

			project.create(null);
			project.open(null);

			// set the Java nature
			IProjectDescription description = project.getDescription();
			description.setNatureIds(new String[] { JavaCore.NATURE_ID });

			// create the project
			project.setDescription(description, null);
			IJavaProject javaProject = JavaCore.create(project);

			// set the build path
			IClasspathEntry[] buildPath = { JavaCore.newSourceEntry(project.getFullPath().append("src")) };

			javaProject.setRawClasspath(buildPath, project.getFullPath().append("bin"), null);

//			IFolder binFolder = project.getFolder("bin");
//			binFolder.create(false, true, null);
//			javaProject.setOutputLocation(binFolder.getFullPath(), null);

			IFolder sourceFolder = project.getFolder("src");
			sourceFolder.create(false, true, null);

			// Add folder to Java element
			IPackageFragmentRoot srcFolder = javaProject.getPackageFragmentRoot(sourceFolder);

			// create package fragment
			IPackageFragment fragment = srcFolder.createPackageFragment("main", true, null);

			writeSRCtoPrject(project, javaProject, fileName, srcStr);

		} catch (CoreException e) {
			System.out.println("Create Project "+projectName+" failed!");
			e.printStackTrace();
		}
		System.out.println("Create Project "+projectName+" successfully!");
		return project;
	}

	public static void writeSRCtoPrject(IProject project, IJavaProject javaProject, String fileName, String srcStr) {
		/**
		 * code from
		 */
		try {
			ICompilationUnit cu = javaProject.getPackageFragmentRoot(project.getFolder("src"))
					.getPackageFragment("main").createCompilationUnit(fileName, srcStr, false, null);

		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

//	@Override
//	public Object run(Object args) throws Exception {
//		String projectName = "testP";
//		IWorkspaceRoot workSpace = createWorkspace(Constants.workspaceDir);
//		// init code string and create compilation unit
//		String str = "package com.programcreek;" + "\n" + "public class Test  {" + "\n" + " private String name;" + "\n"
//				+ "}";
//		createNewPrject(workSpace, projectName, "test.java", str);
//		System.out.println("Finished!");
//		return null;
//	}

}
