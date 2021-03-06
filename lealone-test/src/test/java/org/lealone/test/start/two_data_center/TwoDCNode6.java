/*
 * Copyright Lealone Database Group.
 * Licensed under the Server Side Public License, v 1.
 * Initial Developer: zhh
 */
package org.lealone.test.start.two_data_center;

public class TwoDCNode6 extends TwoDCNodeBase {
    public static void main(String[] args) {
        TwoDCNodeBase.run("lealone-rackdc2.properties", TwoDCNode6.class, args);
    }

    public TwoDCNode6() {
        this.listen_address = "127.0.0.6";
        this.dir = "node6";
    }
}
