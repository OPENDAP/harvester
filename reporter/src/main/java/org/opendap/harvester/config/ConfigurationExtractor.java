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

/**
 * @todo Fix this code. Directory versus config file confusion.
 *
 * It confuses looking for the directory that holds the configuration file
 * with the file itself. Maybe add a fileIsGood() method to test that the
 * service can actually read from the config file? (that is never tested)
 *
 * Maybe stop catching exceptions when the config search breaks?
 *
 *
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

    @Value("${hyrax.logfile.path}")
    private String hyraxLogfilePathFromProperties;

    @Value("${hyrax.default.ping}")
    private Long hyraxDefaultPingFromProperties;

    private String hyraxLogfilePath = null;
    private Long hyraxDefaultPing = null;

    public Long getDefaultPing() {
        if (hyraxDefaultPing != null) {
            return hyraxDefaultPing;
        }

        String defaultPingFromConfig = extractDataFromOlfsXml("/OLFSConfig/LogReporter/DefaultPing").trim();
        hyraxDefaultPing = !StringUtils.isEmpty(defaultPingFromConfig)
                ? Long.valueOf(defaultPingFromConfig)
                : hyraxDefaultPingFromProperties;
        return hyraxDefaultPing;
    }

    public String getHyraxLogfilePath() {
        if (hyraxLogfilePath != null) {
            return hyraxLogfilePath;
        }
        String logfilePathFromConfig = extractDataFromOlfsXml("/OLFSConfig/LogReporter/HyraxLogfilePath").trim();
        hyraxLogfilePath = !StringUtils.isEmpty(logfilePathFromConfig)
                ? logfilePathFromConfig
                : hyraxLogfilePathFromProperties;
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
