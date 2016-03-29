/**
 * Service implementation. All business logic should be here.
 * Call to db are initiating from this place via Repositories
 */
package org.opendap.harvester.service.impl;

import org.opendap.harvester.entity.dto.HyraxInstanceDto;
import org.opendap.harvester.dao.HyraxInstanceRepository;
import org.opendap.harvester.entity.document.HyraxInstance;
import org.opendap.harvester.service.HyraxInstanceRegisterService;
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
import java.time.LocalDateTime;
import java.util.stream.Stream;

@Service
public class HyraxInstanceRegisterServiceImpl implements HyraxInstanceRegisterService {
    @Autowired
    private HyraxInstanceRepository hyraxInstanceRepository;

    @Override
    public HyraxInstance register(String server, int ping, int log) throws Exception {
        String hyraxVersion = checkDomainName(server);
        if (StringUtils.isEmpty(hyraxVersion)){
            throw new IllegalStateException("Bad version, or can not get version of hyrax instance");
        }
        hyraxInstanceRepository.streamByName(server)
                .filter(HyraxInstance::getActive)
                .forEach(a -> {
                    a.setActive(false);
                    hyraxInstanceRepository.save(a);
                });

        HyraxInstance hyraxInstance = HyraxInstance.builder()
                .name(server)
                .log(log)
                .ping(ping)
                .versionNumber(hyraxVersion)
                .registrationTime(LocalDateTime.now())
                .active(true)
                .build();
        return hyraxInstanceRepository.save(hyraxInstance);
    }

    @Override
    public Stream<HyraxInstance> allHyraxInstances() {
        return allHyraxInstances(false);
    }

    @Override
    public Stream<HyraxInstance> allHyraxInstances(boolean onlyActive) {
        return onlyActive ? hyraxInstanceRepository.streamByActiveTrue() :
                hyraxInstanceRepository.findAll().stream();
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
    public HyraxInstanceDto buildDto(HyraxInstance hyraxInstance) {
        return HyraxInstanceDto.builder()
                .name(hyraxInstance.getName())
                .ping(hyraxInstance.getPing())
                .log(hyraxInstance.getLog())
                .versionNumber(hyraxInstance.getVersionNumber())
                .registrationTime(String.valueOf(hyraxInstance.getRegistrationTime()))
                .lastAccessTime(String.valueOf(hyraxInstance.getLastAccessTime()))
                .active(hyraxInstance.getActive())
                .build();
    }
}
