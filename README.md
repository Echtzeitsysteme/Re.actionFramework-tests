# Re.actionFramework-tests
This repository provides Demos and Unit-Tests for the [Re.action](https://github.com/Echtzeitsysteme/Re.actionFramework) framework, which is mainly comprised of the *Re.action* language and the necessary code for integrating and using it with the simulation tool [SimSG](https://github.com/Echtzeitsysteme/SimSG).

## Requirements
* Java SE 8 or higher
 * Eclipse Modeling Tools (do NOT get the latest version but make sure to use [version 2019-12R](https://www.eclipse.org/downloads/packages/release/2019-12/r/eclipse-modeling-tools) instead)
* Working [eMoflon](https://github.com/eMoflon/emoflon-ibex) setup
* [SimSG](https://github.com/Echtzeitsysteme/SimSG)
* [Re.action](https://github.com/Echtzeitsysteme/Re.actionFramework)

If you don't already have a working setup of eMoflon it is recommended to start with a fresh and clean install of eclipse as provided by the [Eclipse Modeling Tools](https://www.eclipse.org/downloads/packages/release/2019-12/r/eclipse-modeling-tools).

## How to Use
Follow the installation guide of the Re.action framework (https://github.com/Echtzeitsysteme/Re.actionFramework), which includes all the necessary setup steps.

### Run the example
This repository contains a working example model of the so-called *Goldbeter-Koshland-Loop (GKL)*. You can find it in the `org.reaction.gklExample`-folder. To see it in action, follow these instructions in your dynamic workspace (i.e., start a new eclipse workspace from within your currently running development workspace):
1. Import the `org.reaction.gklExample`-folder or import via the .psf-file (https://raw.githubusercontent.com/Echtzeitsysteme/Re.actionFramework-tests/master/projectSet.psf).
2. Build it by right-clicking and choosing `SimSG -> Build selected projects fully` or using the corresponding icon in the toolbar while in the SimSG perspective
4. If you still get compilation errors, try building the project as normal Java project now
5. Now you are able to run e.g. `GKLBenchmark` in `org.reaction.gklExample.benchmark`. You can choose between the Pattern Matching Engines *HiPE* and *Democles* by (un-)commenting the corresponding lines in the `GKLBenchmark`-file.
