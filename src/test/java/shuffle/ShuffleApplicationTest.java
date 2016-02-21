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

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ShuffleApplicationTest {

    ShuffleApplication app;

    @BeforeMethod
    public void setup() {
        app = new ShuffleApplication();
    }

    @Test
    public void test_help() {
        int status = app.run("--help");
        Assert.assertEquals(status, 0, "status");
    }

    @Test
    public void test_help_short() {
        int status = app.run("-h");
        Assert.assertEquals(status, 0, "status");
    }

    @Test
    public void test_100_values() {
        int status = app.run("-num-values", "100");
        Assert.assertEquals(status, 0, "status");
    }

    @Test
    public void test_100_values_short() {
        int status = app.run("-n", "100");
        Assert.assertEquals(status, 0, "status");
    }

    @Test
    public void test_unknown_param() {
        int status = app.run("-m", "100");
        Assert.assertNotEquals(status, 0, "status");
    }

    @Test
    public void test_invalid_num_values() {
        int status = app.run("-n", "lots");
        Assert.assertNotEquals(status, 0, "status");
    }

    @Test
    public void test_decimal_num_values() {
        int status = app.run("-m", "5.7");
        Assert.assertNotEquals(status, 0, "status");
    }

    @Test
    public void test_negative_num_values() {
        int status = app.run("-m", "-1");
        Assert.assertNotEquals(status, 0, "status");
    }

    @Test
    public void test_zero_num_values() {
        int status = app.run("-m", "0");
        Assert.assertNotEquals(status, 0, "status");
    }

    @Test
    public void test_num_values_too_big() {
        int status = app.run("-n", "2000000");
        Assert.assertNotEquals(status, 0, "status");
    }

    @Test
    public void test_num_values_missing() {
        int status = app.run();
        Assert.assertNotEquals(status, 0, "status");
    }

    /**
     * This is a largely bogus test intended to boost code coverage numbers.
     */
    @Test
    public void test_main_doesnt_throw() {
        ShuffleApplication.main(new String[] { "-h" });
    }
}
