package com.test.student.service.serviceImpl;

import com.test.student.Exception.BadRequestException;
import com.test.student.Exception.ResourceNotFoundException;
import com.test.student.common.ApiResponse;
import com.test.student.common.Constant;
import com.test.student.dto.SearchStudentDTO;
import com.test.student.dto.StudentDTO;
import com.test.student.models.Student;
import com.test.student.repository.StudentRepository;
import com.test.student.service.StudentService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    private static Logger logger = Logger.getLogger(StudentServiceImpl.class);

    @Override
    public ApiResponse<StudentDTO> createOrUpdate(StudentDTO studentDto) throws BadRequestException {
        Student student = new Student();
        Optional<Student> optionalStudent = Optional.ofNullable(studentDto.getId()).isPresent() ? studentRepository.findById(studentDto.getId()) : Optional.empty();
        if(optionalStudent.isPresent() ) {
            student = optionalStudent.get();
            BeanUtils.copyProperties(studentDto, student);
            student = studentRepository.save(student);

        } else {
            boolean studentExit = studentRepository.existsByEmail(studentDto.getEmail());
            if(studentExit) {
                throw new BadRequestException(Constant.STUDENT_ALREADY_EXISTS);
            }
            BeanUtils.copyProperties(studentDto, student);
            student = studentRepository.save(student);
            BeanUtils.copyProperties(student, studentDto);
        }
        logger.info("studentDto:"+studentDto);
        return new ApiResponse<StudentDTO>(Constant.OK, Constant.SUCCESS, studentDto);
    }

    @Override
    public ApiResponse<List<StudentDTO>> findAll(SearchStudentDTO searchStudentDTO,int pageNo,int pageSize) {
        List<Student> studentList = studentRepository.findAll(new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new LinkedList<>();

                if (StringUtils.isNotBlank(searchStudentDTO.getName())) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchStudentDTO.getName().toLowerCase() + "%"));
                }
                if (StringUtils.isNotBlank(searchStudentDTO.getFatherName())) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("fatherName")), "%" + searchStudentDTO.getFatherName().toLowerCase() + "%"));
                }
                if (StringUtils.isNotBlank(searchStudentDTO.getAddress())) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), "%" + searchStudentDTO.getAddress().toLowerCase() + "%"));
                }
                if (StringUtils.isNotBlank(searchStudentDTO.getEmail())) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + searchStudentDTO.getEmail().toLowerCase() + "%"+"r"));
                }

                return query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))).distinct(true).getRestriction();

            }
        });

        List<StudentDTO> studentDTOList = new ArrayList<StudentDTO>();
        for(Student student : studentList) {
            StudentDTO studentDTO = new StudentDTO();
            BeanUtils.copyProperties(student, studentDTO);
            studentDTOList.add(studentDTO);
        }
            List<StudentDTO> resultStudentDTOList = new ArrayList<StudentDTO>();
            int lastIndex = (studentDTOList.size() - 1) - (pageNo * pageSize - 10);
            int substract = lastIndex < (pageSize-1) ? lastIndex : (pageSize-1);
            int startIndex = lastIndex - substract;

            for (int i = lastIndex; i >= startIndex; i--) {
                StudentDTO studentDTO = studentDTOList.get(i);
                resultStudentDTOList.add(studentDTO);
            }
        logger.info("resultStudentDTOList:"+resultStudentDTOList);
        return new ApiResponse<List<StudentDTO>>(Constant.OK, Constant.SUCCESS, resultStudentDTOList);
    }

    @Override
    public ApiResponse<List<StudentDTO>> getALL(SearchStudentDTO searchStudentDTO) {
        List<Student> studentList = studentRepository.findAll(new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new LinkedList<>();

                if (StringUtils.isNotBlank(searchStudentDTO.getName())) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchStudentDTO.getName().toLowerCase() + "%"));
                }
                if (StringUtils.isNotBlank(searchStudentDTO.getFatherName())) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("fatherName")), "%" + searchStudentDTO.getFatherName().toLowerCase() + "%"));
                }
                if (StringUtils.isNotBlank(searchStudentDTO.getAddress())) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), "%" + searchStudentDTO.getAddress().toLowerCase() + "%"));
                }
                if (StringUtils.isNotBlank(searchStudentDTO.getEmail())) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + searchStudentDTO.getEmail().toLowerCase() + "%" + "r"));
                }

                return query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))).distinct(true).getRestriction();

            }
        });
        return new ApiResponse<List<StudentDTO>>(Constant.OK, Constant.SUCCESS, studentList);
    }


    @Override
    public ApiResponse<Boolean> deleteById(Long id) throws ResourceNotFoundException {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        logger.info("optionalStudent:"+optionalStudent);

        if(optionalStudent.isPresent()) {
            studentRepository.delete(optionalStudent.get());
        } else {
            throw new ResourceNotFoundException(Constant.STUDENT_NOT_FOUND);
        }
        return new ApiResponse<Boolean>(Constant.OK, Constant.DELETE_SUCCESS, true);
    }

    @Override
    public ApiResponse<StudentDTO> findById(Long id) throws ResourceNotFoundException {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        StudentDTO studentDTO = new StudentDTO();
        if(optionalStudent.isPresent() ) {
            BeanUtils.copyProperties(optionalStudent.get(), studentDTO);
        }else {
            throw new ResourceNotFoundException(Constant.STUDENT_NOT_FOUND);
        }
        return new ApiResponse<StudentDTO>(Constant.OK, Constant.SUCCESS, studentDTO);
    }
    @Scheduled(cron="* * */1 * * *")
    public void callMethodEveryHour(){
        logger.info("Run every hour");
    }
}
