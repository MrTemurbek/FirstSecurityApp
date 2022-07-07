package ru.alishev.springcourse.FirstSecurityApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alishev.springcourse.FirstSecurityApp.models.Person;
import ru.alishev.springcourse.FirstSecurityApp.services.PersonDetailsService;
@Component
public class PersonValidator implements Validator {
    private final PersonDetailsService service;

    @Autowired
    public PersonValidator(PersonDetailsService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        try {
            service.loadUserByUsername(person.getUsername());
        }catch (UsernameNotFoundException ignored) {
            return;
        }

        if (person.getPassword().length()<4){
            errors.rejectValue("password", "", "Password should be at least 4 characters");
        }

        errors.rejectValue("username","",  "Username already registered");

    }
}
