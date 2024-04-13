package nu.hack.offer.mapper;

import nu.hack.offer.dto.OfferCreateRequest;
import nu.hack.offer.dto.OfferResponse;
import nu.hack.offer.entity.OfferEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface OfferMapper {

    OfferMapper INSTANCE = Mappers.getMapper(OfferMapper.class);

    OfferEntity toEntity(OfferCreateRequest request);
    
    OfferResponse toResponse(OfferEntity entity);
}
