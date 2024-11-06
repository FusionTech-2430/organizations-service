package co.allconnected.fussiontech.organizationsservice.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private String id_user;
    private String fullname;
    private String username;
    private String mail;
    private String photo_url;
    private String[] roles;
    private String[] organizations;
    private boolean active;
}