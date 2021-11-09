package com.nashtech.minhtran.gearshop.dto.payload.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse {
    private int currentPage;
    private List<?> content;
    private long totalPages;
    private long totalElements;
}
