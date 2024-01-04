package com.alexgiou.accounts.mapper;

import com.alexgiou.accounts.dto.AccountsDto;
import com.alexgiou.accounts.entity.Accounts;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountsMapper {

    AccountsDto mapToAccountsDto(Accounts accounts);


    Accounts mapToAccounts(AccountsDto accountsDto);
}
