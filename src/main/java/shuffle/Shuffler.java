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

import java.util.Random;

import org.apache.commons.lang3.Validate;

/**
 * Generates a uniformly shuffled array of values from 1 to maxValue.
 */
public class Shuffler {

    /**
     * Generates a uniformly shuffled array of values from 1 to maxValue.
     * Uses Fisher-Yates shuffle for speed https://en.wikipedia.org/wiki/Fisher-Yates_shuffle
     * 
     * @param numValues Number of values to return (must be between 1 and 10000000)
     * @return a uniformly shuffled array of values from 1 to numValues
     */
    int[] generateShuffledValues(int numValues) {
        Validate.inclusiveBetween(1, 10000000, numValues, "numValues must be between 1 and 10000000");

        Random r = new Random();
        int[] values = new int[numValues];

        for (int i = 0; i < numValues; ++i) {
            int j = r.nextInt(i + 1);
            values[i] = values[j];
            values[j] = i + 1;
        }

        return values;
    }
}
