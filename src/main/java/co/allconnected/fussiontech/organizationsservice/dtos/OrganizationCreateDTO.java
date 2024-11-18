package co.allconnected.fussiontech.organizationsservice.dtos;

import java.math.BigDecimal;


public record OrganizationCreateDTO(String name, String address, BigDecimal location_lat, BigDecimal location_lng) {

}
