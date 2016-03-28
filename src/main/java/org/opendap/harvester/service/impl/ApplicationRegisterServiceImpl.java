package org.opendap.harvester.service.impl;

import org.opendap.harvester.entity.dto.ApplicationDto;
import org.opendap.harvester.dao.ApplicationRepository;
import org.opendap.harvester.entity.document.Application;
import org.opendap.harvester.service.ApplicationRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.stream.Stream;

@Service
public class ApplicationRegisterServiceImpl implements ApplicationRegisterService {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public Application register(String server, int ping, int log) throws Exception {
        String hyraxVersion = checkDomainName(server);
        if (StringUtils.isEmpty(hyraxVersion)){
            throw new IllegalStateException("Bad version, or can not get version of hyrax instance");
        }
        applicationRepository.streamByName(server)
                .filter(Application::getActive)
                .forEach(a -> {
                    a.setActive(false);
                    applicationRepository.save(a);
                });

        Application application = Application.builder()
                .name(server)
                .log(log)
                .ping(ping)
                .versionNumber(hyraxVersion)
                .registrationTime(LocalDateTime.now())
                .active(true)
                .build();
        return applicationRepository.save(application);
    }

    @Override
    public Stream<Application> allApplications() {
        return allApplications(false);
    }

    @Override
    public Stream<Application> allApplications(boolean onlyActive) {
        return onlyActive ? applicationRepository.streamByActiveTrue() :
                applicationRepository.findAll().stream();
    }


    private String checkDomainName(String server) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String xmlString = restTemplate.getForObject(new URI(server + "/version"), String.class);
        XPath xPath =  XPathFactory.newInstance().newXPath();
        return xPath.compile("/HyraxCombinedVersion/Hyrax/@version")
                .evaluate(loadXMLFromString(xmlString));
    }

    private Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

    @Override
    public ApplicationDto buildDto(Application application) {
        return ApplicationDto.builder()
                .name(application.getName())
                .ping(application.getPing())
                .log(application.getLog())
                .versionNumber(application.getVersionNumber())
                .registrationTime(String.valueOf(application.getRegistrationTime()))
                .lastAccessTime(String.valueOf(application.getLastAccessTime()))
                .active(application.getActive())
                .build();
    }
}
