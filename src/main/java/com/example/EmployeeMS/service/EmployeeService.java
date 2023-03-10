package com.example.EmployeeMS.service;

import com.example.EmployeeMS.dto.EmployeeDTO;
import com.example.EmployeeMS.entity.Employee;
import com.example.EmployeeMS.repository.EmployeeRepo;
import com.example.EmployeeMS.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private ModelMapper modelMapper;

    public String saveEmployee(EmployeeDTO employeeDTO){
        if (employeeRepo.existsById(employeeDTO.getEmpId())){
            return VarList.RSP_DUPLICATED;
        }
        else{
            employeeRepo.save(modelMapper.map(employeeDTO, Employee.class));
            return VarList.RSP_SUCCESS;
        }
    }

    public String updateEmployee(EmployeeDTO employeeDTO){
        if(employeeRepo.existsById(employeeDTO.getEmpId())){
            employeeRepo.save(modelMapper.map(employeeDTO, Employee.class));
            return  VarList.RSP_SUCCESS;

        }else{
            return VarList.RSP_NO_DATA_FOUND;

        }
    }

    public List<EmployeeDTO> getAllEmployees(){
        List<Employee> employeeList=employeeRepo.findAll();
        return modelMapper.map(employeeList,new TypeToken<ArrayList<EmployeeDTO>>(){}
                .getType());
    }

    public EmployeeDTO searchEmployee(int empId){
        if (employeeRepo.existsById(empId)){
            Optional<Employee> employee=employeeRepo.findById(empId);
            return modelMapper.map(employee,EmployeeDTO.class);
        }
        else{
            return null;

        }
    }

    public String deleteEmployee(int empId){
        if (employeeRepo.existsById(empId)){
           employeeRepo.deleteById(empId);
           return VarList.RSP_SUCCESS;

        }else {
            return VarList.RSP_NO_DATA_FOUND;
        }
    }
}
