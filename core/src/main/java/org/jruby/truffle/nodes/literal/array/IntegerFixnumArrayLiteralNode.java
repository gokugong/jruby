/*
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved. This
 * code is released under a tri EPL/GPL/LGPL license. You can use it,
 * redistribute it and/or modify it under the terms of the:
 *
 * Eclipse Public License version 1.0
 * GNU General Public License version 2
 * GNU Lesser General Public License version 2.1
 */
package org.jruby.truffle.nodes.literal.array;

import com.oracle.truffle.api.*;
import com.oracle.truffle.api.frame.*;
import com.oracle.truffle.api.nodes.*;
import org.jruby.truffle.nodes.*;
import org.jruby.truffle.nodes.core.ArrayAllocationSite;
import org.jruby.truffle.runtime.*;
import org.jruby.truffle.runtime.core.RubyArray;

public class IntegerFixnumArrayLiteralNode extends ArrayLiteralNode {

    private final ArrayAllocationSite arrayAllocationSite = new ArrayAllocationSite();

    public IntegerFixnumArrayLiteralNode(RubyContext context, SourceSection sourceSection, RubyNode[] values) {
        super(context, sourceSection, values);
    }

    @ExplodeLoop
    @Override
    public RubyArray executeArray(VirtualFrame frame) {
        if (arrayAllocationSite.hasConvertedIntToLong()) {
            final long[] executedValues = new long[values.length];

            for (int n = 0; n < values.length; n++) {
                try {
                    executedValues[n] = values[n].executeLongFixnum(frame);
                } catch (UnexpectedResultException e) {
                    final Object[] executedObjects = new Object[n];

                    for (int i = 0; i < n; i++) {
                        executedObjects[i] = executedValues[i];
                    }

                    return makeGeneric(frame, executedObjects);
                }
            }

            return new RubyArray(getContext().getCoreLibrary().getArrayClass(), arrayAllocationSite, executedValues, values.length);
        } else {
            final int[] executedValues = new int[values.length];

            for (int n = 0; n < values.length; n++) {
                try {
                    executedValues[n] = values[n].executeIntegerFixnum(frame);
                } catch (UnexpectedResultException e) {
                    final Object[] executedObjects = new Object[n];

                    for (int i = 0; i < n; i++) {
                        executedObjects[i] = executedValues[i];
                    }

                    return makeGeneric(frame, executedObjects);
                }
            }

            return new RubyArray(getContext().getCoreLibrary().getArrayClass(), arrayAllocationSite, executedValues, values.length);
        }
    }

}
