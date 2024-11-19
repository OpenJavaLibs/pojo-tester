package artefact.classes;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class UnpublicClass {
    final static private class PrivateStaticFinalNestedClass {
        final static private class PrivateStaticFinalNestedClass2 {
        }
    }

    private final static class ProtectedStaticFinalNestedClass {
    }

    private final static class PackageStaticFinalNestedClass {
    }

    private final static class PublicStaticFinalNestedClass {
    }

    static private class PrivateStaticNestedClass {
    }

    private static class ProtectedStaticNestedClass {
    }

    private static class PackageStaticNestedClass {
    }

    private static class PublicStaticNestedClass {
    }

    final private class PrivateFinalNestedClass {
    }

    private final class ProtectedFinalNestedClass {
    }

    private final class PackageFinalNestedClass {
    }

    private final class PublicFinalNestedClass {
    }

    private class PrivateNestedClass {
    }

    private class ProtectedNestedClass {
    }

    private class PackageNestedClass {
    }

    private class PublicNestedClass {
    }
}
