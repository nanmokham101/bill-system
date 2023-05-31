package com.coding.assignment.common;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponse<T> {

    private String message;
    private Object result;


    public ApiResponse(String message, Object result) {
        this.message = message;
        this.result = result;
    }

    public ApiResponse(String message) {
        this.message = message;
    }


    public static ApiResponse create() {
    	ApiResponse response = new ApiResponse();
    	response.message = Constant.SUCCESS;
    	return response;
    	
    }
}
