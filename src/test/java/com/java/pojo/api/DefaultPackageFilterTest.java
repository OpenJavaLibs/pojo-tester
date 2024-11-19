package com.java.pojo.api;

import org.junit.jupiter.api.Test;

import artefact.classes.packages.filter.A;
import artefact.classes.packages.filter.B;
import artefact.classes.packages.filter.C;
import artefact.classes.packages.filter.next.D;
import artefact.classes.packages.filter.next.E;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


class DefaultPackageFilterTest {

    @Test
    void Should_Return_Classes_From_Given_Package() {
        // given
        final Class<?>[] expectedClasses = new Class[]{A.class, B.class, C.class, D.class, E.class};

        // when
        final Class<?>[] result = DefaultPackageFilter.forClass(A.class)
                                                      .getClasses();

        // then
        assertThat(result).containsExactlyInAnyOrder(expectedClasses);
    }

    @Test
    void Should_Return_Classes_From_Given_Package_Name() {
        // given
        final Class<?>[] expectedClasses = new Class[]{D.class, E.class};

        // when
        final Class<?>[] result = DefaultPackageFilter.forPackage("artefact.classes.packages.filter.next")
                                                      .getClasses();

        // then
        assertThat(result).containsExactlyInAnyOrder(expectedClasses);
    }

    @Test
    void Should_Throw_Exception_When_Invalid_Package_Name() {
        // given

        // when
        final Throwable result = catchThrowable(() -> DefaultPackageFilter.forPackage("invalid.package.name")
                                                                          .getClasses());

        // then
        assertThat(result).isInstanceOf(PackageFilterException.class);
    }
}