package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Customer;
import org.example.service.ApplicationService;
import org.example.web.CustomerForm;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.result.view.Rendering;
import org.thymeleaf.spring5.SpringWebFluxTemplateEngine;
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
//@RestController
@Controller
@RequestMapping("/customers")
//@Configuration(proxyBeanMethods = false)
public class ApplicationController {

    private final ApplicationService applicationService;

    //ついか
    @ModelAttribute
    CustomerForm setUpForm(){
        return new CustomerForm();
    }

    //ついか
    @GetMapping
    String list(Model model) {
        final Flux<Customer> customers = applicationService.findAll();
        IReactiveDataDriverContextVariable iReactiveDataDriverContextVariable = new ReactiveDataDriverContextVariable(customers, 1);
        model.addAttribute("customers", iReactiveDataDriverContextVariable);
//        model.addAttribute("customers", reactiveDataDriverContextVariable);
        return "customers/list";
    }

    @PostMapping(path = "create")
    String create(@Validated CustomerForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return list(model);
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(form, customer);
        applicationService.create(customer);
        return "redirect:/customers";
    }

    @GetMapping(path="edit", params="form")
    String editForm(@RequestParam Integer id, CustomerForm form){
        Mono<Customer> customer = applicationService.findOne(id);
        BeanUtils.copyProperties(customer, form);
        return "customers/edit";
    }

    @PostMapping(path="edit")
    String edit(@RequestParam Integer id, @Validated CustomerForm form, BindingResult result){
        if(result.hasErrors()){
            return editForm(id, form);
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(form, customer);
        customer.setId(id);
        applicationService.update(customer);
        return "redirect:/customers";
    }

    @RequestMapping(path="edit", params = "goToTop")
    String goToTop(){
        return "redirect:/customers";
    }

    @PostMapping(path="delete")
    String delete(@RequestParam Integer id){
        applicationService.delete(id);
        return "redirect:/customers";
    }

}
