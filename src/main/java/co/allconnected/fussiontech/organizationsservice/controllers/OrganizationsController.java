package co.allconnected.fussiontech.organizationsservice.controllers;

import co.allconnected.fussiontech.organizationsservice.dtos.OrganizationCreateDTO;
import co.allconnected.fussiontech.organizationsservice.dtos.OrganizationDTO;
import co.allconnected.fussiontech.organizationsservice.dtos.Response;
import co.allconnected.fussiontech.organizationsservice.services.OrganizationService;
import co.allconnected.fussiontech.organizationsservice.utils.OperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            @RequestParam(value = "photo_url", required = false) MultipartFile photo) {
        try {
            OrganizationDTO organizationDTO = organizationService.createOrganization(organization, photo);
            return ResponseEntity.status(HttpStatus.CREATED).body(organizationDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrganization(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(organizationService.getOrganization(id));
        } catch (OperationException e) {
            return ResponseEntity.status(e.getCode()).body(new Response(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error occurred: " + e.getMessage()));
        }
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrganization(
            @PathVariable String id,
            @ModelAttribute OrganizationCreateDTO organization,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(organizationService.updateOrganization(id, organization, photo));
        } catch (OperationException e) {
            return ResponseEntity.status(e.getCode()).body(new Response(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error occurred: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrganization(@PathVariable String id) {
        try {
            organizationService.deleteOrganization(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Response(HttpStatus.OK.value(), "Organization deleted"));
        } catch (OperationException e) {
            return ResponseEntity.status(e.getCode()).body(new Response(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error occurred: " + e.getMessage()));
        }
    }

    // Endpoints for user-organization relationship
    @PostMapping("/{id_organization}/users/{id_user}")
    public ResponseEntity<?> assignUserToOrganization(
            @PathVariable("id_organization") String idOrganization,
            @PathVariable("id_user") String idUser
    ) {
        try {
            organizationService.assignUserToOrganization(idOrganization, idUser);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("User assigned to organization successfully.");
        } catch (OperationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id_organization}/users_d/{id_user}")
    public ResponseEntity<?> removeUserFromOrganization(
            @PathVariable("id_organization") String idOrganization,
            @PathVariable("id_user") String idUser
    ) {
        try {
            organizationService.removeUserFromOrganization(idOrganization, idUser);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("User removed from organization successfully.");
        } catch (OperationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + e.getMessage());
        }
    }
}