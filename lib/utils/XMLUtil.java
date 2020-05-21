package javaToolkit.lib.utils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

public class XMLUtil {

	public static String nodeToString(Node node) {
		StringWriter sw = new StringWriter();
		try {
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(new DOMSource(node), new StreamResult(sw));
		} catch (TransformerException te) {
			System.out.println("nodeToString Transformer Exception");
		}
		return sw.toString();
	}

	public static List<Node> getChildbyTagName(Node parent, String nodeName) {
		List<Node> eleList = new ArrayList<Node>();
		for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
//			System.out.println("node name " + child.getNodeName());
			if (nodeName.equals(child.getNodeName())) {
				eleList.add(child);
			}
		}
		return eleList;
	}

}
