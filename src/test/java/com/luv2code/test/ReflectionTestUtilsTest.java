package com.luv2code.test;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class ReflectionTestUtilsTest {

	@Autowired
	ApplicationContext context;

	@Autowired
	CollegeStudent student;

	@Autowired
	StudentGrades studentGrades;

	@BeforeEach
	void studentBeforeEach() {
		this.student.setFirstname("Mano");
		this.student.setLastname("Dahora");
		this.student.setEmailAddress("mano@mail.com");
		this.student.setStudentGrades(this.studentGrades);

		ReflectionTestUtils.setField(this.student, "id", 1);
		ReflectionTestUtils.setField(this.student, "studentGrades",
				new StudentGrades(new ArrayList<>(Arrays.asList(100.00, 85.0, 76.5, 91.75))));
	}
}
