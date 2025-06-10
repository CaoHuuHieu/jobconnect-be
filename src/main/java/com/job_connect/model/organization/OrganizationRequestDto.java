package com.job_connect.model.organization;

import com.job_connect.model.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrganizationRequestDto extends PageRequest {

    private String name;

}
