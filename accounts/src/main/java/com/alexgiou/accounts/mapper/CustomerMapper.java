package com.alexgiou.accounts.mapper;

import com.alexgiou.accounts.dto.CustomerDto;
import com.alexgiou.accounts.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {


    CustomerDto mapToCustomerDto(Customer customer);


    Customer mapToCustomer(CustomerDto customerDto);
}
