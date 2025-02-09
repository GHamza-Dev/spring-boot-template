package com.warya.base.application.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchRequest {
    private String name;

    private Integer page = 0;
    private Integer size = 20;

    @Pattern(regexp = "id")
    private String sortBy = "id";

    @Pattern(regexp = "desc|asc")
    private String sortDirection = "desc";
}