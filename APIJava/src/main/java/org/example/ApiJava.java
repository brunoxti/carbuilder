/*
 * SPDX-License-Identifier: Apache-2.0
 */

package org.example;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import com.owlike.genson.Genson;

@DataType()
public class ApiJava {

    private final static Genson genson = new Genson();

    @Property()
    private String value;

    public ApiJava(){
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toJSONString() {
        return genson.serialize(this).toString();
    }

    public static ApiJava fromJSONString(String json) {
        ApiJava asset = genson.deserialize(json, ApiJava.class);
        return asset;
    }
}
