package com.luv2code.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

	@MockBean
	private ApplicationDao applicationDao;

	@Autowired
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

		verify(this.applicationDao).addGradeResultsForSingleClass(this.studentGrades.getMathGradeResults());
		verify(this.applicationDao, times(1)).addGradeResultsForSingleClass(this.studentGrades.getMathGradeResults());
	}

	@Test
	@DisplayName("Find Gpa")
	void assertEqualsFindGpa() {
		when(this.applicationDao.findGradePointAverage(this.studentGrades.getMathGradeResults())).thenReturn(88.31);

		assertEquals(88.31,
				this.applicationService.findGradePointAverage(this.student.getStudentGrades().getMathGradeResults()));
	}

	@Test
	@DisplayName("Not null")
	void testAssertNotNull() {
		when(this.applicationDao.checkNull(this.studentGrades.getMathGradeResults())).thenReturn(true);

		assertNotNull(this.applicationService.checkNull(this.student.getStudentGrades().getMathGradeResults()),
				"Object should not be null");
	}

	@Test
	@DisplayName("Throw runtime error")
	void throwRuntimeError() {
		CollegeStudent nullStudent = (CollegeStudent) this.context.getBean("collegeStudent");

		doThrow(new RuntimeException()).when(this.applicationDao).checkNull(nullStudent);

		assertThrows(RuntimeException.class, () -> {
			this.applicationService.checkNull(nullStudent);
		});

		verify(this.applicationDao, times(1)).checkNull(nullStudent);
	}

	@Test
	@DisplayName("Multiple Stubbings")
	void stubbingConsecutiveCalls() {
		CollegeStudent nullStudent = (CollegeStudent) this.context.getBean("collegeStudent");

		when(this.applicationDao.checkNull(nullStudent)).thenThrow(new RuntimeException())
				.thenReturn("Do not throw exception second time");

		assertThrows(RuntimeException.class, () -> {
			this.applicationService.checkNull(nullStudent);
		});
		assertEquals("Do not throw exception second time", this.applicationService.checkNull(nullStudent));

		verify(this.applicationDao, times(2)).checkNull(nullStudent);
	}
}
