/*
 * #%L
 * How to Continue from a Subdirectory with SimpleFileVisitor
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2012 - 2015 Java Creed
 * %%
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
 * #L%
 */
package com.javacreed.examples.io;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class Example {
    public static void main(final String[] args) throws IOException {

        final CustomSimpleFileVisitor fileVisitor = new CustomSimpleFileVisitor() {
            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                System.out.println(file);
                return FileVisitResult.TERMINATE;
            }
        };
        fileVisitor.setContinueFrom("1", "2", "3");

        final Path root = Paths.get("C:\\Java Creed");
        Files.walkFileTree(root, fileVisitor);
    }
}
