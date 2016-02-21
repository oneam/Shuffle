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

import org.testng.Assert;
import org.testng.annotations.Test;

public class ShufflerTest {

    /**
     * A standard normal distribution should have a first moment of 1/2 and second moment of 1/3
     * 
     * https://en.wikipedia.org/wiki/Uniform_distribution_(continuous)#Moments_and_parameters
     */
    @Test
    public void test_uniform_distrbution() {
        Shuffler shuffler = new Shuffler();
        int numExecutions = 10000;
        int numValues = 10000;

        double[] s1 = new double[numValues];
        double[] s2 = new double[numValues];

        for (int i = 0; i < numExecutions; ++i) {
            int[] values = shuffler.generateShuffledValues(numValues);
            for (int j = 0; j < numValues; ++j) {
                double v = ((double) values[j] - 1.0) / ((double) numValues - 1.0); // Normalized value [0..1]
                s1[j] += v;
                s2[j] += v * v;
            }
        }

        double expectedM1 = 1.0 / 2.0;
        double expectedM2 = 1.0 / 3.0;

        for (int i = 0; i < numExecutions; ++i) {
            double actualM1 = s1[i] / (double) numExecutions;
            Assert.assertEquals(actualM1, expectedM1, 0.02, "m1 value outside of 98% confidence");

            double actualM2 = s2[i] / (double) numExecutions;
            Assert.assertEquals(actualM2, expectedM2, 0.02, "m2 value outside of 98% confidence");
        }
    }

    /**
     * Assert that the values are in fact 1..maxValue and don't repeat
     */
    @Test
    public void test_correct_values() {
        Shuffler shuffler = new Shuffler();
        int numExecutions = 10000;
        int numValues = 10000;

        for (int i = 0; i < numExecutions; ++i) {
            int[] values = shuffler.generateShuffledValues(numValues);
            Arrays.sort(values);

            Assert.assertEquals(values.length, numValues, "number of values");
            for (int j = 0; j < numValues; ++j) {
                int value = values[j];
                Assert.assertEquals(value, j + 1, "When sorted, value equals index");

                // These tests are extraneous, but included for completeness
                Assert.assertTrue(value > 0, "value is greater than zero");
                Assert.assertTrue(value <= numValues, "value is less or equal to than numValues");
            }
        }
    }
}
