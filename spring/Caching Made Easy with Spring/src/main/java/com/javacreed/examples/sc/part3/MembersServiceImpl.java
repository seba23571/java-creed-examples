/**
 * Copyright 2012-2014 Java Creed.
 * 
 * Licensed under the Apache License, Version 2.0 (the "<em>License</em>");
 * you may not use this file except in compliance with the License. You may 
 * obtain a copy of the License at: 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 */
package com.javacreed.examples.sc.part3;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class MembersServiceImpl implements MembersService, InitializingBean, DisposableBean {

  private final File dataFile = new File("members.txt");

  @Override
  public void afterPropertiesSet() throws Exception {
    FileUtils.writeByteArrayToFile(dataFile, IOUtils.toByteArray(getClass().getResource("members.txt")));
  }

  @Override
  public void destroy() throws Exception {
    dataFile.delete();
  }

  @Override
  @Cacheable("members")
  public Member getMemberWithId(final int id) {
    System.out.printf("Retrieving the member with id: [%d] from file: %s%n", id, dataFile.getAbsolutePath());
    try {
      for (final String line : FileUtils.readLines(dataFile, "UTF-8")) {
        final String[] parts = line.split(",");
        final int memberId = Integer.parseInt(parts[0]);
        if (memberId == id) {
          return new Member(memberId, parts[1]);
        }
      }
    } catch (final Exception e) {
      throw new RuntimeException("Failed to load members", e);
    }

    return null;
  }

  @Override
  @CacheEvict(value = "members", allEntries = true)
  public void saveMember(final Member member) {
    try {
      final StringBuilder toSave = new StringBuilder();
      for (final String line : FileUtils.readLines(dataFile, "UTF-8")) {
        final String[] parts = line.split(",");
        final int memberId = Integer.parseInt(parts[0]);
        if (memberId == member.getMemberId()) {
          toSave.append(memberId).append(",").append(member.getMemberName()).append("\n");
        } else {
          toSave.append(line).append("\n");
        }
      }

      FileUtils.write(new File("members.txt"), toSave.toString().trim(), "UTF-8");
    } catch (final Exception e) {
      throw new RuntimeException("Failed to save members", e);
    }
  }
}
