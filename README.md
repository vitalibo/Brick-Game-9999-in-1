## Brick Game 9999 in 1 

This project is simple simulator of famous Brick Game handheld retro gaming console. 

### Installation

`mvn clean package`

launch game

`java -jar brick-game-0.3.0-SNAPSHOT.jar`

### Usage

##### Control keys

- Default keys (independent of the game) 
    - `Key 'P'` - On/Off **P**ause
    - `Key 'S'` - On/Off **S**ound
    - `Key 'R'` - **R**eset the brick game
- Functional keys
    - `Key Up` - change speed in menu or specific in game
    - `Key Down` - change level in menu or specific in game
    - `Key Left` - decrease the number of game in menu or specific in game
    - `Key Right` - increase the number of game in menu or specific in game
    - `Key Space` - choose game in menu or specific in game

##### View

![](https://raw.githubusercontent.com/vitalibo/Brick-Game-9999-in-1/assets/docs/img/sR35V1.gif) ![](https://raw.githubusercontent.com/vitalibo/Brick-Game-9999-in-1/assets/docs/img/IFREtC.gif) ![](https://raw.githubusercontent.com/vitalibo/Brick-Game-9999-in-1/assets/docs/img/bUdlw7.gif) ![](https://raw.githubusercontent.com/vitalibo/Brick-Game-9999-in-1/assets/docs/img/x82Vbe.gif)

![](https://raw.githubusercontent.com/vitalibo/Brick-Game-9999-in-1/assets/docs/img/AwX9jY.gif) ![](https://raw.githubusercontent.com/vitalibo/Brick-Game-9999-in-1/assets/docs/img/ar3crF.gif) 

- Board panel `capacity [20x10]`
- Preview panel `capacity [4x4]`
- Score `max 999999`
- Speed `max 15`
- Level `max 15`
- Sound `on/off`
- Pause `on/off`

### Games
- `0001` Snake
- `0002` Race
- `0003` Tetris
- `0004` Shoot
- `0005` Tanks