package artefact.classes;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class ClassContainingStaticClasses {

    public static class NestedStaticClass_PublicConstructor {
        public NestedStaticClass_PublicConstructor() {
        }
    }

    public static class NestedStaticClass_PackageConstructor {
        NestedStaticClass_PackageConstructor() {
        }
    }

    public static class NestedStaticClass_ProtectedConstructor {
        protected NestedStaticClass_ProtectedConstructor() {
        }
    }

    public static class NestedStaticClass_PrivateConstructor {
        private NestedStaticClass_PrivateConstructor() {
        }
    }
}
