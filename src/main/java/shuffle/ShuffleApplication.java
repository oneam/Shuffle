/**
 * Copyright 2016 Sam Leitch
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package shuffle;

import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Application used to generate shuffled values based on an input max value
 */
public class ShuffleApplication {

    /**
     * Executes the application
     * 
     * @param args Commandline arguments
     * @return 0 on successful execution of the application. Non-zero otherwise.
     */
    int run(String... args) {
        Options options = new Options();
        options.addOption("n", "num-values", true, "Number of values in the shuffled set. This value must be an integer between 1 and 10000000.");
        options.addOption("h", "help", false, "Displays this help message");

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("help")) {
                printHelp(options);
                return 0;
            }

            if (!cmd.hasOption("num-values")) {
                System.err.println("You must define an num-values parameter.");
                printHelp(options);
                return 1;
            }

            int maxValue = Integer.parseInt(cmd.getOptionValue("num-values"));
            if (maxValue < 1 || maxValue > 10000000) {
                System.err.println("num-values parameter must be an integer between 1 and 10000000.");
                return 4;
            }

            Shuffler shuffler = new Shuffler();
            int[] values = shuffler.generateShuffledValues(maxValue);
            Arrays.stream(values).forEach(System.out::println);
            return 0;

        } catch (ParseException e) {
            System.err.println(e.getMessage());
            printHelp(options);
            return 2;
        } catch (NumberFormatException e) {
            System.err.println("num-values parameter must be an integer between 1 and 10000000.");
            printHelp(options);
            return 3;
        }
    }

    /**
     * Prints the help message for the application
     * 
     * @param options The Commandline options
     */
    void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        System.out.println();
        formatter.printHelp("Shuffle [options]", options);
        System.out.println();
    }

    /**
     * Application main
     * 
     * @param args Cmmandline arguments
     */
    public static void main(String[] args) {
        ShuffleApplication shuffle = new ShuffleApplication();
        int status = shuffle.run(args);
        if (status != 0) System.exit(status);
    }
}
