//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.global.framework.dbutils.support;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public abstract class Entity implements Serializable, Cloneable {
    public Entity() {
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
