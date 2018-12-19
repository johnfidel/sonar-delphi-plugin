/*
 * Example Plugin for SonarQube
 * Copyright (C) 2009-2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonarsource.plugins.delphi.measures;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.awt.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.utils.log.Loggers;
import org.sonar.api.measures.CoreMetrics;

//import org.sonarsource.plugins.delphi.languages.scanner.DelphiScanner;

public class DelphiLocSensor implements Sensor {

  public static boolean IsEmptyLine(String line) {
    boolean empty = true;
    for (char c : line.toCharArray()) {
      empty &= (c == ' ');
      if (!empty)
        break;
    }
    return empty;
  }

  public static Integer CountLines(ArrayList<String> lines) {

    Integer LineCount = 0;

    for (String line : lines) {
      if ((!line.startsWith("/", 0)) & (!IsEmptyLine(line))) {
        LineCount++;
      }
    }
    return LineCount;
  }

  @Override
  public void describe(SensorDescriptor descriptor) {
    descriptor.name(getClass().getName());
  }

  @Override
  public void execute(SensorContext context) {
    FileSystem fs = context.fileSystem();
    Iterable<InputFile> delphiFiles = fs.inputFiles(fs.predicates().hasLanguage("delphi"));

    // DelphiScanner scanner = new DelphiScanner(delphiFiles);

    for (InputFile delphiFile : delphiFiles) {
      Loggers.get(getClass()).info("Found Delphi file:" + delphiFile.filename());

      try {
        BufferedReader reader;
        ArrayList<String> lines = new ArrayList<>();
        try {
          reader = new BufferedReader(new FileReader(delphiFile.filename()));

          String line;
          line = reader.readLine();
          while (line != null) {
            lines.add(line);
            line = reader.readLine();
          }
          reader.close();

          Integer linecount = CountLines(lines);
          context.<Integer>newMeasure().forMetric(CoreMetrics.NCLOC).on(delphiFile).withValue(linecount).save();

        } catch (FileNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
