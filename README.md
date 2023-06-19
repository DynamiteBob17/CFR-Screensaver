# How to use
If you have [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) and up installed you can:
- download the .jar executable from the release  
**OR**
- compile and run the repository as a Maven project with your method of choice.</br></br>
`mvn clean compile exec:java` to run from a command.  

## About
- Desktop screensaver that features Conway's Game of Life presets, fractal trees and RGB rain.
- If you're building the project you can try playing around with values for rain density, tree branch depths, colors etc. They were reduced from their original values for performance.

### About Conway's Game of Life presets  
- presets are loaded randomly from a list generated from the .txt file in the resources directory that was made from a .zip archive containing thousands of presets from a zip file from [this page](https://conwaylife.com/wiki)
