package com.huimi.common.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class XmlTool {
	Logger logger = LoggerFactory.getLogger(XmlTool.class);

	DocumentBuilderFactory factory;
	DocumentBuilder docBuilder;
	Document doc;
	public NodeList nodeList;

	public XmlTool() {
	}

	public void SetDocument(String xml) throws Exception {
		try {
			factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			docBuilder = factory.newDocumentBuilder();
			xml = xml.trim();

			InputStream inputStream = new ByteArrayInputStream(xml.getBytes("utf-8"));// xml为要解析的字符串
			docBuilder = factory.newDocumentBuilder();
			doc = docBuilder.parse(inputStream);
		} catch (Exception e) {
			logger.error("xml解析错误{}", e);
			throw new Exception("xml，SetDocument错误");

		}
	}

	public int getNodeListCount(String NodeName) {
		NodeList list = doc.getDocumentElement().getElementsByTagName(NodeName);
		int count = list.getLength();
		return count;
	}

	public String getNodeValue(String NodeName) throws Exception {
		try {
			NodeList list = doc.getDocumentElement().getElementsByTagName(NodeName);
			if (list == null || list.getLength() <= 0) {
				return "";
			}
			String txt = list.item(0).getFirstChild().getNodeValue() == null ? ""
					: list.item(0).getFirstChild().getNodeValue();
			return txt;
		} catch (Exception e) {
			logger.error("getNodeValue,error{}", e);
			throw new Exception("getNodeValue，异常");
		}
	}

	public String getNodeXml(String... NodeNames) throws Exception {
		StringBuilder sb = new StringBuilder();
		try {
			NodeList list = doc.getDocumentElement().getChildNodes();

			if (list.getLength() > 0) {
				for (int i = 0; i < list.getLength(); i++) {
					if (!RemoveChild(list.item(i).getNodeName(), NodeNames)) {
						String txt = getNodeValue(list.item(i).getNodeName());
						if (txt == "")
							sb.append("<" + list.item(i).getNodeName() + "></" + list.item(i).getNodeName() + ">");
						else {
							sb.append("<" + list.item(i).getNodeName() + ">" + txt + "</" + list.item(i).getNodeName()
									+ ">");
						}
					}
				}
			}
			String txt = sb.toString();
			return txt;
		} catch (Exception e) {
			logger.error("getNodeXml,error{}", e);
			throw new Exception("getNodeXml，异常");
		}
	}

	private boolean RemoveChild(String NodeName, String... NodeNames) {
		for (int i = 0; i < NodeNames.length; i++) {
			if (NodeName.equals(NodeNames[i])) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public NodeList getNodeList(String NodeName) {
		NodeList list = doc.getDocumentElement().getElementsByTagName(NodeName);
		return list;
	}

	public static void main(String[] args) throws Exception {
		XmlTool xt = new XmlTool();
		String xml = "<ap>" + "<ResTime>124649</ResTime>" + "<ResDate>20150519</ResDate>"
				+ "<ResInfo>查询托管平台支付账户出错</ResInfo>" + "<ResCode>0009</ResCode>"
				+ "<ResSeqNo>19000519000000105125</ResSeqNo>" + "<ReqSeqNo></ReqSeqNo>" + "<TradeCode></TradeCode>"
				+ "</ap>";

		xt.SetDocument(xml);

		System.out.println("." + xt.getNodeValue("sfeww") + ".");
	}

}
