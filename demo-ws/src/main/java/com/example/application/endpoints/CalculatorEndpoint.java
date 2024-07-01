package com.example.application.endpoints;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.SoapFaultException;

import com.example.webservices.schemas.calculator.AddRequest;
import com.example.webservices.schemas.calculator.AddResponse;
import com.example.webservices.schemas.calculator.DivRequest;
import com.example.webservices.schemas.calculator.DivResponse;
import com.example.webservices.schemas.calculator.MulRequest;
import com.example.webservices.schemas.calculator.MulResponse;
import com.example.webservices.schemas.calculator.SubRequest;
import com.example.webservices.schemas.calculator.SubResponse;

@Endpoint
public class CalculatorEndpoint {
	private static final String NAMESPACE_URI = "http://example.com/webservices/schemas/calculator";

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addRequest")
	@ResponsePayload
	public AddResponse add(@RequestPayload AddRequest request) {
		var result = new AddResponse();
		result.setAddResult(request.getOp1() + request.getOp2());
		return result;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "subRequest")
	@ResponsePayload
	public SubResponse sub(@RequestPayload SubRequest request) {
		var result = new SubResponse();
		result.setSubResult(round(request.getOp1() - request.getOp2()));
		return result;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "mulRequest")
	@ResponsePayload
	public MulResponse mul(@RequestPayload MulRequest request) {
		var result = new MulResponse();
		result.setMulResult(round(request.getOp1() * request.getOp2()));
		return result;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "divRequest")
	@ResponsePayload
	public DivResponse div(@RequestPayload DivRequest request) {
		if(request.getOp2() == 0)
			throw new SoapFaultException("Division by 0", new ArithmeticException("/ by zero")); //IllegalArgumentException();
		var result = new DivResponse();
		result.setDivResult(round(request.getOp1() / request.getOp2()));
		return result;
	}
	
	private double round(double o) {
		return (new BigDecimal(o)).setScale(16, RoundingMode.HALF_UP).doubleValue();
	}
	
}
