package com.java.pojo.internal.utils;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.util.stream.Stream;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;


class ClassLoaderTest {

    @TestFactory
    Stream<DynamicTest> Should_Load_Expected_Class_By_Qualified_Class_Name() {
        return Stream.of("com.java.pojo.internal.instantiator.Instantiable",
                         "artefact.classes.EmptyEnum",
                         "artefact.classes.Constructor_Field",
                         "artefact.classes.Constructor_Stream",
                         "artefact.classes.Constructor_Thread",
                         "java.lang.Boolean",
                         "java.lang.Byte",
                         "java.lang.Character",
                         "java.lang.Double",
                         "java.lang.Float",
                         "java.lang.Integer",
                         "java.lang.Long",
                         "java.lang.Short",
                         "artefact.classes.ClassContainingStaticClasses$NestedStaticClass_PublicConstructor",
                         "artefact.classes.ClassContainingStaticClasses$NestedStaticClass_PackageConstructor",
                         "artefact.classes.ClassContainingStaticClasses$NestedStaticClass_ProtectedConstructor",
                         "artefact.classes.ClassContainingStaticClasses$NestedStaticClass_PrivateConstructor",
                         "artefact.classes.ClassContainingUnpublicClasses$Package_PublicConstructor",
                         "artefact.classes.ClassContainingUnpublicClasses$Package_PackageConstructor",
                         "artefact.classes.ClassContainingUnpublicClasses$Package_ProtectedConstructor",
                         "artefact.classes.ClassContainingUnpublicClasses$Package_PrivateConstructor",
                         "artefact.classes.ClassContainingUnpublicClasses$Protected_PublicConstructor",
                         "artefact.classes.ClassContainingUnpublicClasses$Protected_PackageConstructor",
                         "artefact.classes.ClassContainingUnpublicClasses$Protected_ProtectedConstructor",
                         "artefact.classes.ClassContainingUnpublicClasses$Protected_PrivateConstructor",
                         "artefact.classes.ClassContainingUnpublicClasses$Private_PublicConstructor",
                         "artefact.classes.ClassContainingUnpublicClasses$Private_PackageConstructor",
                         "artefact.classes.ClassContainingUnpublicClasses$Private_ProtectedConstructor",
                         "artefact.classes.ClassContainingUnpublicClasses$Private_PrivateConstructor",
                         "artefact.classes.UnpublicClass$PublicStaticFinalNestedClass",
                         "artefact.classes.UnpublicClass$PublicStaticNestedClass",
                         "artefact.classes.UnpublicClass",
                         "artefact.classes.UnpublicClass$PrivateStaticFinalNestedClass",
                         "artefact.classes.UnpublicClass$PrivateStaticFinalNestedClass$PrivateStaticFinalNestedClass2",
                         "artefact.classes.UnpublicClass$ProtectedStaticFinalNestedClass",
                         "artefact.classes.UnpublicClass$PackageStaticFinalNestedClass",
                         "artefact.classes.UnpublicClass$PrivateStaticNestedClass",
                         "artefact.classes.UnpublicClass$ProtectedStaticNestedClass",
                         "artefact.classes.UnpublicClass$PackageStaticNestedClass",
                         "artefact.classes.UnpublicClass$PrivateFinalNestedClass",
                         "artefact.classes.UnpublicClass$ProtectedFinalNestedClass",
                         "artefact.classes.UnpublicClass$PackageFinalNestedClass",
                         "artefact.classes.UnpublicClass$PublicFinalNestedClass",
                         "artefact.classes.UnpublicClass$PrivateNestedClass",
                         "artefact.classes.UnpublicClass$ProtectedNestedClass",
                         "artefact.classes.UnpublicClass$PackageNestedClass",
                         "artefact.classes.UnpublicClass$PublicNestedClass",
                         "java.lang.String"
        )
                     .map(value -> dynamicTest(getDefaultDisplayName(value),
                                               Should_Load_Expected_Class_By_Qualified_Class_Name(value)));
    }

    private Executable Should_Load_Expected_Class_By_Qualified_Class_Name(final String qualifiedClassName) {
        return () -> {
            // when
            final Class<?> result = ClassLoader.loadClass(qualifiedClassName);

            // then
            assertThat(result.getName()).isEqualTo(qualifiedClassName);
        };
    }

    @Test
    void Should_Throw_Exception_If_Class_Does_Not_Exist() {
        // given

        // when
        final Throwable result = catchThrowable(() -> ClassLoader.loadClass("lopopopo.ale.tlucze"));

        // then
        assertThat(result).isInstanceOf(ClassLoadingException.class);
    }

}
