package com.Security.Authify.io;

import com.Security.Authify.entity.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApprovalResponse {
    private Long id;
    private String name;
    private String email;
    private AccountStatus status;
}
