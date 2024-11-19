package com.java.pojo.internal.utils;

import org.junit.jupiter.api.Test;

import artefact.classes.reflection.utils.next.D;
import artefact.classes.reflection.utils.next.E;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectionUtilsTest {

    @Test
    void Should_Return_Classes_From_Given_Package_Name() throws IOException {
        // given
        final Class<?>[] expectedClasses = new Class[]{ D.class, E.class };

        // when
        final Class<?>[] result = ReflectionUtils.getClassesFromPackage("artefact.classes.reflection.utils.next");

        // then
        assertThat(result).containsExactlyInAnyOrder(expectedClasses);
    }
}