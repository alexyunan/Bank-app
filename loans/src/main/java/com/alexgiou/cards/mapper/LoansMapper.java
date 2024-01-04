package com.alexgiou.cards.mapper;


import com.alexgiou.cards.dto.LoansDto;
import com.alexgiou.cards.entity.Loans;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoansMapper {

    LoansDto mapToLoansDto(Loans loans);


    Loans mapToLoans(LoansDto loansDto);
}
