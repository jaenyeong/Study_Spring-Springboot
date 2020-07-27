package com.jaenyeong.springboot_started.hateoas;
import org.springframework.hateoas.EntityModel;

import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class HateoasController {

	@GetMapping("/hateoas/hello")
	public EntityModel<Hello> hello() {
		Hello hello = new Hello();
		hello.setPrefix("Hey,");
		hello.setName("jaenyeong");

		EntityModel<Hello> entityModel = EntityModel.of(hello);
		Link link = linkTo(methodOn(HateoasController.class).hello()).withSelfRel();
		entityModel.add(link);

		return entityModel;
	}
}
