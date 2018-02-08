/*
 *  Copyright 2018 Alexey Andreev.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.teavm.backend.c.generate;

import org.teavm.ast.RegularMethodNode;
import org.teavm.ast.decompilation.Decompiler;
import org.teavm.model.ClassHolder;
import org.teavm.model.ElementModifier;
import org.teavm.model.MethodHolder;

public class ClassGenerator {
    private GenerationContext context;
    private Decompiler decompiler;
    private CodeWriter writer;
    private CodeGenerator codeGenerator;
    private NameProvider nameProvider;

    public ClassGenerator(GenerationContext context, Decompiler decompiler, CodeWriter writer) {
        this.context = context;
        this.decompiler = decompiler;
        this.writer = writer;
        codeGenerator = new CodeGenerator(context, writer);
    }

    public void generateClass(ClassHolder cls) {
        for (MethodHolder method : cls.getMethods()) {
            if (method.hasModifier(ElementModifier.NATIVE) || method.hasModifier(ElementModifier.ABSTRACT)) {
                continue;
            }

            RegularMethodNode methodNode = decompiler.decompileRegular(method);
            codeGenerator.generateMethod(methodNode);
        }
    }
}
