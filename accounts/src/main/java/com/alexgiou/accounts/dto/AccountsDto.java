package com.alexgiou.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Account and Account information"
)
public class AccountsDto {


    @NotEmpty(message = "Account number can not be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digits")
    @Schema(
            description = "Account Number of Bank account", example = "1234567890"
    )
    private Long accountNumber;

    @Schema(
            description = "Account Type of Bank account", example = "Savings"
    )
    @NotEmpty(message = "Account type can not be null or empty")
    private String accountType;

    @Schema(
            description = "Bank branch address", example = "123 New York"
    )
    @NotEmpty(message = "Branch address can not be null or empty")
    private String branchAddress;
}
