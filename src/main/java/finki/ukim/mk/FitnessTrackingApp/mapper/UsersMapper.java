package finki.ukim.mk.FitnessTrackingApp.mapper;

import finki.ukim.mk.FitnessTrackingApp.models.Users;
import finki.ukim.mk.FitnessTrackingApp.models.dto.UpsertUserDto;
import finki.ukim.mk.FitnessTrackingApp.models.dto.UsersDto;
import finki.ukim.mk.FitnessTrackingApp.models.enums.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsersMapper {
  UsersDto toUsersDto(Users users);
  List<UsersDto> toUsersDtoList(List<Users> users);

  @Mapping(target = "role", expression = "java(finki.ukim.mk.FitnessTrackingApp.models.enums.Role.USER)")
  Users toUsers(UpsertUserDto dto);

  List<Users> toUsersList(List<UpsertUserDto> dtos);

  @Mapping(target = "email", ignore = true)
  @Mapping(target = "username", ignore = true)
  @Mapping(target = "role", ignore = true)
  @Mapping(target = "password", ignore = true)
  void toEntityEdit(UpsertUserDto dto, @MappingTarget Users users); // Changed return type to `void`
}
