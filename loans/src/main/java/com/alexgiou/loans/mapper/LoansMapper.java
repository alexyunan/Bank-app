package com.alexgiou.loans.mapper;


import com.alexgiou.loans.dto.LoansDto;
import com.alexgiou.loans.entity.Loans;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoansMapper {

    LoansDto mapToLoansDto(Loans loans);


    Loans mapToLoans(LoansDto loansDto);
}
