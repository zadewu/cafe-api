package vn.cmax.cafe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;
import vn.cmax.cafe.api.models.User;
import vn.cmax.cafe.user.UserEntity;

@Mapper(uses = RoleMapper.class)
public interface UserMapper {
    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User fromEntity(UserEntity userEntity);

    @ValueMapping(source = MappingConstants.NULL, target = "USER")
    UserEntity fromUser(User user);


}
