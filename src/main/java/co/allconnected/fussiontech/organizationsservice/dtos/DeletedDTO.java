package co.allconnected.fussiontech.organizationsservice.dtos;

import java.io.Serializable;
import java.time.Instant;

public record DeletedDTO(String id_user, String id_organization, Instant delete_date) implements Serializable {
}