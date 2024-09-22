package co.allconnected.fussiontech.organizationsservice.controllers;

import co.allconnected.fussiontech.organizationsservice.dtos.OrganizationCreateDTO;
import co.allconnected.fussiontech.organizationsservice.dtos.OrganizationDTO;
import co.allconnected.fussiontech.organizationsservice.dtos.Response;
import co.allconnected.fussiontech.organizationsservice.model.Organization;
import co.allconnected.fussiontech.organizationsservice.services.OrganizationService;
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
                    .body(null); // En caso de error, devolver una respuesta vacía
        }
    }



}
