# CED2AR data file to DDI 3.3 Generator

## Purpose 

The program will output DDI 3.3 Fragment format in line with [recommended best practice](https://ddialliance.org/Specification/DDI-Lifecycle/).
It also adds functionality to mute summary statistics on selected variables (e.g. exclude frequencies) either where these are not appropriate or to redact for other reasons. An optional message can be associated with each variable to indicate the reasons why.

## Known issues

Currently this does not support 
- DDI 3.2 Instance format
- Stata
- Should be considered as beta pending further testing

[![DOI](https://zenodo.org/badge/DOI/10.5281/zenodo.1186913.svg)](https://doi.org/10.5281/zenodo.1186913)

This project contains java classes that will allow you to read several versions of **Stata** *or* **SPSS** data sets and generate out DDI 3.3 xml files.

This maven project generates *two* .jar files.  One is used by developers and the other by end users.  The jar files are:
* **ced2arddi3generator.jar** (*Developers*) The *normal jar* file that you can include in other projects.  This jar depends on: [ced2ar-stata-reader](https://github.com/ncrncornell/ced2ar-stata-reader) and [ced2arspssreader](https://github.com/ncrncornell/ced2arspssreader).  (This is the maven project artifact.)  

* **ced2arddi3generator-jar-with-dependencies.jar** (*End Users*) The *runnable jar* file you can use on a command line prompt.

## Artifacts

### Maven Central
[![rdb](https://maven-badges.herokuapp.com/maven-central/edu.cornell.ncrn.ced2ar.ddigen/ced2arddigenerator/badge.svg)](https://maven-badges.herokuapp.com/maven-central/edu.cornell.ncrn.ced2ar.ddigen/ced2arddigenerator)

### Build

*For Developers:* 
1. Clone the github repository to your machine.
2. Go to the root directory of the cloned repository.
3. Use maven 2 to build the project. On the command line, enter the following command

```mvn clean install -Dgpg.skip```
If publishing, omit the `-Dgpg.skip`.

### Usage 
*For Developers:* 


The best way to use this code is to include the jar file in an existing project, such as [ced2ardata2ddi](https://github.com/ncrncornell/ced2ardata2ddi) 
The following code is in: ced2ardata2ddi's DataFileRestController.java file
```
  if (file.getOriginalFilename().toLowerCase().endsWith(".dta")) {
    StataCsvGenerator gen = new StataCsvGenerator();
    variablesCSV = gen.generateVariablesCsv(fileLocation,summaryStats, recordLimit);
  } else if (file.getOriginalFilename().toLowerCase().endsWith(".sav")) {
    SpssCsvGenerator gen = new SpssCsvGenerator();
    variablesCSV = gen.generateVariablesCsv(fileLocation,summaryStats, recordLimit);
  }
```

*For End Users:* 
1. Download ced2arddi3generator-jar-with-dependencies.jar
2. See Run Instructions in next section.


### Run Instructions
Run from a terminal:

`java -jar ced2arddi3generator-jar-with-dependencies.jar  -f <filename>  [ -s <sumstats>  | -l <obsLimit> ]`

usage: Options are as follows...
```
 -f <arg>      (required) data file name and extension.

 -l <arg>      (optional) limit number of observations to process.   Default: Process all observations

 -s <arg>      (optional) generate summary statistics.  Values: TRUE|FALSE   Default: TRUE
 
--config <arg> (optional) use config file with specified path. Format of the config file:
    agency=uk.closer
    ddilang=en-GB
    stats=max,min,mean,valid,invalid,freq,stdev
    outputfile=example_file_name
    sumstats=TRUE
    obsLimit=1000

 --exclude <arg> (optional) exclude statistics for variables specified in the file with specified path. Format for the exlude file:
    var_1=max:user message
    var_2=freq:removed frequencies
    
 --statistics (optional) Produce statistics file
 
 --frequencies (optional) Produce frequency file
```

**Example**

`java -jar ced2arddi3generator-jar-with-dependencies.jar  -f dataset.sav -s TRUE -l 1000`


This run example generates the following files:
* One DDI xml file.  dataset.sav.xml
* Two csv files:
  * dataset.sav.vars.csv
  * dataset.sav_var_values.csv
* One log file.  ced2arstatareader.log

---
Version: 1.3.0 7/31/18 Required: JDK 8.0
