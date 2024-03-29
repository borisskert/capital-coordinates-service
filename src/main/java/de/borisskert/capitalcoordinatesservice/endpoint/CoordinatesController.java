package de.borisskert.capitalcoordinatesservice.endpoint;

import de.borisskert.capitalcoordinatesservice.client.CityLocationClient;
import de.borisskert.capitalcoordinatesservice.client.CountryInfoClient;
import de.borisskert.capitalcoordinatesservice.model.CityWithLocation;
import de.borisskert.capitalcoordinatesservice.model.CountryCode;
import de.borisskert.capitalcoordinatesservice.service.CapitalCoordinatesService;
import de.borisskert.capitalcoordinatesservice.validation.ValidCountryCode;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CoordinatesDto> getCoordinates(
            @PathVariable("countryCode") @NotNull @ValidCountryCode String countryCode
    ) {
        try {
            CityWithLocation cityWithLocation = service.findBy(CountryCode.from(countryCode));
            return ResponseEntity.ok(cityWithLocation.toDto());
        } catch (CountryInfoClient.CountryNotFoundException e) {
            return ResponseEntity.badRequest().build();
        } catch (CountryInfoClient.ServiceUnavailableException | CityLocationClient.ServiceUnavailableException e) {
            return ResponseEntity.status(503).build();
        }
    }

    @GetMapping
    @Hidden
    public ResponseEntity<String> requireCountryCode() {
        return ResponseEntity.badRequest()
                .body("Please provide a country code");
    }
}
