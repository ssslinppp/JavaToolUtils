package com.github.ssslinppp.SimpleXML;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.ssslinppp.model.DocumentSchema;
import org.junit.Test;

import java.io.*;

/**
 * Description：<br/>
 * User: liulin <br/>
 * Date: 2017/11/20 <br/>
 * Time: 11:08 <br/>
 * To change this template use File | Settings | File Templates.
 */
public class DocumentSchemaTest {
    private static String inputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    private ObjectMapper getXMLMapper() {
        //The Jackson XML module supports the full range of annotations that Jackson provides for annotating our POJOs.
        //The Jackson XML module adds some additional support for XML specific features
        JacksonXmlModule module = new JacksonXmlModule();
        //As of version 2.9.0, the only configuration options for the XML Module are:
        module.setDefaultUseWrapper(false);
        ObjectMapper xmlMapper = new XmlMapper(module);
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.FALSE);
        xmlMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, Boolean.TRUE);
        xmlMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, Boolean.TRUE);
        xmlMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, Boolean.TRUE);
        return xmlMapper;
    }

    /**
     * POJO to XML
     *
     * @throws IOException
     */
    @Test
    public void whenJavaSerializedToXmlStr_thenCorrect() throws IOException {
        String xml = getXMLMapper().writeValueAsString(DocumentSchema.getDemo());
        System.out.println(xml);
    }

    /**
     * XML and POJO parse with file
     *
     * @throws IOException
     */
    @Test
    public void whenJavaGotFromXmlFile_thenCorrect() throws IOException {
        String filePath = "D:\\tempTempTemp\\simple_bean.xml";
        getXMLMapper().writeValue(new File(filePath), DocumentSchema.getDemo());

        String xml = inputStreamToString(new FileInputStream(new File(filePath)));
        DocumentSchema value = getXMLMapper().readValue(xml, DocumentSchema.class);
        System.out.println(value);
    }
}
