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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;

import org.junit.*;
//import static org.junit.Assert.*;

public class DelphiLocSensorTest {
    
    @Test
    public void IsEmptyLine_Test() {
        assertTrue(DelphiLocSensor.IsEmptyLine("    "));
        assertTrue(DelphiLocSensor.IsEmptyLine(" "));
        assertTrue(DelphiLocSensor.IsEmptyLine(""));
        assertFalse(DelphiLocSensor.IsEmptyLine("bli"));
    }

    @Test
    public void CountLinesOfCode_Test() {

        ArrayList<String> lines = new ArrayList<>();
        assertThat(DelphiLocSensor.CountLines(lines), is(0));
        
        lines.add("Code line 1");
        lines.add("Code line 2");
        lines.add("Code line 3");
        lines.add("// comment line");
        lines.add("Code line 4");
        lines.add("// another comment line");
        assertThat(DelphiLocSensor.CountLines(lines), is(4));        
    }   

}