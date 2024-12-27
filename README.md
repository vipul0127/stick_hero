# Stick Hero Game

## Description
Stick Hero is an interactive game where the player controls a stick figure that must jump from platform to platform. The objective is to extend the stick to the correct length to reach the next platform without falling off. The game becomes increasingly challenging as the player progresses through multiple levels.

## Features
- **Multiple Levels**: The game has 10 levels, each with increasing difficulty.
- **Dynamic Stick Length**: The stick length is adjustable and must be calculated based on the distance between platforms.
- **Collision Detection**: The game accurately detects collisions with platforms and manages character movement across platforms.
- **Score Tracking**: The player’s score is displayed and updated as they progress through each level.
- **Smooth Animation**: The game includes animations for stick extension and character movement.

## Requirements
- Java JDK 8 or higher
- JavaFX for graphical user interface (GUI)

## Installation
1. Download or clone the repository to your local machine.
2. Make sure you have Java JDK 8 or above installed.
3. Open the project in your favorite IDE (e.g., IntelliJ IDEA, Eclipse).
4. If using IntelliJ IDEA, ensure that the JavaFX library is set up in the project dependencies.

## How to Play
1. Start the game by running the main class.
2. Use the arrow keys or mouse (depending on the version) to control the stick figure.
3. Extend the stick to the correct length to land on the next platform.
4. Try to reach the highest level possible without falling.

## Controls
- **Left Arrow / A**: Move left.
- **Right Arrow / D**: Move right.
- **Spacebar**: Extend the stick to the required length.

## Game Design
- The game uses **Object-Oriented Programming (OOP)** principles for organization and scalability.
- A **Player** class manages the game state, including character position and score.
- A **Stick** class handles stick extension logic.
- A **Platform** class defines platform behavior, including the platform position and collision detection.

## Screenshots
Here are some screenshots of the game in action:

![Gameplay Screenshot](https://media.licdn.com/dms/image/v2/D4E2DAQGPJnqJaO40LQ/profile-treasury-image-shrink_160_160/profile-treasury-image-shrink_160_160/0/1727891236350?e=1735891200&v=beta&t=gsOWJLmNEH6qFIqqIvEhak2qP-sYznuw-KcFy6KCNRc)

*Example of the game interface*

![Level Progression Screenshot](https://media.licdn.com/dms/image/v2/D4E2DAQEDc5DpabmVfA/profile-treasury-image-shrink_1920_1920/profile-treasury-image-shrink_1920_1920/0/1727891267698?e=1735891200&v=beta&t=TetXxPDqvfdeirv49Tofw99J6hQkWAgnN01YroXoHi4)

*Level progression with dynamic difficulty*

## Contributing
Feel free to fork the repository, submit issues, or create pull requests if you would like to contribute improvements, features, or bug fixes.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgements
- Thanks to [JavaFX](https://openjfx.io/) for the GUI framework.
- Inspiration taken from classic platformer games.
