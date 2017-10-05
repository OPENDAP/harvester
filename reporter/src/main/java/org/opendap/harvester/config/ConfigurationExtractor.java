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

/**
 * @brief Read configuration information needed by the reporter service.
 *
 * Look for the "olfs.xml" file and use that as the primary source of configuration
 * information. If that cannot be found, then use the default values baked in from
 * the application.properties file. To look for the olfs.xml file, first check the
 * value of the OLFS_CONFIG_DIR environment variable and, if that does not name a
 * valid directory, fallback to checking "/etc/olfs/".
 *
 * @todo This class has methods that trap exceptions. Review that.
 * @todo Add a check of the webaps/opendap/WEB_INF/conf dir to the list of places for config info
 */
@Component
public class ConfigurationExtractor {
    private static final String ENV_VAR_NAME = "OLFS_CONFIG_DIR";
    private static final String DEFAULT_CONFIG_DIR = "/etc/olfs/";
    private static final String DEFAULT_CONFIG_FILE = "olfs.xml";
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

    public LinePattern getLinePattern() {
        if (linePattern != null) {
            return linePattern;
        }
        try {
            if (isEmpty(getLinePatternPath())) {
                linePattern = getLinePatternDirectly();
            } else {
                linePattern = new ObjectMapper().readValue(new File(getLinePatternPath()), LinePattern.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linePattern;
    }
    
    /**
     * What is the 'ping' interval for the reporter? If the value cannot be read
     * from a configuration file, return the value from the application.properties
     * file.
     *
     * @return The ping interval, in seconds.
     */
    public Long getDefaultPing() {
        if (hyraxDefaultPing != null) {
            return hyraxDefaultPing;
        }

        String hyraxDefaultPingFromConfig = extractDataFromOlfsXml("/OLFSConfig/LogReporter/DefaultPing").trim();
        hyraxDefaultPing = !isEmpty(hyraxDefaultPingFromConfig)
                ?  Long.valueOf(hyraxDefaultPingFromConfig)
                : hyraxDefaultPingFromProperties;
        return hyraxDefaultPing;
    }

    /**
     * What is the pathname to the log file this reporter will read from?
     * If the name cannot be read from a configuration file, return the value
     * set in the application.properties file.
     *
     * @return The pathname to the log file.
     */
    public String getHyraxLogfilePath() {
        if (hyraxLogfilePath != null) {
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

    /**
     * @brief Read configuration information from the olfs.xml file.
     *
     * Read configuration information from the "olfs.xml" (aka DEFAULT_CONFIG_FILE).
     * If the configuration file cannot be found or does not contain the information,
     * return the empty string (not a null).
     *
     * @param xPathRoute The XPath to an element in the olfs.xml file.
     * @return The value of the element or the empty string.
     */
    private String extractDataFromOlfsXml(String xPathRoute) {
        XPath xPath = XPathFactory.newInstance().newXPath();
        String elementValue = null;
        try {
            String configDir = getConfigDir();
            if (configDir != null) {
                // FIXME Add test that the file exists and is readable. jhrg 10/4/17
                configDir += DEFAULT_CONFIG_FILE;
                elementValue = xPath.compile(xPathRoute).evaluate(loadXMLFromFile(configDir));
            }
            /* Removed because XPath...evaluate() might return null.
            else {
                elementValue = "";
            } */
        } catch (XPathExpressionException | ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return elementValue != null ? elementValue : "";
    }

    /**
     * Read and parse an XML file.
     *
     * @param xmlFile The pathname to the file
     * @return A Document instance
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    private Document loadXMLFromFile(String xmlFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new FileReader(xmlFile));
        return builder.parse(is);
    }

    /**
     * @brief Look for the directory that holds the reporter's configuration information.
     *
     * First look in the directory named in the OLFS_CONFIG_DIR environment
     * variable, otherwise look in "/etc/olfs/", otherwise look in the 'opendap'
     * web service's "WEB_INF/conf/" directory. Return null as a fallback.
     *
     * @return The pathname or null if no suitable directory can be found.
     * @note Uses pathIsGood() to determine if the given directory is a config
     * directory for the reporter.
     */
    private String getConfigDir() {
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

        // Trick: the reporter web service directory is probably in the same place
        // as the 'opendap' directory. Get 'reporter/WEB_INF", go up two levels and
        // then descend into "opendap/WEB_INF/conf"
        configDirName = servletContext.getRealPath(WEB_INF);
        File cf = new File(configDirName);
        try {
            File webappsFolder = cf.getParentFile().getParentFile();
            String configPath = webappsFolder.getCanonicalPath() +
                    File.separator + OPENDAP_APPLICATION_NAME +
                    File.separator + WEB_INF +
                    File.separator + "conf";
            return pathIsGood(configPath) ? configPath : null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Is the given pathname a directory from which this process can read?
     *
     * @param path The pathname
     * @return True is the directory exists and is readable, False otherwise.
     */
    private boolean pathIsGood(String path) {
        File confDirPath = new File(path);
        return confDirPath.exists() || confDirPath.canRead();
    }

}
