package com.Security.Authify.service;

import com.Security.Authify.entity.Staff;
import com.Security.Authify.io.cursorPageResponse;


public interface StaffService {

    cursorPageResponse<Staff> getAllStaffs(Long cursor, int size);
}
