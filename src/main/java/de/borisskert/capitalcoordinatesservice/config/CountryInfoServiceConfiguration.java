package de.borisskert.capitalcoordinatesservice.config;

import de.borisskert.capitalcoordinatesservice.client.CountryInfoClient;
import org.oorsprong.webservices.websamples.countryinfo.CountryInfoService;
import org.oorsprong.webservices.websamples.countryinfo.CountryInfoServiceSoapType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountryInfoServiceConfiguration {

    @Bean
    public CountryInfoService countryInfoService() {
        return new CountryInfoService();
    }

    @Bean
    public CountryInfoServiceSoapType countryInfoServiceSoapType(CountryInfoService countryInfoService) {
        return countryInfoService.getCountryInfoServiceSoap12();
    }
}
