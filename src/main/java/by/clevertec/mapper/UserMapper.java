package by.clevertec.mapper;

import by.clevertec.domain.User;
import by.clevertec.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toDomain(UserEntity user);
    List<User> toDomains(List<UserEntity> users);
    UserEntity toEntity(User user);
}
