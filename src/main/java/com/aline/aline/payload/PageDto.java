package com.aline.aline.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {
    private int pageNumber = 0;
    private int pageSize = 10;
    private String sortBy = "id";
    private String sortDir = "DES";
}
