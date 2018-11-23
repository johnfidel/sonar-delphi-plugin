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
package org.sonarsource.plugins.delphi;

import org.sonar.api.Plugin;
import org.sonar.api.config.PropertyDefinition;
import org.sonarsource.plugins.delphi.hooks.DisplayIssuesInScanner;
import org.sonarsource.plugins.delphi.hooks.DisplayQualityGateStatus;
import org.sonarsource.plugins.delphi.languages.DelphiLanguage;
import org.sonarsource.plugins.delphi.languages.DelphiQualityProfile;
import org.sonarsource.plugins.delphi.measures.ComputeSizeAverage;
import org.sonarsource.plugins.delphi.measures.ComputeSizeRating;
import org.sonarsource.plugins.delphi.measures.DelphiMetrics;
import org.sonarsource.plugins.delphi.measures.SetSizeOnFilesSensor;
import org.sonarsource.plugins.delphi.rules.CreateIssuesOnJavaFilesSensor;
import org.sonarsource.plugins.delphi.rules.DelphiLintIssuesLoaderSensor;
import org.sonarsource.plugins.delphi.rules.DelphiLintRulesDefinition;
import org.sonarsource.plugins.delphi.rules.JavaRulesDefinition;
import org.sonarsource.plugins.delphi.settings.DelphiLanguageProperties;
import org.sonarsource.plugins.delphi.settings.HelloWorldProperties;
import org.sonarsource.plugins.delphi.measures.DelphiLocSensor;
import org.sonarsource.plugins.delphi.web.DelphiPluginPageDefinition;

import static java.util.Arrays.asList;

/**
 * This class is the entry point for all extensions. It is referenced in pom.xml.
 */
public class DelphiPlugin implements Plugin {

  @Override
  public void define(Context context) {
    // tutorial on hooks
    // http://docs.sonarqube.org/display/DEV/Adding+Hooks
    context.addExtensions(DisplayIssuesInScanner.class, DisplayQualityGateStatus.class);

    // tutorial on languages
    context.addExtensions(DelphiLanguage.class, DelphiQualityProfile.class);
    context.addExtension(DelphiLanguageProperties.getProperties());

    // tutorial on measures
    context
      .addExtensions(DelphiMetrics.class, SetSizeOnFilesSensor.class, ComputeSizeAverage.class, ComputeSizeRating.class);

    // tutorial on rules
    context.addExtensions(JavaRulesDefinition.class, CreateIssuesOnJavaFilesSensor.class);
    context.addExtensions(DelphiLintRulesDefinition.class, DelphiLintIssuesLoaderSensor.class);

    // tutorial on settings
    context
      .addExtensions(HelloWorldProperties.getProperties())
      .addExtension(DelphiLocSensor.class);

    // tutorial on web extensions
    context.addExtension(DelphiPluginPageDefinition.class);    

    context.addExtensions(asList(
      PropertyDefinition.builder("sonar.delphi.file.suffixes")
        .name("Suffixes DelphiLint")
        .description("Suffixes supported by DelphiLint")
        .category("DelphiLint")
        .defaultValue("")
        .build()));
  }
}
