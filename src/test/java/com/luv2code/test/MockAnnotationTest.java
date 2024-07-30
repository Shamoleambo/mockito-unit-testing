package com.luv2code.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.dao.ApplicationDao;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import com.luv2code.component.service.ApplicationService;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class MockAnnotationTest {

	@Autowired
	ApplicationContext context;

	@Autowired
	CollegeStudent student;

	@Autowired
	StudentGrades studentGrades;

	@Mock
	private ApplicationDao applicationDao;

	@InjectMocks
	private ApplicationService applicationService;

	@BeforeEach
	void beforeEach() {
		this.student.setFirstname("Mano");
		this.student.setLastname("Dahora");
		this.student.setEmailAddress("mano@mail.com");
		this.student.setStudentGrades(this.studentGrades);
	}

	@Test
	@DisplayName("When & Verify")
	void assertEqualsTestAddGrades() {
		when(this.applicationDao.addGradeResultsForSingleClass(this.studentGrades.getMathGradeResults()))
				.thenReturn(100.00);

		assertEquals(100, this.applicationService
				.addGradeResultsForSingleClass(this.student.getStudentGrades().getMathGradeResults()));
	}
}
