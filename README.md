# 🧩 Maze Runner Game

A 2D top-down maze navigation game built with **Java** and **LibGDX**, developed as a university project at TUM. The player navigates through a maze, collects keys, avoids traps and enemies, and escapes through the exit — all before losing all lives.

---

## 🎮 Gameplay Overview

- Navigate a character through a maze using arrow keys
- Collect at least one **key** to unlock the exit
- Avoid **traps** (static) and **enemies** (moving) — each contact costs a life
- Reach the exit with at least one life remaining to **win**
- Lose all lives → **Game Over**

---

## 🕹️ Controls

| Key | Action |
|---|---|
| ↑ / Arrow Up | Move Up |
| ↓ / Arrow Down | Move Down |
| ← / Arrow Left | Move Left |
| → / Arrow Right | Move Right |
| Esc | Open / Close Menu (pauses game) |

---

## 🗺️ Map / Maze Files

The game reads mazes from `.properties` files. Each file defines the maze as key-value pairs:

```
x,y = type
```

| Value | Type |
|---|---|
| 0 | Wall |
| 1 | Entry point (player start) |
| 2 | Exit |
| 3 | Trap (static obstacle) |
| 4 | Enemy (dynamic obstacle) |
| 5 | Key |

- Coordinate `0,0` starts at **bottom-left**
- x extends **right**, y extends **up**
- Sample map files are available in the `maps/` directory

To load a map: open the **Game Menu → Load Map** → select your `.properties` file.

---

## 🧠 Game Mechanics

### Character
- Moves in 4 directions (up, down, left, right)
- Cannot pass through walls
- Starts with a fixed number of lives
- Loses 1 life on contact with any obstacle

### Obstacles
- **Traps**: Fixed positions in the maze — stationary hazards
- **Enemies**: Move dynamically through the maze at regular intervals, never leaving the maze boundaries

### Keys & Exit
- At least one key is placed in each maze
- The exit acts like a wall until the player collects a key
- Once a key is collected, the player can walk through the exit to win

### HUD
Always visible during gameplay:
- ❤️ Lives remaining
- 🔑 Key collected status

### Game Menu
Available at startup and by pressing **Esc** during gameplay:
- **Continue** — resume the current game
- **Load Map** — open a file chooser to load a new maze
- **Exit** — quit the game

> Pressing Esc pauses all movement (character and enemies) while the menu is open.

---

## 🏗️ Project Structure

```
maze-runner-game/
├── core/                   # Main game logic
│   └── src/
│       └── ...
│           ├── Main.java           # LibGDX entry point
│           ├── GameScreen.java     # Main gameplay screen
│           ├── MenuScreen.java     # Main menu screen
│           ├── objects/
│           │   ├── GameObject.java     # Common superclass for all objects
│           │   ├── Wall.java
│           │   ├── Entry.java
│           │   ├── Exit.java
│           │   ├── Trap.java
│           │   ├── Enemy.java
│           │   ├── Key.java
│           │   └── Player.java
│           ├── MazeLoader.java     # Reads .properties map files
│           └── HUD.java            # Heads-up display
├── desktop/                # Desktop launcher
├── assets/                 # Sprites, sounds, music
├── maps/                   # Sample maze .properties files
└── build.gradle            # Gradle build config
```

### Class Hierarchy

```
GameObject (superclass)
├── Wall
├── Entry
├── Exit
├── Key
├── Player
└── Obstacle (abstract)
    ├── Trap       (static)
    └── Enemy      (dynamic, moves randomly)
```

---

## 🚀 How to Run

### Prerequisites
- Java JDK 8 or higher
- IntelliJ IDEA (recommended) or any Java IDE with Gradle support

### Run via Gradle

```bash
# Clone the repo
git clone https://github.com/Saumil-1012/maze-runner-game.git
cd maze-runner-game

# Run (macOS/Linux)
./gradlew desktop:run

# Run (Windows)
gradlew.bat desktop:run
```

### Run via IntelliJ
1. Open the project in IntelliJ IDEA
2. Use the provided **run configuration** (important for correct classpath)
3. If you see a Gradle JVM error → open Gradle settings → set **Project SDK** as Gradle JVM
4. On **Windows/Linux**: remove the `-XstartOnFirstThread` VM option from the run config

---

## 🔊 Audio

- 🎵 **Menu music**: calm background track on the main menu
- 🎵 **Gameplay music**: looping background track during gameplay
- 🔊 **Sound effects**:
  - Life lost
  - Key collected
  - Victory
  - Game over

All audio assets are royalty-free (sourced from OpenGameArt).

---

## 🖥️ Screen & Viewport

- The maze can be **larger than the screen** — the camera follows the player
- The player character is always visible within the **middle 80%** of the screen
- Game elements maintain **fixed size** regardless of window size — resizing reveals more of the maze
- Fully adapts to different window sizes and screen resolutions

---

## 🎨 Assets

All graphics are open-source 2D sprites (16×16 px), sourced from:
- [Kenney.nl](https://kenney.nl)
- [OpenGameArt.org](https://opengameart.org)

---

## 👤 Author

**Saumilkumar Savani**
- GitHub: [@Saumil-1012](https://github.com/Saumil-1012)
- Email: go69jal@mytum.de

---

## 📄 License

This project is for educational purposes at TUM (Technical University of Munich).
