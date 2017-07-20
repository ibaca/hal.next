/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.jboss.gwt.flow;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * General purpose context to be used for functions inside a flow. Provides a stack and a map for sharing data between
 * function calls.
 *
 * @author Harald Pehl
 */
public class FunctionContext {

    private final Stack<Object> stack;
    private final Map<String, Object> data;
    private Throwable throwable;

    public FunctionContext() {
        this.stack = new Stack<>();
        this.data = new HashMap<>();
        this.throwable = new RuntimeException("n/a");
    }

    /**
     * Pushes the value om top of the context stack.
     */
    public <T> void push(T value) {
        stack.push(value);
    }

    /**
     * Removes the object at the top of the context stack and returns that object.
     *
     * @return The object at the top of the context stack.
     *
     * @throws EmptyStackException if this context stack is empty.
     */
    @SuppressWarnings("unchecked")
    public <T> T pop() {
        return (T) stack.pop();
    }

    /**
     * @return {@code true} if the context stack is empty, {@code false} otherwise.
     */
    public boolean emptyStack() {return stack.empty();}

    /**
     * Stores the value under the given key in the context map.
     */
    public <T> void set(String key, T value) {
        data.put(key, value);
    }

    /**
     * @return the object for the given key from the context map or {@code null} if no such key was found.
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) data.get(key);
    }

    public void failed(Throwable throwable) {
        if (throwable != null) {
            this.throwable = throwable;
        }
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public String getError() {
        return throwable == null ? "n/a" : throwable.getMessage();
    }

    public Throwable getException() {
        return throwable;
    }

    public boolean hasError() {
        return throwable != null;
    }

    @Override
    public String toString() {
        return "FunctionContext {stack: " + stack + ", map: " + data + "}";
    }
}
