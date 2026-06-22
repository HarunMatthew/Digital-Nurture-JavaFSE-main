package com.example;

import org.junit.*;

import static org.junit.Assert.*;

public class AAAUserTest {

    User user;

    // Setup
    @Before
    public void setUp() {
        user = new User();
        user.setName("Deepika");
    }

    // Teardown
    @After
    public void tearDown() {
        user = null;
    }

    @Test
    public void testGetName_AAA() {

        // ARRANGE is already done in setUp()

        // ACT
        String name = user.getName();

        // ASSERT
        assertEquals("Deepika", name);
    }
}