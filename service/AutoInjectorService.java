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
package com.pityubak.autoinjector.service;

import com.pityubak.autoinjector.annotation.AutoInject;
import com.pityubak.autoinjector.annotation.Service;
import com.pityubak.liberator.annotations.MethodBox;
import com.pityubak.liberator.annotations.MethodElement;
import com.pityubak.liberator.data.Request;
import com.pityubak.liberator.misc.Flag;
import com.pityubak.liberator.data.Response;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Pityubak
 */
@MethodBox
public class AutoInjectorService {

    private final Map<String, Class<?>> map = new HashMap<>();

    @MethodElement
    public <T> void mapInterface(Service service, Response response) {
        final Class<?> type = response.getType();
        final String injInterface = type.getInterfaces()[0].getSimpleName();
        this.map.put(injInterface, type);

    }

    @MethodElement(Flag.PRIORITY_HIGH)
    public <T> T getValue(AutoInject inject, Response response) {
        final Request request = response.getRequest();
        Class type;
        if (inject.value().length < 1) {
            final Class<?> injType = response.getType();
            type = this.map.get(injType.getSimpleName());
        } else {
            type = inject.value()[0];
        }
        if (type == null) {
            throw new NullPointerException("Class must be annotated with @Service or add explicit classname in @AutoInject annotation ");
        }
        request.setRequestType(type);

        return (T) request.response(response.getName());
    }
}
