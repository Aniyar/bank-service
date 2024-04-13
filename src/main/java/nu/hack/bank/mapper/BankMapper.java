package nu.hack.bank.mapper;

import nu.hack.bank.dto.BankCreateRequest;
import nu.hack.bank.dto.BankResponse;
import nu.hack.bank.entity.BankEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface BankMapper {

    BankMapper INSTANCE = Mappers.getMapper(BankMapper.class);

    BankEntity toEntity(BankCreateRequest request);

    BankEntity toEntity(BankCreateRequest request, @MappingTarget BankEntity entity);

    BankResponse toResponse(BankEntity entity);
}
