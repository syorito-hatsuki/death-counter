# Death Counter
<a title="Fabric Language Kotlin" href="https://minecraft.curseforge.com/projects/fabric-language-kotlin" target="_blank" rel="noopener noreferrer"><img style="display: block; margin-left: auto; margin-right: auto;" src="https://i.imgur.com/c1DH9VL.png" alt="" width="230" /></a>
<img src="https://i.imgur.com/iaETp3c.png" alt="" width="200" >
<img src="https://i.imgur.com/Ol1Tcf8.png" alt="" width="200" >

## Description
Death Counter is a server-side Minecraft Fabric mod to get info about how many players die, directly from player stats, so you always get an actual count. 
Adds support for [WebAPI](https://github.com/syorito-hatsuki/death-counter#server-side-api) and client-side [WebUI](https://github.com/syorito-hatsuki/death-counter#client-side-webui). You can also see it in-game using [commands](https://github.com/syorito-hatsuki/death-counter#commands-and-permission).
> Other mods only start counting from zero after it's been installed. This mod, on the other hand, can also report deaths from before this mod was installed.

## Commands and permission
|            Command           |OP|Permission|              Description           |
|------------------------------|----|----------|------------------------------------|
|`/deathcounter`|❌|`deathcounter.todo`|Get your own death count|
|`/deathcounter all`|❌|`deathcounter.todo`|Get the death count of all players|
|`/deathcounter [<playerName>]`|❌|`deathcounter.todo`|Get a specific player death count|

## Client config
```json5
{
    "ip": "0.0.0.0",  // Local WebUI address (Recommend keep default)
    "port": 3000,     // Local WebUI port (1024-49151)
    "msDelay": 5000   // Local WebUI refresh time in seconds (more is better)
}
```

## Web
### Client side WebUI
WebUI endpoint `http://0.0.0.0:3000`
|Request|Path|Description|
|:-:|-|-|
|GET|`/`|Return own death count|
|GET|`/{playerName}`|Return count of death by playerName|

### Server-side API
The API endpoint is `http://server-ip*:1540`
> *Address will be received from server-ip param in server.properties file

|Request|Path|Description|
|:-:|-|-|
|GET|`/`|Return Map<String, Int> object with all players|
|GET|`/{playerName}`|Return Int count of death by playerName|

## Tips
* [Adding counter in OBS](https://github.com/syorito-hatsuki/death-counter/blob/master/obs-integration.md)
