package com.test.student.controllers;

import com.test.student.Exception.BadRequestException;
import com.test.student.Exception.ResourceNotFoundException;
import com.test.student.common.ApiResponse;
import com.test.student.dto.SearchStudentDTO;
import com.test.student.dto.StudentDTO;
import com.test.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student/api/v1")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDTO>> getStudentById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        return new ResponseEntity<ApiResponse<StudentDTO>>(studentService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    ResponseEntity<ApiResponse<List<StudentDTO>>> filter(@RequestParam int pageNo, @RequestParam int pageSize,SearchStudentDTO searchStudentDTO) {
        return new ResponseEntity<ApiResponse<List<StudentDTO>>>(studentService.findAll(searchStudentDTO,pageNo,pageSize), HttpStatus.OK);
    }
    @GetMapping("/list")
    ResponseEntity<ApiResponse<List<StudentDTO>>> filter(SearchStudentDTO searchStudentDTO) {
        return new ResponseEntity<ApiResponse<List<StudentDTO>>>(studentService.getALL(searchStudentDTO), HttpStatus.OK);
    }
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<StudentDTO>> createOrUpdate(@RequestBody @Validated StudentDTO studentDTO) throws BadRequestException {
        return new ResponseEntity<ApiResponse<StudentDTO>>(studentService.createOrUpdate(studentDTO), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return new ResponseEntity<ApiResponse<Boolean>>(studentService.deleteById(id), HttpStatus.OK);
    }

}
