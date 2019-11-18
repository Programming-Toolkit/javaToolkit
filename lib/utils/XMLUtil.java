package javaToolkit.lib.utils;

import java.io.File;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.*;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import smu.bowen.ct4j.datapreparation.Group;
import smu.bowen.ct4j.datapreparation.Constants;

public class XMLUtil {

	public static Boolean writeProjectGroup(Group group, String xmlPath) {

		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			// root element
			Element root = document.createElement("list");
			document.appendChild(root);

			// projectGroup element
			Element projectGroup = document.createElement("projectGroup");
			root.appendChild(projectGroup);

			// set an attribute to staff element
			Attr groupAttr = document.createAttribute("id");
			groupAttr.setValue(String.valueOf(group.groupId));
			projectGroup.setAttributeNode(groupAttr);

			// bowen define the #train
			System.out.println(group.methodList.size());
			if (group.methodList.size() < 10) {
				return null;
			}
			int trainNum;
			if ((int) (group.methodList.size() * Constants.trainRatio) > 40) {
				trainNum = 40;
			} else {
				trainNum = (int) (group.methodList.size() * Constants.trainRatio);
			}

			for (int i = 0; i < trainNum; i++) {

				System.out.println(group.methodList.get(i).oldProjectName);

				Method method = group.methodList.get(i);
				method.id = i + 1;
				// method element
				Element projectMethod = document.createElement("projectMethod");
				projectGroup.appendChild(projectMethod);

				// set an attribute to staff element
				Attr methodAttr = document.createAttribute("id");
				methodAttr.setValue(String.valueOf(method.id));
				projectMethod.setAttributeNode(methodAttr);

				// src element
				Element src = document.createElement("src");
				src.appendChild(document.createTextNode("SOMETHING"));
				projectMethod.appendChild(src);

				// oldProjectName element
				Element oldProjectName = document.createElement("oldProjectName");
				oldProjectName.appendChild(document.createTextNode(method.oldProjectName));
				projectMethod.appendChild(oldProjectName);

				// newProjectName element
				Element newProjectName = document.createElement("newProjectName");
				newProjectName.appendChild(document.createTextNode(method.newProjectName));
				projectMethod.appendChild(newProjectName);

				// oldClassName element
				Element oldClassName = document.createElement("oldClassName");
				oldClassName.appendChild(document.createTextNode(method.oldClassName));
				projectMethod.appendChild(oldClassName);

				// newClassName element
				Element newClassName = document.createElement("newClassName");
				newClassName.appendChild(document.createTextNode(method.newClassName));
				projectMethod.appendChild(newClassName);

				// oldFilePath element
				Element oldFilePath = document.createElement("oldFilePath");
				oldFilePath.appendChild(document.createTextNode(method.oldFilePath));
				projectMethod.appendChild(oldFilePath);

				// newFilePath element
				Element newFilePath = document.createElement("newFilePath");
				newFilePath.appendChild(document.createTextNode(method.newFilePath));
				projectMethod.appendChild(newFilePath);

				// oldMethodName element
				Element oldMethodName = document.createElement("oldMethodName");
				oldMethodName.appendChild(document.createTextNode(method.oldMethodName));
				projectMethod.appendChild(oldMethodName);

				// newMethodName element
				Element newMethodName = document.createElement("newMethodName");
				newMethodName.appendChild(document.createTextNode(method.newMethodName));
				projectMethod.appendChild(newMethodName);
			}

			// candidate element
			int candidateNum = 1;
			Element candidateProjects = document.createElement("candidateProjects");
			projectGroup.appendChild(candidateProjects);

			for (int i = trainNum; i < group.methodList.size(); i++) {

				Method method = group.methodList.get(i);

				// newMethodName element
				Element candidateProject = document.createElement("candidateProject");

				// set an attribute to staff element
				Attr candidateProjectAttr = document.createAttribute("id");
				candidateProjectAttr.setValue(String.valueOf(candidateNum));
				candidateProject.setAttributeNode(candidateProjectAttr);

				candidateNum++;

				candidateProject.appendChild(document.createTextNode(method.oldProjectName));
				candidateProjects.appendChild(candidateProject);
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(document);

			// Output to console for testing
			StreamResult resultConsole = new StreamResult(System.out);
			transformer.transform(source, resultConsole);

			StreamResult resultFile = new StreamResult(new File(xmlPath));
			transformer.transform(source, resultFile);
			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}

		return true;
	}

}
