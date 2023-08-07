package vn.cmax.cafe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.cmax.cafe.api.models.Role;
import vn.cmax.cafe.user.UserRole;

import java.util.List;
import java.util.Set;

@Mapper
public interface RoleMapper {
    public static final RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Role toApiRole(UserRole userRole);

    UserRole toEntityRole(Role role);

    List<Role> toApiRoles(Set<UserRole> userRoles);
    Set<UserRole> toEntityRoles(List<Role> roles);
}
