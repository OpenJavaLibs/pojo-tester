package com.java.pojo.internal.field.primitive;

class CharacterValueChanger extends AbstractPrimitiveValueChanger<Character> {

    @Override
    protected Character increase(final Character value) {
        return (char) (value + 1);
    }
}
