/*
 * Copyright 2019 Patriot project
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.patriot_framework;

import io.patriot_framework.builders.DeviceYamlBuilder;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class Runner {
    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);
    @Option(name = "-c", aliases = "--config", usage = "Yaml config file path")
    String yamlConfigPath;

    private void doMain(final String[] arguments) {
        final CmdLineParser parser = new CmdLineParser(this);
        if (arguments.length < 1) {
            parser.printUsage(System.out);
            System.exit(-1);
        }
        try {
            DeviceYamlBuilder builder = new DeviceYamlBuilder();
            parser.parseArgument(arguments);
            builder.loadDevices(new File(yamlConfigPath)).startActiveDevices();
        } catch (CmdLineException clEx) {
            LOGGER.error("ERROR: Unable to parse command-line options: " + clEx);
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new Runner().doMain(args);
    }

}
