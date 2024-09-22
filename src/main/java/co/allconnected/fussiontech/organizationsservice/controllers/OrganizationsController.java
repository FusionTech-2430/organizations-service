package co.allconnected.fussiontech.organizationsservice.controllers;

import co.allconnected.fussiontech.organizationsservice.dtos.OrganizationCreateDTO;
import co.allconnected.fussiontech.organizationsservice.dtos.OrganizationDTO;
import co.allconnected.fussiontech.organizationsservice.dtos.Response;
import co.allconnected.fussiontech.organizationsservice.model.Organization;
import co.allconnected.fussiontech.organizationsservice.services.FirebaseService;
import co.allconnected.fussiontech.organizationsservice.services.OrganizationService;
import co.allconnected.fussiontech.organizationsservice.utils.OperationException;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organizations")
public class OrganizationsController {

    private final OrganizationService organizationService;

    @Autowired
    public OrganizationsController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping
    public ResponseEntity<OrganizationDTO> createOrganization(
            @ModelAttribute OrganizationCreateDTO organization,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {
        try {
            OrganizationDTO organizationDTO = organizationService.createOrganization(organization, photo);
            return ResponseEntity.status(HttpStatus.CREATED).body(organizationDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrganization(@PathVariable String id) {
        try {
            organizationService.deleteOrganization(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Response(HttpStatus.OK.value(), "Organization deleted"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error occurred: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrganization(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(organizationService.getOrganization(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error occurred: " + e.getMessage()));
        }
    }

    // Get all organizations
    @GetMapping
    public ResponseEntity<?> getOrganizations() {
        try {
            OrganizationDTO[] listOrganizationsDTO = organizationService.getOrganizations();
            if (listOrganizationsDTO.length == 0)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(HttpStatus.NOT_FOUND.value(), "No organizations found"));
            return ResponseEntity.status(HttpStatus.OK).body(listOrganizationsDTO);
        } catch (OperationException e) {
            return ResponseEntity.status(e.getCode()).body(new Response(e.getCode(), e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

}
