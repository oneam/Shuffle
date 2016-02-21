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
import java.util.stream.IntStream;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ShufflerTest {

    /**
     * A standard normal distribution should have a first moment of 1/2 and second moment of 1/3
     * https://en.wikipedia.org/wiki/Uniform_distribution_(continuous)#Moments_and_parameters
     */
    @Test
    public void test_uniform_distrbution() {
        Shuffler shuffler = new Shuffler();
        int n = 10000;

        double[] s1 = new double[n];
        double[] s2 = new double[n];

        for (int i = 0; i < n; ++i) {
            int[] values = shuffler.generateShuffledValues(n);
            for (int j = 0; j < n; ++j) {
                double v = ((double) values[j] - 1.0) / ((double) n - 1.0); // Normalized value [0..1]
                Assert.assertTrue(v >= 0 && v <= 1, "Normalized value is outside range of [0, 1]");

                s1[j] += v;
                s2[j] += v * v;
            }
        }

        double expectedM1 = 1.0 / 2.0;
        double expectedM2 = 1.0 / 3.0;
        double dn = (double) n;

        for (int i = 0; i < n; ++i) {
            double actualM1 = s1[i] / dn;
            Assert.assertEquals(actualM1, expectedM1, 0.02, "m1 value outside of 0.02");

            double actualM2 = s2[i] / dn;
            Assert.assertEquals(actualM2, expectedM2, 0.02, "m2 value outside of 0.02");
        }
    }

    /**
     * Assert that the values are in fact 1..maxValue and don't repeat
     */
    @Test
    public void test_correct_values() {
        Shuffler shuffler = new Shuffler();
        int n = 10000;

        for (int i = 0; i < n; ++i) {
            int[] values = shuffler.generateShuffledValues(n);
            Arrays.sort(values);

            Assert.assertEquals(values.length, n, "number of values");
            for (int j = 0; j < n; ++j) {
                int value = values[j];
                Assert.assertEquals(value, j + 1, "When sorted, value equals index");

                // These tests are extraneous, but included for completeness
                Assert.assertTrue(value > 0, "value is greater than zero");
                Assert.assertTrue(value <= n, "value is less or equal to than numValues");
            }
        }
    }

    /**
     * The autocorrelation of white noise in the range [-1..1] will be zero for all values of i except 0.
     * https://en.wikipedia.org/wiki/Autocorrelation#Properties
     */
    @Test
    public void test_looks_like_noise() {
        Shuffler shuffler = new Shuffler();
        int n = 10000;
        double[] norm = new double[n];

        // Normalized value = (value - m) / s
        double s = (double) (n - 1) / 2.0;
        double m = (double) (n + 1) / 2.0;

        // Create normalized set of values
        int[] values = shuffler.generateShuffledValues(n);
        for (int i = 0; i < n; ++i) {
            norm[i] = ((double) values[i] - m) / s;
            Assert.assertEquals(norm[i], 0.0, 1.0, "Normalized value is outside range of [-1, 1]");
        }

        // Calculate R(i) = sum(for j in 0..n-1){norm(j) * norm(j+i)}
        // Highly correlated values produce a value of n/3
        // Assert that uncorrelated values are <5% of n/3
        double delta = (double) n / 3.0 * 0.05;

        for (int i = 1; i < n; ++i) {
            final int q = i; // For lambda
            double Ri = IntStream.range(0, n - 1).mapToDouble(j -> norm[j] * norm[(j + q) % n]).sum();
            Assert.assertEquals(Ri, 0, delta, "Ri is not zero within 5%");
        }
    }
}
