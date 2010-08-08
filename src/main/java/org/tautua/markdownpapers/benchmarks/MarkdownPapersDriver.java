/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tautua.markdownpapers.benchmarks;

import com.sun.japex.JapexDriverBase;
import com.sun.japex.TestCase;
import org.tautua.markdownpapers.Markdown;
import org.tautua.markdownpapers.grammar.ParseException;

import java.io.*;

import static org.tautua.markdownpapers.benchmarks.Util.slurp;

public class MarkdownPapersDriver extends JapexDriverBase {
    private Reader reader;
    private Writer writer;

    @Override
    public void prepare(TestCase testCase) {
        try {
            String fileName = testCase.getParam("input");
            File file = new File(fileName);

            // reading from file a put the content in memory
            reader = new StringReader(slurp(new FileReader(file)));
            writer = new StringWriter();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public final void warmup(TestCase testCase) {
        run(testCase);
    }

    @Override
    public final void run(TestCase testCase) {
        transform();
    }

    public void transform() {
        try {
            Markdown md = new Markdown();
            md.transform(reader, writer);
            reader.close();
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
