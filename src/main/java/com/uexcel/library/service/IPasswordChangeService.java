package com.uexcel.library.service;

import com.uexcel.library.dto.AdminPasswordChangeDto;
import com.uexcel.library.dto.PasswordChangeDto;
import com.uexcel.library.dto.ResponseDto;

public interface IPasswordChangeService {
    ResponseDto passwordChangeAdmin(AdminPasswordChangeDto adminPasswordChangeDto);
    ResponseDto passwordChangeEmployee(PasswordChangeDto passwordChangeDto);
}
