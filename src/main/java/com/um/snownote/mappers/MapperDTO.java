package com.um.snownote.mappers;

import com.um.snownote.dto.UserDTO;
import com.um.snownote.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapperDTO {

    MapperDTO INSTANCE = Mappers.getMapper(MapperDTO.class);
    //TODO: Terminar el formateo
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    UserDTO userToUserDTO(User user);

}
