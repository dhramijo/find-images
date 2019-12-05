package co.uk.findimages.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jonad dhrami on 01/12/2019.
 */

@Service
public class FindImagesService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${nasa.url}")
    private String url;

    public String fetchData() throws MalformedURLException, URISyntaxException {
        
        String result = restTemplate.getForObject(url, String.class);
        return result;

    }

    public List<String> getImages(String jsonString) throws IOException {

        List<String> images = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        JsonNode actualObj = mapper.readTree(jsonString);
        JsonNode itemsNode = actualObj.path("collection").path("items");

        Iterator<JsonNode> elements = itemsNode.elements();
        while (elements.hasNext()) {
            JsonNode items = elements.next();
            if (!Stream.of(items.get("links")).collect(Collectors.toList()).removeIf(Objects::isNull)) {
                images.add(items.get("links").get(0).get("href").asText());
            }
        }
        return images;
    }
}
