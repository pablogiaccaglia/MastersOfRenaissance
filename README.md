# Software Engineering Project 2020-2021
<img src="http://www.craniocreations.it/wp-content/uploads/2019/06/Masters-of-Renaissance_box3D_ombra.png" width=400px height=400px align="right" />

![latest commit](https://img.shields.io/github/last-commit/Calonca/ing-sw-2021-laconca-lodari-giaccaglia?color=red)
![license](https://img.shields.io/badge/license-MIT-green)

Maestri del Rinascimento is the final test of **"Software Engineering"**, course of **"Computer Science Engineering"** held at Politecnico di Milano (2020/2021).

**Teacher** Pierluigi San Pietro

#### Group: SP10

#### Students:
* [Giaccaglia Pablo](https://github.com/pablogiaccaglia)
* [La Conca Alessandro](https://github.com/Calonca)
* [Lodari Gianmarco](https://github.com/m3f157O) 

## Project specification
The project consists of a Java version of the board game *Maestri del Rinascimento*, made by Cranio Creations.

You can find the full game [here](http://www.craniocreations.it/prodotto/masters-of-renaissance/) and the rules [here](https://github.com/Calonca/ing-sw-2021-laconca-lodari-giaccaglia/blob/master/maestri-rules-en.pdf).

The final version includes:
* initial UML diagram;
* final UML diagram, generated from the code by automated tools;
* working game implementation, which has to be rules compliant;
* source code of the implementation;
* source code of unity tests.

## Implemented Features

| Functionality | Status |
|:-----------------------|:------------------------------------:|
| Basic rules | [![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/Calonca/ing-sw-2021-laconca-lodari-giaccaglia/tree/master/src/main/java/it/polimi/ingsw/server/model) |
| Complete rules | [![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/Calonca/ing-sw-2021-laconca-lodari-giaccaglia/tree/master/src/main/java/it/polimi/ingsw/server/model) |
| Socket |[![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/Calonca/ing-sw-2021-laconca-lodari-giaccaglia/tree/master/src/main/java/it/polimi/ingsw/server) |
| GUI | [![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/Calonca/ing-sw-2021-laconca-lodari-giaccaglia/tree/master/src/main/java/it/polimi/ingsw/client/view/GUI) |
| CLI |[![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/Calonca/ing-sw-2021-laconca-lodari-giaccaglia/tree/master/src/main/java/it/polimi/ingsw/client/view/CLI) |
| Multiple games | [![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/Calonca/ing-sw-2021-laconca-lodari-giaccaglia/tree/master/src/main/java/it/polimi/ingsw/server/controller)|
| Persistence | [![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/Calonca/ing-sw-2021-laconca-lodari-giaccaglia/tree/master/src/main/java/it/polimi/ingsw/server/controller) |
| Disconnection resilience | [![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/Calonca/ing-sw-2021-laconca-lodari-giaccaglia/tree/master/src/main/java/it/polimi/ingsw/server/controller) |

#### Legend
[![RED](http://placehold.it/15/f03c15/f03c15)]() Not Implemented &nbsp;&nbsp;&nbsp;&nbsp;[![YELLOW](http://placehold.it/15/ffdd00/ffdd00)]() Implementing&nbsp;&nbsp;&nbsp;&nbsp;[![GREEN](http://placehold.it/15/44bb44/44bb44)]() Implemented


<!--
[![RED](http://placehold.it/15/f03c15/f03c15)](#)
[![YELLOW](http://placehold.it/15/ffdd00/ffdd00)](#)
[![GREEN](http://placehold.it/15/44bb44/44bb44)](#)
-->

## Usage

### Windows

1. Open the command line
2. Clone the repository
3. In the repository's directory, run:
```bash
java -jar Maestri.jar
```
4. With the following arguments:
```bash
java -jar Maestri.jar [mode] [port] [ip] [nickname]

Modes:
  -c,--cli        Starts the CLI and connects to server with port, ip and nickname
                  without arguments it will let you choose them later
  -g,--gui        Starts the GUI and connects to server with port, ip and nickname
                  without arguments it will let you choose them later
  -s,--server     Starts server with port.
```

5. If you want to run the server, use the -s or the --server argument, along with the desired TCP port. You will be asked to insert a TCP port if it's not in the argument

6. If you want to run the client, use the -c or the --cli argument, along with the desired TCP port, IP addres and nickname. The user will be asked to insert them again if some of them are missing in the argument

7. If you want to run the GUI, use the -g or the --gui argument, along with the desired TCP port, IP addres and nickname. The user will be asked to insert them again if some of them are missing in the argument

## Game GIFs

<h2><p align="center"><b>GUI</b></></h2>
  
Game Turn                  |  Real time updates
:-------------------------:|:-------------------------:
![](https://github.com/Calonca/ing-sw-2021-laconca-lodari-giaccaglia/blob/master/deliverables/final/gifs/gui1.gif)|  ![](https://github.com/Calonca/ing-sw-2021-laconca-lodari-giaccaglia/blob/master/deliverables/final/gifs/gui2.gif)


<h2><p align="center"><b>CLI</b></></h2> 
  
  <p align= "center">
 <kbd> 
 <img src="https://github.com/Calonca/ing-sw-2021-laconca-lodari-giaccaglia/blob/master/deliverables/final/gifs/cli.gif" align="center" /> |
 </kbd>
 </>
    
    
  ---

## Game screenshots


<h2><p align="center"><b>CLI</b></></h2>
  
  ---
  
 <img src="https://raw.githubusercontent.com/pablogiaccaglia/ing-sw-2021-laconca-lodari-giaccaglia/master/screenshots/cli-1.png" align="center" />
  
  ---

![name-of-you-image](https://github.com/Calonca/ing-sw-2021-laconca-lodari-giaccaglia/blob/master/deliverables/final/startCLI.png?raw=true)
  
  ---

![name-of-you-image](https://github.com/Calonca/ing-sw-2021-laconca-lodari-giaccaglia/blob/master/deliverables/final/boardCLI.png?raw=true)
  
  ---

![name-of-you-image](https://github.com/Calonca/ing-sw-2021-laconca-lodari-giaccaglia/blob/master/deliverables/final/cardshopCLI.png?raw=true)
  
  ---

![name-of-you-image](https://github.com/Calonca/ing-sw-2021-laconca-lodari-giaccaglia/blob/master/deliverables/final/marketCLI.png?raw=true)
  
  ---

<h2><p align="center"><b>GUI</b></></h2>

 ---
  
<img src="https://raw.githubusercontent.com/pablogiaccaglia/ing-sw-2021-laconca-lodari-giaccaglia/master/screenshots/gui-1.png" align="center" />

---

![name-of-you-image](https://github.com/Calonca/ing-sw-2021-laconca-lodari-giaccaglia/blob/master/deliverables/final/boardGUI.png?raw=true)

---

![name-of-you-image](https://github.com/Calonca/ing-sw-2021-laconca-lodari-giaccaglia/blob/master/deliverables/final/cardshopGUI.png?raw=true)

---

<img src="https://raw.githubusercontent.com/pablogiaccaglia/ing-sw-2021-laconca-lodari-giaccaglia/master/screenshots/gui-2.png" align="center" />

---

<img src="https://raw.githubusercontent.com/pablogiaccaglia/ing-sw-2021-laconca-lodari-giaccaglia/master/screenshots/gui-3.png" align="center" />

---

<img src="https://raw.githubusercontent.com/pablogiaccaglia/ing-sw-2021-laconca-lodari-giaccaglia/master/screenshots/gui-4.png" align="center" />



* We implemented a 3D gui based on a simplified model of the server's state machine. The user can freely see their board, using W and S to cycle between Board view, Market view, and Frontal view, which covers every game component. While looking from the Frontal view, the player can use D and S keys to rotate around the table, with 90 degree steps. Both the CLI and GUI implement real time updates on the game state. Common elements, such as CardShop and ResourceMarket are shared between all players, each players sees them in front of their board but they are actually synchronized

## Testing
* All unit test have been automated when possible. For some network functionalities it has been necessary to perform some manual QA instead
  of a mock test , for which it would have required too many stubs (being a stateful protocol) so we choose
  manual Quality Assurance.

* All the model classes have been extensively covered, along with most of the controller's classes when possible. Some functionalities regarding
  production output conversion and leader activation do not have an automated test, but our manual tests led us to assert that the functionalities
  implemented are stable in all cases.

* Clicking repeatedly during the drag and drop action may cause issues.

* All the unit test have been run before each commit

## Software used
**sequencediagram.org** - sequence diagrams

**Draw.io** - UML diagrams

**Intellij IDEA Ultimate** - main IDE 

## Copyright and license

Maestri del Rinascimento is copyrighted 2020.

Licensed under the **[MIT License](https://github.com/Calonca/ing-sw-2021-laconca-lodari-giaccaglia/blob/master/LICENSE)**.
You may not use this software except in compliance with the License.
