/*
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved. This
 * code is released under a tri EPL/GPL/LGPL license. You can use it,
 * redistribute it and/or modify it under the terms of the:
 *
 * Eclipse Public License version 1.0
 * GNU General Public License version 2
 * GNU Lesser General Public License version 2.1
 */
package org.jruby.truffle.nodes.call;

import com.oracle.truffle.api.frame.*;
import org.jruby.truffle.nodes.cast.BoxingNode;
import org.jruby.truffle.runtime.*;
import org.jruby.truffle.runtime.core.*;

/**
 * A node in the dispatch chain that boxes the receiver into a full Ruby {@link RubyBasicObject}.
 * This node is initially created as an {@link UninitializedBoxingDispatchNode} and only becomes
 * this node when we know that we do need to box on the fast path. Within this node we specialized
 * for the case that the receiver is always already boxed.
 */
public class BoxingDispatchNode extends UnboxedDispatchNode {

    @Child protected BoxedDispatchNode next;
    @Child protected BoxingNode boxing;

    public BoxingDispatchNode(RubyContext context, BoxedDispatchNode next) {
        super(context);
        this.next = next;
        boxing = new BoxingNode(context, null, null);
    }

    @Override
    public Object dispatch(VirtualFrame frame, Object receiverObject, RubyProc blockObject, Object[] argumentsObjects) {
        return next.dispatch(frame, boxing.box(receiverObject), blockObject, argumentsObjects);
    }

}
