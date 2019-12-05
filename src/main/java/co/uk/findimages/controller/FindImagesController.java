package co.uk.findimages.controller;

import co.uk.findimages.service.FindImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by jonad dhrami on 01/12/2019.
 */

@RestController
public class FindImagesController {

    @Autowired
    public FindImagesService imageFinderService;

    @GetMapping(value = "/find-image", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> imageFinder() throws IOException, URISyntaxException {

        String result = imageFinderService.fetchData();

        List<String> images = imageFinderService.getImages(result);

        return images;
    }

}
