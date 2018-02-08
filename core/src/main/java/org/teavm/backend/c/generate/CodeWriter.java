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

import java.io.PrintWriter;

public class CodeWriter {
    private PrintWriter writer;
    private int indentLevel;
    private boolean isLineStart;

    public CodeWriter(PrintWriter writer) {
        this.writer = writer;
    }

    public CodeWriter println() {
        return println("");
    }

    public CodeWriter println(String string) {
        addIndentIfNecessary();
        writer.print(string);
        writer.print("\n");
        isLineStart = true;
        return this;
    }

    public CodeWriter print(String string) {
        addIndentIfNecessary();
        writer.print(string);
        return this;
    }

    public CodeWriter indent() {
        indentLevel++;
        return this;
    }

    public CodeWriter outdent() {
        indentLevel--;
        return this;
    }

    private void addIndentIfNecessary() {
        if (isLineStart) {
            for (int i = 0; i < indentLevel; ++i) {
                writer.print("    ");
            }
            isLineStart = false;
        }
    }
}
