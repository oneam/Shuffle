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

import org.apache.commons.math3.stat.inference.ChiSquareTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ShufflerTest {

    @Test
    // Assert that the values are randomly shuffled
    public void test_randomness() {
        Shuffler shuffler = new Shuffler();
        int numExecutions = 10000;
        int numValues = 10000;

        long[] observed = new long[numValues];
        for (int i = 0; i < numExecutions; ++i) {
            int[] values = shuffler.generateShuffledValues(numValues);
            for (int j = 0; j < numValues; ++j) {
                observed[j] += values[j];
            }
        }

        double expectedValue = (double) (numExecutions * (numValues + 1)) / 2.0;
        double[] expected = new double[numValues];
        Arrays.fill(expected, expectedValue);

        ChiSquareTest chiSquareTest = new ChiSquareTest();
        Assert.assertTrue(chiSquareTest.chiSquareTest(expected, observed, 0.01), "10000 samples are shuffled randomly within a 99% confidence interval");
    }

    @Test
    // Assert that the values are in fact 1..maxValue
    public void test_correctness() {
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
