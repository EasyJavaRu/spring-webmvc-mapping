package ru.easyjava.spring.webmvc.helloboot;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class NoteControllerTest {
    private NoteController testedObject = new NoteController();

    @Test
    public void testPostAndGet() {
        testedObject.create("TEST", "TEST");
        assertThat(testedObject.read("TEST"), is("TEST"));
    }

    @Test
    public void testPostGenerateIdAndGet() {
        testedObject.create(null, "EMPTY TEST");
        assertThat(testedObject.read("EMPTY"), is("EMPTY TEST"));
    }

    @Test(expected = NoteNotFoundException.class)
    public void testNotFound() {
        testedObject.read("INVALID");
    }

    @Test
    public void testUpdate() {
        testedObject.create("TOUPDATE", "OLDVALUE");
        assertThat(testedObject.read("TOUPDATE"), is("OLDVALUE"));
        assertThat(testedObject.update("TOUPDATE", "NEWVALUE"), is("NEWVALUE"));
        assertThat(testedObject.read("TOUPDATE"), is("NEWVALUE"));
    }

    @Test
    public void testDelete() {
        testedObject.create("DELETEME", "DELETEME");
        assertThat(testedObject.read("DELETEME"), is("DELETEME"));
        testedObject.delete("DELETEME");
        try {
            testedObject.read("DELETEME");
            fail();
        } catch (NoteNotFoundException ex) { }
    }
}