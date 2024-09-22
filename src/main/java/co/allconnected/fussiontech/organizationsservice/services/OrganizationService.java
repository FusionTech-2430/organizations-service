package co.allconnected.fussiontech.organizationsservice.services;

import co.allconnected.fussiontech.organizationsservice.dtos.OrganizationCreateDTO;
import co.allconnected.fussiontech.organizationsservice.dtos.OrganizationDTO;
import co.allconnected.fussiontech.organizationsservice.model.Organization;
import co.allconnected.fussiontech.organizationsservice.repository.OrganizationRepository;
import co.allconnected.fussiontech.organizationsservice.utils.OperationException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final FirebaseService firebaseService;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository, FirebaseService firebaseService) {
        this.organizationRepository = organizationRepository;
        this.firebaseService = firebaseService;
    }

    public OrganizationDTO createOrganization(OrganizationCreateDTO organizationDTO, MultipartFile photo) throws IOException {
        System.out.println("Address from DTO: " + organizationDTO.address()); // Validar el valor de address

        Organization organization = new Organization(organizationDTO);
        organization.setId(UUID.randomUUID());

        // Manejando el archivo de foto
        if (photo != null && !photo.isEmpty()) {
            String photoName = organization.getId().toString();
            String extension = FilenameUtils.getExtension(photo.getOriginalFilename());
            organization.setPhotoUrl(firebaseService.uploadImg(photoName, extension, photo));
        }

        return new OrganizationDTO(organizationRepository.save(organization));
    }

    public OrganizationDTO updateOrganization(String id, OrganizationCreateDTO organizationDTO, MultipartFile photo) throws IOException {
        Optional <Organization> organizationOptional = organizationRepository.findById(UUID.fromString(id));
        if (organizationOptional.isPresent()) {
            Organization organization = organizationOptional.get();
            organization.setName(organizationDTO.name());
            organization.setAddress(organizationDTO.address());
            organization.setLocationLat(organizationDTO.location_lat());
            organization.setLocationLng(organizationDTO.location_lng());

            if (photo != null && !photo.isEmpty()) {
                if (organization.getPhotoUrl() != null) {
                    firebaseService.deleteImg(organization.getId().toString());
                }
                String photoName = organization.getId().toString();
                String extension = FilenameUtils.getExtension(photo.getOriginalFilename());
                organization.setPhotoUrl(firebaseService.uploadImg(photoName, extension, photo));
            }

            return new OrganizationDTO(organizationRepository.save(organization));
        }
        else{
            throw new OperationException(404, "Organization not found");
        }
    }


    public OrganizationDTO getOrganization (String id) {
        return organizationRepository.findById(UUID.fromString(id))
                .map(OrganizationDTO::new)
                .orElseThrow(() -> new OperationException(404, "Organization not found"));
    }

    public OrganizationDTO [] getOrganizations() {
        return organizationRepository.findAll().stream().map(OrganizationDTO::new).toArray(OrganizationDTO[]::new);
    }

    public void deleteOrganization(String id) {
        Optional <Organization> organizationOptional = organizationRepository.findById(UUID.fromString(id));
        if (organizationOptional.isPresent()) {
            Organization organization = organizationOptional.get();


            if (organization.getPhotoUrl() != null) {
                firebaseService.deleteImg(organization.getId().toString());
            }

            organizationRepository.delete(organization);
        }
        else{
            throw new OperationException(404, "Organization not found");
        }
    }
}
