package com.jaenyeong.springboot_started.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExceptionController {

	@GetMapping("/exception/hello")
	public String hello() {
		throw new SampleException();
	}

	// 기존 예제에서 XML 메세지 컨버터 사용으로 인해서 json 타입이 아닌 XML 타입으로 반환됨
	@ExceptionHandler(SampleException.class)
	@ResponseBody
	public SampleError sampleError(SampleException e) {
		SampleError sampleError = new SampleError();
		sampleError.setMessage("error.app.key");
		sampleError.setReason("blah blah");
		return sampleError;
	}
}
