package nu.hack.bankcard.mapper;

import nu.hack.bank.mapper.BankMapper;
import nu.hack.bankcard.dto.BankCardCreateRequest;
import nu.hack.bankcard.dto.BankCardResponse;
import nu.hack.bankcard.entity.BankCardEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true), uses = BankMapper.class)
public interface BankCardMapper {

    BankCardMapper INSTANCE = Mappers.getMapper(BankCardMapper.class);

    BankCardEntity toEntity(BankCardCreateRequest request);

    BankCardResponse toResponse(BankCardEntity entity);
}
