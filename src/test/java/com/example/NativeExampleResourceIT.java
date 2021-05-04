package com.example;

import com.example.controllers.ExampleResourceTest;
import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
public class NativeExampleResourceIT extends ExampleResourceTest {

    // Execute the same tests but in native mode.
}