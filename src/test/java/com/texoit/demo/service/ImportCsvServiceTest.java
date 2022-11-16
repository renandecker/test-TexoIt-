package com.texoit.demo.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

public class ImportCsvServiceTest {

    @InjectMocks
    ImportCsvService importCsvService = new ImportCsvService();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConsultDocument() throws Exception {
        String result = importCsvService.consultDocument();
        Assert.assertNotNull(result);
    }

    @Test
    public void testInsertCSV() throws Exception {
        importCsvService.insertCSV(new ArrayList<>());
    }
}