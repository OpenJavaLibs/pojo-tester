package com.java.pojo.internal.field.collections;


import com.java.pojo.internal.field.AbstractFieldValueChanger;
import com.java.pojo.internal.field.collections.collection.AbstractCollectionFieldValueChanger;
import com.java.pojo.internal.field.collections.iterators.AbstractIteratorsFieldValueChanger;
import com.java.pojo.internal.field.collections.map.AbstractMapFieldValueChanger;

public final class CollectionsFieldValueChanger {

    public static final AbstractFieldValueChanger INSTANCE = new ArrayValueChanger()
            .attachNext(new StreamValueChanger())
            .attachNext(AbstractCollectionFieldValueChanger.INSTANCE)
            .attachNext(AbstractMapFieldValueChanger.INSTANCE)
            .attachNext(AbstractIteratorsFieldValueChanger.INSTANCE);

    private CollectionsFieldValueChanger() {}
}
