package com.aline.aline.utilities;

import com.aline.aline.payload.PageDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtils {
    public static Pageable getPageableFromPageDto (PageDto pageDto){
        Sort sort = null;
        if(pageDto.getSortDir().equalsIgnoreCase("asc")){
            sort = Sort.by(pageDto.getSortBy()).ascending();
        }
        else{
            sort = Sort.by(pageDto.getSortBy()).descending();
        }

        return PageRequest.of(pageDto.getPageNumber(), pageDto.getPageSize(), sort );
    }
}
