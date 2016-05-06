package org.opendap.harvester.service.impl;

import org.opendap.harvester.entity.dto.HyraxInstanceDto;
import org.opendap.harvester.dao.HyraxInstanceRepository;
import org.opendap.harvester.entity.document.HyraxInstance;
import org.opendap.harvester.service.HyraxInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

import static org.springframework.util.StringUtils.*;

/**
 * Service implementation. All business logic should be here.
 * Call to db are initiating from this place via Repositories
 */
@Service
public class HyraxInstanceServiceImpl implements HyraxInstanceService {
    @Autowired
    private HyraxInstanceRepository hyraxInstanceRepository;

    @Override
    public HyraxInstance register(String serverUrl, String reporterUrl, Long ping, int log) throws Exception {
        String hyraxVersion = checkDomainNameAndGetVersion(serverUrl);
        if (isEmpty(hyraxVersion)){
            throw new IllegalStateException("Bad version, or can not get version of hyrax instance");
        }
        checkReporter(reporterUrl);

        hyraxInstanceRepository.streamByName(serverUrl)
                .filter(HyraxInstance::getActive)
                .forEach(a -> {
                    a.setActive(false);
                    hyraxInstanceRepository.save(a);
                });

        Long reporterDefaultPing = getReporterDefaultPing(reporterUrl);

        HyraxInstance hyraxInstance = HyraxInstance.builder()
                .name(serverUrl)
                .reporterUrl(reporterUrl)
                .log(log)
                .ping(Math.min(ping == null ? Long.MAX_VALUE : ping, reporterDefaultPing))
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

    private void checkReporter(String server) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> entity = restTemplate.getForEntity(new URI(server + "/healthcheck"), String.class);
        if (!entity.getStatusCode().is2xxSuccessful()){
            throw new IllegalStateException("Can not find reporter on this Hyrax Instance");
        }
    }

    private Long getReporterDefaultPing(String server) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Long> entity = restTemplate.getForEntity(new URI(server + "/defaultPing"), Long.class);
        return entity.getBody();
    }


    private String checkDomainNameAndGetVersion(String server) throws Exception {
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
                .reporterUrl(hyraxInstance.getReporterUrl())
                .ping(hyraxInstance.getPing())
                .log(hyraxInstance.getLog())
                .versionNumber(hyraxInstance.getVersionNumber())
                .registrationTime(String.valueOf(hyraxInstance.getRegistrationTime()))
                .lastAccessTime(String.valueOf(hyraxInstance.getLastAccessTime()))
                .active(hyraxInstance.getActive())
                .build();
    }

    @Override
    public void updateLastAccessTime(HyraxInstance hi, LocalDateTime localDateTime) {
        HyraxInstance hyraxInstance = hyraxInstanceRepository.findByIdAndActiveTrue(hi.getId());
        hyraxInstance.setLastAccessTime(localDateTime);
        hyraxInstanceRepository.save(hyraxInstance);
    }

    @Override
    public HyraxInstance findHyraxInstanceByName(String hyraxInstanceName) {
        return hyraxInstanceRepository.findByNameAndActiveTrue(hyraxInstanceName);
    }
}
