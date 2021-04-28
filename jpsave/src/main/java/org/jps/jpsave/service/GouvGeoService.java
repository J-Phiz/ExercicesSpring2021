package org.jps.jpsave.service;

import org.jps.jpsave.entity.GouvGeo;
import org.jps.jpsave.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;
import java.util.Objects;

@Service
public class GouvGeoService {
    private static final String addr = "https://geo.api.gouv.fr/communes?format=json&geometry=centre";
    private static final String format = "format=json";
    private static final String geometry = "geometry=centre";
    private static final String fields = "fields=nom,code,codespostaux,codeDepartement,codeRegion,population";


    private String getUrl(double lat, double lon) {
        return addr + "&" + format + "&" + geometry + "&" + fields + "&" +
                "lat=" + String.format(Locale.US, "%.5f", lat) + "&" +
                "lon=" + String.format(Locale.US, "%.5f", lon);
    }

    public String findCity(double lat, double lon) throws NotFoundException {
        String ret = null;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GouvGeo[]> response = restTemplate.getForEntity(getUrl(lat, lon), GouvGeo[].class);
        if (response.getStatusCode() == HttpStatus.OK && response.hasBody() && response.getBody().length > 0) {
            ret = response.getBody()[0].getNom();
        } else {
            throw new NotFoundException("City at lat:" + lat + " and lon:" + lon + " is not found");
        }

        return ret;
    }
}
