# Death Counter

<a title="Fabric Language Kotlin" href="https://minecraft.curseforge.com/projects/fabric-language-kotlin" target="_blank" rel="noopener noreferrer"><img style="display: block; margin-left: auto; margin-right: auto;" src="https://i.imgur.com/c1DH9VL.png" alt="" width="230" /></a>
<img src="https://i.imgur.com/iaETp3c.png" alt="" width="200" >
<img src="https://i.imgur.com/Ol1Tcf8.png" alt="" width="200" >

## Description

Death Counter is a server-side Minecraft Fabric mod to get info about how many players die, directly from player stats, so you always get an actual count.
Adds support for client-side WebUI, useful for [streamers](https://github.com/syorito-hatsuki/death-counter/blob/master/obs-integration.md). You can also see it in-game using [commands](https://github.com/syorito-hatsuki/death-counter#commands-and-permission).
> Other mods only start counting from zero after it's been installed. This mod, on the other hand, can also report deaths from before this mod was installed.

## Commands and permission

### Client-side commands

| Command                      | OP  | Permission | Description                                                            |
|------------------------------|-----|------------|------------------------------------------------------------------------|
| `/dcc`                       | ❌   | `none`     | Get your own death count                                               |
| `/dcc warning <boolean>`     | ❌   | `none`     | Enable/Disable warning message when mod unavailable on server-side     |
| `/dcc webuinotify <boolean>` | ❌   | `none`     | Enable/Disable web client address toast notification on login to world |

### Server-side commands

| Command               | OP  | Permission | Description                        |
|-----------------------|-----|------------|------------------------------------|
| `/dcs top`            | ❌   | `none`     | Get top of dead inside :D          |
| `/dcs <page>`         | ❌   | `none`     | Get the death count of all players |
| `/dcs [<playerName>]` | ❌   | `none`     | Get a specific player death count  |

## Client config

```json5
{
  // Warning message when mod unavailable on server-side
  "showWarning": true,
  // Web client toast notification
  "showToastNotification": {
    "disable": false,
    "delay": 5000
  },
  "webSetup": {
    // Local WebUI address
    "localAddress": "0.0.0.0",
    // Local WebUI port (1024-49151)
    "servicePort": 1540,
    // Local WebUI refresh time in milliseconds (more is better)
    "refreshDelayMs": 5000
  }
}
```