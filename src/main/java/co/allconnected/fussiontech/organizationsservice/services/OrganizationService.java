package co.allconnected.fussiontech.organizationsservice.services;

import co.allconnected.fussiontech.organizationsservice.dtos.OrganizationCreateDTO;
import co.allconnected.fussiontech.organizationsservice.dtos.OrganizationDTO;
import co.allconnected.fussiontech.organizationsservice.model.Organization;
import co.allconnected.fussiontech.organizationsservice.model.User;
import co.allconnected.fussiontech.organizationsservice.model.UserOrganization;
import co.allconnected.fussiontech.organizationsservice.repository.OrganizationRepository;
import co.allconnected.fussiontech.organizationsservice.repository.UserOrganizationRepository;
import co.allconnected.fussiontech.organizationsservice.repository.UserRepository;
import co.allconnected.fussiontech.organizationsservice.utils.OperationException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final FirebaseService firebaseService;
    private final UserRepository userRepository;
    private final UserOrganizationRepository userOrganizationRepository;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository, FirebaseService firebaseService, UserRepository userRepository, UserOrganizationRepository userOrganizationRepository) {
        this.organizationRepository = organizationRepository;
        this.firebaseService = firebaseService;
        this.userRepository = userRepository;
        this.userOrganizationRepository = userOrganizationRepository;
    }

    public OrganizationDTO createOrganization(OrganizationCreateDTO organizationDTO, MultipartFile photo) throws IOException {
        System.out.println("Address from DTO: " + organizationDTO.address()); // Validar el valor de address

        Organization organization = new Organization(organizationDTO);
        organization.setId(UUID.randomUUID());

        if (photo != null && !photo.isEmpty()) {
            String photoName = String.valueOf(organization.getId());
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

    public OrganizationDTO [] getAllOrganizationsFromAnUser (String idUser) {
        return userRepository.findById(idUser)
                .map(user -> user.getUserOrganizations().stream().map(UserOrganization::getOrganization).map(OrganizationDTO::new).toArray(OrganizationDTO[]::new))
                .orElseThrow(() -> new OperationException(404, "User not found"));
    }

    public void deleteOrganization(String id) {
        Optional <Organization> organizationOptional = organizationRepository.findById(UUID.fromString(id));
        if (organizationOptional.isPresent()) {
            Organization organization = organizationOptional.get();

            // Delete from the database the relationship between the organization and the users
            userOrganizationRepository.deleteAll(organization.getUserOrganizations());
            userOrganizationRepository.flush();

            if (organization.getPhotoUrl() != null) {
                firebaseService.deleteImg(organization.getId().toString());
            }

            organizationRepository.delete(organization);
        }
        else{
            throw new OperationException(404, "Organization not found");
        }
    }

    public void assignUserToOrganization(String idOrganization, String idUser) {
        Optional<User> userOptional = userRepository.findById(idUser);
        Optional<Organization> organizationOptional = organizationRepository.findById(UUID.fromString(idOrganization));

        if (userOptional.isPresent() && organizationOptional.isPresent()) {
            User user = userOptional.get();
            Organization organization = organizationOptional.get();

            boolean relationshipExists = user.getUserOrganizations().stream()
                    .anyMatch(uo -> uo.getOrganization().getId().equals(organization.getId()));

            if (!relationshipExists) {
                UserOrganization userOrganization = new UserOrganization(user, organization, LocalDateTime.now());
                user.getUserOrganizations().add(userOrganization);
                userRepository.save(user);
            } else {
                throw new OperationException(409, "User already assigned to organization");
            }
        } else {
            throw new OperationException(404, "User or Organization not found");
        }
    }

    public void removeUserFromOrganization(String idOrganization, String idUser) {
        Optional<User> userOptional = userRepository.findById(idUser);
        Optional<Organization> organizationOptional = organizationRepository.findById(UUID.fromString(idOrganization));

        if (userOptional.isPresent() && organizationOptional.isPresent()) {
            User user = userOptional.get();
            Organization organization = organizationOptional.get();

            Optional<UserOrganization> userOrganizationOptional = user.getUserOrganizations().stream()
                    .filter(uo -> uo.getOrganization().getId().equals(organization.getId()))
                    .findFirst();

            if (userOrganizationOptional.isPresent()) {
                UserOrganization userOrganization = userOrganizationOptional.get();
                user.getUserOrganizations().remove(userOrganization);
                organization.getUserOrganizations().remove(userOrganization);
                userOrganizationRepository.delete(userOrganization);
            } else {
                throw new OperationException(409, "User not assigned to organization");
            }
        } else {
            throw new OperationException(404, "User or Organization not found");
        }
    }

}
