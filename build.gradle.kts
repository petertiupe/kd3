plugins {
    kotlin("js") version "1.5.31"
}
group = "de.tiupe"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(npm("d3", "7.1.1"))
    testImplementation(kotlin("test", "1.5.31"))
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-html-js
    implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.7.3")

}




kotlin {
    js(IR) {

        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
            /*
            * You can also configure common webpack settings to use in bundling,
            * running, and testing tasks in the commonWebpackConfig block.

*/
            webpackTask {
                outputFileName = "kd3.js"
                // output.libraryTarget = "commonjs2"
            }
        }
        /*
        * The instruction binaries.executable() explicitly instructs the Kotlin compiler to emit executable .js files.
        * This is the default behavior when using the current Kotlin/JS compiler, but the instruction is
        * explicitly required if you are working with the Kotlin/JS IR compiler or have set
        * kotlin.js.generate.executable.default=false in your gradle.properties.
        * In those cases, omitting binaries.executable() will cause the compiler to only generate
        * Kotlin-internal library files, which can be used from other projects, but not run on their own.
        */
        binaries.executable()
    }

}