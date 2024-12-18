package com.java.pojo.api;

import org.junit.jupiter.api.Test;
import com.java.pojo.api.assertion.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static com.java.pojo.api.assertion.Assertions.assertPojoMethodsFor;


class FastTestingTest {

    @Test
    void ShouldCompleteInReasonableTime() {
        final long start = System.currentTimeMillis();
        {
            // given
            final Class<?> classUnderTest = Medium.class;

            // when

            // then
            assertPojoMethodsFor(classUnderTest)
                    .testing(Method.EQUALS, Method.HASH_CODE)
                    .quickly()
                    .areWellImplemented();
        }
        final long end = System.currentTimeMillis();
        assertThat(end - start).isLessThan(500L);
    }
}

class Medium {
    private int a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u;

    @Override
    public boolean equals(final Object o1) {
        if (this == o1) {
            return true;
        }
        if (o1 == null || getClass() != o1.getClass()) {
            return false;
        }

        final Medium medium = (Medium) o1;

        if (a != medium.a) {
            return false;
        }
        if (b != medium.b) {
            return false;
        }
        if (c != medium.c) {
            return false;
        }
        if (d != medium.d) {
            return false;
        }
        if (e != medium.e) {
            return false;
        }
        if (f != medium.f) {
            return false;
        }
        if (g != medium.g) {
            return false;
        }
        if (h != medium.h) {
            return false;
        }
        if (i != medium.i) {
            return false;
        }
        if (j != medium.j) {
            return false;
        }
        if (k != medium.k) {
            return false;
        }
        if (l != medium.l) {
            return false;
        }
        if (m != medium.m) {
            return false;
        }
        if (n != medium.n) {
            return false;
        }
        if (o != medium.o) {
            return false;
        }
        if (p != medium.p) {
            return false;
        }
        if (q != medium.q) {
            return false;
        }
        if (r != medium.r) {
            return false;
        }
        if (s != medium.s) {
            return false;
        }
        if (t != medium.t) {
            return false;
        }
        return u == medium.u;
    }

    @Override
    public int hashCode() {
        int result = a;
        result = 31 * result + b;
        result = 31 * result + c;
        result = 31 * result + d;
        result = 31 * result + e;
        result = 31 * result + f;
        result = 31 * result + g;
        result = 31 * result + h;
        result = 31 * result + i;
        result = 31 * result + j;
        result = 31 * result + k;
        result = 31 * result + l;
        result = 31 * result + m;
        result = 31 * result + n;
        result = 31 * result + o;
        result = 31 * result + p;
        result = 31 * result + q;
        result = 31 * result + r;
        result = 31 * result + s;
        result = 31 * result + t;
        result = 31 * result + u;
        return result;
    }
}
