package com.test.student.service;

import com.test.student.Exception.BadRequestException;
import com.test.student.Exception.ResourceNotFoundException;
import com.test.student.common.ApiResponse;
import com.test.student.dto.SearchStudentDTO;
import com.test.student.dto.StudentDTO;

import java.util.List;

public interface StudentService {
    ApiResponse<StudentDTO> createOrUpdate(StudentDTO studentDto) throws BadRequestException;

    ApiResponse<List<StudentDTO>> findAll(SearchStudentDTO searchStudentDTO,int pageNo, int pageSize);

    ApiResponse<List<StudentDTO>> getALL(SearchStudentDTO searchStudentDTO);

    ApiResponse<Boolean> deleteById(Long id) throws ResourceNotFoundException;

    ApiResponse<StudentDTO> findById(Long id) throws ResourceNotFoundException;
}
