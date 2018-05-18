package com.sunyard.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author chf
 *
 */
@SuppressWarnings("unchecked")
public class XmlUtil {
	private static final Log loger = LogFactory.getLog(XmlUtil.class);
	protected Document doc = null;
	private Element root = null;
	private OutputFormat fmt = OutputFormat.createCompactFormat(); 
	
	public final static int FROM_ROOT = 0; 
	public final static int FROM_BUFF = 1; 
	public final static int FROM_FILE = 2;
	public final static int FROM_ENCODE = 3;

	
	public static XmlUtil Create(int fromType, String tmpStr) throws Exception{
		try { 
			return new XmlUtil(fromType, tmpStr);
		} catch (Exception e) {
			throw new Exception("创建XmlUtil对象失败！！",e);
			//loger.error("create XmlUtil error!fromType=[" + fromType + "]" + e);
			//return null;
		}
	}
	public String getRootPath(){
		return root.getPath();
	}
	public XmlUtil setNodeContent(String xpath, String value) {
		loger.debug("set:[" + xpath + "]val=[" + value + "]");
		if (value != null)
			doc.selectSingleNode(xpath).setText(value);
		return this;
	}
	
	public XmlUtil setNodeContentTrim(String xpath, String value) {
		loger.debug("set:[" + xpath + "]val=[" + value + "]");
		if (value != null)
			doc.selectSingleNode(xpath).setText(value.trim());
		return this;
	}
	
	public String getNodeContent(String xpath) {
		String str = null;
		str = doc.selectSingleNode(xpath).getText();
		loger.debug("get:[" + xpath + "]str=[" + str + "]");
		return str;
	}
	
	public String getNodeContentTrim(String xpath) {
		String str = null;
		if(null!=doc.selectSingleNode(xpath))
			str = doc.selectSingleNode(xpath).getText();
		loger.debug("get:[" + xpath + "]str=[" + str + "]");
		return str == null ? "" : str.trim();
	}
	
	public String getNodeContentNotNUll(String xpath) {
		String str = null;
		str = doc.selectSingleNode(xpath)!=null?doc.selectSingleNode(xpath).getText():"";
		loger.debug("get:[" + xpath + "]str=[" + str + "]");
		return str == null ? "" : str.trim();
	}
	
	public XmlUtil setNodesContent(String xpath, String value) {
		List nodes = doc.selectNodes(xpath);
		Iterator iter = nodes.iterator();
		while(iter.hasNext())
			((Element)iter.next()).setText(value);
		return this;
	}
	
	public String[] getNodesContent(String xpath) {
		int i = 0;
		List nodes = doc.selectNodes(xpath);
		String[] values = new String[nodes.size()];
		Iterator iter = nodes.iterator();
		while(iter.hasNext()) {
			values[i++] = ((Node)iter.next()).getText();
		}
		return  values;
	}
	
	public XmlUtil replaceNode(String oldXPath, XmlUtil newDoc, String newXPath) {
		Element oldNode = (Element)(doc.selectNodes(oldXPath).get(0));
		Element newNode = (Element)(newDoc.getDocument().selectNodes(newXPath).get(0));
	    oldNode.setContent(newNode.content());   
		return this;
	}
	
	public XmlUtil exchgNodeContent(String xpathA, String xpathB) {
		String tmp = getNodeContent(xpathA);
		setNodeContent(xpathA, getNodeContent(xpathB));
		setNodeContent(xpathB, tmp);
		return this;
	}
	
	public static XmlUtil copy(XmlUtil doc) throws Exception {
		String root = doc.getDocument().getRootElement().getName();
		XmlUtil tmp = XmlUtil.Create(XmlUtil.FROM_ROOT, root);
		if (tmp == null)
			return null;
		String xpath = "//" + root;
		tmp.replaceNode(xpath, doc, xpath);

		return tmp;
	}
	
	public XmlUtil setFormat(String encoding, boolean pretty)  {
		if (pretty) {
			fmt = OutputFormat.createPrettyPrint();
			fmt.setIndent("    ");
			fmt.setNewlines(true);
		}
		fmt.setEncoding(encoding);
		
		return this;
	}
	
	public int checkValid(String nodesXPath, String attrName) {
		List nodes = doc.selectNodes(nodesXPath + "[@" + attrName + "]");
		Iterator iter = nodes.iterator();
		Element e = null;
		String checkAttr = null;
		String checkValue = null;
		int length = 0;
		int checkLength = 0;
		while (iter.hasNext()) {
			e = ((Element)iter.next());
			checkValue = getNodeContent("//" + e.getText());
			checkAttr = e.attributeValue(attrName);
			length = Integer.parseInt(checkAttr.substring(2));
			checkLength = checkValue.length();
			if (checkAttr.charAt(0) == 'O') {
				if (checkLength == 0)
					continue;
			} else if (checkAttr.charAt(0) != 'M')
				continue;
			if (checkLength > 0) {
				switch (checkAttr.charAt(1)) {
				case '=': //必需等于length
					if (checkLength == length) continue; else break;
				case '-': //必需小于length
					if (length > checkLength) continue; else break;
				case '+': //必需大于length
					if (checkLength > length) continue; else break;
				}
			}
			loger.error(e.getText() + "元素的值=[" + checkValue + "]不合法!");
			return -1;
		}
		return 0;
	}
	
	public void changeAmtVal(String amtNodesXPath) {
		List nodes = doc.selectNodes(amtNodesXPath);
		Iterator iter = nodes.iterator();
		String checkValue = null;
		String xpath = null;
		int tmp = 0;
		while (iter.hasNext()) {
			loger.info("处理金额类型数据...");
			xpath = "//BODY/" + ((Element)iter.next()).getText();
			checkValue = getNodeContent(xpath);
			if ((tmp = checkValue.indexOf('.')) != -1)
				setNodeContent(xpath, checkValue.substring(0, tmp));
			loger.info("原值:[" + checkValue + "]新值:[" + getNodeContent(xpath) + "]");
		}
	}
	
	public String dumpToBuff() {
		ByteArrayOutputStream str = new ByteArrayOutputStream();
		try {
			fmt.setEncoding("UTF-8");
			fmt.setTrimText(false);
			XMLWriter writer = new XMLWriter(str, fmt);
	        writer.write(doc);
	        writer.close();
		} catch (IOException e) {
			loger.error("XmlUtil dump to buff error!" + e);
			return null;
		}
		return str.toString();
	}
	
	public int dumpToFile(String fname) {
		try {
			XMLWriter writer = new XMLWriter(new FileWriter(fname), fmt);
			writer.write(doc);
	        writer.close();
		} catch (IOException e) {
			loger.error("XmlUtil dump to " + fname + " error!" + e);
			return -1;
		}
		return 0;
	}

	public XmlUtil(){
		
	}
	
	private XmlUtil(int fromType, String tmpStr) throws DocumentException {
		switch (fromType) {
		case FROM_BUFF:
			doc = DocumentHelper.parseText(tmpStr);
			break;
		case FROM_FILE:
			SAXReader reader = new SAXReader();
			reader.setIgnoreComments(true);
			doc = reader.read(new File(tmpStr));
			break;
		case FROM_ROOT:
			doc = DocumentHelper.createDocument();
			doc.addElement(tmpStr);
			break;
		case FROM_ENCODE:
			doc = new DocumentFactory().createDocument(tmpStr);
			break;
		}
	}
	public XmlUtil addElement(String path,String addEle,List l)throws Exception{
		loger.debug(doc.getName());
		Element e = (Element)this.doc.selectNodes(path).get(0);
		Element add = e.addElement(addEle);
		add.setContent(l);
		return this;
	}
	public XmlUtil addElement(String path,String addEle)throws Exception{
		loger.debug(doc.getName());
		Element e = (Element)this.doc.selectNodes(path).get(0);
		e.addElement(addEle);
		return this;
	}
	public XmlUtil getNodes(XmlUtil doc,String path) throws Exception{
		XmlUtil tmp = null;
		List l = null;
//		Element node = null;
		l = doc.getDocument().selectNodes(path);
		int size = l.size();
		if((l==null||size==0)){
			return null;
		}
		this.doc = DocumentHelper.createDocument();
		for(int i =0 ;i<size;i++){
//			node = (Element)l.get(i);
			//node.
			//this.doc.add(node);		
		}	
		//tmp = new XmlUtil();
		//tmp = node == null ? null : XmlUtil.Create(XmlUtil.FROM_ROOT, node.getName());
		//if (tmp == null)
		//	return null;
		//tmp.replaceNode(path,doc,path);
		return tmp;
	}
	
	public Document getDocument() {
		return this.doc;
	}
	
	public static void main(String[] args) throws Exception {
		XmlUtil doc = null;
		doc = XmlUtil.Create(XmlUtil.FROM_FILE, "f:/grid.xml");
		System.out.println(doc.getNodeContentTrim("/root/grid[@id='7102']/fieldsDefine").replaceAll("\\s", ""));
		/*
		doc.setNodesContent("//QM", "queue");
		String[] tmp = doc.getNodesContent("//QM");
		for (int i = 0; i < tmp.length; i++)
		System.out.println(tmp[i]);
		doc.setNodeContent("//QUEUE[@DESC='RealRecvFromPH']/../@NAME", "test");
		System.out.println(doc.getNodeContent("//QUEUE[@DESC='RealRecvFromPH']/Qname"));
		doc.replaceNode("//HEAD", doc2, "//HEAD");
		
		doc.checkValid("//MapList/*", "STDFMT");
		doc.changeAmtVal("//MapList/*[@TYPE='AMT']");
		*/
		//cispub.setCISConfigInfo();
		
//		System.out.println(doc.dumpToBuff());
	}
}
