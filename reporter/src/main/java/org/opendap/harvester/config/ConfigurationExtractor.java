package org.opendap.harvester.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;

@Component
public class ConfigurationExtractor {
    private static final String ENV_VAR_NAME = "OLFS_CONFIG_DIR";
    private static final String DEFAULT_CONFIG_DIR = "/etc/olfs/";
    private static final String WEB_INF = "WEB-INF";
    private static final String OPENDAP_APPLICATION_NAME = "opendap";

    @Autowired
    private ServletContext servletContext;

    @Value("${hyrax.logfile.path}")
    private String hyraxLogfilePathFromProperties;

    @Value("${hyrax.default.ping}")
    private Long hyraxDefaultPingFromProperties;

    private String hyraxLogfilePath = null;
    private Long hyraxDefaultPing = null;

    public Long getDefaultPing() {
        if (hyraxDefaultPing != null){
            return hyraxDefaultPing;
        }
        String hyraxDefaultPingFromConfig = extractDataFromOlfsXml("/OLFSConfig/LogReporter/DefaultPing");
        hyraxDefaultPing = !StringUtils.isEmpty(hyraxDefaultPingFromConfig)
                ?  Long.valueOf(hyraxDefaultPingFromConfig)
                : hyraxDefaultPingFromProperties;
        return hyraxDefaultPing;
    }

    public String getHyraxLogfilePath() {
        if (hyraxLogfilePath != null){
            return hyraxLogfilePath;
        }
        String hyraxLogfilePathFromConfig = extractDataFromOlfsXml("/OLFSConfig/LogReporter/HyraxLogfilePath");
        hyraxLogfilePath = !StringUtils.isEmpty(hyraxLogfilePathFromConfig)
                ? hyraxLogfilePathFromConfig
                : hyraxLogfilePathFromProperties;
        return hyraxLogfilePath;
    }

    private String extractDataFromOlfsXml(String xPathRoute) {
        XPath xPath =  XPathFactory.newInstance().newXPath();
        String hyraxLogfilePathFromConfig = null;
        try {
        	String configPath = getConfigPath();
        	if (configPath != null) {
        		hyraxLogfilePathFromConfig = xPath.compile(xPathRoute).evaluate(loadXMLFromFile(configPath));
        	}
        	else {
        		hyraxLogfilePathFromConfig = "";
        	}
        		
        } catch (XPathExpressionException | ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return hyraxLogfilePathFromConfig;
    }

    private Document loadXMLFromFile(String xmlFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new FileReader(xmlFile));
        return builder.parse(is);
    }

    private String getConfigPath() {
        String configDirName = System.getenv(ENV_VAR_NAME);
        if (configDirName == null) {
            configDirName = DEFAULT_CONFIG_DIR;
            if (pathIsGood(configDirName)) {
                return configDirName;
            }
        } else {
            if (!configDirName.endsWith("/")) {
                configDirName += "/";
            }
            if (pathIsGood(configDirName)) {
                return configDirName;
            }
        }

        configDirName = servletContext.getRealPath(WEB_INF);

        File cf = new File(configDirName);
        try {
            File webappsFolder = cf.getParentFile().getParentFile();
            String configPath = webappsFolder.getCanonicalPath() +
                    File.separator + OPENDAP_APPLICATION_NAME +
                    File.separator + WEB_INF +
                    File.separator + "conf" +
                    File.separator + "olfs.xml";
            return pathIsGood(configPath) ? configPath : null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean pathIsGood(String path) {
        File confDirPath = new File(path);
        return confDirPath.exists() || confDirPath.canRead();
    }

}
