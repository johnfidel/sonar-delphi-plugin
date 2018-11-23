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
import java.io.BufferedReader;
import java.io.FileNotFoundException;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.utils.log.Loggers;
import org.sonar.api.measures.CoreMetrics;

public class DelphiLocSensor implements Sensor {

  @Override
  public void describe(SensorDescriptor descriptor) {
    descriptor.name(getClass().getName());
  }

  @Override
  public void execute(SensorContext context) {
    FileSystem fs = context.fileSystem();
    Iterable<InputFile> delphiFiles = fs.inputFiles(fs.predicates().hasLanguage("delphi"));
    for (InputFile delphiFile : delphiFiles) {           
      Loggers.get(getClass()).info("Found Delphi file:" + delphiFile.filename());    

      try {
        BufferedReader reader;
        try {
          reader = new BufferedReader(new FileReader(delphiFile.filename()));
        
          String line;
          line = reader.readLine();
              
          Integer LineCount = 0;
          while (line != null) {
            LineCount++;
            line = reader.readLine();
          }
          reader.close();
  
          context.<Integer>newMeasure()
            .forMetric(CoreMetrics.NCLOC)
            .on(delphiFile)
            .withValue(LineCount)
            .save();

        } catch (FileNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();        
        }
      }
      catch (IOException e) {
        e.printStackTrace();
      }      
    }
  }

}
