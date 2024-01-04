package com.alexgiou.cards.mapper;


import com.alexgiou.cards.dto.CardsDto;
import com.alexgiou.cards.entity.Cards;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CardsMapper {

    CardsDto mapToCardsDto(Cards cards);


    Cards mapToCards(CardsDto cardsDto);
}
