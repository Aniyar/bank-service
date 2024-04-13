package nu.hack.appUser.mapper;

import nu.hack.appUser.dto.AppUserCreateRequest;
import nu.hack.appUser.dto.AppUserResponse;
import nu.hack.appUser.entity.AppUserEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface AppUserMapper {

    AppUserMapper INSTANCE = Mappers.getMapper(AppUserMapper.class);

    AppUserEntity toEntity(AppUserCreateRequest request);

    AppUserResponse toResponse(AppUserEntity entity);
}
