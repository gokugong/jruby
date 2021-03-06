/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jruby.ast;

import org.jruby.Ruby;
import org.jruby.lexer.yacc.ISourcePosition;
import org.jruby.runtime.Block;
import org.jruby.runtime.CallSite;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;

/**
 *
 * @author enebo
 */
public class AttrAssignThreeArgNode extends AttrAssignNode {
    private Node arg1;
    private Node arg2;
    private Node arg3;
    
    public AttrAssignThreeArgNode(ISourcePosition position, Node receiverNode, String name, ArrayNode argsNode) {
        super(position, receiverNode, name, argsNode);
        
        assert argsNode.size() == 3 : "argsNode.size() is 3";
        
        arg1 = argsNode.get(0);
        arg2 = argsNode.get(1);
        arg3 = argsNode.get(2);
    }
}
