package com.uexcel.library.service;

import com.uexcel.library.dto.PasswordChangeDto;
import com.uexcel.library.dto.ResponseDto;

public interface IPasswordChangeService {
    ResponseDto passwordChangeAdmin(PasswordChangeDto passwordChangeDto,String email);
    ResponseDto passwordChangeEmployee(PasswordChangeDto passwordChangeDto);
}
