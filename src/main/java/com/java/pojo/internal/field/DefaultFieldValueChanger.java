package com.java.pojo.internal.field;


import com.java.pojo.internal.field.collections.CollectionsFieldValueChanger;
import com.java.pojo.internal.field.date.DefaultDateAndTimeFieldValueChanger;
import com.java.pojo.internal.field.math.BigDecimalValueChanger;
import com.java.pojo.internal.field.math.BigIntegerValueChanger;
import com.java.pojo.internal.field.primitive.AbstractPrimitiveValueChanger;


public final class DefaultFieldValueChanger {

    public static final AbstractFieldValueChanger INSTANCE = new EnumValueChanger()
            .attachNext(AbstractPrimitiveValueChanger.INSTANCE)
            .attachNext(CollectionsFieldValueChanger.INSTANCE)
            .attachNext(DefaultDateAndTimeFieldValueChanger.INSTANCE)
            .attachNext(new StringValueChanger())
            .attachNext(new UUIDValueChanger())
            .attachNext(new BigDecimalValueChanger())
            .attachNext(new BigIntegerValueChanger());

    private DefaultFieldValueChanger() {
    }
}
