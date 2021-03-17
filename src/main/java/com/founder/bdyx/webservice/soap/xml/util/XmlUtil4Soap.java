package com.founder.bdyx.webservice.soap.xml.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.founder.bdyx.webservice.core.constants.WsConstant;

public class XmlUtil4Soap
{
    private DocumentBuilderFactory factory;
    private static Logger log = Logger.getLogger(XmlUtil4Soap.class);
    private DocumentBuilder db;
    private Document docIn;
    private Document docOut;
    
    public String strOutputXml = WsConstant.getErrRes4Xml(WsConstant.MSG_CODE_SUCESS, WsConstant.msgMap.get(WsConstant.MSG_CODE_SUCESS));
    public String sSqlResult = "";
    public String Method_Code = "";
    public Map<String, String> inputMap;
    public List<Map<String, String>> inputList;
    public Map<String, String> outputMap;

    // 常量
    public String HospitalId = "hospitalId";
    public String Signature = "signature";
    public String NonceStr = "nonceStr";
    public String OpenId = "openId";

    /**
     * 获取4个共通参数
     * @return
     */
    public String getCommPara() {
        String hospitalId = HospitalId;
        String signature = Signature;
        String nonceStr = NonceStr;
        String openId = OpenId;
        if (inputMap != null) {
            hospitalId = inputMap.get("hospitalId") == null ? HospitalId : inputMap.get("hospitalId").trim();
            signature = inputMap.get("signature") == null ? Signature : inputMap.get("signature").trim();
            nonceStr = inputMap.get("nonceStr") == null ? NonceStr : inputMap.get("nonceStr").trim();
            openId = inputMap.get("openId") == null ? OpenId : inputMap.get("openId").trim();
        }
        return "'" + hospitalId + "'" + ",'" + signature + "'" + ",'"+ nonceStr + "'" + ",'" + openId + "'";
    }

    public XmlUtil4Soap() throws Throwable {
        this.factory = DocumentBuilderFactory.newInstance();
        try
        {
            this.factory.setIgnoringElementContentWhitespace(true);
            this.inputMap = new HashMap<String, String>();
            this.inputList = new ArrayList<>();
            this.db = this.factory.newDocumentBuilder();

            InitOut();
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void InitInput_Common(String strInputXml) throws SAXException {
        try
        {
            if(strInputXml == null || "".equals(strInputXml)) return;
            this.docIn = this.db.parse(new ByteArrayInputStream(strInputXml.trim().getBytes("UTF-8")));
        }
        catch (SAXException e)
        {
            setRetMsg(WsConstant.MSG_CODE_1040);
            throw new SAXException(e);
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
            this.docOut = this.db.parse(new ByteArrayInputStream(this.strOutputXml.toString().getBytes("UTF-8")));
            setRetMsg(WsConstant.MSG_CODE_SUCESS);
        }
        catch (SAXException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*暂不使用*/
    public void getInput()
    {
        Element theElem = null;
        Element root = null;
        Element inElem = null;
        this.inputMap.clear();
        root = this.docIn.getDocumentElement();
        NodeList nlist = root.getElementsByTagName("methodCode");
        this.Method_Code = ((Element)nlist.item(0)).getTextContent();
        nlist = root.getElementsByTagName("methodParam");
        inElem = (Element)nlist.item(0);
        NodeList inputNodes = inElem.getChildNodes();
        for (int i = 0; i < inputNodes.getLength(); i++) {
            Node n = inputNodes.item(i);
            if (n.getNodeType() != Node.ELEMENT_NODE) continue;
            theElem = (Element)inputNodes.item(i);
            this.inputMap.put(theElem.getNodeName(), theElem.getTextContent());
        }
    }

    public void getInput_LIS()
    {
        Element root = null;
        this.inputMap.clear();
        if(this.docIn == null) return;
        root = this.docIn.getDocumentElement();
        if(root.hasChildNodes()){
            NodeList nodeList = root.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE)
                    this.inputMap.put(node.getNodeName(), node.getTextContent());
            }
        }
    }

    public void getInputToList()
    {
        Element root = null;
        this.inputList.clear();
        root = this.docIn.getDocumentElement();

        if(root.hasChildNodes()){
            NodeList nodeList = root.getElementsByTagName("data");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if(node.hasChildNodes()){
                    NodeList childNodes = node.getChildNodes();
                    Map<String,String> retMap = new HashMap<String,String>();
                    for(int j=0;j<childNodes.getLength();j++){
                        Node childNode = childNodes.item(j);
                        if(childNode.getNodeType() == Node.ELEMENT_NODE){
                            retMap.put(childNode.getNodeName(),childNode.getTextContent());
                        }
                    }
                    this.inputList.add(retMap);
                }
            }
        }
    }

    public boolean setRetMsg(String returnCode)
    {
        Element root = this.docOut.getDocumentElement();
        NodeList nlist = root.getElementsByTagName(WsConstant.RETURN_CODE);
        Element codeElem = (Element)nlist.item(0);
        codeElem.setTextContent(returnCode);
//        nlist = root.getElementsByTagName(WsConstant.RETURN_MSG);
//        Element msgElem = (Element)nlist.item(0);
//        msgElem.setTextContent(WsConstant.msgMap.get(returnCode));
        return true;
    }

    public boolean setRetMsg(String returnCode,List<Map<String,Object>> dataList)
    {
        Element root = this.docOut.getDocumentElement();
        NodeList nlist = root.getElementsByTagName(WsConstant.RETURN_CODE);
        Element codeElem = (Element)nlist.item(0);
        codeElem.setTextContent(returnCode);

        NodeList contentNode = root.getElementsByTagName(WsConstant.RETURN_DATA);
        Element content = (Element)contentNode.item(0);

        for(int i=0; i<dataList.size();i++){
            Map<String , Object> dataMap = dataList.get(i);
            Element data = this.docOut.createElement("data");
            content.appendChild(data);

            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                Element el = this.docOut.createElement(entry.getKey());
                el.setTextContent(entry.getValue().toString());
                data.appendChild(el);
            }
        }

        return true;
    }

    public boolean setRetMsg(String returnCode, String returnMsg)
    {
        Element root = this.docOut.getDocumentElement();
        NodeList nlist = root.getElementsByTagName(WsConstant.RETURN_CODE);
        Element codeElem = (Element)nlist.item(0);
        codeElem.setTextContent(returnCode);
        nlist = root.getElementsByTagName(WsConstant.RETURN_MSG);
        Element msgElem = (Element)nlist.item(0);
        msgElem.setTextContent(returnMsg);
        return true;
    }

    public boolean setRetErrorMsg(String returnCode)
    {
        Element root = this.docOut.getDocumentElement();
        NodeList nlist = root.getElementsByTagName(WsConstant.RETURN_CODE);
        Element codeElem = (Element)nlist.item(0);
        codeElem.setTextContent(returnCode);
        return true;
    }

    public Boolean prepareOutNodes()
    {
        Element root = null; Element outElem = null;
        root = this.docOut.getDocumentElement();
        NodeList nlist = root.getElementsByTagName(WsConstant.RETURN_DATA);
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
        	strOutputXml = WsConstant.getErrRes4Xml(WsConstant.MSG_CODE_NO_DATA, WsConstant.msgMap.get(WsConstant.MSG_CODE_NO_DATA));

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
        
        if (StringUtils.hasText(sSqlResult)) {
        	xmlStr = xmlStr.replace(WsConstant.RETURN_NO_DATA, sSqlResult);
        } else {
        	xmlStr = xmlStr.replace(WsConstant.RETURN_NO_DATA, "");
        }
        return xmlStr;
    }
    
    
    public static void main(String arg[]) {
    	
//    	System.out.println(strOutputXml);
    }

    public void setSqlData(List<Map<String, Object>> dataList) {
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<dataList.size();i++){
            Map<String , Object> dataMap = dataList.get(i);
            sb.append("<data>");
            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                sb.append("<").append(entry.getKey()).append(">")
                        .append(entry.getValue().toString()
                                .replace("<","&lt;")
                                .replace(">","&gt;")
                                .replace("'","&apos;")
                                .replace("\"","&quot;")
                                .replace("&","&amp;")
                        )
                        .append("</").append(entry.getKey()).append(">");
            }
            sb.append("</data>");
        }

        this.sSqlResult = sb.toString();
    }
}
