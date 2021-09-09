package com.chencorp.fileapi;

import com.chencorp.fileapi.exception.AlreadyExistException;
import com.chencorp.fileapi.exception.NotFoundException;
import com.chencorp.fileapi.model.File;
import com.chencorp.fileapi.service.FileApiService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;

@SpringBootTest
@Transactional
class FileApplicationTests {

	@Autowired
	FileApiService fileApiService;
	@Test
	void testFileAlreadyExist() {
		final String fileName = "name.txt";
		final String category = "category";
		fileApiService.createFile(fileName, category, "Data".getBytes(StandardCharsets.UTF_8));
		try {
			fileApiService.createFile(fileName, category, "Data2".getBytes(StandardCharsets.UTF_8));
			Assertions.fail();
		} catch (AlreadyExistException e) {
			Assertions.assertNotNull(e.getMessage());
		}
	}

	@Test
	void testFileNotExist() {
		try {
			final File file = fileApiService.getFile(123L);
			Assertions.fail();
		} catch (NotFoundException e) {
			Assertions.assertNotNull(e.getMessage());
		}
	}

	@Test
	void testFileCreation() {
		final String category = "category";
		File file = fileApiService.createFile("file.txt", category, "Data".getBytes(StandardCharsets.UTF_8));
		file = fileApiService.updateFile(file.getId(), "coucou".getBytes(StandardCharsets.UTF_8));
		Assertions.assertEquals(2, file.getDataList().size());
		Assertions.assertEquals("Data", new String(file.getDataList().get(0).getData()));
		Assertions.assertEquals("coucou", new String(file.getDataList().get(1).getData()));
	}

}
