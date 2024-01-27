package com.huachuan.controller;


import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import com.huachuan.dao.StudentMapper;
import com.huachuan.domain.Student;
import jakarta.annotation.Resource;

import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    @Resource
    private FhirContext ctx;
    @Resource
    StudentMapper mapper;
    @RequestMapping("/hello")
    @ResponseBody
    String hello() {
        IParser parser = ctx.newJsonParser();
        Patient patient = new Patient();
        patient.addIdentifier().setSystem("urn:system").setValue("12345");
        patient.addName().setFamily("Smith").addGiven("John");
        String serialized = parser.encodeResourceToString(patient);
        System.out.println(serialized);

        // Create a client
        IGenericClient client = ctx.newRestfulGenericClient("http://8.130.24.206:8090/fhir");


        //将数据写入服务器
//        MethodOutcome outcome = client.create()
//                .resource(patient)
//                .prettyPrint()
//                .encodedJson()
//                .execute();
//
//        IIdType id = outcome.getId();
//        System.out.println("Got ID: " + id.getValue());

        // Read a patient with the given ID
//        Bundle bundle = (Bundle) client.search().forResource(Patient.class)
//                .prettyPrint()
//                .execute();

        Patient patient1 = client.read().resource(Patient.class)
                .withId("1")
                .execute();

        // Print the output
        String string = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(patient1);
        System.out.println(string);
        return string + '\n';
    }
}
