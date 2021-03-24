# ttsdbko
TTS (Tabletop Simulator) console deck builder.

Pass directory location as the first argument

Use file in directory like

```
6 card.png
2 second_card.png
0 back.png
```

0 index - back card.

## Example
/cards

| builder

| hello.png

builder: `15 hello.png`

./gradlew run --args="/cards/" 
