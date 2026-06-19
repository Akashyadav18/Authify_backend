package com.Security.Authify.service;

import com.Security.Authify.entity.Staff;
import com.Security.Authify.io.cursorPageResponse;
import com.Security.Authify.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService{

    private final StaffRepository staffRepository;

    @Override
    public cursorPageResponse<Staff> getAllStaffs(Long cursor, int size) {

//        default page=0, size=10
        Pageable pageable = PageRequest.of(0, size);
//        fetch next page record
        List<Staff> staffList = staffRepository.fetchNextPage(cursor, pageable);
//        check is have more records
        boolean hasNext = staffList.size() == size;
         Long nextCursor = hasNext ? staffList.get(size - 1).getId() : null;

         return new cursorPageResponse<>(staffList, size, nextCursor, hasNext);
    }
}
