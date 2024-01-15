package de.borisskert.capitalcoordinatesservice.endpoint;

import de.borisskert.capitalcoordinatesservice.service.CapitalCoordinatesService;
import de.borisskert.capitalcoordinatesservice.model.CapitalWithCoordinates;
import de.borisskert.capitalcoordinatesservice.model.CountryCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("coordinates")
public class CoordinatesController {
    private final CapitalCoordinatesService service;

    @Autowired
    public CoordinatesController(CapitalCoordinatesService service) {
        this.service = service;
    }

    @GetMapping(path = "/{countryCode}", produces = "application/json")
    public CapitalWithCoordinates getCoordinates(@PathVariable("countryCode") String countryCode) {
        return service.findBy(CountryCode.from(countryCode));
    }
}
