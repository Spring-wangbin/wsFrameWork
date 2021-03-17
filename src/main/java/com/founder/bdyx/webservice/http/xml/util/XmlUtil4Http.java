package com.founder.bdyx.webservice.http.xml.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.founder.bdyx.webservice.core.constants.WsConstant;

public class XmlUtil4Http
{
  private DocumentBuilderFactory factory;
  private static Logger log = Logger.getLogger(XmlUtil4Http.class);
  private DocumentBuilder db;
  private Document docIn;
  private Document docOut;
  public String strOutputXml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?><response><ret_code>0</ret_code><ret_msg>执行成功</ret_msg><output></output></response>";
  public String sSqlResult = "";
  public String InputTime_stamp = "";
  public Map<String, String> inputMap;
  public Map<String, String> outputMap;

  public String Method_Code = "";
  
  public XmlUtil4Http()
    throws Throwable, IOException
  {
    this.factory = DocumentBuilderFactory.newInstance();
    try
    {
      this.factory.setIgnoringElementContentWhitespace(true);
      this.inputMap = new HashMap();
      this.db = this.factory.newDocumentBuilder();

      InitOut();
    }
    catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
  }

  public void InitInput(String strInputXml)
  {
    try
    {
      this.docIn = this.db.parse(new ByteArrayInputStream(strInputXml.trim().getBytes("UTF-8")));
      getInput();
    }
    catch (SAXException e)
    {
      setRetMsg(WsConstant.MSG_CODE_1040);

      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void InitOut()
    throws TransformerException
  {
    try
    {
      this.docOut = this.db.parse(new ByteArrayInputStream(this.strOutputXml.getBytes("UTF-8")));
      setRetMsg(WsConstant.MSG_CODE_SUCESS);
    }
    catch (SAXException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void getInput()
  {
    Element theElem = null; Element root = null;
    Element inElem = null;
    this.inputMap.clear();
    root = this.docIn.getDocumentElement();
    NodeList nlist = root.getElementsByTagName("time_stamp");
    this.InputTime_stamp = ((Element)nlist.item(0)).getTextContent();
    
    // 适用与通用xml http
    NodeList methodlist = root.getElementsByTagName("methodCode");
    if (methodlist != null && methodlist.getLength() > 0) {
    	this.Method_Code = ((Element)methodlist.item(0)).getTextContent();
    }
    
    nlist = root.getElementsByTagName("input");
    inElem = (Element)nlist.item(0);
    NodeList inputNodes = inElem.getChildNodes();
    for (int i = 0; i < inputNodes.getLength(); i++) {
      Node n = inputNodes.item(i);
      if (n.getNodeType() != 1)
        continue;
      theElem = (Element)inputNodes.item(i);
      this.inputMap.put(theElem.getNodeName(), theElem.getTextContent());
    }
  }

  public boolean setRetMsg(String ret_code)
  {
    Element root = this.docOut.getDocumentElement();
    NodeList nlist = root.getElementsByTagName("ret_code");
    Element codeElem = (Element)nlist.item(0);
    codeElem.setTextContent(ret_code);
    nlist = root.getElementsByTagName("ret_msg");
    Element msgElem = (Element)nlist.item(0);
    msgElem.setTextContent(WsConstant.msgMap.get(ret_code));
    return true;
  }

  public boolean setRetErrorMsg(String ret_code, String ret_msg)
  {
    Element root = this.docOut.getDocumentElement();
    NodeList nlist = root.getElementsByTagName("ret_code");
    Element codeElem = (Element)nlist.item(0);
    codeElem.setTextContent(ret_code);
    nlist = root.getElementsByTagName("ret_msg");
    Element msgElem = (Element)nlist.item(0);
    msgElem.setTextContent(ret_msg);
    return true;
  }

  public boolean setRetMsg(String ret_code, String ret_msg)
  {
    Element root = this.docOut.getDocumentElement();
    NodeList nlist = root.getElementsByTagName("ret_code");
    Element codeElem = (Element)nlist.item(0);
    codeElem.setTextContent(ret_code);
    nlist = root.getElementsByTagName("ret_msg");
    Element msgElem = (Element)nlist.item(0);
    msgElem.setTextContent(ret_msg);
    return true;
  }

  public Boolean prepareOutNodes()
  {
    Element root = null; Element outElem = null;
    root = this.docOut.getDocumentElement();
    NodeList nlist = root.getElementsByTagName("output");
    outElem = (Element)nlist.item(0);
    if ((this.sSqlResult != null) && (!this.sSqlResult.equals("")))
    {
      System.out.println("sSqlResult:" + this.sSqlResult);
      try {
        Document dc = this.db.parse(new ByteArrayInputStream(this.sSqlResult.getBytes("UTF-8")));
        Element dbRootNode = dc.getDocumentElement();
        Element dataNode = (Element)this.docOut.importNode(dbRootNode, true);
        outElem.appendChild(dataNode);
      }
      catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      catch (SAXException e) {
        log.error("xml解析错误：" + this.sSqlResult);
        setRetMsg(WsConstant.MSG_CODE_1010);
        e.printStackTrace();
        return Boolean.valueOf(false);
      }
      catch (IOException e) {
        e.printStackTrace();
      }

    }else{
    	strOutputXml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?><response><ret_code>1110</ret_code><ret_msg>数据返回null</ret_msg><output></output></response>";

    }

    return Boolean.valueOf(true);
  }

  public String getOut()
    throws TransformerConfigurationException
  {
    String xmlStr = "";
    TransformerFactory transFactory = TransformerFactory.newInstance();
    Transformer transFormer = transFactory.newTransformer();
    DOMSource domSource = new DOMSource(this.docOut);
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    transFormer.setOutputProperty("encoding", "utf-8");
    transFormer.setOutputProperty("indent", "yes");
    try {
      transFormer.transform(domSource, new StreamResult(bos));
    }
    catch (TransformerException e) {
      log.error("out transform: " + e.getMessage());
      e.printStackTrace();
    }
    try {
      xmlStr = bos.toString("utf-8");
    }
    catch (UnsupportedEncodingException e) {
      log.error("UnsupportedEncodingException: " + e.getMessage());
      e.printStackTrace();
    }
    log.info("输出结果:" + xmlStr);
    return xmlStr;
  }
  
}