/*
 * The MIT License
 *
 * Copyright 2019 Pityubak.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.pityubak.autoinjector;

import com.pityubak.liberator.Liberator;
import com.pityubak.liberator.data.Request;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Pityubak
 */
public class Context {

    private final List<Class<?>> classes;
    private final Liberator liberator = new Liberator(Context.class);

    public Context(Class<?> mainClass) {
        this.classes = this.liberator.getClassListFromTargetPackage(mainClass);
    }

    public <W> W inject(Class<?> type) {
        final Request request = this.liberator.askForRequest();
        request.setRequestType(type);
        this.liberator.initConfig();
        final Map<String, Class<?>> classList = new HashMap<>();
        classes.forEach((cl) -> {
            if (!cl.isInterface()) {
                classList.put(cl.getSimpleName(), cl);
            }
        });
        this.liberator.injectWithName(classList);
        return (W) request.response(type.getSimpleName());
    }
}
