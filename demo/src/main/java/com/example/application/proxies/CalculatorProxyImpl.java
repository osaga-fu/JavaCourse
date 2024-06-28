package com.example.application.proxies;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.example.domains.contracts.proxies.CalculatorProxy;
import com.example.webservice.schema.AddRequest;
import com.example.webservice.schema.AddResponse;
import com.example.webservice.schema.DivRequest;
import com.example.webservice.schema.DivResponse;
import com.example.webservice.schema.MulRequest;
import com.example.webservice.schema.MulResponse;
import com.example.webservice.schema.SubRequest;
import com.example.webservice.schema.SubResponse;

public class CalculatorProxyImpl extends WebServiceGatewaySupport implements CalculatorProxy {
	public double add(double a, double b) {
		var request = new AddRequest();
		request.setOp1(a);
		request.setOp2(b);
		var response = (AddResponse) getWebServiceTemplate().marshalSendAndReceive(request,
				new SoapActionCallback("http://example.com/webservices/schemas/calculator"));
		return response.getAddResult();
	}

	@Override
	public double substract(double a, double b) {
		var request = new SubRequest();
		request.setOp1(a);
		request.setOp2(b);
		var response = (SubResponse) getWebServiceTemplate().marshalSendAndReceive(request,
				new SoapActionCallback("http://example.com/webservices/schemas/calculator"));
		return response.getSubResult();
	}

	@Override
	public double multiply(double a, double b) {
		var request = new MulRequest();
		request.setOp1(a);
		request.setOp2(b);
		var response = (MulResponse) getWebServiceTemplate().marshalSendAndReceive(request,
				new SoapActionCallback("http://example.com/webservices/schemas/calculator"));
		return response.getMulResult();
	}

	@Override
	public double divide(double a, double b) {
		var request = new DivRequest();
		request.setOp1(a);
		request.setOp2(b);
		var response = (DivResponse) getWebServiceTemplate().marshalSendAndReceive(request,
				new SoapActionCallback("http://example.com/webservices/schemas/calculator"));
		return response.getDivResult();
	}

}