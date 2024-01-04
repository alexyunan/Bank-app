package com.alexgiou.accounts.service.impl;

import com.alexgiou.accounts.dto.AccountsDto;
import com.alexgiou.accounts.dto.CustomerDto;
import com.alexgiou.accounts.entity.Accounts;
import com.alexgiou.accounts.entity.Customer;
import com.alexgiou.accounts.exception.CustomerAlreadyExistsException;
import com.alexgiou.accounts.exception.ResourceNotFoundException;
import com.alexgiou.accounts.mapper.AccountsMapper;
import com.alexgiou.accounts.mapper.CustomerMapper;
import com.alexgiou.accounts.repository.AccountsRepository;
import com.alexgiou.accounts.repository.CustomerRepository;
import com.alexgiou.accounts.service.IAccountService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import static com.alexgiou.accounts.constants.AccountsConstants.ADDRESS;
import static com.alexgiou.accounts.constants.AccountsConstants.SAVINGS;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;
    private AccountsMapper accountsMapper;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = customerMapper.mapToCustomer(customerDto);

        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    + customerDto.getMobileNumber());
        }

        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "mobileNumber", customer.getCustomerId().toString())
        );

        CustomerDto customerDto = customerMapper.mapToCustomerDto(customer);
        customerDto.setAccountsDto(accountsMapper.mapToAccountsDto(accounts));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null) {
            // Step 1: Retrieve the accounts information from the repository based on the account number
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            // Step 2: Update the accounts entity with the information from the DTO
            accounts.setAccountNumber(accountsDto.getAccountNumber());
            accounts.setAccountType(accountsDto.getAccountType());
            accounts.setBranchAddress(accountsDto.getBranchAddress());

            // Step 3: Save the updated accounts entity back to the repository
            accounts = accountsRepository.save(accounts);

            // Step 4: Retrieve the customer information based on the customer ID associated with the accounts
            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            // Step 5: Update the customer entity with the information from the customer DTO
            customer.setName(customerDto.getName());
            customer.setEmail(customerDto.getEmail());
            customer.setMobileNumber(customerDto.getMobileNumber());

            // Step 6: Save the updated customer entity back to the repository
            customerRepository.save(customer);

            // Step 7: Set the flag to indicate that the update was successful
            isUpdated = true;
        }

        // Step 8: Return the boolean flag indicating whether the update was successful or not
        return isUpdated;
    }

    @Override
    @Transactional
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }


    private Accounts createNewAccount(Customer customer) {
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(SAVINGS);
        newAccount.setBranchAddress(ADDRESS);
       

        return newAccount;
    }
}
