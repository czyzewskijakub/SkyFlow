package pl.ioad.skyflow.logic.user.dto;


import pl.ioad.skyflow.database.model.User;

public class Mapper {
    public UserDto mapUser(User user) {
        return new UserDto.UserDtoBuilder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .isAdmin(user.isAdmin())
                .profilePictureUrl(user.getProfilePictureUrl())
                .build();
    }
}
