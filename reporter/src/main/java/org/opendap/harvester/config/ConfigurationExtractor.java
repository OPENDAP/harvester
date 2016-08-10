package org.opendap.harvester.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.opendap.harvester.entity.LinePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
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
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.springframework.util.StringUtils.isEmpty;

@Component
public class ConfigurationExtractor {
    private static final String ENV_VAR_NAME = "OLFS_CONFIG_DIR";
    private static final String DEFAULT_CONFIG_DIR = "/etc/olfs/";
    private static final String WEB_INF = "WEB-INF";
    private static final String OPENDAP_APPLICATION_NAME = "opendap";

    @Autowired
    private ServletContext servletContext;

    @Value("${logfile.pattern.path:}")
    private String logfilePatternPathFromProperties;

    @Value("${logfile.pattern.names:}")
    private String logfilePatternNamesFromProperties;

    @Value("${logfile.pattern.regexp:}")
    private String logfilePatternRegexpFromProperties;

    @Value("${hyrax.logfile.path:}")
    private String hyraxLogfilePathFromProperties;

    @Value("${hyrax.default.ping:3600}")
    private Long hyraxDefaultPingFromProperties;

    private String hyraxLogfilePath = null;
    private Long hyraxDefaultPing = null;
    private String linePatternPath = null;
    private LinePattern linePattern = null;

    public LinePattern getLinePattern(){
        if (linePattern != null){
            return linePattern;
        }
        try {
            if (isEmpty(getLinePatternPath())){
                linePattern = getLinePatternDirectly();
            } else {
                linePattern = new ObjectMapper().readValue(new File(getLinePatternPath()), LinePattern.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linePattern;
    }

    private LinePattern getLinePatternDirectly() {
        LinePattern linePattern = extractLinePatternFormOlfsXml();
        return !isEmpty(linePattern.getNames()) && !isEmpty(linePattern.getRegexp()) ?
                linePattern :
                extractLinePatternFormProperties();
    }
    private LinePattern extractLinePatternFormOlfsXml(){
        return LinePattern.builder()
                .names(extractDataFromOlfsXml("/OLFSConfig/LogReporter/LogFilePattern/names").trim())
                .regexp(extractDataFromOlfsXml("/OLFSConfig/LogReporter/LogFilePattern/regexp").trim())
                .build();
    }

    private LinePattern extractLinePatternFormProperties(){
        return LinePattern.builder()
                .names(logfilePatternNamesFromProperties)
                .regexp(logfilePatternRegexpFromProperties)
                .build();
    }

    private String getLinePatternPath() {
        if (linePatternPath != null){
            return linePatternPath;
        }
        String linePatternFromConfig = extractDataFromOlfsXml("/OLFSConfig/LogReporter/LogFilePatternPath").trim();
        linePatternPath = !isEmpty(linePatternFromConfig)
                ?  linePatternFromConfig
                : logfilePatternPathFromProperties;
        return linePatternPath;
    }

    public Long getDefaultPing() {
        if (hyraxDefaultPing != null){
            return hyraxDefaultPing;
        }
        String hyraxDefaultPingFromConfig = extractDataFromOlfsXml("/OLFSConfig/LogReporter/DefaultPing").trim();
        hyraxDefaultPing = !isEmpty(hyraxDefaultPingFromConfig)
                ?  Long.valueOf(hyraxDefaultPingFromConfig)
                : hyraxDefaultPingFromProperties;
        return hyraxDefaultPing;
    }

    public String getHyraxLogfilePath() {
        if (hyraxLogfilePath != null){
            return hyraxLogfilePath;
        }
        String hyraxLogfilePathFromConfig = extractDataFromOlfsXml("/OLFSConfig/LogReporter/HyraxLogfilePath").trim();
        hyraxLogfilePath = !isEmpty(hyraxLogfilePathFromConfig)
                ? hyraxLogfilePathFromConfig
                : hyraxLogfilePathFromProperties;
        if (isEmpty(hyraxLogfilePath)){
            throw new IllegalStateException("Can not find HyraxLogfilePath property");
        }
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
