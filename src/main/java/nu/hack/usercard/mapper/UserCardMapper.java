package nu.hack.usercard.mapper;

import nu.hack.bankcard.mapper.BankCardMapper;
import nu.hack.usercard.dto.UserCardCreateRequest;
import nu.hack.usercard.dto.UserCardResponse;
import nu.hack.usercard.entity.UserCardEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true), uses = BankCardMapper.class)
public interface UserCardMapper {

    UserCardMapper INSTANCE = Mappers.getMapper(UserCardMapper.class);

    UserCardEntity toEntity(UserCardCreateRequest request);

    UserCardResponse toResponse(UserCardEntity entity);
}
